# wayapay

## DB
MySQL

## application.properties
**These values should be changed**
```bash
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/wayapay
spring.datasource.username=root
spring.datasource.password=root
```


## installation
```bash
mvn clean install
```

on the first run 1 sample DB record is created (User)

## user details
```bash
username: john
password: password
```

## swagger url
http://localhost:8080/swagger-ui/#/


