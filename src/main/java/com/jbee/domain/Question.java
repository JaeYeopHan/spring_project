package com.jbee.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Jbee on 2016. 10. 14..
 */
@Entity
public class Question extends AbstractEntity{

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    @JsonProperty
    private User writer;

    @JsonProperty
    private String title;

    @JsonProperty
    private Integer countOfAnswer = 0; // default vaule를 지정해줄 때는 이렇게 해줄 수 있는데 import.sql에는 따로 지정해줘야 한다

    @Lob
    @JsonProperty
    private String contents;


    @OneToMany(mappedBy = "question")
    @OrderBy("createDate DESC")
    private List<Answer> answers;

    public void addAnswer() {
        this.countOfAnswer += 1;
    }

    public void deleteAnswer() {
        this.countOfAnswer -= 1;
    }

    public Question() {
    }

    public Question(User writer, String title, String contents) {
        super();
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public boolean isSameWriter(User sessionedUser) {
        return this.writer.equals(sessionedUser);//instance는 다르지만 갖고있는 hashcode 값이 같으면 true
    }

}
