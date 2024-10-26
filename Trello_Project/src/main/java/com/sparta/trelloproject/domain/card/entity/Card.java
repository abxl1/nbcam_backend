package com.sparta.trelloproject.domain.card.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.trelloproject.domain.card.dto.request.CardSaveRequest;
import com.sparta.trelloproject.domain.card.dto.request.CardUpdateRequest;
import com.sparta.trelloproject.domain.comment.entity.Comment;
import com.sparta.trelloproject.domain.list.entity.TaskList;
import com.sparta.trelloproject.domain.uploadFile.entity.UploadFile;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "cards", indexes = {
        @Index(name = "idx_card_title", columnList = "card_title"),
        @Index(name = "idx_card_taskList_id", columnList = "taskList_id")
})
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UploadFile> uploadFiles = new ArrayList<>();

    @Column(name = "card_title", nullable = false)
    private String title;

    @Column(name = "card_description", nullable = true)
    private String description;

    @Column(name = "card_index", nullable = false)
    private Long index;

    @Column(name = "card_deadline", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime deadline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taskList_id")
    private TaskList taskList;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CardAssignee> cardAssignees = new ArrayList<>();

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CardActivity> cardActivities = new HashSet<>();

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 10)
    private Set<Comment> comments = new HashSet<>();

    public Card(CardSaveRequest request, Long cardIndex, TaskList taskList) {
        this.title = request.getTitle();
        this.description = request.getDescription();
        this.deadline = request.getDeadline();
        this.index = cardIndex;
        this.taskList = taskList;
    }


    public void updateCard(CardUpdateRequest request) {

        if (request.getTitle() != null) {
            this.title = request.getTitle();
        }

        if (request.getDescription() != null) {
            this.description = request.getDescription();
        }

        if (request.getDeadline() != null) {
            this.deadline = request.getDeadline();
        }

    }


    public void changeCardIndex(TaskList taskList, Card card, Long listId, Long index, Long totalCardIndex) {

        List<Card> cards = taskList.getCards();

        List<Card> sortedCards = cards.stream()
                .sorted(Comparator.comparing(Card::getIndex))
                .toList();

        if (index > card.getIndex()) {
            for (int i = 0; i < index; i++) {
                sortedCards.get(i).dcreaseIndex();
            }
        } else {
            for (int i = (int) (index + 1); i < cards.size(); i++) {
                sortedCards.get(i).increaseIndex();
            }
        }

        card.changeIndex(index);

    }

    public void dcreaseIndex() {
        this.index -= 1L;
    }

    public void increaseIndex() {
        this.index += 1L;
    }

    public void changeIndex(Long index) {
        this.index = index;
    }

}
