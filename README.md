# Locals
## Registration API 
#### POST "/signup"  

RequestBody: Request Body
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
          "password": "harin123",
          "registrationDate": "2020-05-03T14:27:42.833"
         }
 ```
## Login API 
#### POST "/login"
RequestBody: Request Parameters
```
            username : harin
            password : harin123
```
Response :
```
         {  
          "userID": 1,
          "username": "harin",
          "password": "harin123",
          "registrationDate": "2020-05-03T14:27:42.833"
         }
```
## Dummy API
#### POST "/dummy"
Response:
```
            Session extended to 65 seconds.
```
## Logout API
#### POST "/logout"
Response:
```
            Logged out.
```
