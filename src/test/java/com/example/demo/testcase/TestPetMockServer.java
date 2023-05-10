package com.example.demo.testcase;

import static io.restassured.RestAssured.given;

import com.example.demo.MockServerTestBase;
import com.example.demo.common.annotations.Duration;
import com.example.demo.common.enums.AuthHeader;
import com.example.demo.common.utils.LifecycleLogger;
import com.example.demo.expecations.GetPetById;
import com.example.demo.model.Pet;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("TestPetMockServer")
@Duration
@ExtendWith(GetPetById.class)
class TestPetMockServer extends MockServerTestBase implements LifecycleLogger {

  private final String API_PATH = MOCK_SERVER_URL + "/pet/{petId}";

  @Test
  void testGetStatusCode200() throws Exception {
    Pet actualResult =
        given()
            .log()
            .all()
            .pathParam("petId", System.currentTimeMillis())
            .header(AuthHeader.OK_200.header())
            .then()
            .log()
            .all()
            .expect()
            .statusCode(200)
            .when()
            .get(API_PATH)
            .as(Pet.class);
    Assertions.assertThat(actualResult).isEqualTo(GetPetById.getPet());
  }

  @Test
  void testGetStatusCode400() throws Exception {
    given()
        .log()
        .all()
        .pathParam("petId", System.currentTimeMillis())
        .header(AuthHeader.BAD_REQUEST_400.header())
        .then()
        .log()
        .all()
        .expect()
        .statusCode(400)
        .when()
        .get(API_PATH);
  }

  @Test
  void testGetStatusCode404() throws Exception {
    given()
        .log()
        .all()
        .pathParam("petId", System.currentTimeMillis())
        .header(AuthHeader.NOT_FOUND_404.header())
        .then()
        .log()
        .all()
        .expect()
        .statusCode(404)
        .when()
        .get(API_PATH);
  }
}
