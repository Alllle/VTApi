package com.company;

import javax.net.ssl.HttpsURLConnection;
import java.io.DataOutputStream;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        Main main = new Main();
	    APIRequest api = new APIRequest();
        Map<String,String> arguments = new HashMap<>();
	    main.sendPost();
    }

    private void sendPost() throws Exception{
        String key = "aIgO0BOwJb8R6hnLmCzTEStacEAa";
        String secret = "EjmHVMooqxkH3A8eecSXkF2onhga";
        String url = "https://api.vasttrafik.se/token";

        String arguments = "Content-Type: https://api.vasttrafik.se:443/token\nAuthorization: Basic " + Base64.getEncoder().encodeToString((key + ":" + secret).getBytes());
        //arguments.put("grant_type", "uniqueID");
        StringBuilder stringArgument = new StringBuilder();


        System.out.println(arguments);


        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "https://api.vasttrafik.se:443/token");
        con.setRequestProperty("Authorization", Base64.getEncoder().encodeToString((key + ":" + secret).getBytes()));
        con.setRequestProperty("grant_type", "4888239");
        //con.setRequestProperty("format", "json");

        con.setDoOutput(true);

        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(arguments);
        wr.flush();
        wr.close();
        int responsCode = con.getResponseCode();
        System.out.println("respons code: " + responsCode);
    }
}
