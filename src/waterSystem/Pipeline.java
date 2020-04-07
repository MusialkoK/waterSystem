package waterSystem;

import waterSystem.calculationStrategies.HeadLoss;
import waterSystem.models.ModelsLists;
import waterSystem.models.PipelineList;
import waterSystem.observersInterfaces.LinkObservable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Pipeline extends NetworkElement {

    private PipelineList pipelineModel;
    private int length;

    public PipelineList getPipelineModel() {
        return pipelineModel;
    }

    public int getLength() {
        return length;
    }

    public String getName() {
        return pipelineModel.getName();
    }

    @Override
    protected void setParameters(ModelsLists model) {
        this.pipelineModel = (PipelineList) model;
        this.calculateStrategy=new HeadLoss(this);
    }

    @Override
    public void calculate() {
        calculateStrategy.calculate();
    }

    @Override
    public void sendMessage() {
        final String HELLO_PIPELINE_FORMAT = "pipeline %s reporting:\nMy ID: %d\nMy length: %dm\nWater conditions: %s\n";
        System.out.printf(HELLO_PIPELINE_FORMAT, getName(), getIDNumber(), length, waterConditions.view());
        System.out.println("-------------------");
    }

    @Override
    protected void setQuantity(int quantity) {
        this.length = quantity;
    }

}
