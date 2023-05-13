package com.example.demo.testdata;

import static io.restassured.RestAssured.given;

import com.example.demo.MockServerTestBase;
import com.example.demo.common.enums.AuthHeader;
import com.example.demo.model.Category;
import com.example.demo.model.ModelApiResponse;
import com.example.demo.model.Pet;
import com.example.demo.model.Pet.StatusEnum;
import com.example.demo.model.Tag;
import com.google.common.collect.Lists;
import io.restassured.http.ContentType;

public class TestData extends MockServerTestBase {

  private static final String API_PATH = SERVER_URL + "/pet";

  public static Category getCategory() {
    Category category = new Category();
    category.setId(System.currentTimeMillis());
    category.setName("CategoryName");
    return category;
  }

  public static Tag getTag() {
    Tag tag = new Tag();
    tag.setId(System.currentTimeMillis());
    tag.setName("TagName");
    return tag;
  }

  public static Pet getPet(StatusEnum status) throws Exception {
    Pet pet = new Pet();
    pet.setId(System.currentTimeMillis());
    pet.setCategory(getCategory());
    pet.setTags(Lists.newArrayList(getTag()));
    pet.setName("PetName");
    pet.setStatus(status);
    pet.addPhotoUrlsItem("PhotoUrl");
    return pet;
  }

  public static Pet getPetDefault() throws Exception {
    Pet pet =
        given()
            .log()
            .all()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .header(AuthHeader.OK_200.header())
            .body(getPet(StatusEnum.AVAILABLE))
            .then()
            .log()
            .all()
            .expect()
            .statusCode(200)
            .when()
            .post(API_PATH)
            .as(Pet.class);
    return pet;
  }

  public static ModelApiResponse getModelApiResponse(String msg) throws Exception {
    ModelApiResponse rsp = new ModelApiResponse();
    rsp.setMessage(msg);
    return rsp;
  }
}
