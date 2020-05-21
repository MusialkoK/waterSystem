package waterSystem.operationController.communicationModule.transfer;

import waterSystem.WaterConditions;

public class WaterConditionTransfer extends Transfer {
    private WaterConditions waterConditions;
    private boolean changeDirection;

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
}
