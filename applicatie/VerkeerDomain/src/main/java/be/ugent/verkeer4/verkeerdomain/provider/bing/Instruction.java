
package be.ugent.verkeer4.verkeerdomain.provider.bing;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Instruction {

    @SerializedName("formattedText")
    @Expose
    private Object formattedText;
    @SerializedName("maneuverType")
    @Expose
    private String maneuverType;
    @SerializedName("text")
    @Expose
    private String text;

    /**
     * 
     * @return
     *     The formattedText
     */
    public Object getFormattedText() {
        return formattedText;
    }

    /**
     * 
     * @param formattedText
     *     The formattedText
     */
    public void setFormattedText(Object formattedText) {
        this.formattedText = formattedText;
    }

    /**
     * 
     * @return
     *     The maneuverType
     */
    public String getManeuverType() {
        return maneuverType;
    }

    /**
     * 
     * @param maneuverType
     *     The maneuverType
     */
    public void setManeuverType(String maneuverType) {
        this.maneuverType = maneuverType;
    }

    /**
     * 
     * @return
     *     The text
     */
    public String getText() {
        return text;
    }

    /**
     * 
     * @param text
     *     The text
     */
    public void setText(String text) {
        this.text = text;
    }

}
