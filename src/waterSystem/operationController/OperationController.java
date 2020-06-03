package waterSystem.operationController;

import waterSystem.NetworkElement;
import waterSystem.operationController.calculationModule.CalculationModule;
import waterSystem.operationController.communicationModule.CommunicationModule;
import waterSystem.operationController.communicationModule.Transfer;
import waterSystem.operationController.splittingModule.SameToAll;
import waterSystem.operationController.splittingModule.SplittingModule;

import java.util.List;

public class OperationController {

    private final CommunicationModule communicationModule = new CommunicationModule(this);
    private CalculationModule waterConditionsCalculationModule;
    private CalculationModule flowDirectionCalculationModule;
    private final NetworkElement owner;

    public OperationController(NetworkElement owner) {
        this.owner = owner;
    }


    public void update(List<Transfer> update) {
        waterConditionsCalculationModule.calculate(update);
        owner.updateWaterCondition(waterConditionsCalculationModule.exportData());

        flowDirectionCalculationModule.calculate(update);
        owner.updateFlowDirection(flowDirectionCalculationModule.exportData());

    }

    public void addConnectionTo(OperationController existingElement) {
        communicationModule.addConnectionTo(existingElement.communicationModule);
    }

    public void removeConnectionTo() {
    }

    public void setWaterConditionsCalculationModule(CalculationModule calculationModule,
                                                    SplittingModule splittingModule) {
        this.waterConditionsCalculationModule = calculationModule;
        this.communicationModule.setSplittingModule(splittingModule);
    }

    public void setFlowDirectionCalculationModule(CalculationModule calculationModule) {
        this.flowDirectionCalculationModule = calculationModule;
        this.communicationModule.setSplittingModule(new SameToAll());
    }

    public void sendTransfer(Transfer transfer) {
        communicationModule.sendTransfer(transfer);
    }
}
