package trafficlight;

public class GreenLight implements Light {
    private int count;

    public GreenLight() {
        count = 4;
    }
    public GreenLight(int count) {
        this.count = count;
    }
    @Override
    public void change(TrafficLight TrafficLight, int trafficDemand, int numOfPedestrians) {
        if (count > 0) {
            count--;
            return;
        }
        int ret = trafficDemand > 100 ? 6 : 4;
        TrafficLight.changeLight(new YellowLight(ret));
    }

    @Override
    public int timeRemaining() {
        return count;
    }

    @Override
    public String reportState() {
        return "Green light";
    }
    public int getCount() {
        return count;
    }
}