//package com.sparta.trelloproject.domain.card.service;
//
//import com.sparta.trelloproject.common.exception.CustomException;
//import com.sparta.trelloproject.common.exception.ErrorCode;
//import com.sparta.trelloproject.domain.auth.entity.AuthUser;
//import com.sparta.trelloproject.domain.card.dto.request.CardSaveRequest;
//import com.sparta.trelloproject.domain.comment.dto.request.CommentRequest;
//import com.sparta.trelloproject.domain.comment.dto.response.CommentResponse;
//import com.sparta.trelloproject.domain.comment.entity.Comment;
//import com.sparta.trelloproject.domain.comment.repository.CommentRepository;
//import com.sparta.trelloproject.domain.card.entity.Card;
//import com.sparta.trelloproject.domain.card.repository.CardRepository;
//import com.sparta.trelloproject.domain.comment.service.CommentService;
//import com.sparta.trelloproject.domain.member.entity.Member;
//import com.sparta.trelloproject.domain.member.repository.MemberRepository;
//import com.sparta.trelloproject.domain.notification.service.NotificationService;
//import com.sparta.trelloproject.domain.user.entity.User;
//import com.sparta.trelloproject.domain.user.enums.UserRole;
//import com.sparta.trelloproject.domain.user.repository.UserRepository;
//import com.sparta.trelloproject.domain.workspace.entity.Workspace;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//class CommentServiceTest {
//
//    @Mock
//    private CommentRepository commentRepository;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private CardRepository cardRepository;
//
//    @Mock
//    private MemberRepository memberRepository;
//
//    @Mock
//    private AuthUser authUser;
//
//    @InjectMocks
//    private CommentService commentService;
//
//    @Mock
//    private NotificationService notificationService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void 댓글작성성공() {
//        // Given
//        Long cardId = 1L;
//        AuthUser authUser = new AuthUser(1L, "email", UserRole.ROLE_USER);
//        CommentRequest commentRequest = new CommentRequest("This is a comment", "👍");
//
//        // User 객체 생성 (생성자 사용)
//        User user = new User("email@asdfds.com", "password", UserRole.ROLE_USER);
//
//        // CardSaveRequest 객체 생성
//        CardSaveRequest cardSaveRequest = new CardSaveRequest("Card Title", "Card Description", null);
//
//        // Card 객체 생성 (CardSaveRequest 사용)
//        Card card = new Card(cardSaveRequest, 1L);
//
//        // Member 객체 생성 (생성자 사용)
//        Member member = new Member(user, new Workspace());
//
//        // UserRepository mock 설정
//        when(userRepository.findById(authUser.getUserId())).thenReturn(Optional.of(user));
//
//        // CardRepository mock 설정
//        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));
//
//        // MemberRepository mock 설정
//        when(memberRepository.findByUserId(authUser.getUserId())).thenReturn(Optional.of(member));
//
//        // CommentRepository mock 설정
//        when(commentRepository.save(any(Comment.class))).thenReturn(new Comment(user, "This is a comment", "👍", card));
//
//        // When
//        CommentResponse response = commentService.saveComment(authUser, cardId, commentRequest);
//
//        // Then
//        assertNotNull(response);
//        assertEquals(commentRequest.getText(), response.getText());
//        assertEquals(commentRequest.getEmoji(), response.getEmoji());
//        verify(commentRepository, times(1)).save(any(Comment.class));
//        verify(notificationService, times(1)).sendSlackNotification(anyString(), anyString(), anyString());
//        verify(notificationService, times(1)).sendDiscordNotification(anyString(), anyString(), anyString());
//    }
//
//    @Test
//    public void 댓글수정성공() {
//        // Given
//        Long commentId = 1L;
//        AuthUser authUser = new AuthUser(1L, "email", UserRole.ROLE_USER);
//        CommentRequest commentRequest = new CommentRequest("Updated comment", "😃");
//
//        User user = new User("email@asdfds.com", "asdf",UserRole.ROLE_USER);
//        Comment existingComment = new Comment(user, "Old comment", "😊", new Card());
//        existingComment.setCommentId(commentId);
//
//        when(userRepository.findById(authUser.getUserId())).thenReturn(Optional.of(user));
//        when(commentRepository.findById(commentId)).thenReturn(Optional.of(existingComment));
//
//        // When
//        CommentResponse response = commentService.updateComment(authUser, commentId, commentRequest);
//
//        // Then
//        assertNotNull(response);
//        assertEquals("Updated comment", response.getText());
//        assertEquals("😃", response.getEmoji());
//        verify(commentRepository, times(1)).save(any(Comment.class));
//    }
//
//    @Test
//    public void 댓글삭제성공() {
//        // Given
//        Long commentId = 1L;
//        AuthUser authUser = new AuthUser(1L, "email", UserRole.ROLE_USER);
//
//        User user = new User("email@asdfds.com", "asdf",UserRole.ROLE_USER);
//        Comment comment = new Comment(user, "This is a comment", "👍", new Card());
//        comment.setCommentId(commentId);
//
//        when(userRepository.findById(authUser.getUserId())).thenReturn(Optional.of(user));
//        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
//
//        // When
//        commentService.deleteComment(authUser, commentId);
//
//        // Then
//        verify(commentRepository, times(1)).delete(comment);
//    }
//
//    @Test
//    public void 댓글작성권한없음() {
//        // Given
//        Long cardId = 1L;
//        AuthUser authUser = new AuthUser(1L, "email", UserRole.ROLE_USER);
//        CommentRequest commentRequest = new CommentRequest("This is a comment", "👍");
//
//        User user = new User("email@asdfds.com", "asdf", UserRole.ROLE_USER);
//        Card card = new Card();
//
//        when(userRepository.findById(authUser.getUserId())).thenReturn(Optional.of(user));
//        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));
//        when(memberRepository.findByUserId(authUser.getUserId())).thenReturn(Optional.empty());  // 멤버가 없음을 시뮬레이션
//
//        // When / Then
//        CustomException exception = assertThrows(CustomException.class, () -> {
//            commentService.saveComment(authUser, cardId, commentRequest);
//        });
//
//        assertEquals(ErrorCode.MEMBER_NOT_FOUND, exception.getErrorCode());  // MEMBER_NOT_FOUND로 수정
//        assertEquals("멤버를 찾을 수 없습니다.", exception.getMessage());
//    }
//}
//
