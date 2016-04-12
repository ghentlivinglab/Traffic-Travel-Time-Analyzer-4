/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.verkeer4.verkeerweb.viewmodels;

/**
 *
 * @author Tomas Bolckmans
 */
public class LogHomeEntryVM {
    private String category;
    private int infoCount;
    private int warningCount;
    private int errorCount;

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category.replaceFirst("be.ugent.verkeer4.", "");
    }

    /**
     * @return the infoCount
     */
    public int getInfoCount() {
        return infoCount;
    }

    /**
     * @param infoCount the infoCount to set
     */
    public void setInfoCount(int infoCount) {
        this.infoCount = infoCount;
    }

    /**
     * @return the warningCount
     */
    public int getWarningCount() {
        return warningCount;
    }

    /**
     * @param warningCount the warningCount to set
     */
    public void setWarningCount(int warningCount) {
        this.warningCount = warningCount;
    }

    /**
     * @return the errorCount
     */
    public int getErrorCount() {
        return errorCount;
    }

    /**
     * @param errorCount the errorCount to set
     */
    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }
    
    

}
