package waterSystem.operationController.communicationModule;

import waterSystem.FlowDirection;
import waterSystem.WaterConditions;

public class TransferBox {
    private int id;  //use to check if current transfer is finished within one NetworkElement
    protected FlowDirection flowDirection;
    protected WaterConditions waterConditions;
    protected boolean changeDirection;
    protected double headLoss;

    public TransferBox() {
    }

    public TransferBox(double flow, double pressure){
        setWaterConditions(new WaterConditions(flow,pressure));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FlowDirection getFlowDirection() {
        return flowDirection;
    }

    public void setFlowDirection(FlowDirection flowDirection) {
        this.flowDirection = flowDirection;
    }

    public WaterConditions getWaterConditions() {
        return waterConditions;
    }

    public void setWaterConditions(WaterConditions waterConditions) {
        this.waterConditions = waterConditions;
    }

    public boolean isChangeDirection() {
        return changeDirection;
    }

    public void setChangeDirection(boolean changeDirection) {
        this.changeDirection = changeDirection;
    }

    public double getHeadLoss() {
        return headLoss;
    }

    public void setHeadLoss(double headLoss) {
        this.headLoss = headLoss;
    }
}
