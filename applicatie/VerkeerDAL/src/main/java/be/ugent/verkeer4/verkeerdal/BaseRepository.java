
package be.ugent.verkeer4.verkeerdal;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.sql2o.Sql2o;


public abstract class BaseRepository {
    
     protected Sql2o sql2o;
     
    static {
         try { 
             Class.forName("org.mariadb.jdbc.Driver");
         } catch (ClassNotFoundException ex) {
             Logger.getLogger(BaseRepository.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
    public BaseRepository(String dbPath, String user, String password) throws ClassNotFoundException {
        this.sql2o = new Sql2o("jdbc:mariadb:" + dbPath, user, password);
        
        initializeSets();
    }
    
    protected abstract void initializeSets();
    
    protected void finalize() {
        
    }
}
