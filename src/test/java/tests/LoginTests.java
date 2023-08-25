package tests;

import io.restassured.filter.cookie.CookieFilter;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import pages.LoginPage;

import static io.restassured.RestAssured.given;

public class LoginTests {
    private LoginPage loginPage = new LoginPage();
    @Test
    public void loginFunctionalityTest() {
        CookieFilter cookieFilter = new CookieFilter();
        loginPage.loginPostRequest(cookieFilter, "hhbrother@gmail.com", "password");
        Response getMyAccount = given()
                .filter(cookieFilter)
                .queryParam("controller", "my-account")
                .get("index.php");
        getMyAccount.then().assertThat().statusCode(200);
        Assert.assertEquals("My account", getMyAccount.body().htmlPath().getString("html.head.title"));
        System.out.println("Test Passed. User login successful.");

    }

    @Test
    public void loginNotValidTest() {
        CookieFilter cookieFilter = new CookieFilter();
        loginPage.loginPostRequest(cookieFilter, "failtest@mail.com", "failtest");
        Response getMyAccount =
                given()
                        .filter(cookieFilter)
                        .queryParam("controller", "my-account")
                        .get("index.php");
        getMyAccount.then().assertThat().statusCode(200);
        Assert.assertEquals("Login", getMyAccount.body().htmlPath().getString("html.head.title"));
        System.out.println("Test passed. User login unsuccessful.");
    }
}
