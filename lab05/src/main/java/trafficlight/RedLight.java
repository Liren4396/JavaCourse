package trafficlight;

public class RedLight implements Light {
    private int count;

    public RedLight() {
        count = 6;
    }
    public RedLight(int count) {
        this.count = count;
    }
    @Override
    public void change(TrafficLight TrafficLight, int trafficDemand, int numOfPedestrians) {
        if (count > 0) {
            count--;
            return;
        }
        int ret = trafficDemand > 100 ? 6 : 4;
        //int ret = trafficDemand < 10 ? 10 : 6;
        if (numOfPedestrians == 0) {
            TrafficLight.changeLight(new GreenLight(ret));
        } else {
            TrafficLight.changeLight(new PedestrianLight());
        }
        
    }
    public int getCount() {
        return count;
    }

    @Override
    public int timeRemaining() {
        return count;
    }

    @Override
    public String reportState() {
        return "Red light";
    }
}