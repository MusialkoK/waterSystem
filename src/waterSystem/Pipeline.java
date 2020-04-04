package waterSystem;

import waterSystem.models.ModelsLists;
import waterSystem.models.PipelineList;

public class Pipeline extends NetworkElement {

    private PipelineList pipelineModel;




    @Override
    protected void setParameters(ModelsLists model) {
        this.pipelineModel=(PipelineList) model;
    }

    @Override
    public void calculate() {

    }
}
