package waterSystem.operationController.calculationModule;

import java.util.List;

public interface Calculation<E> {

    E calculate(List<E> data);

}
