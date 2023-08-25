package pages;

import com.sun.tools.javac.Main;
import io.restassured.RestAssured;
import io.restassured.filter.cookie.CookieFilter;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RegistrationPage {

    public Map registrationFormData(String email) {
        HashMap<String, String> newUserCreds = new HashMap<String, String>();
        newUserCreds.put("id_customer", "");
        newUserCreds.put("id_gender", "1");
        newUserCreds.put("firstname", "Tester");
        newUserCreds.put("lastname", "McTestFace");
        newUserCreds.put("email", email);
        newUserCreds.put("password", "Password1");
        newUserCreds.put("birthday", "05/24/2000");
        newUserCreds.put("submitCreate", "1");
        return newUserCreds;
    }


    public String emailRandomiser() {
        Random randomGenerator = new Random();
        int randominteger = randomGenerator.nextInt(328517329);
        return "Tester" + randominteger + "@test.com";
    }
}
