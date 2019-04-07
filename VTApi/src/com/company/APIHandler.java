package com.company;


import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

//This class handles the POST/GET calls to the VT API
//Followed this guide: https://developer.vasttrafik.se/portal/#/guides/oauth2
class APIHandler {

    //Todo: check if the time is expired and refresh.
    long expiryTime;

    APIHandler(){}

    //IDK why I have his but i think i might add a check if a token is expired or something..
    HashMap<String, String> newToken(String userID){
        HashMap<String, String> token = new HashMap<>();
        try {
            token = sendPost(userID);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(token.containsKey("expires_in")){
            expiryTime = System.currentTimeMillis() + Integer.valueOf(token.get("expires_in"));
        }else{
            System.out.println("There is no expiration time?");
        }
        return token;
    }

    //Todo: Return the get token and figure out what to do with it.
    public HashMap<String, String> newGetRequest(String token, String userID) throws Exception {
        String url = "https://api.vasttrafik.se/bin/rest.exe/v2/svingeln";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", "Bearer " + token);
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
        return jsonToDict(body.toString());
    }


    private HashMap<String, String> sendPost(String userID) throws Exception{
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
        return jsonToDict(body.toString());
    }

    //Returns a hashmap with keys and values corresponding to the JSON from post/get requests
    private HashMap<String, String> jsonToDict(String text){
        HashMap<String, String> dict = new HashMap<>();

        //Remove the unnesessary tokens from the string (", { and })
        text = text.replaceAll("\\{", "").replaceAll("\\}", "").replaceAll("\"", "");
        String [] data = text.split(",");
        for (String s: data) {
            String temp[] = s.split(":");
            dict.put(temp[0],temp[1]);
        }
        return dict;
    }
}
