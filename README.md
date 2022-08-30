# Rest_Assured_API_Testing
### An [API](http://dmoney.professionaltrainingbd.com) is tested by using REST Assured framework integrated with TestNG as testing framework for validation purpose. Here, the status codes, validation messages and the flow of API is tested using a Dmoney API where there is login,searching,creating,updating and deleting features.
Here the following tasks are done:
- Login feature tested using proper valiadtion,negative test cases added for email and password.
- Can get user list by user authorization token, both positive and negative test cases are added for it.
- Can search any user by proper id.
- Can create a user by random name,email,password,nid and phone number using proper validation and secret key token.
- Can update any user by the corresponding id, validated using PUT and PATCH method.
- Can delete any user by the id, negative test cases are added for it.
- The variables are set and used from config.properties file.
### Technology: </br>
- Tool: REST Assured
- IDE: Intellij
- Build tool: Gradle
- Language: Java
- Test_Runner: TestNG

### Prerequisites</br>
- Install jdk 8 or any LTS versio
- Configure JAVA_HOME and GRADLE_HOME
- Download Allure 2.18.1 and configure environment path
- Stable internet connection

### Project Run
- Clone the repo.
- Open cmd in the root folder.
#### Run the Automation Script by the following command:
 ```
 gradle clean test 
 ```
- The following report is generated:

![Sample report](https://user-images.githubusercontent.com/55280106/187321078-523bc5b0-1d5d-424d-bda4-28f151ba247e.png)

- After automation to view allure report , give the following commands:
 ```
allure generate allure-results --clean -o allure-report
allure serve allure-results
 ```
**Here is the Allure report overview:**

![allure-overview](https://user-images.githubusercontent.com/55280106/187321116-49b3dec0-7943-42ca-aa59-5071eb6f6b38.png)

**Below the suites run are shown via Allure :**

![allure-suites](https://user-images.githubusercontent.com/55280106/187321124-28feca0b-2539-47f0-956c-34fe06134928.png)

