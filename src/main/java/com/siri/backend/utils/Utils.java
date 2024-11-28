package com.siri.backend.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static String generateAlphanumericCode(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder code = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            code.append(characters.charAt(random.nextInt(characters.length())));
        }
        return code.toString();
    }

    //Handle relative links to absolute ones
    public static String resolveUrl(String baseUrl, String relativeUrl) {
        if (relativeUrl.startsWith("http://") || relativeUrl.startsWith("https://")) {
            return relativeUrl;
        }
        return URI.create(baseUrl).resolve(relativeUrl).toString();
    }


    public static String getContent(String urlString) throws IOException {
        URL url = new URL(urlString);
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            return content.toString();
        } catch (FileNotFoundException e) {
            return "";
        }
    }

    //Extract links from the content from href tags
    public static Set<String> extractLinks(String content) {
        Set<String> links = new HashSet<>();
        Pattern pattern = Pattern.compile("href=\"(.*?)\"", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            String link = matcher.group(1);
            if (isValidUrl(link)) {
                links.add(link);
            }
        }
        return links;
    }

    private static boolean isValidUrl(String url) {
        try {
            new URI(url);
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }
}
