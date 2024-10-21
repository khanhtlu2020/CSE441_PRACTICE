package com.example.ex23;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class XMLParser {
    public String getXmlFromUrl(String urlString) {
        StringBuilder xml = new StringBuilder();
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {
            // Tạo URL từ chuỗi URL
            URL url = new URL(urlString);

            // Mở kết nối
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(10000); // 10 giây timeout cho kết nối
            urlConnection.setReadTimeout(15000);    // 15 giây timeout cho đọc dữ liệu
            urlConnection.connect();

            // Kiểm tra nếu phản hồi là OK (HTTP 200)
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = urlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));

                // Đọc dữ liệu từ InputStream
                String line;
                while ((line = reader.readLine()) != null) {
                    xml.append(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Đóng các kết nối và luồng
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
        return xml.toString();
    }
}


