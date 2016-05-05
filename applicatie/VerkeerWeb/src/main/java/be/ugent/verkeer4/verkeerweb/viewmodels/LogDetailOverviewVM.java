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
public class LogDetailOverviewVM {
    private List<LogDetailEntryVM> logEntries;
    
    public LogDetailOverviewVM(){
        logEntries = new ArrayList<>();
    }
    
    /**
     * @return the logEntries
     */
    public List<LogDetailEntryVM> getLogEntries() {
        return logEntries;
    }

    /**
     * @param logEntries the logEntries to set
     */
    public void setLogEntries(List<LogDetailEntryVM> logEntries) {
        this.logEntries = logEntries;
    }
    
}
