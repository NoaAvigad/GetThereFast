package gettherefast.gettherefast;

import java.util.HashMap;

/**
 * Created by Noa on 16-02-28.
 */
public class BusStop {

    String stopNumber;
    String stopName;
    HashMap<String, String> busDepartures = new HashMap<String, String>();

    public void BusStop(String name, String number, String busNum, String busTime)
    {
        this.stopName = name;
        this.stopNumber = number;
        this.busDepartures.put(busNum, busTime);
    }

}
