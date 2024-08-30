package trafficlight;

public class TrafficLight {
    private String id;
    private Light light = new RedLight();
    //private int count = 0;

    public TrafficLight(String state, String id) {
        this.id = id;
        switch (state) {
            case "Red light":
                light = new RedLight();
                break;
            case "Green light":
                light = new GreenLight();
                break;
            case "Yellow light":
                light = new YellowLight();
                break;
            case "Pedestrian light":
                light = new PedestrianLight();    
                break;
        }
    }

    public void change(int numOfCars, int numOfPedestrians) {
        int trafficDemand = numOfCars + numOfPedestrians;
        light.change(this, trafficDemand, numOfPedestrians);
    }
    public void changeLight(Light light) {
        this.light = light;
    }
    public int timeRemaining() {
        return light.timeRemaining();
    }

    public String reportState() {
        return light.reportState();
    }
    public static void main(String[] args) {
        Intersection intersection = new Intersection();
        String id = intersection.addTrafficLight("Red light");
        System.out.println(intersection.reportState(id));
    }
}
