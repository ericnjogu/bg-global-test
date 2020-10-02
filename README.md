# Technical Test 2.0
## Transaction list service endpoints
- [Transactions for account `savings-kids-john`](http://localhost:8080/account/transactions/savings-kids-john/list)
- [Transactions for account `savings-kids-john`, type `SEPA`](href="http://localhost:8080/account/transactions/savings-kids-john/type/SEPA/list")
- [Transactions total for account savings-kids-john, type SEPA](href="http://localhost:8080/account/savings-kids-john/type/SEPA/total")

## Running application
`mvn jetty:run`

## Running tests
`mvn tests`

Some tests require an internet connection to access the OPB service

## TODO
- fix spring security
- add swagger endpoint
- map index.html landing page