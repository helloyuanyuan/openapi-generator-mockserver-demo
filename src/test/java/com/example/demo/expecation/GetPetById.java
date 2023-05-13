package com.example.demo.expecation;

import static com.example.demo.testdata.TestData.getModelApiResponse;
import static com.example.demo.testdata.TestData.getPet;
import static org.mockserver.model.HttpResponse.notFoundResponse;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.OpenAPIDefinition.openAPI;

import com.example.demo.MockServerTestBase;
import com.example.demo.model.Pet.StatusEnum;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockserver.model.HttpStatusCode;

public class GetPetById extends MockServerTestBase implements BeforeTestExecutionCallback {

  @Override
  public void beforeTestExecution(ExtensionContext context) throws Exception {
    resetMockServer();
    CLIENT
        .when(openAPI(SWAGGER_URL, "getPetById"))
        .respond(
            httpRequest -> {
              if (httpRequest
                  .getHeader(AUTH_HEADER)
                  .get(0)
                  .contains(HttpStatusCode.OK_200.toString())) {
                return response()
                    .withStatusCode(200)
                    .withHeaders(header())
                    .withBody(body(getPet(StatusEnum.AVAILABLE)));
              }
              if (httpRequest
                  .getHeader(AUTH_HEADER)
                  .get(0)
                  .contains(HttpStatusCode.BAD_REQUEST_400.toString())) {
                return response()
                    .withStatusCode(400)
                    .withHeaders(header())
                    .withBody(body(getModelApiResponse("Invalid ID supplied")));
              }
              if (httpRequest
                  .getHeader(AUTH_HEADER)
                  .get(0)
                  .contains(HttpStatusCode.NOT_FOUND_404.toString())) {
                return response()
                    .withStatusCode(404)
                    .withHeaders(header())
                    .withBody(body(getModelApiResponse("Pet not found")));
              } else {
                return notFoundResponse();
              }
            });
  }
}
