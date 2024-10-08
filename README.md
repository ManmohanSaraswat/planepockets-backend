# Planepockets

## Steps to run the application
1. Clone the project to your folder.
2. Install maven 3.9.1 and unzip it in your C: Drive and put the path of mvn.exe in your system variables (to confirm run `mvn –-version` in cmd)
3. Download Jdk17 and install it (to confirm run `java –-version` in cmd)
4. In your proton folder, open command prompt and run mvn clean install
5. To run the application execute the following commands in command prompt.
a. `cd  /Proton/target`
b. `java -jar planepockets-0.0.1-SNAPSHOT.jar`


## Endpoints created till now in Planepockets

### 1. Register (POST Mapping)
    ENDPOINT: http://localhost:8080/api/v1.0/user/register

    Sample JSON
    {
        "loginId" : "johndoe0@gmail.com",
        "fullName" : "John Doe",
        "password" : "John@123",
        "contactNumber" : "+91 9358342345"
    }

### 2. Login (POST Mapping)
    ENDPOINT: http://localhost:8080/api/v1.0/user/login

    Sample JSON
    {
        "loginId" : "johndoe0@gmail.com",
        "password" : "John@123"
    }

### 3. Logout (GET Mapping)
    ENDPOINT: http://localhost:8080/api/v1.0/user/logout/{loginId}

    Sample Header
        Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtYW5tb2hhbnNhcmFzd2F0MEBnbWFpbC5jb20iLCJpYXQiOjE3MjE0MTU0MjgsImV4cCI6MTcyMTQzMzQyOH0.oNAjZLhUoEO-gGaZ19oBr5tvoXACiNvjoeW4ZXQBSmBm2fclRtQGwLO92SZULSqCWMuzhlMyUAaqx1o2hdTgog

### 4. Forgot Password (POST Mapping)
    ENDPOINT: http://localhost:8080/api/v1.0/user/forgot
    
    Sample JSON
    {
        "loginId" : "johndoe0@gmail.com"
    }

##### 4.1 After receiving password in mail
    ENDPOINT: http://localhost:8080/api/v1.0/user/forgot
    
    Sample JSON (After getting password)
    {
        "loginId" : "johndoe0@gmail.com",
        "otp" : "7241",
        "newPassword" : "John@1234"
    }

### 5. Reset Password (POST Mapping)
    ENDPOINT: http://localhost:8080/api/v1.0/user/reset
    
    Sample JSON
    {
        "loginId" : "johndoe0@gmail.com",
        "oldPassword" : "John@1234",
        "newPassword" : "John@12354"
    }

### 6. Authentication check (GET Mapping)
    ENDPOINT: http://localhost:8080/api/v1.0/user/user
    
    Sample Header
       Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtYW5tb2hhbnNhcmFzd2F0MEBnbWFpbC5jb20iLCJpYXQiOjE3MjE0MTU0MjgsImV4cCI6MTcyMTQzMzQyOH0.oNAjZLhUoEO-gGaZ19oBr5tvoXACiNvjoeW4ZXQBSmBm2fclRtQGwLO92SZULSqCWMuzhlMyUAaqx1o2hdTgog
