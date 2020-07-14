### Simple WebService

- Java Nio Non-Blocking (Buffer + Channel + Selector)
- Java8 (Genenric, Stream, Optional, Reflection, Annotation, Enum)
- Logback, Lombok, Jackson, Gradle, Junit, commons-io



#### 목차

[TOC]




#### 요구사항

| 요구사항                      | 구현여부 | 상세                                                         |
| ----------------------------- | -------- | ------------------------------------------------------------ |
| HTTP/1.1 의 Host 헤더 해석    | 50%      | http protocol header 해석 기능 구현<br>vitual host 기능 미구현 |
| 환경설정 파일 포맷 (JSON)     | O        | commons-io 활용 (host, port, charset, file)                  |
| httpCode [403\|404\|500] 포맷 | O        | { "resultCode" : "%SERVER_CODE%",  <br/> "resultMessage" : "%SERVER_MSG%" } |
| 보안규칙                      | O        | 구현 규칙에 따라 응답코드 반환                               |
| 로깅                          | O        | Logback, 하루단위, stacktrace 출력                           |
| 간단한 웹서비스 구현          | O        | localhost:5000                                               |
| URL 매핑                      | O        | http://HOST::PORT/CLASS이름/메소드명 <br/>규칙에 따라 서비스 호출<br>규칙과 다를 경우 상황에 따른 응답코드 반환 |
| URL 설정파일                  | O        | 요청 경로와 클래스 매핑<br>serviceMap = new HashMap<>(); <br>serviceMap.put("read", ReadService.class);<br/>serviceMap.put("create", CreateService.class); <br/>... |
| JUNIT 테스트                  | O        |                                                              |

* virtual host 구현 방향

  * 3개 이상의 웹서비스를 구현

  * hosts 파일에 한 개의 IP에 3개의 호스트(호스트 별로 포트 상이)를 등록

  * 한개의 호스트에서 나머지 두 호스트에 대한 소켓 통신 하여 구현 가능할 것으로 보입니다. 

    

#### 프로젝트 구조

| Depth                           | Desc                             |
| ------------------------------- | -------------------------------- |
| main.java.c.s.annotation        | 어노테이션 관련                  |
| main.java.c.s.config            | 환경설정                         |
| main.java.c.s.exception         | 예외처리 관련                    |
| main.java.c.s.nio               | nio                              |
| main.java.c.s.parse             | request, response 파싱           |
| main.java.c.s.type              | enum                             |
| main.java.c.s.*Application.java | java -jar *.jar 관련 메인 클래스 |
| main.resources                  | 환경설정, logback, 에러파일      |
| test.java                       | junit 테스트파일                 |

#### Request 

| GET                                                          |
| ------------------------------------------------------------ |
| GET http://localhost:5000/read/notice?id=19485 HTTP/1.1<br/>Accept: application/json<br/>User-Agent: local |

| POST (body 는 Json 포멧으로 요청)                            |
| ------------------------------------------------------------ |
| POST http://localhost:5000/read/date HTTP/1.1<br/>Accept: application/json<br/>User-Agent: local<br/><br/>{"name":"보람.윤","id":"23819"} |

#### Response

| 200 OK                                                       |
| ------------------------------------------------------------ |
| HTTP/1.1 200 OK<br/>Accept: application/json<br/>User-Agent: local<br/><br/>["서울시 지방세","6월 BC 카드 청구서","6월 삼천리 가스요금 청구서","6월 아파트 관리비","6월 학원비"] |

| 200 OK                                                       |
| ------------------------------------------------------------ |
| HTTP/1.1 200 OK<br/>Accept: application/json<br/>User-Agent: local<br/><br/>"2020-07-14 21:54:10" |

| 404 Not Found                                                |
| ------------------------------------------------------------ |
| HTTP/1.1 404 Not Found<br/>Accept: application/json<br/>User-Agent: local<br/><br/>{<br/>  "resultCode" : 404,<br/>  "resultMessage" : "Not Found"<br/>} |

| 403 Forbidden                                                |
| ------------------------------------------------------------ |
| HTTP/1.1 403 Forbidden<br/>Accept: application/json<br/>User-Agent: local<br/><br/>{<br/>  "resultCode" : 403,<br/>  "resultMessage" : "Forbidden"<br/>} |

| 500 Forbidden                                                |
| ------------------------------------------------------------ |
| HTTP/1.1 500 Interner Server Error<br/>Accept: application/json<br/>User-Agent: local<br/><br/>{<br/>  "resultCode" : 500,<br/>  "resultMessage" : "Interner Server Error"<br/>} |



#### 서비스 매핑 규칙

```JAVA

// http://localhost:5050/read/date?key1=value1&key2=value2
// http://localhost:5050/클래스명/메소드명

// ServiceParser.java
// serviceMap.put("read", ReadService.class);

public class ReadService {

    @HttpMethods(accept = {HttpMethods.MethodType.GET})	// 허용할 HttpMethod 선언
    public List noticeList(Map<String, String> parameter) {
       .... 
    }

  
// Request 데이터는 Map 형태로 전달
// GET, POST method 만 처리한 상태
// 응답 데이터는 임의로 생성한 상태

```

#### 테스트

```shell
## ECHO 테스트
java -jar EchoApplication-1.0-SNAPSHOT.jar

## Date 출력 테스트
java -jar DateApplication-1.0-SNAPSHOT.jar

## 샘플 http 요청 테스트
java -jar HttpApplication-1.0-SNAPSHOT.jar


## 서버 인스턴스 생성 후 3초간의 쓰레드 sleep 후 클라이언트 인스턴트 생성합니다. 실행 환경에 따라 서버가 늦게 로드될 수도 있으니 참고 부탁드립니다.
Process server = SimpleServer.start();
Thread.sleep(3000); // server start 대기 시간 확보
SimpleClient client = SimpleClient.start();

```



```JAVA
// ServerSocketChannel, SocketChannel 인스턴스 생성 후
// 간단한 요청 방법 
// CommonServerTest.java 참고 (상속해서 사용가능)
  
String response = new SimpleClientMapper(client)
    .method("POST")
    .body(body)
    .url(url).send();
    
String response = new SimpleClientMapper(client)
    .method("GET")
    .url(url).send();
```



#### GIT HUB
 https://github.com/whybwhy/summer

