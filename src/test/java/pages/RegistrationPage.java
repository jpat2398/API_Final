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


    private BasePage basePage = new BasePage();
    HomePage homePage = new HomePage();

    @BeforeClass
    public static void basePageSetup() {
        RestAssured.baseURI = "http://3.11.77.136/";

    }

    public String emailRandomiser() {
        Random randomGenerator = new Random();
        int randominteger = randomGenerator.nextInt(328517329);
        return "Tester" + randominteger + "@test.com";

    }

    @Test
    public void registerANewUser() {
        CookieFilter cookieFilter = new CookieFilter();
        HashMap<String, String> newUserCreds = new HashMap<String, String>();
        newUserCreds.put("id_customer", "");
        newUserCreds.put("id_gender", "1");
        newUserCreds.put("firstname", "Tester");
        newUserCreds.put("lastname", "McTestFace");
        newUserCreds.put("email", emailRandomiser());
        newUserCreds.put("password", "Password1");
        newUserCreds.put("birthday", "05/24/2000");
        newUserCreds.put("submitCreate", "1");
        Response response = RestAssured
                .given().filter(cookieFilter)
                .queryParam("controller", "authentication")
                .queryParam("create_account", "1")
                .formParams(newUserCreds)
                .when()
                .post("?controller=authentication&create_account=1");
        System.out.println(response.statusCode());
        System.out.println(cookieFilter.getCookieStore());
        Response getNewAccount = RestAssured
                .given().filter(cookieFilter)
                .queryParam("controller", "authentication")
                .queryParam("create_account", "1")
                .get("?controller=authentication&create_account=1");
        System.out.println(getNewAccount.getBody().prettyPrint());
        System.out.println(cookieFilter.getCookieStore());
        Assert.assertEquals("Tester McTestFace", getNewAccount.body().htmlPath().getString("**.findAll { it.@class== 'user-info' }.a.span"));
    }

    @Test
    public void alreadyRegisteredUser() {

        CookieFilter cookieFilter = new CookieFilter();
        HashMap<String, String> newUserCreds = new HashMap<String, String>();
        newUserCreds.put("id_customer", "");
        newUserCreds.put("id_gender", "1");
        newUserCreds.put("firstname", "Tester");
        newUserCreds.put("lastname", "McTestFace");
        newUserCreds.put("email", "tester@test.com");
        newUserCreds.put("password", "Password1");
        newUserCreds.put("birthday", "05/24/2000");
        newUserCreds.put("submitCreate", "1");
        Response response = RestAssured
                .given().filter(cookieFilter)
                .queryParam("controller", "authentication")
                .queryParam("create_account", "1")
                .formParams(newUserCreds)
                .when()
                .post("?controller=authentication&create_account=1");
        System.out.println(response.statusCode());
        System.out.println(cookieFilter.getCookieStore());
        Response getNewAccount = RestAssured
                .given().filter(cookieFilter)
                .queryParam("controller", "authentication")
                .queryParam("create_account", "1")
                .get("?controller=authentication&create_account=1");
        System.out.println(getNewAccount.getBody().prettyPrint());
        System.out.println(cookieFilter.getCookieStore());
        Assert.assertEquals("The email tester@test.com is already used, please choose another one or sign in", getNewAccount.body().htmlPath().getString("**.findAll { @class== 'alert alert-danger' }.span"));
    }
}
