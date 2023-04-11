package com.example.demo.common.enums;

import io.restassured.http.Header;
import org.mockserver.model.HttpStatusCode;

public enum AuthHeader {
  OK_200 {
    public Header header() {
      return new Header("Authorization", "Bearer " + HttpStatusCode.OK_200);
    }
  },
  BAD_REQUEST_400 {
    public Header header() {
      return new Header("Authorization", "Bearer " + HttpStatusCode.BAD_REQUEST_400);
    }
  },
  UNAUTHORIZED_401 {
    public Header header() {
      return new Header("Authorization", "Bearer " + HttpStatusCode.UNAUTHORIZED_401);
    }
  },
  CONFLICT_409 {
    public Header header() {
      return new Header("Authorization", "Bearer " + HttpStatusCode.CONFLICT_409);
    }
  },
  UNPROCESSABLE_ENTITY_422 {
    public Header header() {
      return new Header("Authorization", "Bearer " + HttpStatusCode.UNPROCESSABLE_ENTITY_422);
    }
  },
  NOT_FOUND_404 {
    public Header header() {
      return new Header("Authorization", "Bearer " + HttpStatusCode.NOT_FOUND_404);
    }
  },
  INTERNAL_SERVER_ERROR_500 {
    public Header header() {
      return new Header("Authorization", "Bearer " + HttpStatusCode.INTERNAL_SERVER_ERROR_500);
    }
  };

  public abstract Header header();
}
