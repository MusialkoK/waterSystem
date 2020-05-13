package waterSystem.operationController.calculationModule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransferObj<E> {
    private E mainValue;
    private List<Object> secondaryValues= new ArrayList();

    public TransferObj(E mainValue, Object...secondaryValues) {
        this.mainValue = mainValue;
        this.secondaryValues= Arrays.asList(secondaryValues);;
    }

    public TransferObj(E mainValue) {
        this.mainValue = mainValue;
    }

    public E getMainValue() {
        return mainValue;
    }

    public List<Object> getSecondaryValues() {
        return secondaryValues;
    }
}
