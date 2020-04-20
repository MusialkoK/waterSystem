package waterSystem.operationController.calculationModule;


import java.util.List;

public interface CalculationModule<E> extends Calculation<E> {

    void importData(List<E> data);
    void makeCalculation();
    E exportData();
    Object exportSecond();

    @Override
    default E calculate(List<E> data){
        importData(data);
        makeCalculation();
        return exportData();
    }
}
