package waterSystem;

import waterSystem.operationController.calculationModule.HeadLoss;
import waterSystem.operationController.calculationModule.PassDirection;
import waterSystem.models.ModelsLists;
import waterSystem.models.PipelineList;


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

    public void setHeadLoss(double headLoss) {
        this.headLoss = headLoss;
    }

    @Override
    protected void setModelParameters(ModelsLists model) {
        this.pipelineModel = (PipelineList) model;
        setCalculationParameters(new HeadLoss<>(getPipelineModel().getInternalDiameter(), getLength()),
                new PassDirection<>());
    }

    @Override
    public void sendMessage() {
        final String HELLO_PIPELINE_FORMAT = "pipeline %s reporting:\nMy ID: %d\nMy length: %fm\nMy head loss: %fatm\nWater conditions: %s\n";
        System.out.printf(HELLO_PIPELINE_FORMAT, getName(), getIDNumber(), multiplier, -headLoss, waterConditions.view());
        System.out.println("-------------------");
    }

    @Override
    protected void getFlowValues() {
        waterConditions = flowController.getCalculatedValue();
        headLoss = (double) flowController.getCalculatedValueSecond();
    }
}
