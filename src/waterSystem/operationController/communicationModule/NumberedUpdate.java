package waterSystem.operationController.communicationModule;

public class NumberedUpdate<E> {

    private int updateNumber;
    private E value;

    public NumberedUpdate(int updateNumber, E value) {
        this.updateNumber = updateNumber;
        this.value = value;
    }

    public int getUpdateNumber() {
        return updateNumber;
    }

    public void setUpdateNumber(int updateNumber) {
        this.updateNumber = updateNumber;
    }

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }
}
