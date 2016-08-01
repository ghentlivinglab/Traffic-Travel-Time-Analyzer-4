package be.ugent.verkeer4.verkeerdomain.provider;

import be.ugent.verkeer4.verkeerdomain.Settings;
import be.ugent.verkeer4.verkeerdomain.data.composite.BoundingBox;
import be.ugent.verkeer4.verkeerdomain.data.POI;
import be.ugent.verkeer4.verkeerdomain.data.POICategoryEnum;
import be.ugent.verkeer4.verkeerdomain.data.ProviderEnum;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class POIHelper {
    
    public static List<POI> scrapePOI(BoundingBox bbox, ProviderEnum provider, String scriptName) throws InterruptedException, IOException, Exception {
        
        File baseDir = new File(Settings.getInstance().getScrapePath());
        
        File script = new File(baseDir, scriptName);
        
        ProcessBuilder pb = new ProcessBuilder(Settings.getInstance().getPerlPath(), script.toPath().toString(),
                bbox.getMinLatitude() + "", bbox.getMinLongitude() + "", bbox.getMaxLatitude() + "", bbox.getMaxLongitude() + "");
        
        pb.directory(baseDir);
        java.lang.Process p = pb.start();
        
        BufferedReader errReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream(), "UTF-8"));
        List<POI> pois = parseScrapePOIOutput(reader, provider);
        
        int errCode = p.waitFor();
        return pois;
    }
    
    private static List<POI> parseScrapePOIOutput(BufferedReader reader, ProviderEnum provider) throws IOException, Exception, NumberFormatException {
        String headers = reader.readLine();
        
        String line;
        
        List<POI> lst = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            if (!line.equals("")) {
                // id;lat;lng;type;traffictype;comments;
                String[] parts = line.split(";", -1);
                
                POI p = new POI();
                p.setReferenceId(parts[0]);
                p.setProvider(provider);
                p.setLatitude(Double.parseDouble(parts[1]));
                p.setLongitude(Double.parseDouble(parts[2]));
                
                POICategoryEnum type = POICategoryEnum.fromInt(Integer.parseInt(parts[3]));
                p.setCategory(type);
                
                p.setSince(new Date());
                try {
                    p.setInfo(parts[5]);
                    
                } catch (Exception ex) {
                    p.setInfo("");
                }
                lst.add(p);
            }
        }
        return lst;
    }
}
