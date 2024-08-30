package unsw.blackout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import unsw.device.DeviceFile;
import unsw.response.models.EntityInfoResponse;
import unsw.response.models.FileInfoResponse;
import unsw.satellite.RelaySatellite;
import unsw.satellite.Satellite;
import unsw.satellite.StandardSatellite;
import unsw.satellite.TeleportingSatellite;
import unsw.satellite.ShrinkingSatellite;
import unsw.utils.Angle;
import unsw.blackout.FileTransferException.VirtualFileAlreadyExistsException;
import unsw.blackout.FileTransferException.VirtualFileNoBandwidthException;
import unsw.blackout.FileTransferException.VirtualFileNoStorageSpaceException;
import unsw.blackout.FileTransferException.VirtualFileNotFoundException;
import unsw.connector.Connector;
import unsw.device.Device;
import unsw.utils.MathsHelper;
import unsw.utils.ZippedFileUtils;

/**
 * The controller for the Blackout system.
 *
 * WARNING: Do not move this file or modify any of the existing method
 * signatures
 */
public class BlackoutController {
    private ArrayList<Device> devices = new ArrayList<>();
    private ArrayList<String> devicesId = new ArrayList<>();
    private ArrayList<Satellite> satellites = new ArrayList<>();
    private ArrayList<String> satellitesId = new ArrayList<>();

    public void createDevice(String deviceId, String type, Angle position) {
        boolean flag_device = true;
        for (String id : devicesId) {
            if (id.equals(deviceId)) {
                flag_device = false;
            }
        }
        boolean flag_satellite = true;
        for (String id : satellitesId) {
            if (id.equals(deviceId)) {
                flag_satellite = false;
            }
        }
        if (flag_device && flag_satellite) {
            if (type.equals("HandheldDevice") || type.equals("LaptopDevice") || type.equals("DesktopDevice")) {
                Device device = new Device(deviceId, position, type);
                devices.add(device);
                devicesId.add(deviceId);

            }
        }
    }

    public void removeDevice(String deviceId) {
        for (Device device : devices) {
            if (device.getId().equals(deviceId)) {
                devices.remove(device);
                devicesId.remove(deviceId);
                break;
            }
        }
    }

    public void createSatellite(String satelliteId, String type, double height, Angle position) {
        boolean flag_device = true;
        for (String id : devicesId) {
            if (id.equals(satelliteId)) {
                flag_device = false;
            }
        }
        boolean flag_satellite = true;
        for (String id : satellitesId) {
            if (id.equals(satelliteId)) {
                flag_satellite = false;
            }
        }
        if (flag_device && flag_satellite) {
            if (type.equals("StandardSatellite")) {
                satellites.add(new StandardSatellite(satelliteId, position, height, type));
                satellitesId.add(satelliteId);
            } else if (type.equals("RelaySatellite")) {
                satellites.add(new RelaySatellite(satelliteId, position, height, type));
                satellitesId.add(satelliteId);
            } else if (type.equals("TeleportingSatellite")) {
                satellites.add(new TeleportingSatellite(satelliteId, position, height, type));
                satellitesId.add(satelliteId);
            } else if (type.equals("ShrinkingSatellite")) {
                satellites.add(new ShrinkingSatellite(satelliteId, position, height, type));
                satellitesId.add(satelliteId);
            }
        }
    }

    public void removeSatellite(String satelliteId) {
        for (Satellite satellite : satellites) {

            if (satellite.getId().equals(satelliteId)) {
                satellites.remove(satellite);
                satellitesId.remove(satelliteId);
                break;
            }
        }
    }

    public List<String> listDeviceIds() {

        return devicesId;
    }

    public List<String> listSatelliteIds() {
        return satellitesId;
    }

    public void addFileToDevice(String deviceId, String filename, String content) {
        for (Device device : devices) {
            if (device.getId().equals(deviceId)) {
                device.addFile(new DeviceFile(content, content.length(), filename, true));
                break;
            }
        }
    }

