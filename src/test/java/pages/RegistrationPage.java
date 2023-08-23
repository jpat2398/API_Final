package pages;

import io.restassured.RestAssured;
import org.junit.Test;

import java.util.Random;

public class RegistrationPage {

    public String emailRandomiser(){
        Random randomGenerator = new Random();
        int randominteger = randomGenerator.nextInt(328517329);
        return "Tester" + randominteger + "@test.com";
    }

    public String firstNameRandomiser(){
        Random random = new Random();
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder firstname = new StringBuilder();
        for (int i = 0; i < firstname.length(); i++) {
            int randomIndex = random.nextInt(characters.length());
            firstname.append(characters.charAt(randomIndex));
    }

    public String surnameRandomiser(){
        Random surnamerandomiser = new Random();
        String lettersAndNumbers = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder surname = new StringBuilder();
        for (int i = 0; i < surname.length(); i++) {
            int randomIndex = surnamerandomiser.nextInt(lettersAndNumbers.length());
            surname.append(lettersAndNumbers.charAt(randomIndex));
    }

    public String passwordGenerator(){
        Random passwordrandomiser = new Random();
        String figures = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < password.length(); i++) {
            int randomIndex = passwordrandomiser.nextInt(figures.length());
            password.append(figures.charAt(randomIndex));
    }
    @Test
    public void registerAsAUser() {
        RestAssured.baseURI = "http://3.11.77.136/";
        RestAssured.given()
                .when()
                .post(emailRandomiser();

    }
}
