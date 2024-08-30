package unsw.device;
import java.util.ArrayList;
import java.util.List;
import unsw.connector.Connector;
import unsw.utils.Angle;

public class Device {
    /**
     * id of device
     */
    private String id;
    /**
     * device position
     */
    private Angle position;
    /**
     * device type
     */
    private String type;
    private List<DeviceFile> files;
    private ArrayList<Connector> connectors;
    public Device(String id, Angle position, String type) {
        this.id = id;
        this.type = type;
        this.position = position;
        files = new ArrayList<>();
        connectors = new ArrayList<>();
    }
    public ArrayList<Connector> getConnectors() {
        return connectors;
    }
    public void addConnector(Connector connector) {
        this.connectors.add(connector);
    }
    public DeviceFile findFile(String fileName) {
        for (DeviceFile file : files) {
            if (file.getFilename().equals(fileName)) {
                return file;
            }
        }
        return null;
    }
    public void addFile(DeviceFile file) {
        files.add(file);
    }

    public void removeFile(DeviceFile obj) {
        files.remove(obj);
    }
    public List<DeviceFile> getFiles() {
        return files;
    }

    public String getId() {
        return id;
    }

    public Angle getPosition() {
        return position;
    }
    
    public String getType() {
        return type;
    }

}
