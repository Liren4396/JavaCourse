package unsw.satellite;

import unsw.utils.Angle;


/**
 * speed: 2500 km/min
 * support device type: handhelds & laptops
 * maximum range: 150000
 * max store file: 3 | 80 bytes
 * only transfet 1 file at a time
 */
public class StandardSatellite extends Satellite {
    private static final double LINEAR_VELOCITY = 2500;
    public StandardSatellite(String id, Angle angle, double height, String type) {
        super(id, angle, height, type);
        super.setVelocity(LINEAR_VELOCITY);
        super.setFromBandwidth(1);
        super.setToBandwidth(1);
    }
}
