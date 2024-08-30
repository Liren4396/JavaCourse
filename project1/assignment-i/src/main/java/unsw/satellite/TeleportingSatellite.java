package unsw.satellite;

import unsw.utils.Angle;

/**
 * speed: 1000 km/min
 * support all devices
 * maximum range: 200000
 * If a file transfer from a satellite to a device or a satellite to another satellite is in progress when the satellite teleports, the rest of the file is instantly downloaded, however all "t" letter bytes are removed from the remaining bytes to be sent.

For the satellite to satellite case, the behaviour is the same whether it is the sender or receiving that is teleporting


If a file transfer from a device to a satellite is in progress when the satellite teleports, the download fails and the partially uploaded file is removed from the satellite, and all "t" letter bytes are removed from the file on the device.
There is no 'correction' with the position after a teleport occurs as there is for Relay Satellites (see below). Once the satellite teleports to θ = 0 it does not continue moving for the remainder of the tick.
Teleporting satellites start by moving anticlockwise.
 */
public class TeleportingSatellite extends Satellite {
    private static final double LINEAR_VELOCITY = 1000;
    public TeleportingSatellite(String id, Angle angle, double height, String type) {
        super(id, angle, height, type);
        super.setVelocity(LINEAR_VELOCITY);
        super.setFlag(1);;
        super.setFromBandwidth(15);
        super.setToBandwidth(10);
    }
}
