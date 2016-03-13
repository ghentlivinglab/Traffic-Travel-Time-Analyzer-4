package be.ugent.verkeer4.verkeerdomain;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class Settings {

    private static final String DB_DEV_CONFIG = "database-dev.conf";
    private static final String DB_PROD_CONFIG = "database-prod.conf";
    private static final String SETTINGS_CONFIG = "application.conf";

    private static Settings instance;

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }

    public String getTomTomRoutingAPIKey() {
        Config conf = ConfigFactory.load(SETTINGS_CONFIG);
        return conf.getString("APIKeys.TomTom");
    }

    public String getDatabaseConnectionString() {
        String configName = inProduction() ? DB_PROD_CONFIG : DB_DEV_CONFIG;
        Config conf = ConfigFactory.load(configName);
        return conf.getString("database.connection");
    }

    public String getDatabaseUser() {
        String configName = inProduction() ? DB_PROD_CONFIG : DB_DEV_CONFIG;
        Config conf = ConfigFactory.load(configName);
        return conf.getString("database.user");

    }

    public String getDatabasePassword() {
        String configName = inProduction() ? DB_PROD_CONFIG : DB_DEV_CONFIG;
        Config conf = ConfigFactory.load(configName);
        return conf.getString("database.password");

    }

    public String getScrapePath() {
        Config conf = ConfigFactory.load(SETTINGS_CONFIG);
        return conf.getString("ScrapePath");
    }

    public String getPerlPath() {
        Config conf = ConfigFactory.load(SETTINGS_CONFIG);
        return conf.getString("PerlPath");
    }
    
    double getMaxDistanceForPOIRouteMatching() {
        Config conf = ConfigFactory.load(SETTINGS_CONFIG);
        // in kilometers
        return Double.parseDouble(conf.getString("MaxDistanceForPOIRouteMatching"));
    }

    public boolean inProduction() {
        Config conf = ConfigFactory.load(SETTINGS_CONFIG);
        return conf.getBoolean("general.inProduction");
    }

    public String getHereRoutingAPPId() {
        Config conf = ConfigFactory.load(SETTINGS_CONFIG);
        return conf.getString("APIKeys.Here.AppId");
    }

    public String getHereRoutingAPPCode() {
        Config conf = ConfigFactory.load(SETTINGS_CONFIG);
        return conf.getString("APIKeys.Here.AppCode");
    }

    public String getGoogleRoutingAPPCode() {
        Config conf = ConfigFactory.load(SETTINGS_CONFIG);
        return conf.getString("APIKeys.Google.AppCode");
    }

    public String getBingRoutingAPIKey() {
        Config conf = ConfigFactory.load(SETTINGS_CONFIG);
        return conf.getString("APIKeys.BingMaps");
    }

}
