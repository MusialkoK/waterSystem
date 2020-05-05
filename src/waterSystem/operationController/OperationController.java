package waterSystem.operationController;

import waterSystem.NetworkElement;
import waterSystem.ValueObservable;
import waterSystem.ValueObserver;
import waterSystem.WaterConditions;
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
    public void transfer(List<E> value) {
        calculationModule.calculate(value);
        sendTransfer();
        sendUpdate(calculationModule.exportData());
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

    public void setCalculationModule(CalculationModule<E> calculationModule,
                                     SplittingModule<E> splittingModule) {
        this.calculationModule = calculationModule;
        this.communicationModule.setSplittingModule(splittingModule);
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
