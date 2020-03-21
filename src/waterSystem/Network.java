package waterSystem;


import waterSystem.netConnections.DirectionObservable;
import waterSystem.netConnections.DirectionObserver;

import java.util.ArrayList;
import java.util.List;

import static waterSystem.FlowDirection.DIRECT;

public class Network implements DirectionObservable {
    private List<DirectionObserver> directionObservers;
    private FlowDirection flowDirection;

    public Network() {
        this.directionObservers = new ArrayList<>();
        this.flowDirection = DIRECT;
    }

    @Override
    public void addObserver(DirectionObserver o) {
        this.directionObservers.add(o);
    }

    @Override
    public void deleteObserver(DirectionObserver o) {
        this.directionObservers.remove(o);
    }

    @Override
    public void sendUpdate() {
        for (DirectionObserver directionObserver:directionObservers) {
            directionObserver.update(this.flowDirection);
        }
    }
}
