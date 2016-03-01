package gettherefast.gettherefast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Noa on 16-03-01.
 */
public abstract class RequestHandler {


    public String getData(String strURL) throws IOException, MalformedURLException
    {
        URL url = new URL (strURL);
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("content-type", "application/JSON");
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String input = "";
        String inputLine = "";

        while ((inputLine = br.readLine()) != null)
            input += inputLine;

        br.close();

        return input;
    }

}
