package com.sparta.trelloproject.domain.uploadFile.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UploadFileSaveResponse {

  private Long cardId;

  private String fileName;

  private String fileType;

  private Long fileSize;

  private String fileUrl;

  private LocalDateTime createdAt;

}
