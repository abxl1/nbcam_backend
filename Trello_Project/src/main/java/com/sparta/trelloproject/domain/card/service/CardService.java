package com.sparta.trelloproject.domain.card.service;

import com.sparta.trelloproject.common.aop.CreateActivity;
import com.sparta.trelloproject.common.exception.CustomException;
import com.sparta.trelloproject.common.exception.ErrorCode;
import com.sparta.trelloproject.domain.auth.entity.AuthUser;
import com.sparta.trelloproject.domain.card.dto.request.CardSaveRequest;
import com.sparta.trelloproject.domain.card.dto.request.CardUpdateRequest;
import com.sparta.trelloproject.domain.card.dto.response.CardDetailResponse;
import com.sparta.trelloproject.domain.card.dto.response.CardSaveResponse;
import com.sparta.trelloproject.domain.card.dto.response.CardSearchResponse;
import com.sparta.trelloproject.domain.card.dto.response.ViewCountSearchResponse;
import com.sparta.trelloproject.domain.card.entity.Card;
import com.sparta.trelloproject.domain.card.entity.CardAssignee;
import com.sparta.trelloproject.domain.card.repository.CardAssigneeRepository;
import com.sparta.trelloproject.domain.card.repository.CardRepository;
import com.sparta.trelloproject.domain.list.entity.TaskList;
import com.sparta.trelloproject.domain.list.repository.TaskListRepository;
import com.sparta.trelloproject.domain.member.entity.Member;
import com.sparta.trelloproject.domain.member.repository.MemberRepository;
import com.sparta.trelloproject.domain.notification.service.NotificationService;
import com.sparta.trelloproject.domain.user.entity.User;
import com.sparta.trelloproject.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final TaskListRepository taskListRepository;
    private final MemberRepository memberRepository;
    private final NotificationService notificationService;
    private final CardAssigneeRepository cardAssigneeRepository;
    private final UserRepository userRepository;
    private final ViewCountService viewCountService;

    @CreateActivity
    @Transactional
    public CardSaveResponse createCard(AuthUser authUser, Long boardId, Long listId, CardSaveRequest request) {

        User user = User.fromAuthUser(authUser);

        Member member = findMember(user);

        TaskList taskList = findTaskList(listId);

        Long cardIndex = (long) taskList.getCards().size() + 1;

        Card card = new Card(request, cardIndex, taskList);

        if (request.getAssignUser() != null) {
            User assignUser = findUser(request.getAssignUser());
            designate(card, assignUser);
        }

        cardRepository.save(card);

        // 카드 생성 알림을 NotificationService에서 처리
        notificationService.sendCardCreationNotification(user.getEmail(), boardId, listId, card.getId());

        return new CardSaveResponse(card);

    }

    @CreateActivity
    @Transactional
    public CardSaveResponse updateCard(AuthUser authUser, Long listId, Long cardId, CardUpdateRequest request) {

        User user = User.fromAuthUser(authUser);

        Member member = findMember(user);

        TaskList taskList = findTaskList(listId);

        Long totalCardIndex = (long) taskList.getCards().size();

        Card card = findCard(cardId);

        card.updateCard(request);

        if (request.getIndex() != null) {
            card.changeCardIndex(taskList, card, listId, request.getIndex(), totalCardIndex);
        }

        if (request.getAssignUser() != null) {
            User assignUser = findUser(request.getAssignUser());
            designate(card, assignUser);
        }

        return new CardSaveResponse(card);

    }

    public CardDetailResponse searchCard(Long cardId) {
        return cardRepository.findByCardDetail(cardId);
    }

    public ViewCountSearchResponse viewCountSearch(Long cardId) {
        viewCountService.incrementViewCount(cardId);
        return new ViewCountSearchResponse(viewCountService.getViewCount(cardId));
    }

    public Page<CardSearchResponse> conditionSearchCard(int page, int size, String title, String description, LocalDateTime deadline, Long assignId) {

        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Card> cards = cardRepository.findByCondition(title, description, deadline, assignId, pageable);

        return cards.map(this::createCardSearchResponse);

    }

    @Transactional
    public void deleteCard(AuthUser authUser, Long boardId, Long listId, Long cardId) {

        User user = User.fromAuthUser(authUser);

        Member member = findMember(user);

        Card card = findCard(cardId);

        cardRepository.delete(card);

    }


    // 카드 담당자 지정
    public void designate(Card card, User user) {
        CardAssignee cardAssignee = new CardAssignee(card, user);
        cardAssigneeRepository.save(cardAssignee);

    }

    // User 조회
    public User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    // TaskList 조회
    public TaskList findTaskList(Long listId) {
        return taskListRepository.findById(listId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    // Card 조회
    public Card findCard(Long cardId) {
        return cardRepository.findById(cardId)
                .orElseThrow(() -> new CustomException(ErrorCode.CARD_NOT_FOUND));
    }

    // 맴버 조회(읽기 전용일 시 예외)
    public Member findMember(User user) {
        return memberRepository.findByUserId(user.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.ROLE_ERROR, "읽기 전용 맴버로 카드 생성, 수정이 불가능합니다."));
    }

    private CardSearchResponse createCardSearchResponse(Card card) {
        return new CardSearchResponse(card);
    }
}
