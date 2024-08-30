METRICS
ListSelectionListener changeListener = new ListSelectionListener() {
    @Override
    public void valueChanged(ListSelectionEvent event) {
        String statistic = statisticsList.getSelectedValue();
        // TODO Change Strategy based on what button is pressed
        System.out.println(statistic);

        DataDisplayStrategy strategy = new AllPointsStrategy();

        if (statistic.equals("All Points")) {
            strategy = new AllPointsStrategy();
        } else if (statistic.equals("Average")) {
            strategy = new AverageStrategy();
        } else if (statistic.equals("Max")) {
            strategy = new MaxStrategy();
        } else if (statistic.equals("Sum")) {
            strategy = new SumStrategy();
        }
        plotPanel.setDataDisplayStrategy(strategy);
    
        // Repaint the panel to apply the new strategy
        // plotPanel.repaint();
    }
};

Throughout the source code, there are a few other examples of the observer pattern being used.
Users interact with the interface primarily by selecting statistics options on the control panel. The user's selections trigger events, which are handled by the event listener ListSelectionListener.

Although the code does not explicitly implement the observer interface in Java, it uses an event listening mechanism to implement the core idea of the observer pattern.

TRAFFICLIGHT
What are the different states?
green light, red light, red light, pedestrian light

What are the actions/transitions between each state?

                        Red Light                                       Green Light                       Yellow Light          Pedestrian Light  

next light              person == 0 ? Green Light : Pedestrian Light    Yellow Light                      Red Light             Green Light

default ticks           6                                               4                                 1                     2

ticks after change      traffic demand < 10 ? 10 : 6                    traffic demand > 100 ? 6 : 4      1                     2



How does the initial code break the open/closed principle, and how does the state pattern fix it?
What is the difference between the Strategy Pattern and the state pattern? What makes this an example of the State Pattern?

The initial code provided breaks the open/closed principle because it doesn't provide an easy 
way to extend or modify the behavior of the  class without directly modifying the class itself.
it just like a process oriented thinking. but use obj oriented thinking is more readable and safe.
And layer-by-layer encapsulation can make future updates more convenient. 
We don’t have to think about how the bottom layer is implemented. We just need to keep building up like building a ladder.
The strategy mode is used to switch between different algorithms, and the state mode is used to manage different states of objects. 
The code example is the state pattern because it manages the different states of a traffic light, such as "red light", "green light", or "yellow light", and allows the behavior of these states to be easily extended or modified.

CoreCheckIn
How do you think you are going in the course?
What has been the most difficult/challenging part of the course so far?
Look back at the goals you set in Week 1. How are they tracking?
What is one thing you want to improve for the second half of the term?

object-oriented thinking
I think the various design patterns so far are worth learning (I have only been exposed to the singleton pattern before)
Compared to the first week, I think I have learned some Java syntax, and now I can use Java to write some simple things.
The rest is more about continuous learning and writing code to make progress.