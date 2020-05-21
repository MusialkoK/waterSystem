package waterSystem.operationController.communicationModule.transfer;

import waterSystem.FlowDirection;

public class FlowDirectionTransfer extends Transfer{
    private FlowDirection flowDirection;

    public FlowDirection getFlowDirection() {
        return flowDirection;
    }

    public void setFlowDirection(FlowDirection flowDirection) {
        this.flowDirection = flowDirection;
    }
}
