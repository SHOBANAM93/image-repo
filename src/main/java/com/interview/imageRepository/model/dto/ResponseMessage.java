package com.interview.imageRepository.model.dto;

import com.interview.imageRepository.Constants;

import java.time.LocalDateTime;

public class ResponseMessage {

  private Integer status = Constants.SUCCESS_CODE;
  private String message;
  private final LocalDateTime timestamp = LocalDateTime.now();

  public ResponseMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
