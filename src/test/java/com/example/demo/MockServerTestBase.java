package com.example.demo;

import static org.mockserver.mock.OpenAPIExpectation.openAPIExpectation;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.OpenAPIDefinition.openAPI;

import java.util.Map;

import org.mockserver.client.MockServerClient;
import org.mockserver.model.ClearType;
import org.mockserver.model.Header;
import org.mockserver.verify.VerificationTimes;

import com.example.demo.common.utils.PropertyUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.http.ContentType;

public class MockServerTestBase {

  protected static final String SWAGGER_URL = PropertyUtils.getInstance().getProperty("swaggerurl");

  protected static final String SERVER_URL = PropertyUtils.getInstance().getProperty("serverurl");

  protected static final MockServerClient CLIENT = new MockServerClient(
      PropertyUtils.getInstance().getProperty("mockserverhost"),
      Integer.parseInt(PropertyUtils.getInstance().getProperty("mockserverport")));

  protected static final String AUTH_HEADER = "Authorization";

  protected static Header header() {
    return new Header("Content-Type", ContentType.JSON.toString());
  }

  protected static String body(Object value) throws JsonProcessingException {
    return new ObjectMapper().writeValueAsString(value);
  }

  protected static void createOpenApiExpectation(String openApiUrl) {
    CLIENT.upsert(openAPIExpectation(openApiUrl));
  }

  protected static void createOpenApiExpectation(
      String openApiUrl, Map<String, String> operationsAndResponses) {
    CLIENT.upsert(openAPIExpectation(openApiUrl, operationsAndResponses));
  }

  protected static void createOpenApiExpectation(
      String openApiUrl, String operationId, Integer statusCode, Object body) throws Exception {
    String bodyString = new ObjectMapper().writeValueAsString(body);
    CLIENT
        .when(openAPI(openApiUrl, operationId))
        .respond(
            response()
                .withStatusCode(statusCode)
                .withHeader(new Header("Content-Type", "application/json"))
                .withBody(bodyString));
  }

  protected static void resetMockServer() {
    CLIENT.reset();
  }

  protected static void clearMockServer(String path, ClearType clearType) {
    CLIENT.clear(request().withPath(path), clearType);
  }

  protected static void verifyTimes(String path, int times) {
    CLIENT.verify(request().withPath(path), VerificationTimes.atLeast(times));
  }

  protected static void verifyMethod(String path, String method) {
    CLIENT.verify(request().withPath(path).withMethod(method));
  }

  protected static void verifyZero() {
    CLIENT.verifyZeroInteractions();
  }
}
