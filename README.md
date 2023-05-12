# Openapi Mock Server Test Project

## Sample Openapi: <https://petstore.swagger.io/v2/swagger.json>

### Commands

- Start Mock Server:

  ~~~Text
  docker-compose up -d
  ~~~

- Stop Mock Server:

  ~~~Text
  docker-compose down
  ~~~

- Make sure code be formatted via google style before commit:

  ~~~Text
  ./mvnw com.coveo:fmt-maven-plugin:format
  ~~~

- Run test and generates test report:

  ~~~Text
  ./mvnw clean compile test -Dmaven.test.failure.ignore=true allure:serve
  ~~~

### Mock Server Dashboard

- <http://localhost:1080/mockserver/dashboard>

### Development Flow

~~~Text
0. Defines api contract (Whole Team - Dev, QA, BA…);
1. Developer adds openapi annotation in dev project code;
2. Developer genarates online openapi document;
3. Tester genaretes defined object from online openapi document;
4. Tester writes mock server expecations accroding to openapi document;
5. Tester writes mock server testcases accroding to openapi document;
6. API Test - send request to mock server to get expected return;
7. UI test - point front side to use mock server;
8. UI test - selenium UI automation;
~~~

### Reference

- Contrct Driven - <https://www.ibm.com/garage/method/practices/code/contract-driven-testing/>
- OpenAPI (Swagger) - <https://www.openapis.org> (<https://swagger.io>)
- Mock Server - <https://mock-server.com/#what-is-mockserver>
- Openapi Genetrator <https://openapi-generator.tech> -> <https://github.com/OpenAPITools/openapi-generator>
