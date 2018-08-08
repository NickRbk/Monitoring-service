package com.petproject.monitoring.domain.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Entity
//@Table(name = "tweets")
public class Tweet {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

//    @Column(name = "created_at")
    private Date createdAt;

    private String text;

//    @Column(name = "text_url")
    private String textUrl;

//    @Column(name = "favourite_count")
    private Integer favouriteCount;

//    @Column(name = "retweet_count")
    private Integer retweetCount;

    private TwitterUser originalAuthor;

    private TwitterUser user;

}
