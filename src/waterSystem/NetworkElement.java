package waterSystem;

import waterSystem.operationController.OperationController;
import waterSystem.operationController.calculationModule.CalculationModule;
import waterSystem.curve.Curve;
import waterSystem.models.ModelsLists;
import waterSystem.operationController.calculationModule.TransferObj;
import waterSystem.operationController.splittingModule.SameToAll;
import waterSystem.operationController.splittingModule.SplittingModule;

import java.util.List;


public abstract class NetworkElement implements ValueObserver<TransferObj>{

    private static int numberOfElements = 0;

    protected OperationController<WaterConditions> flowController;
    protected OperationController<FlowDirection> directionController;
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
    public void transfer(TransferObj value) {
        if(value.getMainValue() instanceof WaterConditions){
            waterConditions= (WaterConditions) value.getMainValue();
        }
        sendStatusMessage();
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

    public abstract void sendStatusMessage();

    public abstract void sendHelloMessage();

    public int getIDNumber(){
        return IDNumber;
    }


    protected void setMultiplier(double multiplier) {
        this.multiplier=multiplier;
    }

    protected void addToNetwork(List<NetworkElement> networkElements){
        this.flowController = new OperationController<>(this);
        this.directionController = new OperationController<>(this);
        waterConditions = new WaterConditions();
        IDNumber = numberOfElements++;
        connectTo(networkElements);
    }

    private void connectTo(List<NetworkElement> networkElements){
        networkElements.forEach(this::addConnectionTo);
    }

}
