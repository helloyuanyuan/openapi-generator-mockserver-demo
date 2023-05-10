package com.example.demo.testcase;

import static io.restassured.RestAssured.given;

import com.example.demo.MockServerTestBase;
import com.example.demo.common.annotations.Duration;
import com.example.demo.common.enums.AuthHeader;
import com.example.demo.common.utils.LifecycleLogger;
import com.example.demo.model.ModelApiResponse;
import com.example.demo.model.Pet;
import com.example.demo.model.Pet.StatusEnum;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("TestPet")
@Duration
class TestPet extends MockServerTestBase implements LifecycleLogger {

  @Test
  void testAddPet() throws Exception {
    Pet pet = new Pet();
    pet.setName("TEST-PET-" + System.currentTimeMillis());
    pet.setStatus(StatusEnum.PENDING);

    pet =
        given()
            .log()
            .all()
            .contentType(ContentType.JSON)
            .body(pet)
            .then()
            .log()
            .all()
            .statusCode(200)
            .when()
            .post("https://petstore.swagger.io/v2/pet")
            .as(Pet.class);

    Assertions.assertThat(pet.getName()).isNotNull();
    Assertions.assertThat(pet.getStatus()).isEqualTo(StatusEnum.PENDING);
  }

  @Test
  void testGetPetById() throws Exception {
    Pet pet = new Pet();
    pet.setName("TEST-PET-" + System.currentTimeMillis());
    pet.setStatus(StatusEnum.PENDING);

    pet =
        given()
            .log()
            .all()
            .contentType(ContentType.JSON)
            .body(pet)
            .then()
            .log()
            .all()
            .statusCode(200)
            .when()
            .post("https://petstore.swagger.io/v2/pet")
            .as(Pet.class);

    pet =
        given()
            .log()
            .all()
            .pathParam("petId", pet.getId())
            .header(AuthHeader.OK_200.header())
            .then()
            .log()
            .all()
            .expect()
            .statusCode(200)
            .when()
            .get("https://petstore.swagger.io/v2/pet/{petId}")
            .as(Pet.class);
    Assertions.assertThat(pet.getId()).isNotNull();
  }

  @Test
  void testGetPetByIdError() throws Exception {
    ModelApiResponse rsp =
        given()
            .log()
            .all()
            .pathParam("petId", "1a1")
            .header(AuthHeader.BAD_REQUEST_400.header())
            .then()
            .log()
            .all()
            .expect()
            .statusCode(404)
            .when()
            .get("https://petstore.swagger.io/v2/pet/{petId}")
            .as(ModelApiResponse.class);
    Assertions.assertThat(rsp.getMessage())
        .contains("java.lang.NumberFormatException: For input string");
  }

  @Test
  void testGetPetByIdNotFound() throws Exception {
    ModelApiResponse rsp =
        given()
            .log()
            .all()
            .pathParam("petId", "42347863")
            .header(AuthHeader.OK_200.header())
            .then()
            .log()
            .all()
            .expect()
            .statusCode(404)
            .when()
            .get("https://petstore.swagger.io/v2/pet/{petId}")
            .as(ModelApiResponse.class);
    Assertions.assertThat(rsp.getMessage()).isEqualTo("Pet not found");
  }
}
