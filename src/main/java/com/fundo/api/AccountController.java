package com.fundo.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fundo.exception.*;
import com.fundo.models.Account;
import com.fundo.models.Transaction;
import com.fundo.models.User;
import com.fundo.requests.BuyRequest;
import com.fundo.requests.DepositRequest;
import com.fundo.requests.SellRequest;
import com.fundo.requests.WithdrawRequest;
import com.fundo.services.AccountService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final MongoTemplate mongoTemplate;

    private final AccountService accountService;

    private final static Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    public AccountController(MongoTemplate mongoTemplate, AccountService accountService) {
        this.mongoTemplate = mongoTemplate;
        this.accountService = accountService;
    }

    @GetMapping("create/")
    public ResponseEntity<Account> create(@RequestParam("ownerName") String username)
            throws MissingUserException, AccountCreationException {
        User user = this.mongoTemplate.findOne(Query.query(Criteria.where("username").is(username)), User.class);
        if (user == null)
            throw new MissingUserException(username);
        return new ResponseEntity<>(accountService.create(user), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Account> get(@PathVariable ("id" )String accountId) throws missingAccountException {
        return new ResponseEntity<>(accountService.getAccount(accountId), HttpStatus.OK);
    }

    @PostMapping("deposit/")
    public ResponseEntity<Transaction> deposit(@Valid @RequestBody DepositRequest depositRequest)
            throws missingAccountException{
        Transaction transaction = accountService.deposit(depositRequest.accountId, depositRequest.usdAmount);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @PostMapping("withdraw/")
    public ResponseEntity<Transaction> withdraw(@RequestBody WithdrawRequest withdrawRequest)
            throws InsufficientFundsException, missingAccountException {
        Transaction transaction = accountService.withdraw(withdrawRequest.accountId, withdrawRequest.usdAmount);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @PostMapping("buy/")
    public ResponseEntity<Transaction> buy(@RequestBody BuyRequest buyRequest)
            throws MarketConnectionException, InsufficientFundsException, invalidMarketRequestException, missingAccountException, JsonProcessingException {
        Transaction transaction = accountService.buy(buyRequest.accountId, buyRequest.symbol, buyRequest.usdAmount);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @PostMapping("sell/")
    public ResponseEntity<Transaction> sell(@RequestBody SellRequest sellRequest)
            throws MarketConnectionException, InsufficientFundsException, invalidMarketRequestException, missingAccountException, JsonProcessingException {
        Transaction transaction = accountService.sell(sellRequest.accountId, sellRequest.symbol, sellRequest.stockAmount);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }
}
