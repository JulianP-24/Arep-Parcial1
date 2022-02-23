package edu.escuelaing.parcial;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class getClima {
    public String getClimaCiudadAPI(String lugar)  throws IOException {

        String USER_AGENT = "Mozilla/5.0";
        String GET_URL = "http://api.openweathermap.org/data/2.5/weather?q=" + lugar + "&appid=9e25101b6a0a09d541dc8c564b04fb83";
        URL url = new URL(GET_URL);
        HttpURLConnection solicitud = (HttpURLConnection) url.openConnection();
        solicitud.setRequestMethod("GET");
        solicitud.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = solicitud.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(solicitud.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response.toString());
            return response.toString();
        } else {
            return null;
        }
    }
}
