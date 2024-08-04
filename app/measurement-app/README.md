# 성능 측정을 위한 모듈

## properties

| key                                                 | 설명                         | 타입     | 기본값                      |
|-----------------------------------------------------|----------------------------|--------|--------------------------|
| app.target.url                                      | (읽기)측정 대상 URL              | string | ${API_TARGET_URL}        |
| app.read.measurement.config.init-client             | (읽기)성능 측정 시 초기 요청 클라이언트 개수 | number | 100                      |
| app.read.measurement.config.increasing-client       | (읽기)매 단계 마다 증가시킬 클라이언트 개수  | number | 100                      |
| app.read.measurement.config.duration-ms-per-phase   | (읽기)단계가 유지되는 시간 (ms)       | number | 5000                     |
| app.read.measurement.config.phase                   | (읽기)성능 측정 단계 수             | number | 10                       |
| app.read.measurement.config.output-path             | (읽기)측정 결과 저장 경로            | string | board_read_output.json   |
| app.create.measurement.config.init-client           | (쓰기)성능 측정 시 초기 요청 클라이언트 개수 | number | 100                      |
| app.create.measurement.config.increasing-client     | (쓰기)매 단계 마다 증가시킬 클라이언트 개수  | number | 100                      |
| app.create.measurement.config.duration-ms-per-phase | (쓰기)단계가 유지되는 시간 (ms)       | number | 5000                     |
| app.create.measurement.config.phase                 | (쓰기)성능 측정 단계 수             | number | 10                       |
| app.create.measurement.config.output-path           | (쓰기)측정 결과 저장 경로            | string | board_create_output.json |
