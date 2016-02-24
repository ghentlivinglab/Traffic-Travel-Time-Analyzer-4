
package be.ugent.verkeer4.verkeerdomain;

import be.ugent.verkeer4.verkeerdal.IUnitOfWork;
import be.ugent.verkeer4.verkeerdal.UnitOfWork;


public class BaseService {
    
    protected IUnitOfWork repo;
    
    public BaseService() throws ClassNotFoundException {
        repo = new UnitOfWork(Settings.getInstance().getDatabaseConnectionString(), Settings.getInstance().getDatabaseUser(), Settings.getInstance().getDatabasePassword());
    }
}
