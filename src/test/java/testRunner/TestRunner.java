package testRunner;

import io.qameta.allure.Allure;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.annotations.Test;
import user.User;

import java.io.IOException;

public class TestRunner {
    User user;

    @Test(priority = 4, description = "Calling the login API by giving valid credentials")
    public void callingLoginAPI() throws ConfigurationException, IOException {
        user = new User();
        user.callingLoginAPI();
        Allure.description("After calling login API by valid credentials a token will be generated for authorization");

    }

    @Test(priority = 1, description = "Incorrect email provided for login")
    public void incorrectEmail() throws IOException {
        user = new User();
        user.incorrectEmail();
        Allure.description("Will give 'User not found' and status '404' if incorrect email is provided");
    }

    @Test(priority = 2, description = "Incorrect password provided for login")
    public void incorrectPassword() throws IOException {
        user = new User();
        user.incorrectPassword();
        Allure.description("Will give 'Password incorrect' message and status '401' if incorrect password is provided");
    }

    @Test(priority = 3, description = "No password provided for login")
    public void blankPassword() throws IOException {
        user = new User();
        user.blankPassword();
        Allure.description("Will give 'Password incorrect' message and status '401' if no password is provided");
    }

    @Test(priority = 7, description = "user list will be extracted by giving proper authorization")
    public void getUserList() throws IOException {
        user = new User();
        user.getUserList();
        Allure.description("After giving proper authorization token user list will be extracted where the status code is 200, the first id of the user is 33 and the message is 'User List'");
    }

    @Test(priority = 5, description = "Cannot get user list for incorrect authorization token")
    public void getUserListForIncorrectAuth() throws IOException {
        user = new User();
        user.getUserListForIncorrectAuth();
        Allure.description("After giving incorrect authorization token,won't be able to get user list,status code will be 403 and message will be 'Token expired!'");
    }

    @Test(priority = 6, description = "If user does not input token will give 401 status code")
    public void getUserListForBlankAuthorizationToken() throws IOException {
        user = new User();
        user.getUserListForBlankAuthorizationToken();
        Allure.description("If no authorization token is given will give 401 status code and 'No token found' message and cannot derive user list");
    }

    @Test(priority = 9, description = "New User created")
    public void createUser() throws ConfigurationException, IOException {
        user = new User();
        user.createUser();
        Allure.description("The user is created through Authorization and secret key, name,email,password,phone_number,nid and role is set uniquely." +
                "Status code is 201 as created new user and 'User created successfully will be generated.' ");
    }

    @Test(priority = 8, description = "Already Created User")
    public void alreadyCreatedUser() throws ConfigurationException, IOException {
        user = new User();
        user.alreadyCreatedUser();
        Allure.description("User tried to sign up/create on creadentials that is already being authenticated or signed up, 208 status code will be given" +
                "'User already exists' message will be given.");
    }

    @Test(priority = 10, description = "Search user using the stored id")
    public void searchUser() throws IOException {
        user = new User();
        user.searchUser();
        Allure.description("Search user using stored id will give status as 200 and user id shall be equal to the stored id in the environment variable");
    }

    @Test(priority = 11, description = "Search user for invalid id")
    public void searchUserForInvalidId() throws IOException {
        user = new User();
        user.searchUserForInvalidId();
        Allure.description("Search user for invalid id should give 401 as status code and user should be null ");
    }

    @Test(priority = 12, description = "Search user failing for invalid secret key")
    public void searchUserForInvalidSecretKey() throws IOException {
        user = new User();
        user.searchUserForInvalidSecretKey();
        Allure.description("Search user for invalid secret key will give 401 as status code and 'Secret auth key validation failure!' as error message");
    }

    @Test(priority = 13, description = "User is updated based on the stored id on the variables")
    public void userUpdated() throws IOException {
        user = new User();
        user.updateUser();
        Allure.description("User updated using the stored id should give 200 as Status code and 'user updated successfully message!'");
    }

    @Test(priority = 14, description = "Users phone number is updated by using HTTP Patch method")
    public void userUpdatedPhoneNumber() throws IOException {
        user = new User();
        user.updateUserPhoneNumber();
        Allure.description("User's phone number is updated by using the stored id and the status code will be 200" +
                "and the users phone number is matched with the updated user phone number ");
    }
    @Test(priority = 15,description = "Delete user")
    public void deleteUser() throws IOException {
        user=new User();
        user.deleteUser();
        Allure.description("Deleting user using the stored Id in the variables , status code 200 will be returned and 'user deleted successfully' message will be derived");
    }
    @Test(priority = 16,description = "Already Deleted User")
    public void alreadyDeletedUser() throws IOException {
        user=new User();
        user.alreadyDeleteUser();
        Allure.description("Already deleted user cannot be deleted again and status code of 404 will be returned and 'user not found' message will be derived");

    }
    @Test(priority = 17,description = "Searching deleted user")
    public void searchingDeletedUser() throws IOException {
        user=new User();
        user.searchDeletedUser();
        Allure.description("Searching deleted user will give 200 as status code and user will be null");
    }
}
