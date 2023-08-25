package pages;

import io.restassured.filter.cookie.CookieFilter;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class LoginPage  {

    public void loginPostRequest(CookieFilter cookieFilter, String email, String password) {
        Response response = given()
                .filter(cookieFilter)
                .queryParam("controller", "authentication")
                .queryParam("back", "my-account")
                .formParams(this.signInFormData(email, password))
                .when()
                .post("index.php");

    }
    public Map signInFormData(String email, String password) {
        Map<String, String> loginDetails = new HashMap<String, String>();
        loginDetails.put("back", "my-account");
        loginDetails.put("email", email);
        loginDetails.put("password", password);
        loginDetails.put("submitLogin", "1");
        return loginDetails;
    }

}
