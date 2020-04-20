package waterSystem.operationController.calculationModule;

import waterSystem.FlowDirection;

import java.util.List;

public class PassDirection<E extends FlowDirection> implements CalculationModule<E>{

    private E newValue;

    @Override
    public void importData(List<E> data) {
        this.newValue=data.get(0);
    }

    @Override
    public void makeCalculation() {

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
