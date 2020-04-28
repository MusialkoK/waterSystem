package waterSystem.operationController;

import waterSystem.NetworkElement;
import waterSystem.ValueObservable;
import waterSystem.ValueObserver;
import waterSystem.operationController.calculationModule.CalculationModule;
import waterSystem.operationController.collectingModule.CollectingModule;
import waterSystem.operationController.communicationModule.CommunicationModule;

import java.util.List;

public class OperationController<E> implements ValueObserver<List<E>>, ValueObservable<E> {

    private final CommunicationModule<E> communicationModule = new CommunicationModule<>(this);
    private CalculationModule<E> calculationModule;
    private CollectingModule<E> collectingModule;
    private final NetworkElement owner;

    public OperationController(NetworkElement owner) {
        this.owner = owner;
    }

    @Override
    public void transfer(List<E> value) {
        calculationModule.calculate(value);
        sendTransfer();
    }

    @Override
    public void sendTransfer() {
        owner.transfer(calculationModule.exportData());
        owner.transfer(calculationModule.exportSecond());

    }

    public void addConnectionTo(OperationController<E> existingElement) {
        communicationModule.addConnectionTo(existingElement.communicationModule);
    }

    public void removeConnectionTo() {}

    public void setCalculationModule(Object obj) {
        this.calculationModule = (CalculationModule<E>) obj;
    }

    public E getCalculatedValue() {
        return calculationModule.exportData();
    }

    public Object getCalculatedValueSecond() {
        return calculationModule.exportSecond();
    }

    public void sendUpdate(E upd) {
        communicationModule.sendUpdate(upd);
    }
}
