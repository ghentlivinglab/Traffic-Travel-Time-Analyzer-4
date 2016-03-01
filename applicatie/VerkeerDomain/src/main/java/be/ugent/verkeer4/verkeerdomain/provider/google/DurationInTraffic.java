
package be.ugent.verkeer4.verkeerdomain.provider.google;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class DurationInTraffic {

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("value")
    @Expose
    private Integer value;

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

    public DurationInTraffic withText(String text) {
        this.text = text;
        return this;
    }

    /**
     * 
     * @return
     *     The value
     */
    public Integer getValue() {
        return value;
    }

    /**
     * 
     * @param value
     *     The value
     */
    public void setValue(Integer value) {
        this.value = value;
    }

    public DurationInTraffic withValue(Integer value) {
        this.value = value;
        return this;
    }

}
