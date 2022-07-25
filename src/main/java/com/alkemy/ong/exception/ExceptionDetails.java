package com.alkemy.ong.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Setter @Getter
@NoArgsConstructor
public class ExceptionDetails {
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private LocalDateTime local_date;
   private String message;
   private String uri;

   public ExceptionDetails(LocalDateTime local_date, Exception exception, HttpServletRequest url) {
      this.local_date = local_date;
      this.message = exception.getMessage();
      this.uri = url.getRequestURI();
   }
}
