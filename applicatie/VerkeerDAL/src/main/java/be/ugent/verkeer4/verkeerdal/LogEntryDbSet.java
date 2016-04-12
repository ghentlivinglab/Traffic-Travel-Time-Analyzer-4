package be.ugent.verkeer4.verkeerdal;

import be.ugent.verkeer4.verkeerdomain.data.Logging;
import be.ugent.verkeer4.verkeerdomain.data.composite.LogCount;
import java.util.Date;
import java.util.List;
import org.sql2o.Query;
import org.sql2o.Sql2o;

/**
 *
 * @author Tomas Bolckmans
 */
public class LogEntryDbSet extends DbSet<Logging>  {
    
    public LogEntryDbSet(Sql2o sql2o) {
        super(Logging.class, sql2o);
    }
    
    public List<LogCount> getLogCount() {
        try (org.sql2o.Connection con = sql2o.open()) {
            String query = 
                    "SELECT category,"
                    + "CASE WHEN type = 0 THEN count(1) END AS infoCount"
                    + "CASE WHEN type = 1 THEN count(1) END AS warningCount"
                    + "CASE WHEN type = 2 THEN count(1) END AS errorCount"
                    + "group by category";

            Query q = con.createQuery(query);

            return q.executeAndFetch(LogCount.class);
        }
    }    
}