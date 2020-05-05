package waterSystem;

import waterSystem.operationController.OperationController;
import waterSystem.operationController.calculationModule.CalculationModule;
import waterSystem.curve.Curve;
import waterSystem.models.ModelsLists;
import waterSystem.operationController.splittingModule.SameToAll;
import waterSystem.operationController.splittingModule.SplittingModule;

import java.util.List;


public abstract class NetworkElement implements ValueObserver{

    private static int numberOfElements = 0;

    protected OperationController flowController;
    protected OperationController directionController;
    protected int IDNumber;
    protected WaterConditions waterConditions;
    protected Curve waterCurve;
    protected ModelsLists model;
    protected double multiplier;

    public NetworkElement create(ModelsLists model, List<NetworkElement> networkElements) {
        addToNetwork(networkElements);
        setModelParameters(model);
        return this;
    }

    public NetworkElement create(ModelsLists model, int quantity, List<NetworkElement> networkElements){
        setMultiplier(quantity);
        create(model, networkElements);
        return this;
    }

    @Override
    public void transfer(Object value) {
        if(value instanceof WaterConditions){
            waterConditions=(WaterConditions) value;
        }
    }

    public void sendFlowUpdate(WaterConditions value) {
        flowController.sendUpdate(value);
    }


    public void addConnectionTo(NetworkElement existingElement) {
        flowController.addConnectionTo(existingElement.flowController);
        directionController.addConnectionTo(existingElement.directionController);
    }


    public void removeConnectionTo() {
        flowController.removeConnectionTo();
        directionController.removeConnectionTo();
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
                                            SplittingModule<WaterConditions> flowSplittingModule,
                                            CalculationModule<FlowDirection> directionModule){
        this.flowController.setCalculationModule(flowModule,flowSplittingModule);
        this.directionController.setCalculationModule(directionModule,new SameToAll<FlowDirection>());
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
        this.flowController = new OperationController<WaterConditions>(this);
        this.directionController = new OperationController<FlowDirection>(this);
        waterConditions = new WaterConditions();
        IDNumber = numberOfElements++;
        connectTo(networkElements);
    }

    private void connectTo(List<NetworkElement> networkElements){
        networkElements.forEach(this::addConnectionTo);
    }

}
