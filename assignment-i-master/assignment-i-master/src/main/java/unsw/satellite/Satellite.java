package unsw.satellite;

import java.util.ArrayList;
import java.util.Iterator;

import unsw.connector.Connector;
import unsw.device.*;
import unsw.utils.Angle;
import unsw.utils.ZippedFileUtils;

public class Satellite {
    private String id;
    private Angle angle;
    private double height;
    private String type;
    private Double velocity;
    private int fromBandwidth;
    private int toBandwidth;
    private int storage;
    private ArrayList<DeviceFile> files;
    private int flag;
    private ArrayList<Connector> connectors;
    public Satellite(String id, Angle angle, double height, String type) {
        this.id = id;
        this.angle = angle;
        this.height = height;
        this.type = type;
        this.files = new ArrayList<>();
        this.connectors = new ArrayList<>();
    }
    public ArrayList<Connector> getConnectors() {
        return connectors;
    }
    public void addConnector(Connector connector) {
        this.connectors.add(connector);
    }
    public void setFromBandwidth(int fromBandwidth) {
        this.fromBandwidth = fromBandwidth;
    }
    public void setToBandwidth(int toBandwidth) {
        this.toBandwidth = toBandwidth;
    }
    public int getFromBandwidth() {
        return fromBandwidth;
    }
    public int getToBandwidth() {
        return toBandwidth;
    }
    public void setFlag(int flag) {
        this.flag = flag;
    }
    public String toString() {
        return id + " " + angle + " "  + height + " " + type;
    }

    public ArrayList<DeviceFile> getFiles() {
        return files;
    }
    public void dropFile(DeviceFile file) {
        files.remove(file);
    }
    public void addFile(DeviceFile file) {
        files.add(file);
    }
    public DeviceFile findFilefromSatellite(String fileName) {
        for (DeviceFile file : files) {
            if (file.getFilename().equals(fileName)) {
                return file;
            }
        }
        return null;
    }

    
    public int getNumFileStorage() {
        return files.size();
    }


    public int getStorage() {
        return storage;
    }

    public Double getVelocity() {
        return velocity;
    }
    public Angle getAngle() {
        return angle;
    }
    public double getHeight() {
        return height;
    }
    public String getId() {
        return id;
    }
    public String getType() {
        return type;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }
    public void setAngle(Angle angle) {
        this.angle = angle;
    }
    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public void updatePosition() {
        switch (this.type) {
            case "StandardSatellite":
                Angle angularVelocity = Angle.fromRadians(this.getVelocity() / this.getHeight());
                this.setAngle(this.getAngle().subtract(angularVelocity));
                break;
            case "ShrinkingSatellite":
                angularVelocity = Angle.fromRadians(this.getVelocity() / this.getHeight());
                this.setAngle(this.getAngle().subtract(angularVelocity));
                break;
            case "TeleportingSatellite":
                angularVelocity = Angle.fromRadians(this.getVelocity() / this.getHeight());
                if (flag == 1) {
                    this.setAngle(this.getAngle().add(angularVelocity));
                    if (this.getAngle().compareTo(Angle.fromDegrees(180.00)) >= 0) {
                        this.setAngle(Angle.fromDegrees(0));
                        flag *= -1;
                        Iterator<Connector> iterator = this.getConnectors().iterator();
                        while (iterator.hasNext()) {
                            Connector c = iterator.next();
                            DeviceFile file = c.getFile();
                            int bandwidth = c.getBandwidth();
                            if (c.getData().length() == 0) {
                                this.setStorage(file.getContent().length() + this.getStorage());
                                this.setFromBandwidth(this.getFromBandwidth() + bandwidth);
                                if (c.getForTeleportingSatellite() != null) {
                                    Satellite fromSatellite = c.getForTeleportingSatellite();
                                    fromSatellite.setToBandwidth(fromSatellite.getToBandwidth() + bandwidth);
                                }
                                    
                                if (this.getType() == "ShrinkingSatellite"
                                        && !ZippedFileUtils.isZipped(file.getContent())) {
                                    file.setContent(ZippedFileUtils.zipFile(file.getContent()));
                                    file.setSize(file.getContent().length());
                                } else if (ZippedFileUtils.isZipped(file.getContent())) {
                                    file.setContent(ZippedFileUtils.unzipFile(file.getContent()));
                                    file.setSize(file.getContent().length());
                                }

                                file.setIsComplete(true);
                                this.addFile(file);
                                iterator.remove();
                            }
                        }
                        
                    }
                } else {

                    this.setAngle(this.getAngle().subtract(angularVelocity));
                    if (this.getAngle().compareTo(Angle.fromDegrees(-180.00)) <= 0) {
                        this.setAngle(Angle.fromDegrees(0));
                        flag *= -1;
                    }
                }
                break;
            case "RelaySatellite":
                angularVelocity = Angle.fromRadians(this.getVelocity() / this.getHeight());
                if (flag == 1) {
                    if (this.getAngle().add(angularVelocity).compareTo(Angle.fromDegrees(190.00)) >= 0 && flag != -1) {
                        flag = -1;
                    }
                    this.setAngle(this.getAngle().add(angularVelocity));
                } else {
                    if (this.getAngle().subtract(angularVelocity).compareTo(Angle.fromDegrees(140.00)) <= 0 && flag != 1) {
                        flag = 1;
                    }
                    this.setAngle(this.getAngle().subtract(angularVelocity));
                }
        }
    }

}
