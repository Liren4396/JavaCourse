package trafficlight;

public class PedestrianLight implements Light {
    private int count;

    public PedestrianLight() {
        count = 2;
    }
    @Override
    public void change(TrafficLight TrafficLight, int trafficDemand, int numOfPedestrians) {
        if (count > 0) {
            count--;
            return;
        }
        TrafficLight.changeLight(new GreenLight());
    }

    @Override
    public int timeRemaining() {
        return count;
    }

    @Override
    public String reportState() {
        return "Pedestrian light";
    }
    public int getCount() {
        return count;
    }
}
