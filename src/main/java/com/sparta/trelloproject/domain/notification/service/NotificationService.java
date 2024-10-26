package com.sparta.trelloproject.domain.notification.service;

import com.sparta.trelloproject.config.NotificationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final RestTemplate restTemplate;
    private final NotificationProperties notificationProperties;

    // 댓글 생성 알림
    public void sendSlackNotification(String email, String cardId, String comment) {
        String message = String.format("%s님이 '%s' 카드에 댓글을 달았습니다: \"%s\"", email, cardId, comment);
        sendNotification(notificationProperties.getSlackUrl(), message, "text");
    }

    public void sendDiscordNotification(String email, String cardId, String comment) {
        String message = String.format("%s님이 '%s' 카드에 댓글을 달았습니다: \"%s\"", email, cardId, comment);
        sendNotification(notificationProperties.getDiscordUrl(), message, "content");
    }

    // 워크스페이스 생성 알림
    public void sendWorkspaceCreationNotification(String email, String entityType, String entityId) {
        String message = String.format("%s님이 %s번 %s를 생성했습니다.", email, entityId, entityType);
        sendNotification(notificationProperties.getSlackUrl(), message, "text");
        sendNotification(notificationProperties.getDiscordUrl(), message, "content");
    }

    // 보드 생성 알림
    public void sendBoardCreationNotification(String email, String workspaceId, String boardId, String boardTitle) {
        String message = String.format("%s님이 %s번 워크스페이스에 '%s' 보드를 생성했습니다.", email, workspaceId, boardTitle);
        sendNotification(notificationProperties.getSlackUrl(), message, "text");
        sendNotification(notificationProperties.getDiscordUrl(), message, "content");
    }

    // 리스트 생성 알림
    public void sendListCreationNotification(String email, String boardId, String listId, String listTitle) {
        String message = String.format("%s님이 %s번 보드에 %s번 리스트(%s)를 생성했습니다.", email, boardId, listId, listTitle);
        sendNotification(notificationProperties.getSlackUrl(), message, "text");
        sendNotification(notificationProperties.getDiscordUrl(), message, "content");
    }

    // 카드 생성 알림
    public void sendCardCreationNotification(String email, Long boardId, Long listId, Long cardId) {
        String message = String.format("%s님이 %s번 보드에 %s번 리스트에서 %s번 카드를 생성하였습니다.", email, boardId, listId, cardId);
        sendNotification(notificationProperties.getSlackUrl(), message, "text");
        sendNotification(notificationProperties.getDiscordUrl(), message, "content");
    }

    // 공통 알림 전송 로직
    private void sendNotification(String url, String message, String messageType) {
        Map<String, String> payload = new HashMap<>();
        payload.put(messageType, message);

        try {
            restTemplate.postForObject(url, payload, String.class);
            System.out.println("알림 전송에 성공했습니다. : " + url);
        } catch (Exception e) {
            System.err.println("알림 전송에 실패했습니다. : " + url + ": " + e.getMessage());
        }
    }
}