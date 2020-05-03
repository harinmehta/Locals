# Locals Service Private Limited Internship
## Registration API 
#### POST "/signup"  

Request: Request Body
1. Acceptable Request-
```
         {
          "username" : "harin",
          "password" : "harin123"
         }
```
Response:
```
        {  
          "userID": 1,
          "username": "harin",
          "password": "$2a$10$sRtXAHN00PTIPWW29mVMh.P9dQm822Wonp6a9u/P5djw.6UT0ga8.",
          "registrationDate": "2020-05-03T14:27:42.833"
         }
 ```
2. Bad Requests-
```
         {
          "username" : "max"
         },
         {
          "password" : "max123"
         },
         {
          "username" : "  ",
          "password" : " "
         },
         {
          "username" : "harin",
          "password" : "xyz123"
         }
```
Note- In the last case, if user with the username "harin" is already present in db, it's unacceptable.<br />
Response-
```
         400 Bad Request
```
## Login API 
#### POST "/login"
Request: Request Parameters<br />
1. Acceptable Requests
```
            username : harin
            password : harin123
```
Response :
```
         {  
          "userID": 1,
          "username": "harin",
          "password": "$2a$10$sRtXAHN00PTIPWW29mVMh.P9dQm822Wonp6a9u/P5djw.6UT0ga8.",
          "registrationDate": "2020-05-03T14:27:42.833"
         }
```
2. Bad Requests
```
         username : zyfer
         password : 1234
```
Note : This user is not in db<br />
Response:
```
         {
             "timestamp": "2020-05-03T17:39:54.925+0000",
             "status": 403,
             "error": "Forbidden",
             "message": "Access Denied",
             "path": "/login"
         }
```
## Dummy API
#### POST "/dummy"
Response for logged in user:
```
            Session extended to 65 seconds.
```
Response for a user who is not logged in:
```
         {
             "timestamp": "2020-05-03T17:39:54.925+0000",
             "status": 403,
             "error": "Forbidden",
             "message": "Access Denied",
             "path": "/dummy"
         }
```
## Logout API
#### POST "/logout"
Response:
```
            Logged out.
```
