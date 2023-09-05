# FXyou  ![Build status](https://github.com/apssouza22/trading-system/actions/workflows/ci.yml/badge.svg) [![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=apssouza22_trading-system&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=apssouza22_trading-system) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=apssouza22_trading-system&metric=alert_status)](https://sonarcloud.io/dashboard?id=apssouza22_trading-system)
Simple High frequency trading with backtesting simulation and live trading engine written in Java.

## Disclaimer
This software is for educational purposes only. Do not risk money which you are afraid to lose. USE THE SOFTWARE AT YOUR OWN RISK. THE AUTHORS AND ALL AFFILIATES ASSUME NO RESPONSIBILITY FOR YOUR TRADING RESULTS.

Always start by running a trading bot in Dry-run and do not engage money before you understand how it works and what profit/loss you should expect.

<a href="https://www.buymeacoffee.com/apssouza"><img src="https://miro.medium.com/max/654/1*rQv8JgstmK0juxP-Kb4IGg.jpeg"></a>

## Features
* Persistence: Persistence is achieved through the H2
* Backtesting: Run a simulation of your buy/sell strategy.
* Stop order/ Limit order management
* Broker reconciliation
* Multi position for currency pair
* Daily summary of profit/loss: Provide a daily summary of your profit/loss.
* Performance status report: Provide a performance status of your current trades.

# Tech details
* Java 17
* Event driven architecture
* Maven multi-modules
* Java modularization
* Packaging by domain
* Domain driven design (DDD)
* BDD with cucumber
* Multiple design patterns (Strategy, factory, builder, Observer...)
* Cross-cutting concern

![Alt text](assets/trading-system.png?raw=true "Trading system")

## How to run
```
mvn package && \
java -jar runner/target/runner-1.0-SNAPSHOT.jar
``` 

### Using Docker

```
docker build . -t trading-engine
docker run --rm -p 8080:8080 trading-engine
```

