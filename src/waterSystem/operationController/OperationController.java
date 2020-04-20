package waterSystem.operationController;

import waterSystem.NetworkElement;
import waterSystem.operationController.calculationModule.Calculation;
import waterSystem.operationController.calculationModule.CalculationModule;
import waterSystem.operationController.communicationModule.Communication;
import waterSystem.operationController.communicationModule.CommunicationModule;
import waterSystem.operationController.communicationModule.NumberedUpdate;

import java.util.List;
import java.util.stream.Collectors;

public class OperationController<E> {

    private Communication<E> communicationModule = new CommunicationModule<>();
    private Calculation<E> calculationModule;

    public Communication<E> getCommunicationModule() {
        return communicationModule;
    }

    public void setCalculationModule(Object obj) {
        this.calculationModule= (CalculationModule<E>) obj;
    }

    public void sendUpdate(NumberedUpdate<E> upd){
        communicationModule.sendUpdate(upd);
    }

    public void addConnectionTo(List<OperationController<E>> list){
        List<Communication<E>> communicationList = list.stream()
                .map(x->x.communicationModule).collect(Collectors.toList());
        communicationModule.addConnectionTo(communicationList);
    }
}
