package waterSystem.operationController.calculationModule;


import java.util.List;

public interface CalculationModule<E>  {
    void calculate(List<E> data);
    E exportData();
    Object exportSecond();
}
