package com.sparta.trelloproject.domain.uploadFile.service;

import com.amazonaws.services.s3.AmazonS3;
import com.sparta.trelloproject.common.exception.CustomException;
import com.sparta.trelloproject.common.exception.ErrorCode;
import com.sparta.trelloproject.domain.auth.entity.AuthUser;
import com.sparta.trelloproject.domain.card.entity.Card;
import com.sparta.trelloproject.domain.card.repository.CardRepository;
import com.sparta.trelloproject.domain.member.entity.Member;
import com.sparta.trelloproject.domain.member.enums.Assign;
import com.sparta.trelloproject.domain.member.repository.MemberRepository;
import com.sparta.trelloproject.domain.uploadFile.dto.response.UploadFileDeleteFileResponse;
import com.sparta.trelloproject.domain.uploadFile.entity.UploadFile;
import com.sparta.trelloproject.domain.uploadFile.repository.UploadFileRepository;
import com.sparta.trelloproject.domain.user.entity.User;
import com.sparta.trelloproject.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UploadFileService {

  private final UploadFileRepository uploadFileRepository;
  private final UserRepository userRepository;
  private final MemberRepository memberRepository;
  private final CardRepository cardRepository;
  private final AmazonS3 amazonS3;
  private final String bucketName = "pyjbucket";


  @Transactional
  // 파일 업로드
  public UploadFile createUploadfile(AuthUser authUser,
                                     Long cardId,
                                     MultipartFile uploadFile) {

    // 이 워크스페이스에 멤버이고, 역할이 읽기,쓰기가 아니면 됨
    Member member = findMemberByUserId(authUser);

    if (member == null || member.getAssign().equals(Assign.READ_ONLY)) {
      throw new CustomException(ErrorCode.CARD_NOT_FORBIDDEN);
    }
    // 파일 체크
    fileCheck(uploadFile);

    // S3에 업로드
    String fileUrl = uploadToS3(uploadFile);

    Card card = findCardById(cardId);

    // DB용 UploadFile 객체생성
    UploadFile dbuploadFile = new UploadFile(card,
        uploadFile.getOriginalFilename(),
        uploadFile.getContentType(),
        uploadFile.getSize(),
        fileUrl);

    // 해당 카드에 업로드 파일 저장
    card.getUploadFiles().add(dbuploadFile);

    return uploadFileRepository.save(dbuploadFile);
  }

  @Transactional
  public UploadFileDeleteFileResponse deleteUploadFile(Long fileId, AuthUser authUser, Long cardId) {
    // 카드 검증 로직 추가 (예: 해당 카드에 속한 파일인지 확인)
    Card card = findCardById(cardId);

    // 파일 정보 조회
    UploadFile deletedfile = uploadFileRepository.findById(fileId)
        .orElseThrow(() -> new CustomException(ErrorCode.FILE_NOT_FOUND));

    // 파일이 해당 카드에 속하는지 확인
    if (!card.getUploadFiles().contains(deletedfile)) {
      throw new CustomException(ErrorCode.FILE_NOT_FOUND_CARD);
    }

    // 권한 체크
    Member member = findMemberByUserId(authUser);
    if (member.getAssign().equals(Assign.READ_ONLY)) {
      throw new CustomException(ErrorCode.CARD_NOT_FORBIDDEN);
    }

    // S3에서 파일 삭제
    deleteFromS3(deletedfile.getFileUrl());

    UploadFileDeleteFileResponse uploadFileDeleteFileResponse = new UploadFileDeleteFileResponse(
        deletedfile.getCard().getId(),
        deletedfile.getFileName(),
        "파일이 삭제되었습니다.");

    card.getUploadFiles().remove(deletedfile);

    // DB에서 삭제
    uploadFileRepository.delete(deletedfile);

    return uploadFileDeleteFileResponse;
  }



  /////////////////////// 예외처리를 위한 메서드 /////////////////////////

  private User findUserById(Long userId) {
    return userRepository.findById(userId).orElseThrow(
        () -> new CustomException(ErrorCode.USER_NOT_FOUND));
  }

  private Member findMemberByUserId(AuthUser authUser) {
    return memberRepository.findByUserId(authUser.getUserId()).orElseThrow(
        () -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
  }

  private Card findCardById(Long cardId) {
    return cardRepository.findById(cardId).orElseThrow(
        () -> new CustomException(ErrorCode.CARD_NOT_FOUND));
  }

  public void fileCheck(MultipartFile uploadFile) {
    // 파일 크기 확인
    if (uploadFile.getSize() > 5 * 1024 * 1024) { // 5MB
      throw new CustomException(ErrorCode.FILE_SIZE_EXCEEDED);
    }
    // 파일 형식 확인
    String fileType = uploadFile.getContentType();
    if (!isSupportedFileType(uploadFile.getContentType())) {
      throw new CustomException(ErrorCode.UNSUPPORTED_FILE_TYPE);
    }
  }

  private boolean isSupportedFileType(String fileType) {
    return fileType.equals("image/jpeg") || fileType.equals("image/png")
        || fileType.equals("application/pdf") || fileType.equals("text/csv");
  }

  private String uploadToS3(MultipartFile uploadFile) {

    String fileName = System.currentTimeMillis() + "_" + uploadFile.getOriginalFilename();

    try {
      // S3에 파일 업로드
      amazonS3.putObject(bucketName, fileName, uploadFile.getInputStream(), null);

      // S3에서 파일 URL 생성
      return amazonS3.getUrl(bucketName, fileName).toString();
    } catch (IOException e) {
      throw new CustomException(ErrorCode.FILE_UPLOAD_FAILED);
    }

  }

  private void deleteFromS3(String fileUrl) {
    String fileName = extractFileNameFromUrl(fileUrl);
    amazonS3.deleteObject(bucketName, fileName);
  }

  private String extractFileNameFromUrl(String fileUrl) {
    // URL에서 파일 이름 추출하는 로직
    return fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
  }

}
