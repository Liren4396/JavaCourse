package unsw.satellite;

import unsw.utils.Angle;

/**
 * Supports all devices
 */
public class RelaySatellite extends Satellite {
    private static final double LINEAR_VELOCITY = 1500;
    public RelaySatellite(String id, Angle angle, double height, String type) {
        super(id, angle, height, type);
        super.setVelocity(LINEAR_VELOCITY);
        super.setFlag(-1);;
    }
    
}
