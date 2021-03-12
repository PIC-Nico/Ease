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

public class Invoices {
    // Log4J2 logger object
    private final Logger logger = LogManager.getLogger(Invoices.class);

    // singleton pattern object
    private static final Invoices OBJ = new Invoices();

    // create the Gson object to serialize/deserialize invoice objects
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // path to the invoice database (relative to the app root path)
    private final String INVOICE_DATABASE = "invoices.json";

    private ArrayList<Invoice> database;

    /**
     * Constructor
     */
    private Invoices() {
        load();
    }

    public static Invoices getInstance() {
        return OBJ;
    }

    /**
     * This will import the invoice database
     */
    public void load() {
        String file = Config.getInstance().getAppRootPath() + INVOICE_DATABASE;

        try (Reader reader = new FileReader(file)) {

            // convert JSON to JsonElement and later to string
            JsonElement element = gson.fromJson(reader, JsonElement.class);

            Invoice all[] = gson.fromJson(gson.toJson(element), Invoice[].class);

            // copy all invoices into the database
            database = new ArrayList<>();
            for(int i=0; i<all.length; i++) {
                database.add(all[i]);
            }

            logger.trace("Invoices were imported (" + database.size() + ")");
        } catch (IOException e) {
            logger.error("Invoice import failed");
            logger.error(e);
        }
    }
}
