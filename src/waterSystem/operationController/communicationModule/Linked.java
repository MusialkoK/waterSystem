package waterSystem.operationController.communicationModule;

import waterSystem.NetworkElement;

public interface Linked {
    void addConnectionTo(NetworkElement newElement, NetworkElement existingElement);
    void removeConnectionTo(NetworkElement newElement, NetworkElement existingElement);
}

