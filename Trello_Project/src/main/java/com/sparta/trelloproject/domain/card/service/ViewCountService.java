package com.sparta.trelloproject.domain.card.service;

import com.sparta.trelloproject.domain.card.entity.Card;
import com.sparta.trelloproject.domain.card.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ViewCountService {

    private final RedisTemplate<String, String> redisTemplate;
    private final CardRepository cardRepository;

    public String getViewCount(Long cardId) {
        String key = "card:" + cardId + ":viewCount";
        return redisTemplate.opsForValue().get(key);
    }

    public void incrementViewCount(Long cardId) {
        String key = "card:" + cardId + ":viewCount";
        redisTemplate.opsForValue().increment(key);
    }

    public void resetViewCount(Long cardId) {
        String key = "card:" + cardId + ":viewCount";
        redisTemplate.opsForValue().set(key, "1");
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void resetAllViewCounts() {
        // 카드 ID 목록을 가져오고, 각각에 대해 리셋
        List<Long> cardIds = cardRepository
                .findAll()
                .stream()
                .map(Card::getId)
                .toList();

        for (Long cardId : cardIds) {
            resetViewCount(cardId);
        }
    }
}
