package be.ugent.verkeer4.verkeerdal;

import be.ugent.verkeer4.verkeerdomain.data.Logging;
import org.sql2o.Sql2o;

/**
 *
 * @author Tomas Bolckmans
 */
public class LogEntryDbSet extends DbSet<Logging>  {
    
    public LogEntryDbSet(Sql2o sql2o) {
        super(Logging.class, sql2o);
    }    
}