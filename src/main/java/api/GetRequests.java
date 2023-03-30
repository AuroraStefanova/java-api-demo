package api;

import helpers.ConfigJson;
import helpers.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import java.io.IOException;
import java.io.InputStream;


public class GetRequests {

    //private static String urlString = "http://restapi.adequateshop.com/api/users?page=1";
    private static String accessToken;
    private static String responseCode;
    private static String responseBody;


    public static void getPageUser() throws IOException {

        //HttpGet getUsers = new HttpGet(urlString);
        ConfigJson url = new ConfigJson();
        String getUrl = url.getRequestUrl();
        HttpGet getUsers = new HttpGet(getUrl);
        //опит
        System.out.println(getUsers);
        getUsers.setHeader("Content-type", "application/json");
        getUsers.setHeader("Authorization", accessToken);
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = httpClient.execute(getUsers);
        responseCode = response.getStatusLine().toString();

        //Parse the response body
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            // A Simple JSON Response Read
            InputStream instream = entity.getContent();
            responseBody = new ResponseReader().convertStreamToString(instream);
            instream.close();
        }
        if(responseCode.contains("200") == true){
            JsonParser json = new JsonParser();
            accessToken = json.getAccessToken(responseBody);
        }
        System.out.println(responseCode);
        System.out.println(responseBody);



    }
    public void setAccessToken(String accessToken) {
        GetRequests.accessToken ="bearer " + accessToken;
    }
    public String getAccessToken(){
        return accessToken;
    }

    public static String getResponseCode(){
        return responseCode;
    }
    public static String getResponseBody(){
        return responseBody;
    }
}
