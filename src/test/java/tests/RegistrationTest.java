package tests;

import io.restassured.RestAssured;
import io.restassured.filter.cookie.CookieFilter;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import pages.RegistrationPage;

public class RegistrationTest {

    private RegistrationPage registrationPage = new RegistrationPage();
    private static final Logger logger = LogManager.getLogger(RegistrationTest.class);

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "http://3.11.77.136/";
    }

    @Before
    public void logStep (){
        logger.info("Tests started....");}

    @Test
    public void registerANewUserTest() {
        CookieFilter cookieFilter = new CookieFilter();
        Response registrationPost = RestAssured
                .given().filter(cookieFilter)
                .queryParam("controller", "authentication")
                .queryParam("create_account", "1")
                .formParams(registrationPage.registrationFormData(registrationPage.emailRandomiser()))
                .when()
                .post("?controller=authentication&create_account=1");


        Response getNewAccount = RestAssured
                .given().filter(cookieFilter)
                .queryParam("controller", "authentication")
                .queryParam("create_account", "1")
                .get("?controller=authentication&create_account=1");

        //Assert account has been created.
        Assert.assertEquals("Tester McTestFace", getNewAccount.body().htmlPath().getString("**.findAll { it.@class== 'user-info' }.a.span"));
    }

    @Test
    public void alreadyRegisteredUserTest() {

        CookieFilter cookieFilter = new CookieFilter();
        Response response = RestAssured
                .given().filter(cookieFilter)
                .queryParam("controller", "authentication")
                .queryParam("create_account", "1")
                .formParams(registrationPage.registrationFormData("tester@test.com"))
                .when()
                .post("?controller=authentication&create_account=1");

        //Assert for account already in use message.
        Assert.assertEquals("The email \"tester@test.com\" is already used, please choose another one or sign in",
                response.body().htmlPath().getString("**.findAll { it.@class== 'help-block' }.ul.li"));
    }

    @After
    public void logEnd(){
        logger.info("Tests Ended.....");
    }

}