    public EntityInfoResponse getInfo(String id) {
        for (Device device : devices) {
            if (device.getId().equals(id)) {
                List<DeviceFile> files = device.getFiles();

                Map<String, FileInfoResponse> retFile = new HashMap<>();
                for (DeviceFile file : files) {
                    retFile.put(file.getFilename(), new FileInfoResponse(file.getFilename(), file.getContent(),
                            file.getSize(), file.getIsComplete()));
                }
                for (Connector c : device.getConnectors()) {
                    DeviceFile file = c.getFile();
                    retFile.put(file.getFilename(), new FileInfoResponse(file.getFilename(), file.getContent(),
                            file.getSize(), file.getIsComplete()));
                }
                return new EntityInfoResponse(id, device.getPosition(), MathsHelper.RADIUS_OF_JUPITER, device.getType(),
                        retFile);
            }
        }
        for (Satellite satellite : satellites) {

            if (satellite.getId().equals(id)) {

                Map<String, FileInfoResponse> retFile = new HashMap<>();
                List<DeviceFile> files = satellite.getFiles();
                for (DeviceFile file : files) {
                    retFile.put(file.getFilename(), new FileInfoResponse(file.getFilename(), file.getContent(),
                            file.getSize(), file.getIsComplete()));
                }
                for (Connector c : satellite.getConnectors()) {
                    DeviceFile file = c.getFile();
                    retFile.put(file.getFilename(), new FileInfoResponse(file.getFilename(), file.getContent(),
                            file.getSize(), file.getIsComplete()));
                }
                return new EntityInfoResponse(id, satellite.getAngle(), satellite.getHeight(), satellite.getType(),
                        retFile);

            }
        }
        return null;
    }

    public static void main(String[] args) throws FileTransferException {
        BlackoutController controller = new BlackoutController();

        controller.createDevice("Device1", "HandheldDevice", Angle.fromDegrees(180), true);
        controller.simulate(5);

        System.out.println(controller.getInfo("Device1").getPosition());
    }

    public void simulate() {
        for (Satellite satellite : satellites) {

            //satellite.updateConnection(currentTime);
            //satellite.updatePossibleConnection(currentTime, devices);
            satellite.updatePosition();

            if (satellite.getConnectors() != null) {
                Iterator<Connector> iterator = satellite.getConnectors().iterator();
                while (iterator.hasNext()) {
                    Connector c = iterator.next();
                    String fromId = c.getFromId();
                    if (!communicableEntitiesInRange(satellite.getId()).contains(fromId)) {
                        iterator.remove();
                    }
                    int bandwidth = c.getBandwidth();
                    DeviceFile file = c.getFile();
                    String data = c.getData();
                    int charsToRemove = Math.min(bandwidth, data.length());
                    String removeChars = data.substring(0, charsToRemove);
                    file.setContent(file.getContent() + removeChars);

                    c.setData(data.substring(charsToRemove));
                    if (c.getData().length() == 0) {
                        satellite.setStorage(file.getContent().length() + satellite.getStorage());
                        satellite.setFromBandwidth(satellite.getFromBandwidth() + bandwidth);
                        for (Satellite s : satellites) {
                            if (s.getId().equals(c.getFromId())) {
                                s.setToBandwidth(s.getToBandwidth() + bandwidth);
                            }
                        }
                        if (satellite.getType() == "ShrinkingSatellite"
                                && !ZippedFileUtils.isZipped(file.getContent())) {
                            file.setContent(ZippedFileUtils.zipFile(file.getContent()));
                            file.setSize(file.getContent().length());
                        } else if (ZippedFileUtils.isZipped(file.getContent())) {
                            file.setContent(ZippedFileUtils.unzipFile(file.getContent()));
                            file.setSize(file.getContent().length());
                        }

                        file.setIsComplete(true);
                        satellite.addFile(file);
                        iterator.remove();
                    }
                }

            }
        }
        for (Device device : devices) {
            if (device.getConnectors() != null) {
                Iterator<Connector> iterator = device.getConnectors().iterator();
                while (iterator.hasNext()) {
                    Connector c = iterator.next();
                    String fromId = c.getFromId();
                    if (!communicableEntitiesInRange(device.getId()).contains(fromId)) {
                        iterator.remove();
                    }
                    int bandwidth = c.getBandwidth();
                    DeviceFile file = c.getFile();
                    String data = c.getData();
                    int charsToRemove = Math.min(bandwidth, data.length());
                    String removeChars = data.substring(0, charsToRemove);
                    file.setContent(file.getContent() + removeChars);

                    c.setData(data.substring(charsToRemove));
                    if (c.getData().length() == 0) {
                        for (Satellite s : satellites) {
                            if (s.getId().equals(c.getFromId())) {
                                s.setToBandwidth(s.getToBandwidth() + c.getBandwidth());
                            }
                        }

                        file.setIsComplete(true);

                        device.addFile(file);
                        iterator.remove();
                    }
                }
            }
        }
    }

    /**
     * Simulate for the specified number of minutes. You shouldn't need to modify
     * this function.
     */
    public void simulate(int numberOfMinutes) {
        for (int i = 0; i < numberOfMinutes; i++) {
            simulate();

        }
    }

