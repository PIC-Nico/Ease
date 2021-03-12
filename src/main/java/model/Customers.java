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

public class Customers {
    // Log4J2 logger object
    private final Logger logger = LogManager.getLogger(Customers.class);

    // singleton pattern object
    private static final Customers OBJ = new Customers();

    // create the Gson object to serialize/deserialize customer objects
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // path to the customer database (relative to the app root path)
    private final String CUSTOMER_DATABASE = "customers.json";

    private ArrayList<Customer> database;

    /**
     * Constructor
     */
    private Customers() {
        load();
    }

    /**
     * This will return with the singleton pattern object.
     * @return Customer object (singleton)
     */
    public static Customers getInstance() {
        return OBJ;
    }

    /**
     * This will import the customer database
     */
    private void load() {
        String file = Config.getInstance().getAppRootPath() + CUSTOMER_DATABASE;

        try (Reader reader = new FileReader(file)) {

            // convert JSON to JsonElement and later to string
            JsonElement element = gson.fromJson(reader, JsonElement.class);

            Customer allCustomer[] = gson.fromJson(gson.toJson(element), Customer[].class);

            // copy all customer into the database
            database = new ArrayList<>();
            for(int i=0; i<allCustomer.length; i++) {
                database.add(allCustomer[i]);
            }

            logger.trace("Customers were imported (" + database.size() + ")");
        } catch (IOException e) {
            logger.error("Customer import failed");
            logger.error(e);
        }
    }
}
