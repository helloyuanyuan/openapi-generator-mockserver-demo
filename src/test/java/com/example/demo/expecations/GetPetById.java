package com.example.demo.expecations;

import static org.mockserver.model.HttpResponse.notFoundResponse;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.OpenAPIDefinition.openAPI;

import com.example.demo.MockServerTestBase;
import com.example.demo.model.Pet;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockserver.model.HttpStatusCode;

public class GetPetById extends MockServerTestBase implements BeforeTestExecutionCallback {

  public static final Pet getPet() {
    Pet pet = new Pet();
    return pet;
  }

  @Override
  public void beforeTestExecution(ExtensionContext context) throws Exception {
    resetMockServer();
    CLIENT
        .when(openAPI(V2_OPEN_API_URL, "getPetById"))
        .respond(
            httpRequest -> {
              if (httpRequest
                  .getHeader(AUTH_HEADER)
                  .get(0)
                  .contains(HttpStatusCode.OK_200.toString())) {
                return response()
                    .withStatusCode(200)
                    .withHeaders(header())
                    .withBody(body(getPet()));
              }
              if (httpRequest
                  .getHeader(AUTH_HEADER)
                  .get(0)
                  .contains(HttpStatusCode.BAD_REQUEST_400.toString())) {
                return response().withStatusCode(400).withHeaders(header());
              }
              if (httpRequest
                  .getHeader(AUTH_HEADER)
                  .get(0)
                  .contains(HttpStatusCode.NOT_FOUND_404.toString())) {
                return response().withStatusCode(404).withHeaders(header());
              } else {
                return notFoundResponse();
              }
            });
  }
}
