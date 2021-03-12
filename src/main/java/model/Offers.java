package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import controller.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class Offers {
    // Log4J2 logger object
    private final Logger logger = LogManager.getLogger(Offers.class);

    // singleton pattern object
    private static final Offers OBJ = new Offers();

    // create the Gson object to serialize/deserialize offers objects
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // path to the offer database (relative to the app root path)
    private final String OFFER_DATABASE = "offers.json";

    private ArrayList<Offer> database;

    /**
     * Constructor
     */
    private Offers() {
        load();
    }

    public static Offers getInstance() {
        return OBJ;
    }

    /**
     * This will import the offer database
     */
    public void load() {
        String file = Config.getInstance().getAppRootPath() + OFFER_DATABASE;

        try (Reader reader = new FileReader(file)) {

            // convert JSON to JsonElement and later to string
            JsonElement element = gson.fromJson(reader, JsonElement.class);

            Offer all[] = gson.fromJson(gson.toJson(element), Offer[].class);

            // copy all offer into the database
            database = new ArrayList<>();
            for(int i=0; i<all.length; i++) {
                database.add(all[i]);
            }

            logger.trace("Offers were imported (" + database.size() + ")");
        } catch (IOException e) {
            logger.error("Offer import failed");
            logger.error(e);
        }
    }
}
