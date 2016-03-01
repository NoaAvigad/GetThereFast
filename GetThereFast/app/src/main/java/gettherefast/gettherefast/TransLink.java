package gettherefast.gettherefast;

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

/**
 * Created by Noa on 16-02-27.
 */
public class TransLink extends RequestHandler{

    private final String apiKey = "efRc4ohQgIEpfLHXeLWK"; // API key from TransLink Open API

    // =================
    // URL related fields
    // =================


    private String baseRequestURL = "http://api.translink.ca/rttiapi/v1/";

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

        try
        {
            String url =  this.baseRequestURL + "stops?apikey=" + this.apiKey + "&lat=" + lat +"&long=" + lon;
            String input = this.getData(url);

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
        BusStop toAdd = new BusStop();

        try {
            for (int i = 0; i < this.nearestStops.size(); i++) {
                toAdd.stopName = this.nearestStops.get(i).getString("Name");
                toAdd.stopNumber = this.nearestStops.get(i).getString("StopNo");

                // get bus numbers and schedule -- check elements of current response

                this.stopsToDisplay.add(toAdd);
            }
        }
        catch (JSONException je)
        {
            System.out.println(je.getMessage());
        }
    }
}
