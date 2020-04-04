package waterSystem;

import waterSystem.observersInterfaces.LinkObservable;
import waterSystem.models.ModelsLists;

public class NetworkElementObject extends NetworkElement {

    public NetworkElementObject(LinkObservable... o) {
        addToNetwork(o);

    }

    public NetworkElementObject() {
    }

    @Override
    protected void setParameters(ModelsLists model) {

    }

    @Override
    public void calculate() {

    }
}
