package waterSystem.operationController.calculationModule;

import waterSystem.FlowDirection;

import java.util.List;

public final class PassDirection<E extends FlowDirection> implements CalculationModule<E>{

    private E newValue;

    public void importData(List<E> data) {
        this.newValue=data.get(0);
    }

    public void makeCalculation() {
    }

    @Override
    public void calculate(List<E> data) {
        importData(data);
        makeCalculation();
    }

    @Override
    public E exportData() {
        return newValue;
    }

    @Override
    public Object exportSecond() {
        return null;
    }
}
