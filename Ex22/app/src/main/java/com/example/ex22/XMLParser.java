package com.example.ex22;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class XMLParser {

    public String getXmlFromUrl(String urlString) {
        String xml = null;
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {
            // Tạo đối tượng URL và kết nối
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(10000); // Timeout trong 10 giây
            urlConnection.setReadTimeout(10000); // Read timeout trong 10 giây
            urlConnection.connect();

            // Kiểm tra mã phản hồi
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Đọc luồng đầu vào và chuyển nó thành chuỗi
                InputStream inputStream = urlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                xml = stringBuilder.toString();
            } else {
                System.err.println("Server returned HTTP " + responseCode);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối và reader
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // return XML
        return xml;
    }
}


