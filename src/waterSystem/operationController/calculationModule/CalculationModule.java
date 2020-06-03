package waterSystem.operationController.calculationModule;


import waterSystem.operationController.communicationModule.Transfer;

import java.util.List;

public interface CalculationModule  {
    void calculate(List<Transfer> data);
    Transfer exportData();
}
