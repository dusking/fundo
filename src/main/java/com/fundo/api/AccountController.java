package com.fundo.api;

import com.fundo.exception.InsufficientFundsException;
import com.fundo.exception.missingAccountException;
import com.fundo.models.Account;
import com.fundo.models.Transaction;
import com.fundo.models.User;
import com.fundo.requests.BuyRequest;
import com.fundo.requests.DepositRequest;
import com.fundo.requests.SellRequest;
import com.fundo.requests.WithdrawRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.io.IOException;


@RestController
@RequestMapping("/api/account")
public class AccountController {

    private MongoTemplate mongoTemplate;

    @Autowired
    public AccountController(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    Account getAccount(String accountId) throws missingAccountException{
        Account account = this.mongoTemplate.findOne(Query.query(Criteria.where("id").is(accountId)),
                Account.class);
        if (account == null) {
            throw new missingAccountException(accountId);
        }
        return account;
    }

    @GetMapping("create/")
    public ResponseEntity<String> create(@RequestParam("ownerName") String username) {
        User user = this.mongoTemplate.findOne(Query.query(Criteria.where("username").is(username)),
                User.class);
        if (user == null) {
            return new ResponseEntity<>(String.format("Username %s does not exists", username), HttpStatus.NOT_FOUND);
        }
        System.out.println("Creating a new account for user");
        Account account = new Account(user.getId());
        this.mongoTemplate.insert(account);
        return new ResponseEntity<>("Great Success!", HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public Account get(@PathVariable ("id" )String accountId) throws missingAccountException {
        return getAccount(accountId);
    }

    @PostMapping("deposit/")
    public ResponseEntity<Account> deposit(@RequestBody DepositRequest depositRequest) {
        Transaction transaction = new Transaction();
        transaction.setDeposit(depositRequest.accountId, depositRequest.usdAmount);
        try {
            Account account = getAccount(depositRequest.accountId);
            account.deposit(depositRequest.usdAmount);
            transaction.setSuccess();
            this.mongoTemplate.save(transaction);
            this.mongoTemplate.save(account);
            return new ResponseEntity<>(account, HttpStatus.OK);
        } catch(Exception e) {
            transaction.setFailure();
            this.mongoTemplate.save(transaction);
            System.out.printf("Transaction Failed %s", transaction.getData());
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("withdraw/")
    public ResponseEntity<Transaction> withdraw(@RequestBody WithdrawRequest withdrawRequest)  {
        Transaction transaction = new Transaction();
        transaction.setWithdraw(withdrawRequest.accountId, withdrawRequest.usdAmount);
        try {
            Account account = getAccount(withdrawRequest.accountId);
            account.withdraw(withdrawRequest.usdAmount);
            transaction.setSuccess();
            this.mongoTemplate.save(transaction);
            this.mongoTemplate.save(account);
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        } catch(Exception e) {
            transaction.setFailure();
            this.mongoTemplate.save(transaction);
            System.out.printf("Transaction Failed %s", transaction.getData());
            return new ResponseEntity<>(transaction, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("buy/")
    public ResponseEntity<Transaction> buy(@RequestBody BuyRequest buyRequest){
        Transaction transaction = new Transaction();
        transaction.setBuy(buyRequest.accountId, buyRequest.symbol, buyRequest.usdAmount);
        try {
            int currentPrice = 10;
            double stockAmount = buyRequest.usdAmount / currentPrice;
            transaction.setStockAmount(stockAmount);
            Account account = getAccount(buyRequest.accountId);
            account.buy(buyRequest.symbol, stockAmount, buyRequest.usdAmount);
            transaction.setSuccess();
            this.mongoTemplate.save(transaction);
            this.mongoTemplate.save(account);
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        } catch(Exception e) {
            transaction.setFailure();
            this.mongoTemplate.save(transaction);
            System.out.printf("Transaction Failed %s", transaction.getData());
            return new ResponseEntity<>(transaction, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("sell/")
    public ResponseEntity<Transaction> sell(@RequestBody SellRequest sellRequest) {
        Transaction transaction = new Transaction();
        transaction.setBuy(sellRequest.accountId, sellRequest.symbol, sellRequest.stockAmount);
        try {
            int currentPrice = 10;
            double usdAmount = sellRequest.stockAmount * currentPrice;
            transaction.setUsdAmount(usdAmount);
            Account account = getAccount(sellRequest.accountId);
            account.sell(sellRequest.symbol, sellRequest.stockAmount, usdAmount);
            transaction.setSuccess();
            this.mongoTemplate.save(transaction);
            this.mongoTemplate.save(account);
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        } catch(Exception e) {
            transaction.setFailure();
            this.mongoTemplate.save(transaction);
            System.out.printf("Transaction Failed %s", transaction.getData());
            return new ResponseEntity<>(transaction, HttpStatus.EXPECTATION_FAILED);
        }
    }

    public static void getStockQuote(String symbol) {
        String rapidApiHost = "twelve-data1.p.rapidapi.com";
        String RapidApiEndpoint = "https://twelve-data1.p.rapidapi.com/price";
        String rapidApiKey = "oBvV4zrXWCmshhQrYwgfKYLtzPIEp1nfsjBjsnLNYBQDnQft8M";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(RapidApiEndpoint + "?symbol=" + symbol + "&format=json&outputsize=30"))
                .header("X-RapidAPI-Key", rapidApiKey)
                .header("X-RapidAPI-Host", rapidApiHost)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(response.body());
    }
}
