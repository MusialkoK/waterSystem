package waterSystem.operationController.splittingModule;

import waterSystem.WaterConditions;
import waterSystem.operationController.communicationModule.CommunicationModule;
import waterSystem.operationController.communicationModule.Transfer;


import java.util.HashMap;
import java.util.Map;

public class PressureDrivenSplit implements SplittingModule {

    private Map<CommunicationModule, Transfer> connectionList = new HashMap<>();

    @Override
    public void setConnectionList(Map<CommunicationModule, Transfer> connectionList) {
        this.connectionList=connectionList;
    }

    @Override
    public Map<CommunicationModule, Transfer> split(Transfer transfer) {
        return connectionList.containsValue(null) ? splitEqually(transfer)
                : splitByPressure(transfer);
    }

    private Map<CommunicationModule, Transfer> splitEqually(Transfer transfer) {
        Map<CommunicationModule, Transfer> result = new HashMap<>();
        transfer.getWaterConditions()
                .setFlow(transfer.getWaterConditions().getFlow() / connectionList.size());
        connectionList.forEach((k, v) -> result.put(k, transfer));
        return result;
    }

    private Map<CommunicationModule, Transfer> splitByPressure(Transfer transfer) {
        Map<CommunicationModule, WaterConditions> waterConditionsMap = new HashMap<>();
        Map<CommunicationModule, Transfer> result = new HashMap<>();
        connectionList.forEach((k, v) -> waterConditionsMap.put(k, v.getWaterConditions()));

        double pressureSum = waterConditionsMap.values().stream()
                .map(WaterConditions::getPressure)
                .mapToDouble(Double::doubleValue)
                .sum();

        waterConditionsMap.forEach((k, v) ->
            result.put(k, new Transfer(v.getFlow() * v.getPressure() / pressureSum, v.getPressure())));
        return result;
    }
}
