package user;

import Utils.Utils;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.CreateUserModel;
import model.UserLoginModel;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.Assert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class User {
    Properties props = new Properties();
    FileInputStream file;

    {
        try {
            file = new FileInputStream("./src/test/resources/config.properties");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void callingLoginAPI() throws IOException, ConfigurationException {
        props.load(file);
        UserLoginModel userLoginModel = new UserLoginModel("salman@grr.la", "1234");
        RestAssured.baseURI = props.getProperty("BaseURL");
        Response res = given().
                contentType("application/json")
                .body(userLoginModel)
                .when().post("/user/login")
                .then().assertThat().statusCode(200).extract().response();
        JsonPath jsonPath = res.jsonPath();
        String token = jsonPath.get("token");
        System.out.println(token);
        Utils.setCollectionVariable("token", token);
    }

    public void incorrectEmail() throws IOException {
        props.load(file);
        UserLoginModel userLoginModel = new UserLoginModel("salman@grr", "1234");
        RestAssured.baseURI = props.getProperty("BaseURL");
        Response res = given().contentType("application/json")
                .body(userLoginModel)
                .when().post("/user/login")
                .then().assertThat().statusCode(404).extract().response();
        JsonPath response = res.jsonPath();
        Assert.assertTrue(response.get("message").toString().contains("User not found"));

    }

    public void incorrectPassword() throws IOException {
        props.load(file);
        UserLoginModel userLoginModel = new UserLoginModel("salman@grr.la", "369");
        RestAssured.baseURI = props.getProperty("BaseURL");
        Response res = given().contentType("application/json")
                .body(userLoginModel)
                .when().post("/user/login")
                .then().assertThat().statusCode(401).extract().response();
        JsonPath response = res.jsonPath();
        Assert.assertTrue(response.get("message").toString().contains("Password incorrect"));
    }

    public void blankPassword() throws IOException {
        props.load(file);
        UserLoginModel userLoginModel = new UserLoginModel("salman@grr.la", "");
        RestAssured.baseURI = props.getProperty("BaseURL");
        Response res = given().contentType("application/json")
                .body(userLoginModel)
                .when().post("/user/login")
                .then().assertThat().statusCode(401).extract().response();
        JsonPath response = res.jsonPath();
        Assert.assertTrue(response.get("message").toString().contains("Password incorrect"));
    }

    public void getUserList() throws IOException {
        props.load(file);
        RestAssured.baseURI = props.getProperty("BaseURL");
        Response res = given().contentType("application/json")
                .header("Authorization", props.getProperty("token"))
                .when().get("/user/list")
                .then().assertThat().statusCode(200).extract().response();
        JsonPath response = res.jsonPath();
//        System.out.println(response.get("users").toString());
        Assert.assertEquals(response.get("users[0].id").toString(), "33");
//              System.out.println(response.get("message").toString());
        Assert.assertTrue(response.get("message").toString().contains("User list"));
    }

    public void getUserListForIncorrectAuth() throws IOException {
        props.load(file);
        RestAssured.baseURI = props.getProperty("BaseURL");
        Response res = given().contentType("application/json")
                .header("Authorization", "incorrect token")
                .when().get("/user/list")
                .then().assertThat().statusCode(403).extract().response();
        JsonPath response = res.jsonPath();
        System.out.println(response.get("error.message").toString());
        Assert.assertTrue(response.get("error.message").toString().contains("Token expired!"));
    }

    public void getUserListForBlankAuthorizationToken() throws IOException {
        props.load(file);
        RestAssured.baseURI = props.getProperty("BaseURL");
        Response res = given().contentType("application/json")
                .header("Authorization", "")
                .when().get("/user/list")
                .then().assertThat().statusCode(401).extract().response();
        JsonPath response = res.jsonPath();
        System.out.println(response.get("error.message").toString());
        Assert.assertTrue(response.get("error.message").toString().contains("No Token Found!"));
    }

    public void createUser() throws IOException, ConfigurationException {
        props.load(file);
        Faker faker = new Faker();
        String name = faker.name().fullName();
        String phone = faker.phoneNumber().phoneNumber();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        String nid = "641" + (int) Math.random() * ((9999999 - 1000000) + 1) + 9999999;
        RestAssured.baseURI = props.getProperty("BaseURL");
        CreateUserModel createUserModel = new CreateUserModel(name, email, password, phone, nid, "Customer");
        Response res = given().contentType("application/json")
                .header("Authorization", props.getProperty("token"))
                .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                .body("{\n" +
                        "    \"name\":\"" + name + "\",\n" +
                        "    \"email\":\"" + email + "\",\n" +
                        "    \"password\":\"" + password + "\",\n" +
                        "    \"phone_number\":\"" + phone + "\",\n" +
                        "    \"nid\":\"" + nid + "\",\n" +
                        "    \"role\":\"Customer\"\n" +
                        "}")
//                .body(createUserModel)
                .when().post("/user/create")
                .then().assertThat().statusCode(201).extract().response();
        JsonPath response = res.jsonPath();
        System.out.println(response.get("message").toString());
        Assert.assertTrue(response.get("message").toString().contains("User created successfully"));
        String id = response.get("user.id").toString();
        System.out.println(id);
        Utils.setCollectionVariable("id", id);
    }

    public void alreadyCreatedUser() throws IOException, ConfigurationException {
        props.load(file);
        RestAssured.baseURI = props.getProperty("BaseURL");
        Response res = given().contentType("application/json")
                .header("Authorization", props.getProperty("token"))
                .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                .body(" {\n" +
                        "        \"name\": \"Guillermo Beer\",\n" +
                        "        \"email\": \"Madonna0@yahoo.com\",\n" +
                        "        \"password\": \"fTTTI_KdnKnjv5R\",\n" +
                        "        \"phone_number\": \"01713648066\",\n" +
                        "        \"nid\": \"6413648066\",\n" +
                        "        \"role\": \"Customer\"\n" +
                        "}")
                .when().post("/user/create")
                .then().assertThat().statusCode(208).extract().response();
        JsonPath response = res.jsonPath();
        System.out.println(response.get("message").toString());
        Assert.assertTrue(response.get("message").toString().contains("User already exists"));
    }

    public void searchUser() throws IOException {
        props.load(file);
        RestAssured.baseURI = props.getProperty("BaseURL");
        Response res = given().contentType("application/json")
                .header("Authorization", props.getProperty("token"))
                .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                .when().get("/user/search?id=" + props.getProperty("id"))
                .then().assertThat().statusCode(200).extract().response();
        JsonPath response = res.jsonPath();
        System.out.println(response.get("user.id").toString());
        Assert.assertEquals(response.get("user.id").toString(), props.getProperty("id"));
    }

    public void searchUserForInvalidId() throws IOException {
        props.load(file);
        RestAssured.baseURI = props.getProperty("BaseURL");
        Response res = given().contentType("application/json")
                .header("Authorization", props.getProperty("token"))
                .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                .when().get("/user/search?id=" + "947598347598347")
                .then().assertThat().statusCode(200).extract().response();
        JsonPath response = res.jsonPath();
        System.out.println(response.get("user.id") == null);
        Assert.assertTrue(response.get("user.id") == null);
    }

    public void searchUserForInvalidSecretKey() throws IOException {
        props.load(file);
        RestAssured.baseURI = props.getProperty("BaseURL");
        Response res = given().contentType("application/json")
                .header("Authorization", props.getProperty("token"))
                .header("X-AUTH-SECRET-KEY", "WHOAREYOU?")
                .when().get("/user/search?id=" + props.getProperty("id"))
                .then().assertThat().statusCode(401).extract().response();
        JsonPath response = res.jsonPath();
        System.out.println(response.get("error.message").toString());
        Assert.assertTrue(response.get("error.message").toString().contains("validation failure!"));
    }

    public void updateUser() throws IOException {
        props.load(file);
        RestAssured.baseURI = props.getProperty("BaseURL");
        Response res = given().contentType("application/json")
                .header("Authorization", props.getProperty("token"))
                .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                .body("{\n" +
                        "            \"name\": \"Christiano Ronaldo\",\n" +
                        "            \"email\": \"christiano.davis49@gmail.com\",\n" +
                        "            \"password\": \"HAeXwp2Ml04VEYf\",\n" +
                        "            \"phone_number\": \"01784396900\",\n" +
                        "            \"nid\": \"6416489798\",\n" +
                        "            \"role\": \"Customer\"\n" +
                        "}")
                .when().put("/user/update/" + props.getProperty("id"))
                .then().assertThat().statusCode(200).extract().response();
        JsonPath response = res.jsonPath();
        Assert.assertTrue(response.get("message").toString().contains("User updated"));

    }

    public void updateUserPhoneNumber() throws IOException {
        props.load(file);
        RestAssured.baseURI = props.getProperty("BaseURL");
        Response res = given().contentType("application/json")
                .header("Authorization", props.getProperty("token"))
                .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                .body("{\n" +
                        "\n" +
                        "           \"phone_number\":\"01732132556\" \n" +
                        "}")
                .when().patch("/user/update/" + props.getProperty("id"))
                .then().assertThat().statusCode(200).extract().response();
        JsonPath response = res.jsonPath();
        Assert.assertEquals(response.get("user.phone_number").toString(), "01732132556");
    }
    public void deleteUser() throws IOException {
        props.load(file);
        RestAssured.baseURI = props.getProperty("BaseURL");
        Response res = given().contentType("application/json")
                .header("Authorization", props.getProperty("token"))
                .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                .when().delete("/user/delete/" + props.getProperty("id"))
                .then().assertThat().statusCode(200).extract().response();
        JsonPath response = res.jsonPath();
        Assert.assertEquals(response.get("message").toString(), "User deleted successfully");
    }
    public void alreadyDeleteUser() throws IOException {
        props.load(file);
        RestAssured.baseURI = props.getProperty("BaseURL");
        Response res = given().contentType("application/json")
                .header("Authorization", props.getProperty("token"))
                .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                .when().delete("/user/delete/" + props.getProperty("id"))
                .then().assertThat().statusCode(404).extract().response();
        JsonPath response = res.jsonPath();
        Assert.assertEquals(response.get("message").toString(), "User not found");
    }
    public void searchDeletedUser() throws IOException {
        props.load(file);
        RestAssured.baseURI = props.getProperty("BaseURL");
        Response res = given().contentType("application/json")
                .header("Authorization", props.getProperty("token"))
                .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                .when().get("/user/search?id=" + props.getProperty("id"))
                .then().assertThat().statusCode(200).extract().response();
        JsonPath response = res.jsonPath();
        System.out.println(response.get("user")==null);
        Assert.assertTrue(response.get("user")==null);
    }

}
