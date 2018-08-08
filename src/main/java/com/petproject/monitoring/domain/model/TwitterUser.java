package com.petproject.monitoring.domain.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Entity
//@Table(name = "twitter_user")
public class TwitterUser {
    private String userName;

    private String screenName;

    private String location;

    private String description;

    private Integer followersCount;

    private Integer friendsCount;

    private Integer favouritesCount;

    private Integer statusesCount;

    private String originalProfileImageURL;
}
