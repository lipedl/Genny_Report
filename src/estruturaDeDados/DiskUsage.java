/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estruturaDeDados;

import java.io.Serializable;
import javax.annotation.processing.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class DiskUsage implements Serializable {

    @SerializedName("free")
    @Expose
    private String free;
    @SerializedName("used")
    @Expose
    private String used;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("device")
    @Expose
    private String device;
    @SerializedName("fstype")
    @Expose
    private String fstype;
    @SerializedName("percent")
    @Expose
    private Integer percent;
    private final static long serialVersionUID = 7440907296316041303L;

    /**
     * No args constructor for use in serialization
     *
     */
    public DiskUsage() {
    }

    /**
     *
     * @param total
     * @param used
     * @param free
     * @param device
     * @param percent
     * @param fstype
     */
    public DiskUsage(String free, String used, String total, String device, String fstype, Integer percent) {
        super();
        this.free = free;
        this.used = used;
        this.total = total;
        this.device = device;
        this.fstype = fstype;
        this.percent = percent;
    }

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getFstype() {
        return fstype;
    }

    public void setFstype(String fstype) {
        this.fstype = fstype;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(DiskUsage.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("free");
        sb.append('=');
        sb.append(((this.free == null) ? "<null>" : this.free));
        sb.append(',');
        sb.append("used");
        sb.append('=');
        sb.append(((this.used == null) ? "<null>" : this.used));
        sb.append(',');
        sb.append("total");
        sb.append('=');
        sb.append(((this.total == null) ? "<null>" : this.total));
        sb.append(',');
        sb.append("device");
        sb.append('=');
        sb.append(((this.device == null) ? "<null>" : this.device));
        sb.append(',');
        sb.append("fstype");
        sb.append('=');
        sb.append(((this.fstype == null) ? "<null>" : this.fstype));
        sb.append(',');
        sb.append("percent");
        sb.append('=');
        sb.append(((this.percent == null) ? "<null>" : this.percent));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
