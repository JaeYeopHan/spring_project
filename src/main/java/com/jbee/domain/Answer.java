package com.jbee.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Jbee on 2016. 10. 21..
 */

@Entity
public class Answer extends AbstractEntity {
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    @JsonProperty
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_question"))
    @JsonProperty
    private Question question;

    @Lob
    @JsonProperty
    private String contents;

    public Answer() {
    }

    public Answer(User writer, String contents, Question question) {
        this.writer = writer;
        this.contents = contents;
        this.question = question;
    }

    public boolean isSameWriter(User loginUser) {
        return loginUser.equals(this.writer);
    }
}
