package com.sparta.trelloproject.domain.card.controller;

import com.sparta.trelloproject.domain.auth.entity.AuthUser;
import com.sparta.trelloproject.domain.card.dto.request.CardSaveRequest;
import com.sparta.trelloproject.domain.card.dto.request.CardUpdateRequest;
import com.sparta.trelloproject.domain.card.dto.response.CardDetailResponse;
import com.sparta.trelloproject.domain.card.dto.response.CardSaveResponse;
import com.sparta.trelloproject.domain.card.dto.response.CardSearchResponse;
import com.sparta.trelloproject.domain.card.dto.response.ViewCountSearchResponse;
import com.sparta.trelloproject.domain.card.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping("/boards/{boardId}/lists/{listId}/cards")
    public ResponseEntity<CardSaveResponse> createCard(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long boardId,
            @PathVariable Long listId,
            @RequestBody CardSaveRequest request) {

        CardSaveResponse response = cardService.createCard(authUser, boardId, listId, request);
        return ResponseEntity.ok(response);

    }


    @PatchMapping("/boards/{boardId}/lists/{listId}/cards/{cardId}")
    public ResponseEntity<CardSaveResponse> updateCard(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long boardId,
            @PathVariable Long listId,
            @PathVariable Long cardId,
            @RequestBody CardUpdateRequest request
    ) {

        CardSaveResponse response = cardService.updateCard(authUser, listId, cardId, request);
        return ResponseEntity.ok(response);

    }


    @GetMapping("/boards/{boardId}/lists/{listId}/cards/{cardId}")
    public ResponseEntity<CardDetailResponse> searchCard(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long boardId,
            @PathVariable Long listId,
            @PathVariable Long cardId) {

        CardDetailResponse response = cardService.searchCard(cardId);
        return ResponseEntity.ok(response);

    }

    @GetMapping("/v1/cards/{cardId}")
    public ResponseEntity<ViewCountSearchResponse> viewCountSearch(@PathVariable Long cardId) {
        return ResponseEntity.ok(cardService.viewCountSearch(cardId));
    }


    @GetMapping("/cards/search")
    public ResponseEntity<Page<CardSearchResponse>> conditionSearchCard(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) LocalDateTime deadline,
            @RequestParam(required = false) Long assignId
    ){

        return ResponseEntity.ok(cardService.conditionSearchCard(page, size, title, description, deadline, assignId));

    }


    @DeleteMapping("/boards/{boardId}/lists/{listId}/cards/{cardId}")
    public ResponseEntity<String> deleteCard(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long boardId,
            @PathVariable Long listId,
            @PathVariable Long cardId) {

        cardService.deleteCard(authUser, boardId, listId, cardId);
        return ResponseEntity.ok("카드가 삭제됐습니다.");

    }

}
