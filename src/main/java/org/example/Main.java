package org.example;

import com.github.javafaker.Faker;

public class Main {
    public static void main(String[] args) {
        Faker faker=new Faker();
        String name=faker.name().fullName();
        String phone=faker.phoneNumber().phoneNumber();
        String email=faker.internet().emailAddress();
        String password=faker.internet().password();
        String nid="641"+(int)Math.random()*((9999999-1000000)+1)+9999999;
        System.out.println("{\n" +
                "    \"name\":"+name+",\n" +
                "    \"email\":"+email+",\n" +
                "    \"password\":"+password+",\n" +
                "    \"phone_number\":"+phone+",\n" +
                "    \"nid\":"+nid+",\n" +
                "    \"role\":\"Customer\"\n" +
                "\n" +
                "\n" +
                "}");
    }
}