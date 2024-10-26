package com.sparta.trelloproject.domain.card.service;

import com.sparta.trelloproject.domain.card.entity.Card;
import com.sparta.trelloproject.domain.card.entity.CardActivity;
import com.sparta.trelloproject.domain.card.repository.CardActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardActivityService {

    private final CardActivityRepository cardActivityRepository;

    @Transactional
    public void createActivity(String activity, Card card){

        CardActivity cardActivity = new CardActivity(activity, card);
        cardActivityRepository.save(cardActivity);

    }

}
