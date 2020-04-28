package waterSystem.operationController.communicationModule;

public interface Linked<E> {
    void addConnectionTo(CommunicationModule<E> existingElement);
    void removeConnectionTo(CommunicationModule<E> newElement, CommunicationModule<E> existingElement);
}

