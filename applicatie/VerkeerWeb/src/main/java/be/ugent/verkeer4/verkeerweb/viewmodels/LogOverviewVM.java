/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.verkeer4.verkeerweb.viewmodels;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Tomas Bolckmans
 */
public class LogOverviewVM {
    private List<> logEntries;
    
    public LogOverviewVM(){
        logEntries = new ArrayList<>();
    }
    
    /**
     * @return the logEntries
     */
    public List<> getLogEntries() {
        return logEntries;
    }

    /**
     * @param logEntries the logEntries to set
     */
    public void setLogEntries(List<> logEntries) {
        this.logEntries = logEntries;
    }
    
}
