package waterSystem.operationController;

import waterSystem.Updates;
import waterSystem.NetworkElement;
import waterSystem.operationController.calculationModule.CalculationModule;
import waterSystem.operationController.communicationModule.Linked;
import waterSystem.operationController.communicationModule.CommunicationModule;
import waterSystem.operationController.communicationModule.NumberedUpdate;

public class OperationController<E> implements Updates<E>, Linked {

    private CommunicationModule<E> communicationModule = new CommunicationModule<>();
    private CalculationModule<E> calculationModule;

    @Override
    public void sendUpdate(NetworkElement sender, E upd) {
        communicationModule.sendUpdate(sender, upd);
    }

    @Override
    public void update(NetworkElement sender, NumberedUpdate<E> upd) {
        communicationModule.update(sender, upd);
        calculationModule.calculate(communicationModule.getBeforeValuesByDirection());
    }

    @Override
    public void addConnectionTo(NetworkElement newElement, NetworkElement networkElement) {
        communicationModule.addConnectionTo(newElement, networkElement);
    }

    @Override
    public void removeConnectionTo(NetworkElement newElement, NetworkElement existingElement) {

    }
/*
    @Override
    public List<E> getBeforeValuesByDirection() {
        return null;
    }

    @Override
    public Object getSecondValue() {
        return null;
    }
*/
    public void setCalculationModule(Object obj) {
        this.calculationModule = (CalculationModule<E>) obj;
    }

    public E getCalculatedValue() {
        return calculationModule.exportData();
    }

    public Object getCalculatedValueSecond() {
        return calculationModule.exportSecond();
    }

    public void connectFrom(NetworkElement networkElement) {
    }
}
