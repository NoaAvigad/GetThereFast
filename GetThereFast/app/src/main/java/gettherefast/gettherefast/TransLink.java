package gettherefast.gettherefast;

import android.os.Trace;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.ws.rs.core.Response;
/**
 * Created by Noa on 16-02-27.
 */
public class TransLink {

    private final String apiKey = "efRc4ohQgIEpfLHXeLWK"; // API key from TransLink Open API

    // =================
    // URL related fields
    // =================

    private URL transLinkURL;
    private URLConnection connectionURL;
    private BufferedReader responseBuffer;
    private String requestURL = "http://api.translink.ca/rttiapi/v1/";

    public TransLink()
    {


    }


    public void getStops(double lat, double lon)
    {
        // URL Example: http://api.translink.ca/rttiapi/v1/stops?apikey=[APIKey]&lat=49.187706&long=-122.850060

        // ================
        // HTTP Request
        // ================

        this.requestURL += "stops?apikey=" + this.apiKey + "&lat=" + lat +"&long=" + lon;

        try
        {
            this.transLinkURL = new URL (this.requestURL);
            this.connectionURL = this.transLinkURL.openConnection();
            this.connectionURL.setRequestProperty("content-type", "application/JSON");
            this.responseBuffer = new BufferedReader(new InputStreamReader(this.connectionURL.getInputStream()));

            String input = "";
            String inputLine = "";

            while ((inputLine = this.responseBuffer.readLine()) != null)
                input += inputLine;

            this.responseBuffer.close();

            JSONArray jsonStopList = new JSONArray(input);

            findClosestStopFromJSON(jsonStopList);
        }
        catch (MalformedURLException e)
        {
            System.out.println("Failed to connect to TransLink - " + e.getMessage());
        }
        catch (IOException e)
        {
            System.out.println("Failed to connect to TransLink - " + e.getMessage());
        }
        catch (JSONException je)
        {
            System.out.println("FAILLLLL" + je.getMessage());
            je.getStackTrace();
        }

    }



    private void findClosestStopFromJSON(JSONArray jsonStopList)
    {
        try {

            System.out.println(jsonStopList.get(0).toString());
        }
        catch (JSONException je)
        {
            System.out.println(je.getMessage());
        }
    }




}
