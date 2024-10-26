package com.sparta.trelloproject.domain.uploadFile.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UploadFileDeleteFileResponse {

  private Long cardId;
  private String fileName;
  private String message;

}
