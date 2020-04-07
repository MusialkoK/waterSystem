package waterSystem;

import waterSystem.calculationStrategies.CalculateStrategy;
import waterSystem.observersInterfaces.LinkObservable;
import waterSystem.observersInterfaces.LinkObserver;
import waterSystem.models.ModelsLists;


public abstract class NetworkElement implements LinkObserver, LinkObservable {

    private static int numberOfElements = 0;

    protected Connections connections;
    protected String testField;
    protected int IDNumber;
    protected WaterConditions waterConditions;
    protected CalculateStrategy calculateStrategy;


    public NetworkElement create(ModelsLists model, LinkObservable... connections){
        addToNetwork(connections);
        setParameters(model);
        return this;
    }

    public NetworkElement create(ModelsLists model, int quantity, LinkObservable... connections){
        create(model, connections);
        setQuantity(quantity);
        return this;
    }

    public void setWaterConditions(double flow, double pressure) {
        this.waterConditions.setFlowAndPressure(flow, pressure);
    }

    public Connections getConnections() {
        return connections;
    }

    public WaterConditions getWaterConditions() {
        return waterConditions;
    }

    public void changeCalculateStrategy(CalculateStrategy calculateStrategy) {
        this.calculateStrategy = calculateStrategy;
    }

    protected abstract void setParameters(ModelsLists model);

    public void calculate(){
        calculateStrategy.calculate();
    };

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
            obs.update(this,waterConditions);
        }
    }

    @Override
    public void update(LinkObservable observable, WaterConditions waterConditions) {
        this.connections.getObservables().put(observable,waterConditions);
        calculate();
        sendMessage();
        sendUpdate();
    }

    public void sendMessage(){
        System.out.println("I am number " + IDNumber + " my message: " + this.testField);
    }

    public int getIDNumber(){
        return IDNumber;
    }

    protected void setQuantity(int quantity) {
    }

    private void addToReverse(LinkObservable o){
        this.connections.getReverseLinkObservers().add((LinkObserver) o);
    }

    protected void addToNetwork(LinkObservable...connections){
        this.connections = new Connections();
        waterConditions = new WaterConditions();
        IDNumber = numberOfElements++;
        setConnections(connections);

    }

    private void setConnections(LinkObservable...connections){
        if(connections.length>0){
            for (LinkObservable linkObservable : connections) {
                linkObservable.addObserver(this);
                addToReverse(linkObservable);
            }
        }
    }
}
