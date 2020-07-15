package waterSystem.operationController.splittingModule;

import waterSystem.operationController.communicationModule.CommunicationModule;
import waterSystem.operationController.communicationModule.TransferBox;

import java.util.HashMap;
import java.util.Map;

public class SameToAll implements SplittingModule {


    private Map<CommunicationModule, TransferBox> connectionList;

    @Override
    public Map<CommunicationModule, TransferBox> split(TransferBox transfer, Map<CommunicationModule, TransferBox> connectionList) {
        this.connectionList = connectionList;
        Map<CommunicationModule, TransferBox> result = new HashMap<>();
        connectionList.forEach((k, v) -> result.put(k, transfer));
        return result;
    }
}
