package com.company;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        Main main = new Main();
        Map<String,String> arguments = new HashMap<>();
	    main.sendPost();
    }

    private void sendPost() throws Exception{
        String key = "aIgO0BOwJb8R6hnLmCzTEStacEAa";
        String secret = "EjmHVMooqxkH3A8eecSXkF2onhga";
        String url = "https://api.vasttrafik.se/token";

        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Authorization", "Basic " + Base64.getEncoder().encodeToString((key + ":" + secret).getBytes()));
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        con.setDoOutput(true);

        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes("grant_type=client_credentials&scope=4566");
        wr.flush();
        wr.close();
        int responsCode = con.getResponseCode();
        System.out.println("respons code: " + responsCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        StringBuilder body = new StringBuilder();
        String inputLine;
        while((inputLine = in.readLine()) != null) {
            body.append(inputLine);
        }
        System.out.println(body.toString());
    }
}
