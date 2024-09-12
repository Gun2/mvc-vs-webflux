# 성능 측정을 위한 모듈

## Command
모듈에서 제공하는 커맨드
### $read
조회 API 성능 측정
```
OPTIONS
       -u String
       대상 서버 url
       [Mandatory]

       -t Integer
       초기 요청 클라이언트 수.
       [Optional, default = 1]

       -i Integer
       매 단계마다 증가될 클라이언트 수
       [Optional, default = 1]

       -d Integer
       단계의 지속 시간
       [Optional, default = 5000]

       -p Integer
       총 단계
       [Optional, default = 10]

       -o String
       측정 결과 저장 경로
       [Optional, default = read_api_measure_output.json]

       --help or -h 
       help for read
       [Optional]
```
> e.g. `read -u http://localhost:8081 -t 10 -i 100 -d 5000 -p 10 -o read_output.json` <br/>
> 위의 커맨드는 일기 요청 API를 타겟으로하는 초기 10개의 클라이언트(-t)요청으로 5초마다(-d) 100개씩 증가(-i)하며 총 10단계(-p)를 수행하고 수행 결과를 read_output.json(-o)에 저장

### $create
생성 API 성능 측정
```
OPTIONS
       -u String
       대상 서버 url
       [Mandatory]

       -t Integer
       초기 요청 클라이언트 수.
       [Optional, default = 1]

       -i Integer
       매 단계마다 증가될 클라이언트 수
       [Optional, default = 1]

       -d Integer
       단계의 지속 시간
       [Optional, default = 5000]

       -p Integer
       총 단계
       [Optional, default = 10]

       -o String
       측정 결과 저장 경로
       [Optional, default = create_api_measure_output.json]

       --help or -h 
       help for create
       [Optional]
```

> e.g. `create -u http://localhost:8081 -t 10 -i 100 -d 5000 -p 10 -o create_output.json` <br/>
> 위의 커맨드는 일기 요청 API를 타겟으로하는 초기 10개의 클라이언트(-t)요청으로 5초마다(-d) 100개씩 증가(-i)하며 총 10단계(-p)를 수행하고 수행 결과를 read_output.json(-o)에 저장
