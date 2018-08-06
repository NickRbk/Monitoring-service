# Monitoring service

## Overview

This web app provides ability to create users, attach to them links
to social media and shows users posts in them.

### User controller (`api/users`)

- `/` (GET) - get all users
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

- `/` (POST) - save new user
```json
{
	"email": "*****",
	"firstName": "*****",
	"lastName": "*****"
}
```

- `/{userId}` (PATCH) - update user by id
```json
{
	"email": "*****",
	"firstName": "*****",
	"lastName": "*****"
}
```

- `/{userId}` (DELETE) - delete user by id

### Social media controller (`api/users/{userId}/media`)

- `/` (POST) - set social media links to user
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