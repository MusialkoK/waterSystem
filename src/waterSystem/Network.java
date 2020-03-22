package waterSystem;


import waterSystem.observersInterfaces.DirectionObservable;
import waterSystem.observersInterfaces.DirectionObserver;

import java.util.ArrayList;
import java.util.List;

import static waterSystem.FlowDirection.*;

public class Network implements DirectionObservable {
    private List<DirectionObserver> directionObservers;
    private FlowDirection flowDirection;
    private List<NetworkElement> networkElements;

    public Network() {
        this.directionObservers = new ArrayList<>();
        this.networkElements=new ArrayList<>();
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

    public void addNetworkElement(NetworkElement networkElement){
        this.networkElements.add(networkElement);
        addObserver(networkElement.connections);
    }

    public NetworkElement getNetworkElementOf(int index){
        return this.networkElements.get(index);
    }

    public void changeFlowDirection(){
        flowDirection = DIRECT.equals(flowDirection) ? REVERSE:DIRECT;
        sendUpdate();
    }
}
