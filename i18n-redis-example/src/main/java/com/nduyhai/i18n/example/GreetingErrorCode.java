package com.nduyhai.i18n.example;

import com.nduyhai.i18n.core.error.BaseHttpErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum GreetingErrorCode implements BaseHttpErrorCode {
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "G500000"),
  BAD_REQUEST(HttpStatus.BAD_REQUEST, "G400000"),
  INVALID_REQUEST(HttpStatus.BAD_REQUEST, "G400001"),
  ;

  private final HttpStatusCode httpStatusCode;
  private final String code;
  private final String messageKey;

  GreetingErrorCode(HttpStatusCode httpStatusCode, String code) {
    this.httpStatusCode = httpStatusCode;
    this.code = code;
    this.messageKey = code;
  }

  @Override
  public HttpStatusCode getHttpStatusCode() {
    return httpStatusCode;
  }

  @Override
  public String getCode() {
    return code;
  }

  @Override
  public String getMessageKey() {
    return messageKey;
  }
}
