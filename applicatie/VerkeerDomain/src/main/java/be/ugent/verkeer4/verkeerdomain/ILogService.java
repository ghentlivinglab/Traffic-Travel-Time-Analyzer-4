/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.verkeer4.verkeerdomain;

import be.ugent.verkeer4.verkeerdomain.data.LogTypeEnum;
import be.ugent.verkeer4.verkeerdomain.data.Logging;
import be.ugent.verkeer4.verkeerdomain.data.composite.LogCount;
import java.util.List;



/**
 *
 * @author Tomas Bolckmans
 */
public interface ILogService {

    public void insert(LogTypeEnum type, String category, String message);
    
    public List<Logging> getLogs(); 
    
    public List<LogCount> getLogCount();

}
