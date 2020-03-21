package waterSystem;

import waterSystem.netConnections.LinkObservable;
import waterSystem.netConnections.LinkObserver;


public abstract class NetworkElement implements LinkObserver, LinkObservable {

    protected Connections connections;
    protected String testField;

    public NetworkElement(LinkObservable...o) {
        connections=new Connections();
        for (LinkObservable linkObservable:o) {
            linkObservable.addObserver(this);
        }
    }

    public NetworkElement() {
        connections=new Connections();
        testField="im first";
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
        for (LinkObserver obs:connections.getLinkObservers()) {
            obs.update(this.testField);
        }
    }

    @Override
    public void update(String update) {
        this.testField=update;
        sendUpdate();
    }

    public String getValue(){
        return this.testField;
    }
}
