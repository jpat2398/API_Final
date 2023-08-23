package pages;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BasePage {

    public static final String PATH = "/";

    public String generatePath() {
        Random rnd = new Random();
        String randomString = generateRandomString(4, rnd);
        String path = PATH + randomString;
        return path;

    }
    private String generateRandomString(int length, Random rnd) {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = rnd.nextInt(characters.length());
            sb.append(characters.charAt(randomIndex));
        }
        return sb.toString();
    }

    public static Map getCookiesData(){
        Map <String,String> cookie=new HashMap<String, String>();
        cookie.put("PHPSESSID", "vj4lpguma0jcc37fhq2ujpk8c9");
        return cookie;
    }
}
