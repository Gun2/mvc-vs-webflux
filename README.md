# Spring MVC Rest VS WEB Flux Rest
해당 저장소는 멀티스레드 방식으로 구현된 Rest API와 비동기 논블록킹 방식으로 구현된 Rest API의 성능을 비교하는 것을 목표로 합니다.

# 측정 비교 대상
- 멀티 스레드 방식의 REST API
> multi-thread-api-app <br/>http://localhost:8081
- 가상 스레드를 사용한 멀티 스레드 방식의 REST API
> virtual-multi-thread-api-app <br/>http://localhost:8082
- 논블록킹 방식으로 구현된 Rest API (블록킹 DB)
> reactor-api-block-db-app <br/>http://localhost:8083
- 논블록킹 방식으로 구현된 Rest API (논 블록킹 DB)
> reactor-api-non-block-db-app <br/>http://localhost:8084

# 측정 시나리오
요청을 반복하는 가상의 사용자들을 만들고 일정 시간마다 단계가 상승하면 가상의 사용자들을 추가하면서 성능을 측정합니다.
> 초기 100명의 가상의 사용자가 존재하고 5초마다 100명씩 증가하여 총 50초간 측정을 진행하고 5초마다 수치를 기록합니다.
> 
> 성능 측정은 `measurement-app` 앱을 통해 측정을 진행합니다.

# 측정 항목
각 측정 비교 대상들의 아래 형태의 API를 측정합니다.
- DB 조회 API
- DB 쓰기 API
- 가벼운 기능의 API
- 무거운 기능의 API
> **측정 정보 :** 최소 요청 시간, 최대 요청 시간, 평균 요청 시간, 성공 요청 횟수, 실패 요청 횟수


# 측정 환경
측정 앱과 대상 앱 사이의 영향도를 최소화 하기 위해 Google Cloud Platform의 VM 인스턴스 2대를 사용합니다.

## 측정 VM
성능 측정 모듈이 동작할 VM
- OS: RockyOS 9
- CPU: 2 vCPU(1 Core) Intel(R) Xeon(R) CPU @ 2.20GHz
- Memory: 2GB

## 대상 VM
측정 대상 서비스가 동작할 VM
- OS: RockyOS 9
- CPU: 2 vCPU(1 Core) Intel(R) Xeon(R) CPU @ 2.20GHz
- Memory: 2GB

## 관련 프레임워크 및 라이브러리
- Spring boot 3
- Spring Shell
- Web MVC
- Web Flux

## JDK VERSION
-  21

# 측정 방법
측정을 수행하였던 방법을 기술하며 기술된 모든 커맨드는 project의 최상위 디렉터리에서 진행됩니다.
## Build
측정 대상들과 측정을 진행하는 앱을 빌드합니다.
```shell
sh gradlew clean app:multi-thread-api-app:build app:virtual-multi-thread-api-app:build  app:reactor-api-block-db-app:build  app:reactor-api-non-block-db-app:build app:measurement-app:build -x test 
```

## 멀티 스레드 방식 API 측정
멀티 스레드 방식의 읽기/쓰기 API에 대한 성능 측정 방식입니다.
### 구동
```shell
java -jar app/multi-thread-api-app/build/libs/multi-thread-api-app-1.0.jar
```
### 읽기 API 측정
```shell
java -jar app/measurement-app/build/libs/measurement-app-1.0.jar read -u http://{target_address}:8081 -t 50 -i 50 -d 10000 -p 10 -o multi-thread-read-api-output.json
```
### 쓰기 API 측정
```shell
java -jar app/measurement-app/build/libs/measurement-app-1.0.jar create -u http://{target_address}:8081 -t 50 -i 50 -d 10000 -p 10 -o multi-thread-create-api-output.json
```
### 가벼운 API 측정
```shell
java -jar app/measurement-app/build/libs/measurement-app-1.0.jar simple -u http://{target_address}:8081 -t 50 -i 50 -d 10000 -p 10 -o multi-thread-simple-api-output.json
```
### 무거운 API 측정
```shell
java -jar app/measurement-app/build/libs/measurement-app-1.0.jar complex -u http://{target_address}:8081 -t 50 -i 50 -d 10000 -p 10 -o multi-thread-complex-api-output.json
```

