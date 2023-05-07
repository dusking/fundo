package com.fundo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fundo.enums.TransactionStatus;
import com.fundo.exception.InsufficientFundsException;
import com.fundo.exception.MarketConnectionException;
import com.fundo.exception.missingAccountException;
import com.fundo.models.Account;
import com.fundo.models.Transaction;
import com.fundo.services.AccountService;
import com.fundo.services.ExchangeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Assertions;



public class AccountServiceTest {

    AccountService accountServiceTest;

    MongoTemplate mongoTemplate;

    ExchangeService exchangeService;

    String testOwnerId = "645169548c53b95cf8a7918e";

    String testAccountId = "645285bb6b663c284296b896";

    double testQuoteValue = 10;

    @BeforeEach
    public void setup() throws MarketConnectionException, JsonProcessingException {
        mongoTemplate = mock(MongoTemplate.class);
        exchangeService = mock(ExchangeService.class);
        accountServiceTest = new AccountService(mongoTemplate, exchangeService);
        Account testAccount = new Account(testOwnerId, testAccountId);
        when(mongoTemplate.findOne(Mockito.any(), Mockito.eq(Account.class))).thenReturn(testAccount);
        when(exchangeService.getStockQuote(Mockito.any())).thenReturn(testQuoteValue);
    }

    @Test
    public void testGetAccount() throws missingAccountException {
        Account account = accountServiceTest.getAccount(testAccountId);
        Assertions.assertEquals(testOwnerId, account.getOwnerId());
    }

    @Test
    public void testDepositUsdToAccount() throws Exception {
        double depositUsdAmountTest = 100;
        Transaction transaction = accountServiceTest.deposit(testAccountId, depositUsdAmountTest);
        Account account = accountServiceTest.getAccount(testAccountId);
        Assertions.assertEquals(depositUsdAmountTest, account.getUsdAmount());
        Assertions.assertEquals(TransactionStatus.SUCCESS, transaction.getStatus());
    }

    @Test
    public void testWithdrawUsdFromAccount() throws Exception {
        double depositUsdAmountTest = 100;
        double withdrawUsdAmountTest = 100;
        accountServiceTest.deposit(testAccountId, depositUsdAmountTest);
        Transaction transaction = accountServiceTest.withdraw(testAccountId, withdrawUsdAmountTest);
        Account account = accountServiceTest.getAccount(testAccountId);
        Assertions.assertEquals(depositUsdAmountTest - withdrawUsdAmountTest, account.getUsdAmount());
        Assertions.assertEquals(TransactionStatus.SUCCESS, transaction.getStatus());
    }

    @Test
    public void testWithdrawUsdFromAccountInsufficientFunds() throws Exception {
        double depositUsdAmountTest = 100;
        double withdrawUsdAmountTest = 200;
        accountServiceTest.deposit(testAccountId, depositUsdAmountTest);
        InsufficientFundsException thrown = Assertions.assertThrows(InsufficientFundsException.class, () -> {
            Transaction transaction = accountServiceTest.withdraw(testAccountId, withdrawUsdAmountTest);
            Assertions.assertEquals(TransactionStatus.FAILURE, transaction.getStatus());
        });
        Account account = accountServiceTest.getAccount(testAccountId);
        Assertions.assertEquals(depositUsdAmountTest, account.getUsdAmount());
    }

    @Test
    public void testBuySymbolWithValidValues() throws Exception {
        String testSymbol = "GOOG";
        double depositUsdAmountTest = 100;
        double testBuyUsdAmount = 100;
        accountServiceTest.deposit(testAccountId, depositUsdAmountTest);
        Account account = accountServiceTest.getAccount(testAccountId);
        Transaction transaction = accountServiceTest.buy(testAccountId, testSymbol, testBuyUsdAmount);
        Assertions.assertEquals(testBuyUsdAmount / testQuoteValue, account.getHolding(testSymbol));
        Assertions.assertEquals(TransactionStatus.SUCCESS, transaction.getStatus());
    }

    @Test
    public void testBuySymbolWithInsufficientFunds() throws Exception {
        String testSymbol = "GOOG";
        double depositUsdAmountTest = 10;
        double testBuyUsdAmount = 100;
        accountServiceTest.deposit(testAccountId, depositUsdAmountTest);
        InsufficientFundsException thrown = Assertions.assertThrows(InsufficientFundsException.class, () -> {
            Transaction transaction = accountServiceTest.buy(testAccountId, testSymbol, testBuyUsdAmount);
            Assertions.assertEquals(TransactionStatus.FAILURE, transaction.getStatus());
        });
        Assertions.assertEquals("Failed to buy " + testSymbol, thrown.getMessage());
    }

    @Test
    public void testSellSymbolWithValidValues() throws Exception {
        String testSymbol = "GOOG";
        double testStockAmount = 2;
        double testSellStockAmount = 1;
        Account account = accountServiceTest.getAccount(testAccountId);
        account.addHolding(testSymbol, testStockAmount);
        Transaction transaction = accountServiceTest.sell(testAccountId, testSymbol, testSellStockAmount);
        Assertions.assertEquals(testStockAmount - testSellStockAmount, account.getHolding(testSymbol));
        Assertions.assertEquals(TransactionStatus.SUCCESS, transaction.getStatus());
    }

    @Test
    public void testSellSymbolWithInsufficientStockAmount() throws Exception {
        String testSymbol = "GOOG";
        double testStockAmount = 2;
        double testSellStockAmount = 3;
        Account account = accountServiceTest.getAccount(testAccountId);
        account.addHolding(testSymbol, testStockAmount);
        InsufficientFundsException thrown = Assertions.assertThrows(InsufficientFundsException.class, () -> {
            Transaction transaction = accountServiceTest.sell(testAccountId, testSymbol, testSellStockAmount);
            Assertions.assertEquals(TransactionStatus.FAILURE, transaction.getStatus());
        });
        Assertions.assertEquals(testStockAmount, account.getHolding(testSymbol));
    }
}
