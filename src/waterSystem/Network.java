package waterSystem;


import waterSystem.models.PipelineList;
import waterSystem.models.SprinklerList;
import waterSystem.observersInterfaces.DirectionObservable;
import waterSystem.observersInterfaces.DirectionObserver;
import waterSystem.models.PumpsList;
import waterSystem.observersInterfaces.LinkObservable;

import java.util.ArrayList;
import java.util.List;

import static waterSystem.FlowDirection.*;

public class Network implements DirectionObservable {
    private List<DirectionObserver> directionObservers;
    private static FlowDirection flowDirection;
    private List<NetworkElement> networkElements;
    private List<Pump> pumps;
    private List<Pipeline> pipelines;
    private List<Sprinkler> sprinklers;


    public Network() {
        this.directionObservers = new ArrayList<>();
        this.networkElements = new ArrayList<>();
        this.pumps = new ArrayList<>();
        this.pipelines = new ArrayList<>();
        this.sprinklers = new ArrayList<>();
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
        for (DirectionObserver directionObserver : directionObservers) {
            directionObserver.update(this.flowDirection);
        }
    }

    public void addNetworkElement(NetworkElement networkElement) {
        this.networkElements.add(networkElement);
        addObserver(networkElement.connections);
    }

    public NetworkElement getNetworkElementOf(int index) {
        return this.networkElements.get(index);
    }

    public static void changeFlowDirection() {
        flowDirection = DIRECT.equals(flowDirection) ? REVERSE : DIRECT;
    }

    public void addPump(PumpsList model) {
        Pump pump = new Pump();
        pump.create(model);
        pumps.add(pump);
        networkElements.add(pump);
        pump.sendMessage();
    }

    public void changePumpTo(PumpsList model) {
        getPump().changeModelTo(model);
    }

    public void changePumpTo(int index, PumpsList model) {
        getPump(index).changeModelTo(model);
    }

    public Pump getPump() {
        return pumps.get(0);
    }

    public Pump getPump(int index) {
        if (pumps.size() == 1) {
            return getPump();
        } else return pumps.get(index);
    }

    public Sprinkler getSprinkler(int index) {
        return sprinklers.get(index);
    }

    public Pipeline getPipeline(int index){
        return pipelines.get(index);
    }

    public void startPump() {
        pumps.get(0).start();
    }

    public void addPipeline(PipelineList model, int length, LinkObservable... connections) {
        Pipeline pipeline = new Pipeline();
        pipeline.create(model, length, connections);
        pipeline.sendMessage();
        networkElements.add(pipeline);
        pipelines.add(pipeline);
    }

    public void addSprinkler(SprinklerList model, int quantity, LinkObservable... connections) {
        Sprinkler sprinkler = new Sprinkler();
        sprinkler.create(model, quantity, connections);
        sprinkler.sendMessage();
        networkElements.add(sprinkler);
        sprinklers.add(sprinkler);
    }

    public LinkObservable[] connectTo(int... index) {
        LinkObservable[] links = new LinkObservable[index.length];
        for (int i = 0; i < index.length; i++) {
            links[i] = getNetworkElementOf(index[i]);
        }
        return links;
    }
}
