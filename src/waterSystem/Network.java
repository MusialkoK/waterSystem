package waterSystem;


import waterSystem.observersInterfaces.DirectionObservable;
import waterSystem.observersInterfaces.DirectionObserver;
import waterSystem.models.PumpsList;

import java.util.ArrayList;
import java.util.List;

import static waterSystem.FlowDirection.*;

public class Network implements DirectionObservable {
    private List<DirectionObserver> directionObservers;
    private FlowDirection flowDirection;
    private List<NetworkElement> networkElements;
    private List<Pump> pumps;



    public Network() {
        this.directionObservers = new ArrayList<>();
        this.networkElements=new ArrayList<>();
        this.pumps=new ArrayList<>();
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

    public void addPump(PumpsList model){
        Pump pump=new Pump();
        pump.create(model);
        pumps.add(pump);
        pump.sendMessage();
    }
/*
    public void changePumpTo(PumpModel pumpModel){
        getPump().setPumpModel(pumpModel);
    }

    public void changePumpTo(int index,PumpModel pumpModel){
        getPump(index).setPumpModel(pumpModel);
    }
*/
    public Pump getPump(){
        return pumps.get(0);
    }

    public Pump getPump(int index){
        if(pumps.size()==1){
            return getPump();
        }else return pumps.get(index);
    }
}
