package unsw.connector;

import unsw.device.DeviceFile;
import unsw.satellite.Satellite;


// check its from id and to id and check if it's in connected by using communicate in range
public class Connector {
    private String fromId;
    private DeviceFile file;
    private String data;
    private int bandwidth;
    private Satellite forTeleportingSatellite;
    public Connector(String fromId, DeviceFile file, String data, int bandwidth) {
        this.bandwidth = bandwidth;
        this.fromId = fromId;
        this.file = file;
        this.data = data;
        this.forTeleportingSatellite = null;
    }
    public Connector(String fromId, DeviceFile file, String data, int bandwidth, Satellite satellite) {
        this.bandwidth = bandwidth;
        this.fromId = fromId;
        this.file = file;
        this.data = data;
        this.forTeleportingSatellite = satellite;
    }
    public Satellite getForTeleportingSatellite() {
        return forTeleportingSatellite;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public DeviceFile getFile() {
        return file;
    }
    public void setFile(DeviceFile file) {
        this.file = file;
    }
    public String getFromId() {
        return fromId;
    }
    public int getBandwidth() {
        return bandwidth;
    }
    public String toString() {
        return fromId+" | "+file+" | "+data+" | "+bandwidth;
    }
}
