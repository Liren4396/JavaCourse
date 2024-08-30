package trafficlight;

public class YellowLight implements Light {
    private int count;

    public YellowLight() {
        count = 1;
    }
    public YellowLight(int count) {
        this.count = count;
    }

    @Override
    public void change(TrafficLight TrafficLight, int trafficDemand, int numOfPedestrians) {
        if (count > 0) {
            count--;
            return;
        }
        int ret = trafficDemand < 10 ? 10 : 6;
        TrafficLight.changeLight(new RedLight(ret));
    }

    @Override
    public int timeRemaining() {
        return count;
    }

    @Override
    public String reportState() {
        return "Yellow light";
    }
    public int getCount() {
        return count;
    }
}
