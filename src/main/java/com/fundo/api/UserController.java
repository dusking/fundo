package com.fundo.api;

import com.fundo.exception.missingUserException;
import com.fundo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

	private MongoTemplate mongoTemplate;

	@Value("${app.name}")
	private String appName;

	@Autowired
	public UserController(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	User getUser(String username) throws missingUserException {
		User user = this.mongoTemplate.findOne(Query.query(Criteria.where("username").is(username)),
				User.class);
		if (user == null) {
			throw new missingUserException(username);
		}
		return user;
	}

	@GetMapping("/")
	public String index() {
		return "Welcome to " + appName;
	}

	@GetMapping("{username}")
	public User get(@PathVariable("username" )String username) throws missingUserException {
		return getUser(username);
	}

	@GetMapping("signup/")
	public ResponseEntity<String> signup(@RequestParam("username") String username) {
		try {
			getUser(username);
			return new ResponseEntity<>(String.format("Username %s already exists", username), HttpStatus.CONFLICT);
		} catch(Exception e) {
			User newUser = new User(username);
			this.mongoTemplate.insert(newUser);
			return new ResponseEntity<>("Great Success!", HttpStatus.OK);
		}
	}

	@GetMapping("login/")
	public String login() {
		return "Welcome!";
	}
}
