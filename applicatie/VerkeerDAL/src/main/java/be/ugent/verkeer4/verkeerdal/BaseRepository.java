
package be.ugent.verkeer4.verkeerdal;

import org.sql2o.Sql2o;


public abstract class BaseRepository {
    
     protected Sql2o sql2o;
    public BaseRepository(String dbPath, String user, String password) throws ClassNotFoundException {
         Class.forName("org.mariadb.jdbc.Driver");  

        this.sql2o = new Sql2o("jdbc:mariadb:" + dbPath, user, password);
        
        initializeSets();
    }
    
    protected abstract void initializeSets();
    
}
