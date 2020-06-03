package waterSystem.operationController.splittingModule;

import waterSystem.operationController.communicationModule.CommunicationModule;

import waterSystem.operationController.communicationModule.Transfer;

import java.util.Map;

public interface SplittingModule {
    void setConnectionList(Map<CommunicationModule, Transfer> connectionList);

    Map<CommunicationModule, Transfer> split(Transfer transfer);
}
