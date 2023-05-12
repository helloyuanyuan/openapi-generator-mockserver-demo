package com.example.demo.testcase;

import static com.example.demo.testdata.TestData.getPetDefault;
import static io.restassured.RestAssured.given;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.MockServerTestBase;
import com.example.demo.common.annotations.Duration;
import com.example.demo.common.enums.AuthHeader;
import com.example.demo.common.utils.LifecycleLogger;
import com.example.demo.expecations.GetPetById;
import com.example.demo.model.ModelApiResponse;
import com.example.demo.model.Pet;

@SpringBootTest
@DisplayName("MockServerGetPetByIdTest")
@Duration
@ExtendWith(GetPetById.class)
class MockServerGetPetByIdTest extends MockServerTestBase implements LifecycleLogger {

  private static final String API_PATH = MOCK_SERVER_URL + "/pet/{petId}";

  @Test
  void testGetPetByIdStatusCode200() throws Exception {
    Pet pet = given()
        .log()
        .all()
        .pathParam("petId", getPetDefault().getId())
        .header(AuthHeader.OK_200.header())
        .then()
        .log()
        .all()
        .expect()
        .statusCode(200)
        .when()
        .get(API_PATH)
        .as(Pet.class);
    Assertions.assertThat(pet).isNotNull();
  }

  @Test
  void testGetPetByIdStatusCode400() throws Exception {
    ModelApiResponse rsp = given()
        .log()
        .all()
        .pathParam("petId", "aaaaa")
        .header(AuthHeader.BAD_REQUEST_400.header())
        .then()
        .log()
        .all()
        .expect()
        .statusCode(400)
        .when()
        .get(API_PATH)
        .as(ModelApiResponse.class);
    Assertions.assertThat(rsp.getMessage()).isEqualTo("Invalid ID supplied");
  }

  @Test
  void testGetPetByIdStatusCode404() throws Exception {
    ModelApiResponse rsp = given()
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
        .get(API_PATH)
        .as(ModelApiResponse.class);
    Assertions.assertThat(rsp.getMessage()).isEqualTo("Pet not found");
  }
}
