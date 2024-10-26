package com.sparta.trelloproject.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    /*
    ex)
    예외명(대문자+'_') + (HttpStatus.오류코드, "메세지")
    STORE_FORBIDDEN(HttpStatus.FORBIDDEN,"사장님 권한을 가진 사용자만 가게를 생성할 수 있습니다."),
    STORE_BAD_REQUEST(HttpStatus.BAD_REQUEST,"사장님은 최대 3개의 가게까지만 운영할 수 있습니다."),
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 가게를 찾을 수 없습니다."),


    throw new CustomException(ErrorCode.STORE_FORBIDDEN);
     */


    // Token ErrorCode
    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "조회 실패 : %s"),




    // User ErrorCode
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "조회 실패 : %s"),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "유효성 검사 실패 : %s"),
    SIGNUP_ERROR(HttpStatus.BAD_REQUEST, "회원가입 실패 : %s"),
    SIGNIN_ERROR(HttpStatus.BAD_REQUEST, "로그인 실패 : %s"),

    // Role ErrorCode
    PERMISSION_ERROR(HttpStatus.FORBIDDEN, "권한 없음 : %s"),



    // Member ErrorCode
    ROLE_ERROR(HttpStatus.FORBIDDEN, "권한 없음 : %s"),
    SAME_ROLE_REQUEST(HttpStatus.BAD_REQUEST, "중복 요청 불가 : %s"),
    ROLE_NOT_FOUND(HttpStatus.NOT_FOUND, "조회 실패 : %s"),
    SELF_REQUEST_FORBIDDEN(HttpStatus.FORBIDDEN, "본인 요청 불가 : %s"),
    USER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "중복 요청 불가 : %s"),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "멤버를 찾을 수 없습니다."),

    // Workspace ErrorCode
    WORKSPACE_NOT_FOUND(HttpStatus.NOT_FOUND, "워크스페이스를 찾을 수 없습니다."),




    // Board Errorcode
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 보드를 찾을 수 없습니다"),
    BOARD_FORBIDDEN(HttpStatus.FORBIDDEN, "보드를 수정/삭제할 권한이 없습니다."),
    BOARD_BAD_REQUEST(HttpStatus.BAD_REQUEST, "보드 요청에 잘못된 정보가 포함되어 있습니다."),
    BOARD_CREATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "보드 생성에 실패했습니다."),
    BOARD_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "보드 수정에 실패했습니다."),
    BOARD_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "보드 삭제에 실패했습니다."),
    BOARD_TITLE_EMPTY(HttpStatus.BAD_REQUEST, "보드 제목은 필수입니다."),
    BOARD_READ_ONLY_MEMBER(HttpStatus.FORBIDDEN, "읽기 전용 권한입니다"),

    // List Errorcode
    LIST_NOT_FOUND(HttpStatus.NOT_FOUND, "조회 실패 : %s"),



    // Card Errorcode
    CARD_NOT_FORBIDDEN(HttpStatus.FORBIDDEN, "카드 생성/수정 권한이 없습니다."),
    CARD_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 카드가 존재하지 않습니다.."),



    // Comment Errorcode
    Comment_FORBIDDEN(HttpStatus.FORBIDDEN, "댓글 작성 권한이 없습니다."),
    Comment_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),
    Comment_AUTH_FORBIDDEN(HttpStatus.FORBIDDEN,"작성자가 아니므로 수정/삭제가 불가능합니다."),
    Comment_BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 형식의 텍스트/이모지입니다."),

    // UploadFile Errorcode
    FILE_SIZE_EXCEEDED(HttpStatus.PAYLOAD_TOO_LARGE, "파일 크기는 5Mb를 넘을 수 없습니다."),
    UNSUPPORTED_FILE_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "지원하지않는 파일 형식입니다."),
    FILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "업로드에 실패했습니다."),
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "파일을 찾을 수 없습니다."),
    FILE_NOT_FOUND_CARD(HttpStatus.NOT_FOUND, "카드에서 파일을 찾을 수 없습니다."),

    // Alarm Errorcode
    Notification_NOTIFICATION_FAILED(HttpStatus.BAD_REQUEST,"알림 전송에 실패했습니다."),




    // Search Errorcode




    // 아래 코드 위에 ErrorCode 작성
    NOT_FOUND(HttpStatus.NOT_FOUND, "찾지못했습니다.");


    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message){
        this.status = httpStatus;
        this.message = message;
    }

    public String customMessage(String detail) {
        return String.format(message, detail);
    }
}
