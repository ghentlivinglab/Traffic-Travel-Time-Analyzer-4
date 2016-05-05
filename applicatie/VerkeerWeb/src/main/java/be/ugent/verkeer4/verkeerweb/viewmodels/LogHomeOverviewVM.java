/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.verkeer4.verkeerweb.viewmodels;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tomas Bolckmans
 */
public class LogHomeOverviewVM {
    private List<LogHomeEntryVM> logEntries;
    
    public LogHomeOverviewVM(){
        logEntries = new ArrayList<>();
    }
    
    /**
     * @return the logEntries
     */
    public List<LogHomeEntryVM> getLogEntries() {
        return logEntries;
    }

    /**
     * @param logEntries the logEntries to set
     */
    public void setLogEntries(List<LogHomeEntryVM> logEntries) {
        this.logEntries = logEntries;
    }
    
}
