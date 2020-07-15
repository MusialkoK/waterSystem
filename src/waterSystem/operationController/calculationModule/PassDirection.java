package waterSystem.operationController.calculationModule;


import waterSystem.operationController.communicationModule.TransferBox;

import java.util.List;

public final class PassDirection implements CalculationModule {

    private TransferBox newValue;

    public void makeCalculation() {
    }

    @Override
    public void calculate(List<TransferBox> data) {
        importData(data);
        makeCalculation();
    }

    @Override
    public TransferBox getTransferBox() {
        return newValue;
    }

    private void importData(List<TransferBox> data) {
        this.newValue = data.get(0);
    }
}
