package tests;

import io.restassured.RestAssured;
import io.restassured.filter.cookie.CookieFilter;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import pages.LoginPage;

import static io.restassured.RestAssured.given;

public class LoginTest {

    private LoginPage loginPage = new LoginPage();
    private static final Logger logger = LogManager.getLogger(LoginTest.class);

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "http://3.11.77.136/";
    }

    @Before
    public void logStep (){
        logger.info("Tests started....");}

    @Test
    public void loginFunctionalityTest() {
        CookieFilter cookieFilter = new CookieFilter();
        loginPage.loginPostRequest(cookieFilter, "hhbrother@gmail.com", "password");
        Response getMyAccount = given()
                .filter(cookieFilter)
                .queryParam("controller", "my-account")
                .get("index.php");

        //Assert status code 200.
        getMyAccount.then().assertThat().statusCode(200);

        //Assert user is logged in.
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

        //Assert status code 200.
        getMyAccount.then().assertThat().statusCode(200);

        //Assert user is not logged in.
        Assert.assertEquals("Login", getMyAccount.body().htmlPath().getString("html.head.title"));
        System.out.println("Test passed. User login unsuccessful.");
    }

    @After
    public void logEnd(){
        logger.info("Tests Ended.....");
    }

}
