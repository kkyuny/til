package mediator;

public class SmartHome implements Mediator{
    public Door door = new Door(this);
    public Window window = new Window(this);
    public CoolAircon coolAircon = new CoolAircon(this);
    public HeatBoiler heatBoiler = new HeatBoiler(this);

    @Override
    public void participantChanged(Participant participant) {
        if(participant == door && !door.isbClosed()){
            coolAircon.off();
            heatBoiler.off();
        }

        if(participant == window && window.isbClosed()){
            coolAircon.off();
            heatBoiler.off();
        }

        if(participant == coolAircon && !coolAircon.isOffed()){
            heatBoiler.off();
            window.close();
            door.close();
        }

        if(participant == heatBoiler && !heatBoiler.isOffed()){
            coolAircon.off();
            window.close();
            door.close();
        }
    }

    public void report() {
        System.out.println("집안 상태");
        System.out.println(door);
        System.out.println(window);
        System.out.println(coolAircon);
        System.out.println(heatBoiler);
    }
}
