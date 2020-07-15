package waterSystem.operationController.calculationModule;


import waterSystem.operationController.communicationModule.TransferBox;

import java.util.List;

public interface CalculationModule  {
    void calculate(List<TransferBox> data);
    TransferBox getTransferBox();
}
