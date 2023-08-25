import io.restassured.RestAssured;
import io.restassured.filter.cookie.CookieFilter;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.*;
import io.restassured.http.ContentType;

import static groovy.json.JsonOutput.prettyPrint;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.matcher.RestAssuredMatchers.*;
import pages.*;
//import io.restassured.module.jsv.JsonSchemaValidator.*;
//import io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class Main {
    private BasePage basePage = new BasePage();
    private HomePage homePage = new HomePage();

    private CartPage cartPage = new CartPage();
    private LoginPage loginPage = new LoginPage();

    private RegistrationPage registrationPage = new RegistrationPage();
    private static final Logger logger = LogManager.getLogger(Main.class);


    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "http://3.11.77.136/";
    }

    @Before
            public void logStep (){
            logger.info("Tests started....");}

    @Test
    public void invalidURLTest() {
        Response response = RestAssured.get(basePage.generatePath());
        response.then().assertThat().statusCode(404);
        System.out.println("Status code: 404. Page not found");
    }

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

    @Test
    public void addItemToCartTest() {
        CookieFilter cookieFilter = new CookieFilter();

        homePage.productGetRequest(cookieFilter, "**.findAll { it.@name == 'token' }.@value");

        Response cartPost = RestAssured.given()
                .filter(cookieFilter)
                .queryParam("controller", "cart")
                .formParams(cartPage.cartItemFormData("1"))
                .when()
                .post("index.php");
        cartPost.then().assertThat().statusCode(200);
        String cartItem = cartPost.body().htmlPath().getString("**.findAll { it.@class == 'cart-products-count' }");
        String cartItemQuantityString = cartItem.replace("(", "").replace(")", "");
        int cartItemQuantity = Integer.parseInt(cartItemQuantityString);
        Assert.assertTrue(cartItemQuantity > 0);
        System.out.printf("Test passed. There are %s item(s) in the basket.", cartItemQuantity);
    }

    @Test
    public void addMultipleItemsToCartTest() {
        CookieFilter cookieFilter = new CookieFilter();

        homePage.productGetRequest(cookieFilter, "**.findAll { it.@name == 'token' }.@value");

        //Get POST Response
        Response cartPost = RestAssured.given()
                .filter(cookieFilter)
                .queryParam("controller", "cart")
                .formParams(cartPage.cartItemFormData("2"))
                .when()
                .post("index.php");
        cartPost.then().assertThat().statusCode(200);
        String cartItem = cartPost.body().htmlPath().getString("**.findAll { it.@class == 'cart-products-count' }");
        String cartItemQuantityString = cartItem.replace("(", "").replace(")", "");
        int cartItemQuantity = Integer.parseInt(cartItemQuantityString);
        Assert.assertTrue(cartItemQuantity > 0);
        System.out.printf("Test passed. There are %s item(s) in the basket.", cartItemQuantity);


    }
    @Test
    public void registerANewUser() {
        CookieFilter cookieFilter = new CookieFilter();
        HashMap<String, String> newUserCreds = new HashMap<String, String>();
        newUserCreds.put("id_customer", "");
        newUserCreds.put("id_gender", "1");
        newUserCreds.put("firstname", "Tester");
        newUserCreds.put("lastname", "McTestFace");
        newUserCreds.put("email", registrationPage.emailRandomiser());
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
        Assert.assertEquals("The email \"tester@test.com\" is already used, please choose another one or sign in",
                response.body().htmlPath().getString("**.findAll { it.@class== 'help-block' }.ul.li"));
    }

    @After
    public void logEnd(){
        logger.info("Tests Ended.....");
    }

}
