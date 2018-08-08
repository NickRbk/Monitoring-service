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
@Entity
@Table(name = "tweets")
public class Tweet extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at_t")
    private Date createdAtTwitter;

    private String text;

    @Column(name = "text_url")
    private String textUrl;

    @Column(name = "favourites_count")
    private Integer favouritesCount;

    @Column(name = "retweet_count")
    private Integer retweetCount;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "original_author_id ")
    private TwitterUser originalAuthor;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "twitter_user_id ")
    private TwitterUser targetUser;
}
