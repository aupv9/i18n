package com.nduyhai.i18n;

import com.nduyhai.i18n.core.AbstractBusinessException;
import com.nduyhai.i18n.core.BaseException;
import com.nduyhai.i18n.core.BaseHttpErrorCode;
import java.util.Locale;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@EnableConfigurationProperties(I18nProperties.class)
@ImportAutoConfiguration(MessageResourceConfig.class)
@AutoConfigureAfter(MessageResourceConfig.class)
@ConditionalOnProperty(prefix = "i18n", name = "enabled", havingValue = "true", matchIfMissing = true)
@RestControllerAdvice
public class I18nAutoConfiguration {

  private final MessageSource i18nMessageSource;

  public I18nAutoConfiguration(MessageSource i18nMessageSource) {
    this.i18nMessageSource = i18nMessageSource;
  }

  @ExceptionHandler(AbstractBusinessException.class)
  public ResponseEntity<ProblemDetail> handleBaseException(AbstractBusinessException ex,
      Locale locale) {

    HttpStatusCode status = status(ex);

    String localizedMessage = i18nMessageSource.getMessage(ex.getErrorCode().getMessageKey(), null,
        locale);

    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, localizedMessage);
    problemDetail.setProperty("error_code", ex.getErrorCode().getCode());
    problemDetail.setTitle(localizedMessage);

    return ResponseEntity.status(status)
        .body(problemDetail);

  }


  private HttpStatusCode status(BaseException ex) {
    if (ex instanceof BaseHttpErrorCode) {
      return ((BaseHttpErrorCode) ex).getHttpStatusCode();
    }
    return HttpStatus.INTERNAL_SERVER_ERROR;
  }


}
