package waterSystem;

import waterSystem.operationController.OperationController;
import waterSystem.operationController.calculationModule.CalculationModule;
import waterSystem.curve.Curve;
import waterSystem.models.ModelsLists;
import waterSystem.operationController.communicationModule.Linked;
import waterSystem.operationController.communicationModule.NumberedUpdate;

import java.util.List;


public abstract class NetworkElement implements Linked, Updates<WaterConditions>, ValueRetrieval<WaterConditions> {

    private static int numberOfElements = 0;

    protected OperationController flowController;
    protected OperationController<FlowDirection> directionController;
    protected int IDNumber;
    protected WaterConditions waterConditions;
    protected CalculationModule calculationModule;
    protected Curve waterCurve;
    protected ModelsLists model;
    protected double multiplier;

    public NetworkElement create(ModelsLists model, List<NetworkElement> networkElements) {
        addToNetwork(networkElements);
        setModelParameters(model);
        return this;
    }

    public NetworkElement create(ModelsLists model, int quantity, List<NetworkElement> networkElements){
        create(model, networkElements);
        setMultiplier(quantity);
        return this;
    }

    @Override
    public void update(NetworkElement sender, NumberedUpdate<WaterConditions> upd) {
        flowController.update(sender,upd);
        getFlowValues();
        sendMessage();
        sendFlowUpdate(waterConditions);
    }

    @Override
    public void sendUpdate(NetworkElement sender, WaterConditions value) {
        flowController.sendUpdate(sender, value);
    }

    @Override
    public void addConnectionTo(NetworkElement newElement, NetworkElement existingElement) {
        flowController.addConnectionTo(this, existingElement);
        directionController.addConnectionTo(this, existingElement);
    }

    @Override
    public void removeConnectionTo(NetworkElement newElement, NetworkElement existingElement) {
        flowController.removeConnectionTo(this, existingElement);
        directionController.removeConnectionTo(this, existingElement);
    }

    public Curve getWaterCurve() {
        return waterCurve;
    }

    public void setWaterConditions(double flow, double pressure) {
        this.waterConditions.setFlowAndPressure(flow, pressure);
    }

    public void setWaterConditions(WaterConditions waterConditions){
        setWaterConditions(waterConditions.getFlow(),waterConditions.getPressure());
    }

    public WaterConditions getWaterConditions() {
        return waterConditions;
    }

    protected abstract void setModelParameters(ModelsLists model);

    protected void setCalculationParameters(CalculationModule<WaterConditions> flowModule,
                                            CalculationModule<FlowDirection> directionModule){
        this.flowController.setCalculationModule(flowModule);
        this.directionController.setCalculationModule(directionModule);
    }

    public void sendMessage(){
    }

    public int getIDNumber(){
        return IDNumber;
    }


    protected void setMultiplier(double multiplier) {
        this.multiplier=multiplier;
    }

    protected void addToNetwork(List<NetworkElement> networkElements){
        this.flowController = new OperationController<WaterConditions>();
        this.directionController = new OperationController<FlowDirection>();
        waterConditions = new WaterConditions();
        IDNumber = numberOfElements++;
        connectTo(networkElements);
    }

    public void flowUpdate(NetworkElement sender, NumberedUpdate<WaterConditions> upd){
        update(sender,upd);
    }

    public void sendFlowUpdate(WaterConditions upd) {
        sendUpdate(this, upd);
    }

    protected void getFlowValues(){
        waterConditions=(WaterConditions) flowController.getCalculatedValue();
    };



    public void connectFrom(NetworkElement networkElement){
        flowController.connectFrom(networkElement);
        //directionController.addConnectionAfterFrom(networkElement);
    }

    private void connectTo(List<NetworkElement> networkElements){
        networkElements.forEach(existingElement -> addConnectionTo(this, existingElement));
    }

}