## 가상 스레드 방식 API 측정
가상 스레드 방식의 읽기/쓰기 API에 대한 성능 측정 방식입니다.
### 구동
```shell
java -jar app/virtual-multi-thread-api-app/build/libs/virtual-multi-thread-api-app-1.0.jar
```
### 읽기 API 측정
```shell
java -jar app/measurement-app/build/libs/measurement-app-1.0.jar read -u http://{target_address}:8082 -t 50 -i 50 -d 10000 -p 10 -o virtual-multi-thread-read-api-output.json
```
### 쓰기 API 측정
```shell
java -jar app/measurement-app/build/libs/measurement-app-1.0.jar create -u http://{target_address}:8082 -t 50 -i 50 -d 10000 -p 10 -o virtual-multi-thread-create-api-output.json
```
### 가벼운 API 측정
```shell
java -jar app/measurement-app/build/libs/measurement-app-1.0.jar simple -u http://{target_address}:8082 -t 50 -i 50 -d 10000 -p 10 -o virtual-multi-thread-simple-api-output.json
```
### 무거운 API 측정
```shell
java -jar app/measurement-app/build/libs/measurement-app-1.0.jar complex -u http://{target_address}:8082 -t 50 -i 50 -d 10000 -p 10 -o virtual-multi-thread-complex-api-output.json
```

## 논블록킹 방식(with blocking DB) API 측정
논블록킹 방식(blocking DB) 읽기/쓰기 API에 대한 성능 측정 방식입니다.
### 구동
```shell
java -jar app/reactor-api-block-db-app/build/libs/reactor-api-block-db-app-1.0.jar
```
### 읽기 API 측정
```shell
java -jar app/measurement-app/build/libs/measurement-app-1.0.jar read -u http://{target_address}:8083 -t 50 -i 50 -d 10000 -p 10 -o reactor-read-api-block-db-output.json
```
### 쓰기 API 측정
```shell
java -jar app/measurement-app/build/libs/measurement-app-1.0.jar create -u http://{target_address}:8083 -t 50 -i 50 -d 10000 -p 10 -o reactor-create-api-block-db-output.json
```
### 가벼운 API 측정
```shell
java -jar app/measurement-app/build/libs/measurement-app-1.0.jar simple -u http://{target_address}:8083 -t 50 -i 50 -d 10000 -p 10 -o reactor-simple-api-block-db-output.json
```
### 무거운 API 측정
```shell
java -jar app/measurement-app/build/libs/measurement-app-1.0.jar complex -u http://{target_address}:8083 -t 50 -i 50 -d 10000 -p 10 -o reactor-complex-api-block-db-output.json
```

## 논블록킹 방식(with non blocking DB) API 측정
논블록킹 방식(non blocking DB) 읽기/쓰기 API에 대한 성능 측정 방식입니다.
### 구동
```shell
java -jar app/reactor-api-non-block-db-app/build/libs/reactor-api-non-block-db-app-1.0.jar
```
### 읽기 API 측정
```shell
java -jar app/measurement-app/build/libs/measurement-app-1.0.jar read -u http://{target_address}:8084 -t 50 -i 50 -d 10000 -p 10 -o reactor-read-api-non-block-db-output.json
```
### 쓰기 API 측정
```shell
java -jar app/measurement-app/build/libs/measurement-app-1.0.jar create -u http://{target_address}:8084 -t 50 -i 50 -d 10000 -p 10 -o reactor-create-api-non-block-db-output.json
```
### 가벼운 API 측정
```shell
java -jar app/measurement-app/build/libs/measurement-app-1.0.jar simple -u http://{target_address}:8084 -t 50 -i 50 -d 10000 -p 10 -o reactor-simple-api-non-block-db-output.json
```
### 무거운 API 측정
```shell
java -jar app/measurement-app/build/libs/measurement-app-1.0.jar complex -u http://{target_address}:8084 -t 50 -i 50 -d 10000 -p 10 -o reactor-complex-api-non-block-db-output.json
```