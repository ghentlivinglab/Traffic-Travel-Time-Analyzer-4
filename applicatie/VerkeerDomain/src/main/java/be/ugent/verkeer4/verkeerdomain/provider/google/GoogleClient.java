
package be.ugent.verkeer4.verkeerdomain.provider.google;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class GoogleClient {

    @SerializedName("destination_addresses")
    @Expose
    private List<String> destinationAddresses = new ArrayList<String>();
    @SerializedName("origin_addresses")
    @Expose
    private List<String> originAddresses = new ArrayList<String>();
    @SerializedName("rows")
    @Expose
    private List<Row> rows = new ArrayList<Row>();
    @SerializedName("status")
    @Expose
    private String status;

    /**
     * 
     * @return
     *     The destinationAddresses
     */
    public List<String> getDestinationAddresses() {
        return destinationAddresses;
    }

    /**
     * 
     * @param destinationAddresses
     *     The destination_addresses
     */
    public void setDestinationAddresses(List<String> destinationAddresses) {
        this.destinationAddresses = destinationAddresses;
    }

    public GoogleClient withDestinationAddresses(List<String> destinationAddresses) {
        this.destinationAddresses = destinationAddresses;
        return this;
    }

    /**
     * 
     * @return
     *     The originAddresses
     */
    public List<String> getOriginAddresses() {
        return originAddresses;
    }

    /**
     * 
     * @param originAddresses
     *     The origin_addresses
     */
    public void setOriginAddresses(List<String> originAddresses) {
        this.originAddresses = originAddresses;
    }

    public GoogleClient withOriginAddresses(List<String> originAddresses) {
        this.originAddresses = originAddresses;
        return this;
    }

    /**
     * 
     * @return
     *     The rows
     */
    public List<Row> getRows() {
        return rows;
    }

    /**
     * 
     * @param rows
     *     The rows
     */
    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    public GoogleClient withRows(List<Row> rows) {
        this.rows = rows;
        return this;
    }

    /**
     * 
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    public GoogleClient withStatus(String status) {
        this.status = status;
        return this;
    }

}
