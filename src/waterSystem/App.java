package waterSystem;

public class App {
    public static void main(String[] args) {
        Network network=new Network();

        network.addNetworkElement(new NetworkElementObject());
        network.addNetworkElement(new NetworkElementObject(network.getNetworkElementOf(0)));
        network.addNetworkElement(new NetworkElementObject(network.getNetworkElementOf(1)));
        network.addNetworkElement(new NetworkElementObject(network.getNetworkElementOf(0)));

        network.getNetworkElementOf(0).update("how how how");
        network.changeFlowDirection();
        network.getNetworkElementOf(3).update("hey hey hey");
    }
}
