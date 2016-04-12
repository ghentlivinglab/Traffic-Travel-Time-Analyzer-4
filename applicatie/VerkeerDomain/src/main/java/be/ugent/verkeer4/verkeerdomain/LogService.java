package be.ugent.verkeer4.verkeerdomain;

import be.ugent.verkeer4.verkeerdomain.data.Logging;
import be.ugent.verkeer4.verkeerdomain.data.LogTypeEnum;
import be.ugent.verkeer4.verkeerdomain.data.composite.LogCount;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tomas Bolckmans
 */
public class LogService extends BaseService implements ILogService {

    private static LogService instance;

    public static LogService getInstance() {
        if (instance == null) {
            try {
                instance = new LogService();
            } catch (ClassNotFoundException ex) {
                //Bij problemen met de logservice schrijven we logs niet weg in de DB
                Logger.getLogger(LogService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return instance;
    }

    public LogService() throws ClassNotFoundException {
        super();
    }

    /**
     * Deze methode wordt opgeroepen om een nieuw logentry toe te voegen in de database.
     * @param type
     * @param category
     * @param message 
     */
    @Override
    public void insert(LogTypeEnum type, String category, String message) {
        //logs krijgen de huidige datum mee
        Date now = new Date();
        
        Logging log = new Logging();
        log.setType(type);
        log.setDate(now);
        log.setCategory(category);
        log.setMessage(message);
        
        try {
            //id is de Id die aan de logentry gegeven is.
            int id = repo.getLogEntrySet().insert(log);
        } catch(Exception ex){
            //vermijden dat de volledige website crashed als er een fout is bij het wegschrijven van logberichten
            //daarom hier niet proberen om errors in DB op te slaan
            Logger.getLogger(RouteService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     /**
     * Hier komt de methode om logs op te vragen.
     * Deze wordeng gebruikt om weer te geven op het dashboard.
     * @return Lijst van logentries
     */
    @Override
    public List<Logging> getLogs() {
        List<Logging> logs = null;
        
        try {
            logs = repo.getLogEntrySet().getItems();
        } catch (Exception ex) {
            Logger.getLogger(RouteService.class.getName()).log(Level.SEVERE, null, ex); 
        }
        
        return logs;
    }
    
    /**
     * 
     * @return Lijst van LoggingCount entries
     */
    @Override
    public List<LogCount> getLogCount(){
        //Hier moeten de logs nog meer gespecifieerd worden!
        List<LogCount> logs = null;
        
        try {
            logs = repo.getLogEntrySet().getLogCount();
        } catch (Exception ex) {
            Logger.getLogger(LogService.class.getName()).log(Level.SEVERE, null, ex); 
        }
        return logs;
        
    }
}
