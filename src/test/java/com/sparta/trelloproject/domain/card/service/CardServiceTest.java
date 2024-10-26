//package com.sparta.trelloproject.domain.card.service;
//
//import com.sparta.trelloproject.common.aop.CardActivityAspect;
//import com.sparta.trelloproject.domain.auth.entity.AuthUser;
//import com.sparta.trelloproject.domain.board.entity.Board;
//import com.sparta.trelloproject.domain.card.dto.request.CardSaveRequest;
//import com.sparta.trelloproject.domain.card.dto.request.CardUpdateRequest;
//import com.sparta.trelloproject.domain.card.dto.response.CardSaveResponse;
//import com.sparta.trelloproject.domain.card.entity.Card;
//import com.sparta.trelloproject.domain.card.entity.CardActivity;
//import com.sparta.trelloproject.domain.card.repository.CardActivityRepository;
//import com.sparta.trelloproject.domain.card.repository.CardRepository;
//import com.sparta.trelloproject.domain.list.entity.TaskList;
//import com.sparta.trelloproject.domain.list.repository.TaskListRepository;
//import com.sparta.trelloproject.domain.user.enums.UserRole;
//import jakarta.servlet.http.HttpServletRequest;
//import org.aspectj.lang.JoinPoint;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Arrays;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//class CardServiceTest {
//
//    @Mock
//    private HttpServletRequest request;
//
//    @Mock
//    private CardActivityService cardActivityService;
//
//    @Mock
//    private CardActivityRepository cardActivityRepository;
//
//    @InjectMocks
//    private CardActivityAspect cardActivityAspect;
//
//    @Mock
//    private JoinPoint joinPoint;
//
//    @Mock
//    private Card card;
//
//    @Mock
//    private AuthUser authUser;
//
//    @Mock
//    private TaskListRepository taskListRepository;
//
//    @Mock
//    private CardRepository cardRepository;
//
//    @InjectMocks
//    private CardService cardService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void 카드생성성공() {
//        // Given
//        Long listId = 1L;
//        AuthUser authUser = new AuthUser(1L, "emain", UserRole.ROLE_ADMIN);
//        CardSaveRequest request = new CardSaveRequest("Title", "Description", null);
//        Card card = new Card(request, 1L);
//
//        when(taskListRepository.findById(listId)).thenReturn(Optional.of(new TaskList()));
//        when(cardRepository.save(any(Card.class))).thenReturn(card);
//
//        // When
//        CardSaveResponse response = cardService.createCard(authUser, 1L, listId, request);
//
//        // Then
//        assertNotNull(response);
//        assertEquals(card.getTitle(), response.getTitle());
//        assertEquals(card.getDescription(), response.getDescription());
//        verify(cardRepository, times(1)).save(any(Card.class));
//    }
//
//    @Test
//    public void 카드수정성공() {
//
//        // Given
//        Long listId = 1L;
//        AuthUser authUser = new AuthUser(1L, "emain", UserRole.ROLE_ADMIN);
//        CardSaveRequest request = new CardSaveRequest("Title", "Description", null);
//        Card card = new Card(request, 1L);
//
//        CardUpdateRequest updateRequest = new CardUpdateRequest("title update", "description update", null, null);
//
//        when(taskListRepository.findById(listId)).thenReturn(Optional.of(new TaskList()));
//        when(cardRepository.save(any(Card.class))).thenReturn(card);
//        when(cardRepository.findById(1L)).thenReturn(Optional.of(new Card()));
//
//        // When
//        CardSaveResponse response = cardService.updateCard(authUser, listId, 1L, updateRequest);
//        Card result = cardRepository.findById(1L).orElseThrow();
//
//        // Then
//        assertNotNull(response);
//        assertEquals("title update" , result.getTitle());
//        assertEquals("description update", result.getDescription());
//    }
//
//    @Test
//    public void 카드순서변경성공() {
//        // Given
//        Long listId = 1L;
//        AuthUser authUser = new AuthUser(1L, "emain", UserRole.ROLE_ADMIN);
//
//        Card card1 = new Card(new CardSaveRequest("Title 1", "Description 1", null), 1L);
//        Card card2 = new Card(new CardSaveRequest("Title 2", "Description 2", null), 2L);
//        Card card3 = new Card(new CardSaveRequest("Title 3", "Description 3", null), 3L);
//
//        CardUpdateRequest updateRequest = new CardUpdateRequest(null, null, null, 3L);
//
//        TaskList taskList = mock(TaskList.class);
//        when(taskList.getCards()).thenReturn(Arrays.asList(card1, card2, card3));
//        when(taskListRepository.findById(listId)).thenReturn(Optional.of(taskList));
//
//        when(cardRepository.save(any(Card.class))).thenReturn(card1).thenReturn(card2).thenReturn(card3);
//        when(cardRepository.findById(1L)).thenReturn(Optional.of(card1));
//        when(cardRepository.findById(2L)).thenReturn(Optional.of(card2));
//        when(cardRepository.findById(3L)).thenReturn(Optional.of(card3));
//
//        // When
//        CardSaveResponse response = cardService.updateCard(authUser, listId, 1L, updateRequest);
//        Card result1 = cardRepository.findById(1L).orElseThrow();
//        Card result2 = cardRepository.findById(2L).orElseThrow();
//        Card result3 = cardRepository.findById(3L).orElseThrow();
//
//        // Then
//        assertNotNull(response);
//        assertEquals(3L, result1.getIndex());
//        assertEquals(1L, result2.getIndex());
//        assertEquals(2L, result3.getIndex());
//    }
//
//}