    public Satellite findSatellite(String id) {
        for (Satellite s : satellites) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    public List<String> communicableEntitiesInRange(String id) {
        List<String> visibleIds = new ArrayList<>();
        for (Device device : devices) {
            if (device.getId().equals(id)) {
                for (Satellite satellite : satellites) {
                    boolean isVisible = MathsHelper.isVisible(satellite.getHeight(), satellite.getAngle(),
                            device.getPosition());
                    if (!isVisible && satellite.getType().equals("RelaySatellite")) {
                        visibleIds.add(satellite.getId());
                    }
                    if (isVisible && !visibleIds.contains(satellite.getId())) {
                        visibleIds.add(satellite.getId());
                    }
                }
                return visibleIds;
            }
        }
        for (Satellite satellite : satellites) {
            if (satellite.getId().equals(id)) {

                for (Device device : devices) {
                    boolean isVisible = MathsHelper.isVisible(satellite.getHeight(), satellite.getAngle(),
                            device.getPosition());
                    if (isVisible && !visibleIds.contains(device.getId())) {
                        visibleIds.add(device.getId());
                    }
                }
                for (Satellite otherSatellite : satellites) {
                    boolean isVisible = MathsHelper.isVisible(satellite.getHeight(), satellite.getAngle(),
                            otherSatellite.getHeight(), otherSatellite.getAngle());
                    if (!isVisible && satellite.getType().equals("RelaySatellite")) {
                        visibleIds.add(satellite.getId());
                    }
                    if (isVisible && !visibleIds.contains(otherSatellite.getId())) {
                        visibleIds.add(otherSatellite.getId());
                    }
                }
            }
        }

        for (String visibleId : visibleIds) {
            Satellite satellite = findSatellite(visibleId);
            if (satellite == null) {
                continue;
            }
            if (satellite.getType().equals("RelaySatellite")) {
                for (Device device : devices) {
                    boolean isVisible = MathsHelper.isVisible(satellite.getHeight(), satellite.getAngle(),
                            device.getPosition());
                    if (isVisible && !visibleIds.contains(device.getId())) {
                        visibleIds.add(device.getId());
                    }
                }
                for (Satellite otherSatellite : satellites) {
                    boolean isVisible = MathsHelper.isVisible(satellite.getHeight(), satellite.getAngle(),
                            otherSatellite.getHeight(), otherSatellite.getAngle());
                    if (isVisible && !visibleIds.contains(otherSatellite.getId())) {
                        visibleIds.add(otherSatellite.getId());
                    }
                }
            }
        }
        visibleIds.remove(id);
        return visibleIds;
    }

    // from satellite to device or satellite
    private void fromSatelliteToDevice(String fileName, String fromId, String toId) throws FileTransferException {
        // to device

        Device toDevice = null;
        Satellite fromSatellite = null;
        for (Satellite satellite : satellites) {
            if (satellite.getId().equals(fromId)) {
                fromSatellite = satellite;
            }
        }
        for (Device device : devices) {
            if (device.getId().equals(toId)) {
                toDevice = device;
            }
        }
        if (toDevice != null && fromSatellite != null) {
            if (toDevice.getType().equals("DesktopDevice") && fromSatellite.getType().equals("StandardSatellite")) {
                System.out.println("device not support");
                return;
            }
            DeviceFile file = fromSatellite.findFilefromSatellite(fileName);
            if (file == null) {
                throw new VirtualFileNotFoundException(fileName);
            }

            checkSatellite(fileName, fromSatellite);
            int transferBandwidth = fromSatellite.getToBandwidth();
            fromSatellite.setToBandwidth(fromSatellite.getToBandwidth() - transferBandwidth);
            if (fromSatellite.getType().equals("ShrinkingSatellite")) {
                file.setContent(ZippedFileUtils.zipFile(file.getContent()));
                file.setSize(file.getContent().length());
            }
            Connector connector = new Connector(fromSatellite.getId(),
                    new DeviceFile("", file.getSize(), fileName, false), file.getContent(), transferBandwidth, fromSatellite);
            toDevice.addConnector(connector);
            //String fromType = fromSatellite.getType();
            //setSimulateToDevice(toDevice, file, fromSatellite);
        }
    }

    private void checkSatellite(String fileName, Satellite Satellite) throws FileTransferException {
        String type = Satellite.getType();

        if (type.equals("StandardSatellite")) {
            if (Satellite.getStorage() == 80 || Satellite.getNumFileStorage() == 3) {
                throw new VirtualFileNoStorageSpaceException(fileName);
            }
        } else if (type.equals("TeleportingSatellite")) {
            if (Satellite.getStorage() == 200) {
                throw new VirtualFileNoStorageSpaceException(fileName);
            }
        }
    }

    private void fromSatelliteToSatellite(String fileName, String fromId, String toId) throws FileTransferException {
        // to satellite
        Satellite toSatellite = null;
        Satellite fromSatellite = null;
        for (Satellite satellite : satellites) {
            if (satellite.getId().equals(toId)) {
                toSatellite = satellite;
                for (DeviceFile file : satellite.getFiles()) {
                    if (file.getFilename().equals(fileName)) {
                        throw new VirtualFileAlreadyExistsException(fileName);
                    }
                }
            }
            if (satellite.getId().equals(fromId)) {
                fromSatellite = satellite;
            }
        }
        if (toSatellite != null && fromSatellite != null) {
            DeviceFile file = fromSatellite.findFilefromSatellite(fileName);
            if (file == null) {
                throw new VirtualFileNotFoundException(fileName);
            }
            int transferBandwidth = Math.min(toSatellite.getFromBandwidth(), fromSatellite.getToBandwidth());
            if (toSatellite.getFromBandwidth() - transferBandwidth < 0
                    || fromSatellite.getToBandwidth() - transferBandwidth < 0) {
                throw new VirtualFileNoBandwidthException(fileName);
            }
            toSatellite.setFromBandwidth(toSatellite.getFromBandwidth() - transferBandwidth);
            fromSatellite.setToBandwidth(fromSatellite.getToBandwidth() - transferBandwidth);

            checkSatellite(fileName, fromSatellite);
            if (fromSatellite.getType().equals("ShrinkingSatellite")) {
                file.setContent(ZippedFileUtils.zipFile(file.getContent()));
                file.setSize(file.getContent().length());
            }
            //toSatellite(fileName, toSatellite, file, fromSatellite);
            Connector connector = new Connector(fromSatellite.getId(),
                    new DeviceFile("", file.getSize(), fileName, false), file.getContent(),
                    Math.min(toSatellite.getFromBandwidth(), transferBandwidth), fromSatellite);
            toSatellite.addConnector(connector);
        }
    }

    // from device to satellite
    private void fromDevicetoSatellite(String fileName, String fromId, String toId) throws FileTransferException {
        Device fromDevice = null;
        Satellite toSatellite = null;
        for (Satellite satellite : satellites) {
            if (satellite.getId().equals(toId)) {
                toSatellite = satellite;
                for (DeviceFile file : satellite.getFiles()) {
                    if (file.getFilename().equals(fileName)) {
                        throw new VirtualFileAlreadyExistsException(fileName);
                    }
                }
            }
        }
        for (Device device : devices) {
            if (device.getId().equals(fromId)) {
                fromDevice = device;
            }
        }
        if (toSatellite != null && fromDevice != null) {
            if (fromDevice.getType().equals("DesktopDevice") && toSatellite.getType().equals("StandardSatelite")) {
                System.out.println("device not support");
                return;
            }
            DeviceFile file = fromDevice.findFile(fileName);

            if (file == null) {

                throw new VirtualFileNotFoundException(fileName);
            }
            int transferBandwidth = toSatellite.getFromBandwidth();
            if (toSatellite.getFromBandwidth() - transferBandwidth < 0) {
                throw new VirtualFileNoBandwidthException(fileName);
            }
            checkSatellite(fileName, toSatellite);
            toSatellite.setFromBandwidth(toSatellite.getFromBandwidth() - transferBandwidth);
            //toSatellite(fileName, toSatellite, file, null);
            Connector connector = new Connector(fromDevice.getId(), new DeviceFile("", file.getSize(), fileName, false),
                    file.getContent(), transferBandwidth);
            toSatellite.addConnector(connector);
        }
    }

    // from device or satellite to device
    // from satellite to satellite or device
    public void sendFile(String fileName, String fromId, String toId) throws FileTransferException {

        fromSatelliteToDevice(fileName, fromId, toId);
        fromSatelliteToSatellite(fileName, fromId, toId);
        fromDevicetoSatellite(fileName, fromId, toId);
    }

    public void createDevice(String deviceId, String type, Angle position, boolean isMoving) {
        createDevice(deviceId, type, position);
        // TODO: Task 3
    }

    public void createSlope(int startAngle, int endAngle, int gradient) {
        // TODO: Task 3
        // If you are not completing Task 3 you can leave this method blank :)
    }

}
