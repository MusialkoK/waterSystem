package waterSystem;

import waterSystem.observersInterfaces.LinkObservable;
import waterSystem.observersInterfaces.LinkObserver;


public abstract class NetworkElement implements LinkObserver, LinkObservable {

    private static int numberOfElements = 0;

    protected Connections connections;
    protected String testField;
    private int IDNumber;

    public NetworkElement(LinkObservable... o) {
        connections = new Connections();
        for (LinkObservable linkObservable : o) {
            linkObservable.addObserver(this);
            IDNumber = numberOfElements++;
        }
    }

    public NetworkElement() {
        connections = new Connections();
        IDNumber = numberOfElements++;

    }

    @Override
    public void addObserver(LinkObserver o) {
        connections.getLinkObservers().add(o);
    }

    @Override
    public void deleteObserver(LinkObserver o) {
        connections.getLinkObservers().remove(o);
    }

    @Override
    public void sendUpdate() {
        for (LinkObserver obs : connections.getLinkObservers()) {
            obs.update(this.testField);
        }
    }

    @Override
    public void update(String update) {
        this.testField = update;
        System.out.println("I am number " + IDNumber + " my message: " + this.testField);
        sendUpdate();
    }

    public String getValue() {
        return this.testField;
    }
}
