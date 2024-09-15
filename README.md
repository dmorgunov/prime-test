### Installation Guide

1. Compile /shared

```
cd services/shared
mvn clean compile
```
2. Create docker container with Kafka (Docker version: 4.34.0 (165256))
```
docker compose up kafka -d
```
3. Run producer, consumer apps

### API
#### Producer API 

##### Get generation status
- request
 ```
GET: localhost:8082/producer/status
```
- response
```
{
    "generated": 18,
    "limit": 100
}
```

##### Download CSV with generated numbers
- request
 ```
GET: localhost:8082/producer/download/csv
```
- response
```
[CSV file]
"Generated ints"
"1184694171"
"186922398"
...
"677940092"

```

#### Consumer API 
##### Download CSV with prime numbers
- request
 ```
GET: localhost:8081/consumer/download/csv
```
- response
```
[CSV file]
"Prime ints"
"1072681109"
"1661854163"
...
"1987501991"

```

