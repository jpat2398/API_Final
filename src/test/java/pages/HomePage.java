package pages;

import io.restassured.RestAssured;
import io.restassured.filter.cookie.CookieFilter;
import io.restassured.response.Response;

public class HomePage {
    String token;
    public void productGetRequest(CookieFilter cookieFilter, String path) {
        //GET Request
         Response getProduct = RestAssured
                .given()
                .filter(cookieFilter)
                .queryParam("id_product", "1")
                .queryParam("id_product_attribute", "1")
                .queryParam("rewrite", "faded-short-sleeves-tshirt")
                .queryParam("controller", "product")
                .get("index.php");
         this.token = getProduct.body().htmlPath().getString(path);
    }
    public String getToken() {
        return this.token;
    }


}
