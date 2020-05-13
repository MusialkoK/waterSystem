package waterSystem;


import waterSystem.models.PipelineList;
import waterSystem.models.SprinklerList;
import waterSystem.models.PumpsList;

import java.util.ArrayList;
import java.util.List;

public class Network{
    private final List<NetworkElement> networkElements;
    private final List<Pump> pumps;
    private final List<Pipeline> pipelines;
    private final List<Sprinkler> sprinklers;


    public Network() {
        this.networkElements = new ArrayList<>();
        this.pumps = new ArrayList<>();
        this.pipelines = new ArrayList<>();
        this.sprinklers = new ArrayList<>();
    }

    public NetworkElement getNetworkElementOf(int index) {
        return this.networkElements.get(index);
    }

    public void addPump(PumpsList model) {
        NetworkElement pump = new Pump();
        pump.create(model,new ArrayList<>());
        pumps.add((Pump) pump);
        networkElements.add(pump);
        pump.sendHelloMessage();
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

    public void addPipeline(PipelineList model, int length, List<NetworkElement> connections) {
        NetworkElement pipeline = new Pipeline();
        pipeline.create(model, length, connections);
        pipeline.sendHelloMessage();
        networkElements.add(pipeline);
        pipelines.add((Pipeline) pipeline);
    }

    public void addSprinkler(SprinklerList model, int quantity, List<NetworkElement> connections) {
        NetworkElement sprinkler = new Sprinkler();
        sprinkler.create(model, quantity, connections);
        sprinkler.sendHelloMessage();
        networkElements.add(sprinkler);
        sprinklers.add((Sprinkler) sprinkler);
    }

    public List<NetworkElement> connectTo(int... index) {
        List<NetworkElement> result = new ArrayList<>();
        for (int i:index) {
            result.add(getNetworkElementOf(i));
        }
        return result;
    }
}
