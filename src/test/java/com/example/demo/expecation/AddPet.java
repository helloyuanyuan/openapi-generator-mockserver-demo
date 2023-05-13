package com.example.demo.expecation;

import static com.example.demo.testdata.TestData.getModelApiResponse;
import static com.example.demo.testdata.TestData.getPet;
import static org.mockserver.model.HttpResponse.notFoundResponse;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.HttpRequest.request;

import com.example.demo.MockServerTestBase;
import com.example.demo.model.Pet.StatusEnum;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockserver.model.HttpStatusCode;

public class AddPet extends MockServerTestBase implements BeforeTestExecutionCallback {

  @Override
  public void beforeTestExecution(ExtensionContext context) throws Exception {
    resetMockServer();
    CLIENT
        .when(request()
        .withMethod("POST")
        .withPath("/pet"))
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
                  .contains(HttpStatusCode.METHOD_NOT_ALLOWED_405.toString())) {
                return response()
                    .withStatusCode(405)
                    .withHeaders(header())
                    .withBody(body(getModelApiResponse("Invalid input")));
              } else {
                return notFoundResponse();
              }
            });
  }
}
