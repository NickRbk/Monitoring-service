# Monitoring service

## Overview

This web app provides ability to create targetUsers, attach to them links
to social media and shows targetUsers posts in them.

### User controller (`api/targetUsers`)

- `/` (GET) - get all targetUsers
```json
[
    {
        "createdAt": "*****",
        "updatedAt": "*****",
        "id": 1,
        "email": "*****",
        "firstName": "*****",
        "lastName": "*****",
        "socialMedia": {
            "id": 1,
            "userId": 1,
            "facebookUrl": "*****",
            "twitterUrl": "*****"
        }
    }
]
```

- `/` (POST) - save new targetUser
```json
{
	"email": "*****",
	"firstName": "*****",
	"lastName": "*****"
}
```

- `/{userId}` (PATCH) - update targetUser by id
```json
{
	"email": "*****",
	"firstName": "*****",
	"lastName": "*****"
}
```

- `/{userId}` (DELETE) - delete targetUser by id

### Social media controller (`api/targetUsers/{userId}/media`)

- `/` (POST) - set social media links to targetUser
```json
{
	"facebookUrl": "*****",
	"twitterUrl": "*****"
}
```


## Prerequisites
- app should be run with "dev" profile
- start postgreSQL and write actual info into `resources/application-dev.yml`.

## How to start app?
1) download project `git clone https://github.com/NickRbk/Monitoring-service.git`
2) enter to downloaded folder
3) run cmd `mvn spring-boot:run -Dspring.profiles.active=dev`