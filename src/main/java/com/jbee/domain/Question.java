package com.jbee.domain;

import org.hibernate.annotations.Generated;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Jbee on 2016. 10. 14..
 */
@Entity
public class Question {
    @Id
    @GeneratedValue
    private Long id;

    private String writer;
    private String title;
    private String contents;

    public Question(){}
    public Question(String writer, String title, String contents) {
        super();
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }
}
