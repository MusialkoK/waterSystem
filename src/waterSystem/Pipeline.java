package waterSystem;

import waterSystem.operationController.calculationModule.HeadLoss;
import waterSystem.operationController.calculationModule.PassDirection;
import waterSystem.models.ModelsLists;
import waterSystem.models.PipelineList;
import waterSystem.operationController.calculationModule.TransferObj;
import waterSystem.operationController.splittingModule.PressureDrivenSplit;



public class Pipeline extends NetworkElement {

    private PipelineList pipelineModel;
    private double headLoss;

    public PipelineList getPipelineModel() {
        return pipelineModel;
    }

    public double getLength() {
        return multiplier;
    }

    public String getName() {
        return pipelineModel.getName();
    }

    @Override
    protected void setModelParameters(ModelsLists model) {
        this.pipelineModel = (PipelineList) model;
        setCalculationParameters(new HeadLoss<>(getPipelineModel().getInternalDiameter(), getLength()),
                new PressureDrivenSplit<>(),
                new PassDirection<>());
    }

    @Override
    public void sendStatusMessage() {
        final String STATUS_PIPELINE_FORMAT = "pipeline %s reporting:\nMy ID: %d\nMy length: %fm\nMy head loss: %fatm\nWater conditions: %s\n";
        System.out.printf(STATUS_PIPELINE_FORMAT, getName(), getIDNumber(), multiplier, -headLoss, waterConditions.view());
        System.out.println("-------------------");
    }

    @Override
    public void update(TransferObj value) {
        if(value.getMainValue() instanceof WaterConditions){
            headLoss=(double) value.getSecondaryValues().get(0);
        }
        super.update(value);
    }

    @Override
    public void sendHelloMessage() {
        final String HELLO_SPRINKLER_FORMAT ="ID:%d, %s %fm\n";
        System.out.printf(HELLO_SPRINKLER_FORMAT,getIDNumber(), getName(), multiplier);
    }
}
