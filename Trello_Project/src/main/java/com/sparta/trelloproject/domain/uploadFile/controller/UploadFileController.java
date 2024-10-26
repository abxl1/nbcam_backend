package com.sparta.trelloproject.domain.uploadFile.controller;

import com.sparta.trelloproject.domain.auth.entity.AuthUser;
import com.sparta.trelloproject.domain.uploadFile.dto.response.UploadFileDeleteFileResponse;
import com.sparta.trelloproject.domain.uploadFile.dto.response.UploadFileSaveResponse;
import com.sparta.trelloproject.domain.uploadFile.entity.UploadFile;
import com.sparta.trelloproject.domain.uploadFile.service.UploadFileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cards/{cardId}/uploadfiles")
public class UploadFileController {

  private final UploadFileService uploadFileService;


  // 파일 업로드
  @PostMapping
  public ResponseEntity<UploadFileSaveResponse> createUploadFile(@Valid @AuthenticationPrincipal AuthUser authUser,
                                                     @PathVariable("cardId") Long cardId,
                                                     @RequestParam MultipartFile uploadFile) {

    UploadFile createdfile = uploadFileService.createUploadfile(authUser, cardId, uploadFile);

    UploadFileSaveResponse uploadFileSaveResponse = new UploadFileSaveResponse(createdfile.getCard().getId(),
        createdfile.getFileName(),
        createdfile.getFileType(),
        createdfile.getFileSize(),
        createdfile.getFileUrl(),
        createdfile.getCreatedAt()
    );

    return ResponseEntity.ok(uploadFileSaveResponse);
  }

  @DeleteMapping("/{fileId}")
  public ResponseEntity<UploadFileDeleteFileResponse> deleteUploadFile(@Valid @AuthenticationPrincipal AuthUser authUser,
                                               @PathVariable("cardId") Long cardId,
                                               @PathVariable("fileId") Long fileId) {

    UploadFileDeleteFileResponse uploadFileDeleteFileResponse = uploadFileService.deleteUploadFile(fileId, authUser, cardId);


    return ResponseEntity.ok(uploadFileDeleteFileResponse);
  }

}
