package waterSystem.operationController.splittingModule;

import waterSystem.operationController.communicationModule.CommunicationModule;

import waterSystem.operationController.communicationModule.TransferBox;

import java.util.Map;

public interface SplittingModule {
    Map<CommunicationModule, TransferBox> split(TransferBox transfer, Map<CommunicationModule, TransferBox> transferTo);
}
