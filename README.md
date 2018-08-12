# Monitoring service

## Overview

This web app provides ability to create targetUsers, attach to them links
to social media and shows targetUsers posts in them. All operation on service you
(customer) can do after registration ang getting auth token.
Information about social activity _**updates every 30 min by schedule**_.

There is two open routes:
- `auth/sign-up` (POST) - create user (customer). Email should be unique.
```json
{
  "email": "*****",
  "firstName": "*****",
  "lastName": "*****",
  "password": "*****",
  "phoneNumber": "*****"
}
```
#### To login on service on top level exists route `/login`, so
- `/login` (POST)
```json
{
  "email": "*****",
  "password": "*****"
}
```
After that customer get access token (JWT) which pinned to header and open access
to service.

The bellow routes are protected and need auth token
### Auth controller (`/auth`)

- `/` (PATCH) - update customer
```json
{
  "email": "*****",
  "firstName": "*****",
  "lastName": "*****",
  "password": "*****",
  "phoneNumber": "*****"
}
```
- `/` (DELETE) - delete customer (all associated targetUsers deleted also)

### TargetUser controller (`api/users`)

- `/` (GET) - get all targetUsers
```json
[
    {
        "createdAt": "2018-08-09T11:12:18.106+0000",
        "updatedAt": "2018-08-09T11:12:18.106+0000",
        "id": 3,
        "customerId": 5,
        "firstName": "*****",
        "lastName": "*****",
        "socialMedia": {
            "id": 3,
            "targetUserId": 3,
            "twitterProfile": {
                "id": 3,
                "targetUserId": 3,
                "twitterUser": {
                    "createdAt": "2018-08-09T11:17:32.235+0000",
                    "updatedAt": "2018-08-09T11:17:32.235+0000",
                    "id": 4,
                    "userName": "*****",
                    "screenName": "*****",
                    "location": "*****",
                    "description": "*****",
                    "followersCount": 23,
                    "friendsCount": 213,
                    "favouritesCount": 34,
                    "statusesCount": 123,
                    "profileImageURL": "*****",
                    "target": true
                }
            }
        }
    }
]
```

- `/` (POST) - save new targetUser
```json
{
	"firstName": "*****",
	"lastName": "*****"
}
```

- `/{userId}` (PATCH) - update targetUser by id
```json
{
	"firstName": "*****",
	"lastName": "*****"
}
```

- `/{userId}` (DELETE) - delete targetUser by id

- `/{userId}/media` (POST) - set social media nickname to targetUser (like nickname at Twitter)
```json
{
	"alias": "*****"
}
```

### Social media controller (`api/media`)

- `?page=*&size=*[[ &order={HERE YOU CAN SET desc OR asc, OPTIONAL PARAMETER} ]]` 
(GET) - get posts of targeted users with pagination (set page and size value)
and (OPTIONALLY) ordered by posts' creation date
```json
{
    "content": [
        {
            "createdAt": "2018-08-09T08:45:21.508+0000",
            "updatedAt": "2018-08-09T08:50:16.639+0000",
            "id": 23,
            "createdAtTwitter": "2018-08-07T10:08:57.000+0000",
            "text": "*****",
            "textUrl": null,
            "favouritesCount": 0,
            "retweetCount": 0,
            "originalAuthor": null,
            "targetUser": {
                "createdAt": "2018-08-09T08:45:13.273+0000",
                "updatedAt": "2018-08-09T08:45:13.273+0000",
                "id": 1,
                "userName": "*****",
                "screenName": "*****",
                "location": "*****",
                "description": "*****",
                "followersCount": 23,
                "friendsCount": 34,
                "favouritesCount": 45,
                "statusesCount": 65,
                "profileImageURL": "*****",
                "target": true
            }
        }
    ],
    "pageable": {
        "sort": {
            "sorted": false,
            "unsorted": true
        },
        "offset": 0,
        "pageNumber": 0,
        "pageSize": 1,
        "paged": true,
        "unpaged": false
    },
    "last": false,
    "totalElements": 60,
    "totalPages": 60,
    "size": 1,
    "number": 0,
    "sort": {
        "sorted": false,
        "unsorted": true
    },
    "first": true,
    "numberOfElements": 1
}
```

## Prerequisites
- initially install `twitter4j-spring-boot-starter` from [there](https://github.com/sivaprasadreddy/twitter4j-spring-boot-starter)
by `mvn clean install`
- app should be run with "dev" profile
- start postgreSQL and write actual info into `resources/application-dev.yml`.

## How to start app?
1) download project `git clone https://github.com/NickRbk/Monitoring-service.git`
2) enter to downloaded folder
3) run cmd `mvn spring-boot:run -Dspring.profiles.active=dev`