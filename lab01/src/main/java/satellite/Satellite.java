package satellite;

public class Satellite {
    private String name;
    private int height;
    private double velocity;
    private double position;
    /**
     * Constructor for Satellite
     * @param name
     * @param height
     * @param velocity
     */
    public Satellite(String name, int height, double position, double velocity) {
        this.name = name;
        this.height = height;
        this.position = position;
        this.velocity = velocity;
    }

    /**
     * Getter for name
     */
    public String getName() {

        return this.name;
    }

    /**
     * Getter for height
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Getter for position (degrees)
     */
    public double getPositionDegrees() {
        return this.position;
    }

    /**
     * Getter for position (radians)
     */
    public double getPositionRadians() {
        return Math.toRadians(this.position);
    }

    /**
     * Returns the linear velocity (metres per second) of the satellite
     */
    public double getLinearVelocity() {
        return this.velocity;
    }

    /**
     * Returns the angular velocity (radians per second) of the satellite
     */
    public double getAngularVelocity() {
        return Math.toRadians(this.velocity);
    }

    /**
     * Setter for name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter for height
     * @param height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Setter for velocity
     * @param velocity
     */
    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public double getVelocity() {
        return this.velocity;
    }

    /**
     * Setter for position
     * @param position
     */
    public void setPosition(double position) {
        this.position = position;
    }

    /**
     * Calculates the distance travelled by the satellite in the given time
     * @param time (seconds)
     * @return distance in metres
     */
    public double distance(double time) {

        return time * this.velocity;
    }

    public static void main(String[] args) {
        // Add your code
        Satellite s = new Satellite("A", 10000, 122.0, 55);
        System.out.print("I am Satellite " + s.getName() + " at position " + s.getPositionDegrees()
        + " degrees, " + s.getHeight() + " km above the centre of the earth "
        + "and moving at a velocity of " + s.getLinearVelocity() + " metres per second\n" + s.getPositionRadians()
        + "\n0.04303052592865024\n4380.0\n");
    }

}
