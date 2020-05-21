package waterSystem.operationController;

import waterSystem.NetworkElement;
import waterSystem.ValueObservable;
import waterSystem.ValueObserver;
import waterSystem.operationController.calculationModule.CalculationModule;
import waterSystem.operationController.communicationModule.CommunicationModule;
import waterSystem.operationController.splittingModule.SplittingModule;

import java.util.List;

public class OperationController<E> implements ValueObserver<List<E>>, ValueObservable<E> {

    private final CommunicationModule<E> communicationModule = new CommunicationModule<>(this);
    private CalculationModule<E> calculationModule;
    private final NetworkElement owner;

    public OperationController(NetworkElement owner) {
        this.owner = owner;
    }

    @Override
    public void update(List<E> value) {
        calculationModule.calculate(value);
        sendUpdate();
        sendUpdate(calculationModule.exportData().getMainValue());
    }

    @Override
    public void sendUpdate() {
        owner.update(calculationModule.exportData());
    }

    public void addConnectionTo(OperationController<E> existingElement) {
        communicationModule.addConnectionTo(existingElement.communicationModule);
    }

    public void removeConnectionTo() {}

    public void setCalculationModule(CalculationModule<E> calculationModule,
                                     SplittingModule<E> splittingModule) {
        this.calculationModule = calculationModule;
        this.communicationModule.setSplittingModule(splittingModule);
    }

    public void sendUpdate(E upd) {
        communicationModule.sendTransfer(upd);
    }
}
