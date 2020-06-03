package waterSystem;

import waterSystem.operationController.OperationController;
import waterSystem.operationController.calculationModule.CalculationModule;
import waterSystem.curve.Curve;
import waterSystem.models.ModelsLists;
import waterSystem.operationController.communicationModule.Transfer;

import waterSystem.operationController.splittingModule.SplittingModule;

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


    public void updateWaterCondition(Transfer value) {
        waterConditions = value.getWaterConditions();
        sendStatusMessage();
    }

    public void updateFlowDirection(Transfer value) {
        flowDirection = value.getFlowDirection();
        sendStatusMessage();
    }

    public void sendWaterConditionTransfer() {
        Transfer transfer = new Transfer();
        transfer.setWaterConditions(waterConditions);
        operationController.sendTransfer(transfer);
    }

    public void sendWaterConditionTransfer(WaterConditions waterConditions) {
        Transfer transfer = new Transfer();
        transfer.setWaterConditions(waterConditions);
        operationController.sendTransfer(transfer);
    }

    public void sendFlowDirectionTransfer() {
        Transfer transfer = new Transfer();
        transfer.setFlowDirection(flowDirection);
        operationController.sendTransfer(transfer);
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

}
