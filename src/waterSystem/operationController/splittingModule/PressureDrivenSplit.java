package waterSystem.operationController.splittingModule;

import waterSystem.WaterConditions;
import waterSystem.operationController.communicationModule.CommunicationModule;
import waterSystem.operationController.communicationModule.TransferBox;




import java.util.HashMap;
import java.util.Map;

public class PressureDrivenSplit implements SplittingModule {

    private Map<CommunicationModule, TransferBox> connectionList = new HashMap<>();

    @Override
    public Map<CommunicationModule, TransferBox> split(TransferBox transfer,Map<CommunicationModule, TransferBox> connectionList) {
        this.connectionList=connectionList;
        return connectionList.containsValue(null) ? splitEqually(transfer)
                : splitByPressure(transfer);
    }

    private Map<CommunicationModule, TransferBox> splitEqually(TransferBox transfer) {
        Map<CommunicationModule, TransferBox> result = new HashMap<>();
        transfer.getWaterConditions()
                .setFlow(transfer.getWaterConditions().getFlow() / connectionList.size());
        connectionList.forEach((k, v) -> result.put(k, transfer));
        return result;
    }

    private Map<CommunicationModule, TransferBox> splitByPressure(TransferBox transfer) {
        Map<CommunicationModule, WaterConditions> waterConditionsMap = new HashMap<>();
        Map<CommunicationModule, TransferBox> result = new HashMap<>();
        connectionList.forEach((k, v) -> waterConditionsMap.put(k, v.getWaterConditions()));

        double pressureSum = waterConditionsMap.values().stream()
                .map(WaterConditions::getPressure)
                .mapToDouble(Double::doubleValue)
                .sum();

        waterConditionsMap.forEach((k, v) ->
            result.put(k, new TransferBox(v.getFlow() * v.getPressure() / pressureSum, v.getPressure())));
        return result;
    }
}
