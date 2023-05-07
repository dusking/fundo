package com.fundo;

import com.fundo.enums.TransactionStatus;
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

    @BeforeEach
    public void setup() {
        mongoTemplate = mock(MongoTemplate.class);
        exchangeService = mock(ExchangeService.class);
        accountServiceTest = new AccountService(mongoTemplate, exchangeService);

        Account testAccount = new Account(testOwnerId, testAccountId);
        testAccount.addHolding("AAPL", 1.5);
        when(mongoTemplate.findOne(Mockito.any(), Mockito.eq(Account.class))).thenReturn(testAccount);
    }

    @Test
    public void testGetAccount() throws missingAccountException {
        Account account = accountServiceTest.getAccount(testAccountId);
        Assertions.assertEquals(account.getOwnerId(), testOwnerId);
    }

    @Test
    public void testDepositUsdToAccount() throws Exception {
        double depositUsdAmountTest = 100;
        Transaction transaction = accountServiceTest.deposit(testAccountId, depositUsdAmountTest);
        Account account = accountServiceTest.getAccount(testAccountId);
        Assertions.assertEquals(account.getUsdAmount(), depositUsdAmountTest);
        Assertions.assertEquals(transaction.getStatus(), TransactionStatus.SUCCESS);
    }
}
