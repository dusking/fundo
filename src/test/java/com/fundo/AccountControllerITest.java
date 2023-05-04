package com.fundo;
import com.fundo.models.Account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountControllerITest {

	@Autowired
	private TestRestTemplate template;

    @Test
    public void getHello() throws Exception {
        String path = "/api/account/645285bb6b663c284296b896";
        ResponseEntity<Account> response = template.getForEntity(path, Account.class);
        Account account= response.getBody();
        System.out.println(account.getId());
        assertThat(account.getId()).isEqualTo("645285bb6b663c284296b896");
    }
}