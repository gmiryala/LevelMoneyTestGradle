# LevelMoneyTestGradle
Used Gradle, Spring Boot, Java 1.8

build jar file using below in gradle command prompt
clean build

jar will be generated at build\distributions\

Go to jar location execute commands

1. For getting output for ALLTransactions

java -jar GopiLevelMoneyTest-1.0

2. For excluding Donut transactions

java -jar GopiLevelMoneyTest-1.0 ignore-donuts

3. For including projected transactions

java -jar GopiLevelMoneyTest-1.0 crystal-ball

4. For excluding Donut transactions and including projected transactions

java -jar GopiLevelMoneyTest-1.0 crystal-ball ignore-donuts
or
java -jar GopiLevelMoneyTest-1.0 ignore-donuts crystal-ball


