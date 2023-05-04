package com.fundo.api;

import com.fundo.config.AppConfig;
import com.fundo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

	private MongoTemplate mongoTemplate;

	@Autowired
	public UserController(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Autowired
	private AppConfig appDetails;

	@GetMapping("/")
	public String index() {
		return "Hello";
	}

	@GetMapping("signup/")
	public ResponseEntity<String> signup(@RequestParam("username") String username) {
		User user = this.mongoTemplate.findOne(Query.query(Criteria.where("username").is(username)),
				User.class);

		if (user != null) {
			return new ResponseEntity<>(String.format("Username %s already taken (%s)", username, user.getId()),
					HttpStatus.CONFLICT);
		}

		User newUser = new User(username);
		this.mongoTemplate.insert(newUser);
		return new ResponseEntity<>("Great Success!", HttpStatus.OK);
	}

	@GetMapping("login/")
	public String login() {
		return "Welcome!";
	}
}
