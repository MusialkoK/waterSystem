package waterSystem.operationController.calculationModule;


import waterSystem.operationController.communicationModule.Transfer;

import java.util.List;

public final class PassDirection implements CalculationModule {

    private Transfer newValue;

    public void makeCalculation() {
    }

    @Override
    public void calculate(List<Transfer> data) {
        importData(data);
        makeCalculation();
    }

    @Override
    public Transfer exportData() {
        return newValue;
    }

    private void importData(List<Transfer> data) {
        this.newValue = data.get(0);
    }
}
