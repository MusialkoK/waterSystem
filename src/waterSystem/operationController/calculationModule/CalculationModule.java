package waterSystem.operationController.calculationModule;


import java.util.List;

public interface CalculationModule<E>  {
    void calculate(List<E> data);
    TransferObj<E> exportData();
    //Object exportSecond();
}
