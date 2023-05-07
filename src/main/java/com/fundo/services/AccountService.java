package com.fundo.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fundo.exception.*;
import com.fundo.models.Account;
import com.fundo.models.Transaction;
import com.fundo.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;


@Service
public class AccountService {
    private final MongoTemplate mongoTemplate;

    private final ExchangeService exchangeService;

    private final static Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    public AccountService(MongoTemplate mongoTemplate, ExchangeService exchangeService) {
        this.mongoTemplate = mongoTemplate;
        this.exchangeService = exchangeService;
    }

    public Account create(User user) throws AccountCreationException {
        logger.info("Creating a new account for user " + user.getId());
        if (this.mongoTemplate.findOne(Query.query(Criteria.where("ownerId").is(user.getId())),
                Account.class) != null) {
            logger.warn("User `" + user.getName() + "` already have an account");
            throw new AccountCreationException("Owner already have an account");
        }
        Account account = new Account(user.getId());
        this.mongoTemplate.insert(account);
        return account;
    }

    public Account getAccount(String accountId) throws missingAccountException {
        Account account = this.mongoTemplate.findOne(Query.query(Criteria.where("id").is(accountId)),
                Account.class);
        if (account == null) {
            throw new missingAccountException(accountId);
        }
        return account;
    }

    public Transaction deposit(String accountId, double usdAmount) throws missingAccountException {
        Transaction transaction = new Transaction();
        transaction.setDeposit(accountId, usdAmount);
        try {
            // ToDo: Implement db transaction
            Account account = getAccount(accountId);
            account.addUsdAmount(usdAmount);
            transaction.setSuccess();
            this.mongoTemplate.save(transaction);
            this.mongoTemplate.save(account);
            return transaction;
        } catch(Exception e) {
            transaction.setFailure();
            this.mongoTemplate.save(transaction);
            logger.error("Transaction Failed", e);
            throw e;
        }
    }

    public Transaction withdraw(String accountId, double usdAmount)
            throws missingAccountException, InsufficientFundsException {
        Transaction transaction = new Transaction();
        transaction.setWithdraw(accountId, usdAmount);
        try {
            // ToDo: Implement db transaction
            Account account = getAccount(accountId);
            if (usdAmount > account.getUsdAmount())
                throw new InsufficientFundsException("Failed to withdraw");
            account.addUsdAmount(-usdAmount);
            transaction.setSuccess();
            this.mongoTemplate.save(transaction);
            this.mongoTemplate.save(account);
            return transaction;
        } catch(Exception e) {
            transaction.setFailure();
            this.mongoTemplate.save(transaction);
            logger.error("Transaction Failed", e);
            throw e;
        }
    }

    public Transaction buy(String accountId, String symbol, double usdAmount)
            throws missingAccountException, InsufficientFundsException, MarketConnectionException, invalidMarketRequestException, JsonProcessingException {
        Transaction transaction = new Transaction();
        transaction.setBuy(accountId, symbol, usdAmount);
        try {
            // ToDo: Implement db transaction
            double currentPrice = exchangeService.getStockQuote(symbol);
            double stockAmount = usdAmount / currentPrice;
            transaction.setStockAmount(stockAmount);
            Account account = getAccount(accountId);
            if (account.getUsdAmount() < usdAmount)
                throw new InsufficientFundsException("Failed to buy " + symbol);
            account.addHolding(symbol, stockAmount);
            account.addUsdAmount(-usdAmount);
            exchangeService.buy(symbol, stockAmount);
            transaction.setSuccess();
            this.mongoTemplate.save(transaction);
            this.mongoTemplate.save(account);
            return transaction;
        } catch(Exception e) {
            transaction.setFailure();
            this.mongoTemplate.save(transaction);
            logger.error("Transaction Failed", e);
            throw e;
        }
    }

    public Transaction sell(String accountId, String symbol, double stockAmount)
            throws missingAccountException, InsufficientFundsException, MarketConnectionException, invalidMarketRequestException, JsonProcessingException {
        Transaction transaction = new Transaction();
        transaction.setSell(accountId, symbol, stockAmount);
        try {
            // ToDo: Implement db transaction
            double currentPrice = exchangeService.getStockQuote(symbol);
            double usdAmount = stockAmount * currentPrice;
            transaction.setUsdAmount(usdAmount);
            Account account = getAccount(accountId);
            double currentAmount = account.getHolding(symbol);
            if (currentAmount < stockAmount)
                throw new InsufficientFundsException("Failed to sell " + symbol);
            account.addHolding(symbol, -stockAmount);
            account.addUsdAmount(usdAmount);
            exchangeService.sell(symbol, stockAmount);
            transaction.setSuccess();
            this.mongoTemplate.save(transaction);
            this.mongoTemplate.save(account);
            return transaction;
        } catch(Exception e) {
            transaction.setFailure();
            this.mongoTemplate.save(transaction);
            logger.error("Transaction Failed", e);
            throw e;
        }
    }
}
