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
import java.util.ArrayList;

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

    private ArrayList<JSONObject> nearestStops =  new ArrayList<JSONObject>();
    public ArrayList<BusStop> stopsToDisplay = new ArrayList<BusStop>();

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
            System.out.println("Failed to connect to TransLink 2 - " + e.getMessage());
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
            String minDistance = jsonStopList.getJSONObject(0).get("Distance").toString();
            int counter = 1;

            // Adds the closest stop to the list - first stop in the jsonStopList
            nearestStops.add(jsonStopList.getJSONObject(0));

            // Adds all the stops with the same distance to the list ---> jsonStopList is ordered by distance from current location
           while (minDistance == jsonStopList.getJSONObject(counter).get("Distance").toString())
           {
               nearestStops.add(jsonStopList.getJSONObject(counter));
               counter++;
           }

            extractInfoFromNearestStops();
        }
        catch (JSONException je)
        {
            System.out.println(je.getMessage());
        }

    }


    private void extractInfoFromNearestStops()
    {
        String name;
        String number;

        try {
            for (int i = 0; i < this.nearestStops.size(); i++) {
                name = this.nearestStops.get(i).getString("Name");
                number = this.nearestStops.get(i).getString("StopNo");

            }
        }
        catch (JSONException je)
        {
            System.out.println(je.getMessage());
        }
    }



    private String getData(URL url)
    {


        return "";
    }

}
