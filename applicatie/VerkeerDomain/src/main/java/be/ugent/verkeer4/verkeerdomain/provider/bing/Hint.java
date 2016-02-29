
package be.ugent.verkeer4.verkeerdomain.provider.bing;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Hint {

    @SerializedName("hintType")
    @Expose
    private String hintType;
    @SerializedName("text")
    @Expose
    private String text;

    /**
     * 
     * @return
     *     The hintType
     */
    public String getHintType() {
        return hintType;
    }

    /**
     * 
     * @param hintType
     *     The hintType
     */
    public void setHintType(String hintType) {
        this.hintType = hintType;
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
