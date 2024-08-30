package trafficlight;

public interface Light {
    void change(TrafficLight light, int trafficDemand, int numOfPedestrians);
    int timeRemaining();
    String reportState();
    int getCount();
}
