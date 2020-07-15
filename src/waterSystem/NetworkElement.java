package waterSystem;

import waterSystem.operationController.OperationController;
import waterSystem.operationController.calculationModule.CalculationModule;
import waterSystem.curve.Curve;
import waterSystem.models.ModelsLists;
import waterSystem.operationController.communicationModule.TransferBox;

import waterSystem.operationController.splittingModule.SplittingModule;

import java.util.ArrayList;
import java.util.List;


public abstract class NetworkElement {

    private static int numberOfElements = 0;

    protected OperationController operationController;
    protected int IDNumber;
    protected WaterConditions waterConditions;
    protected FlowDirection flowDirection;
    protected Curve waterCurve;
    protected ModelsLists model;
    protected double multiplier;

    public void create(ModelsLists model, List<NetworkElement> networkElements) {
        addToNetwork(networkElements);
        setModelParameters(model);
    }

    public void create(ModelsLists model, int quantity, List<NetworkElement> networkElements) {
        setMultiplier(quantity);
        create(model, networkElements);
    }

    public void create(ModelsLists model){
        create(model,new ArrayList<>());
    }

    public void updateWaterCondition(TransferBox value) {
        waterConditions = value.getWaterConditions();
        sendStatusMessage();
        TransferBox transferBox = new TransferBox();
        transferBox.setWaterConditions(waterConditions);
        sendTransfer(transferBox);
    }

    public void updateFlowDirection(TransferBox value) {
        if(value.isChangeDirection()){
            flowDirection=value.getFlowDirection();
            sendStatusMessage();
        }
    }

    public void startWaterFlow() {
        TransferBox transferBox = new TransferBox();
        transferBox.setWaterConditions(waterConditions);
        sendTransfer(transferBox);
    }

    public void sendFlowDirectionTransfer() {
        TransferBox transferBox = new TransferBox();
        transferBox.setFlowDirection(flowDirection);
        operationController.sendTransfer(transferBox);
    }

    public void addConnectionTo(NetworkElement existingElement) {
        operationController.addConnectionTo(existingElement.operationController);
    }

    public void removeConnectionTo() {
        operationController.removeConnectionTo();
    }

    public Curve getWaterCurve() {
        return waterCurve;
    }

    public void setWaterConditions(double flow, double pressure) {
        this.waterConditions.setFlowAndPressure(flow, pressure);
    }

    public void setWaterConditions(WaterConditions waterConditions) {
        setWaterConditions(waterConditions.getFlow(), waterConditions.getPressure());
    }

    public WaterConditions getWaterConditions() {
        return waterConditions;
    }

    protected abstract void setModelParameters(ModelsLists model);

    protected void setCalculationParameters(CalculationModule flowModule,
                                            SplittingModule flowSplittingModule,
                                            CalculationModule directionModule) {
        this.operationController.setWaterConditionsCalculationModule(flowModule, flowSplittingModule);
        this.operationController.setFlowDirectionCalculationModule(directionModule);

    }

    public abstract void sendStatusMessage();

    public abstract void sendHelloMessage();

    public int getIDNumber() {
        return IDNumber;
    }


    protected void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    protected void addToNetwork(List<NetworkElement> networkElements) {
        this.operationController = new OperationController(this);
        waterConditions = new WaterConditions();
        IDNumber = numberOfElements++;
        connectTo(networkElements);
    }

    private void connectTo(List<NetworkElement> networkElements) {
        networkElements.forEach(this::addConnectionTo);
    }

    private void sendTransfer(TransferBox transferBox){
        operationController.sendTransfer(transferBox);
    }
}
