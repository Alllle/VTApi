package com.company;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

//This class handles the POST/GET calls to the VT API
//Followed this guide: https://developer.vasttrafik.se/portal/#/guides/oauth2
class APIHandler {

    APIHandler(){}

    //IDK why I have his but i think i might add a check if a token is expired or something..
    String newToken(String userID){
        try {
            return sendPost(userID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Todo: Return the get token and figure out what to do with it.
    private String newGet(String token){
        String url = "https://api.vasttrafik.se/bin/rest.exe/v2/location.name?input=ols&format=json";

        return null;
    }


    //Todo: Make the return into JSON format
    private String sendPost(String userID) throws Exception{
        String url = "https://api.vasttrafik.se/token";
        String key = "aIgO0BOwJb8R6hnLmCzTEStacEAa";
        String secret = "EjmHVMooqxkH3A8eecSXkF2onhga";


        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Authorization", "Basic " + Base64.getEncoder().encodeToString((key + ":" + secret).getBytes()));
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        con.setDoOutput(true);

        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes("grant_type=client_credentials&scope=" + userID);
        wr.flush();
        wr.close();
        //int responsCode = con.getResponseCode();


        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder body = new StringBuilder();
        String inputLine;
        while((inputLine = in.readLine()) != null) {
            body.append(inputLine);
        }
        System.out.println(body.toString());
        return body.toString();
    }
}
