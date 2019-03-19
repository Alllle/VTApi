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
        APIHandler apiHandler = new APIHandler();
        Map<String,String> arguments = new HashMap<>();
	    apiHandler.newToken("1234");
    }

}
