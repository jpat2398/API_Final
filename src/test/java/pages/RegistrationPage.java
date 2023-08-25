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
import java.util.Random;

public class RegistrationPage {


    public String emailRandomiser() {
        Random randomGenerator = new Random();
        int randominteger = randomGenerator.nextInt(328517329);
        return "Tester" + randominteger + "@test.com";
    }
}
