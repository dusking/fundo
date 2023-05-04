# FundO Application

A web application written in Java 18.0.2, using Spring Boot v3.0.0 with Tomcat as the servlet container.
The app uses Spring Data MongoDB.

## Application Functionality
- [x] The application is designed to be a fund account management service
- [x] Managing account actions: 
  - Deposit
  - Withdraw
  - Buy
  - Sell
- [x] Creating transaction for service calls

## Sample Usage
```agsl
# Get account
curl http://localhost:8080/api/account/645285bb6b663c284296b896
# Deposit account
curl -X POST http://localhost:8080/api/account/deposit/ -H 'content-type: application/json' -d '{"accountId":"645285bb6b663c284296b896", "usdAmount":500}'
# Withdraw account
curl -X POST http://localhost:8080/api/account/withdraw/ -H 'content-type: application/json' -d '{"accountId":"645285bb6b663c284296b896", "usdAmount":100}'
# Buy symbol
curl -X POST http://localhost:8080/api/account/buy/ -H 'content-type: application/json' -d '{"accountId":"645285bb6b663c284296b896", "symbol": "AAPL", "usdAmount":100}'
# Sell symbol
curl -X POST http://localhost:8080/api/account/sell/ -H 'content-type: application/json' -d '{"accountId":"645285bb6b663c284296b896", "symbol": "AAPL", "usdAmount":100}'
```

## Running demo
To run the application need to create env vars for the mongo db username and password

## Todo
* Implement basic Auth. There is a basic User Controller that is not really being used.
* Implement application properties in addition to env vars.
* store secret db password.
* Implement logging.
