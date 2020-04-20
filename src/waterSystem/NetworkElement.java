package waterSystem;

import waterSystem.operationController.OperationController;
import waterSystem.operationController.calculationModule.CalculationModule;
import waterSystem.operationController.communicationModule.CommunicationModule;
import waterSystem.curve.Curve;
import waterSystem.models.ModelsLists;

import java.util.List;
import java.util.stream.Collectors;


public abstract class NetworkElement {

    private static int numberOfElements = 0;

    protected OperationController<WaterConditions> flowController;
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

    public OperationController<WaterConditions> getFlowController() {
        return flowController;
    }

    public OperationController<FlowDirection> getDirectionController() {
        return directionController;
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
        this.flowController = new OperationController<>();
        this.directionController = new OperationController<>();
        waterConditions = new WaterConditions();
        IDNumber = numberOfElements++;
        connectTo(networkElements);
    }

    private void connectTo(List<NetworkElement> networkElements){
        List<OperationController<WaterConditions>> flowList = networkElements.stream()
                .map(x->x.flowController).collect(Collectors.toList());
        flowController.addConnectionTo(flowList);

        List<OperationController<FlowDirection>> directionList = networkElements.stream()
                .map(x->x.directionController).collect(Collectors.toList());
        directionController.addConnectionTo(directionList);
    }

}
