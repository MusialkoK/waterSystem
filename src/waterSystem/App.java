package waterSystem;



import static waterSystem.models.PumpsList.*;


public class App {
    public static void main(String[] args) {
        Network network=new Network();
        network.addPump(SHE40_200_55);
        network.getPump().changeModelTo(SHE40_200_75);
        //network.addNetworkElement(new NetworkElementObject(network.getNetworkElementOf(0)));
        //network.addNetworkElement(new NetworkElementObject(network.getNetworkElementOf(1)));
        //network.addNetworkElement(new NetworkElementObject(network.getNetworkElementOf(1)));
/*
        network.getNetworkElementOf(0).update("how how how");
        network.changeFlowDirection();
        network.getNetworkElementOf(2).update("hey hey hey");
       // network.changePumpTo(new SHE40_200_55());
        network.changeFlowDirection();
        network.getNetworkElementOf(0).update("go go go");*/
    }
}
