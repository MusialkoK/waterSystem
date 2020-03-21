package waterSystem;

import waterSystem.netConnections.DirectionObserver;
import waterSystem.netConnections.LinkObservable;
import waterSystem.netConnections.LinkObserver;

import java.util.ArrayList;
import java.util.List;

import static waterSystem.FlowDirection.*;

public class Connections implements DirectionObserver {

    private FlowDirection flowDirection;
    private List<LinkObserver> directLinkObservers;
    private List<LinkObserver> reverseLinkObservers;

    public Connections() {
        this.directLinkObservers = new ArrayList<>();
        this.reverseLinkObservers = new ArrayList<>();
        this.flowDirection=DIRECT;
    }

    @Override
    public void update(FlowDirection flowDirection) {
        this.flowDirection = flowDirection;
    }

    public List<LinkObserver> getLinkObservers() {
        return (DIRECT.equals(this.flowDirection)) ? this.directLinkObservers : this.reverseLinkObservers;
    }
}
