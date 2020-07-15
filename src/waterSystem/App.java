package waterSystem;



import static waterSystem.models.PipelineList.*;
import static waterSystem.models.PumpsList.*;
import static waterSystem.models.SprinklerList.*;


public class App {
    public static void main(String[] args) {
        Network network=new Network();
        setNetworkOne(network);
        network.startPump();


    }

    private static void setNetworkOne(Network network){
        network.addPump(SHE40_200_55);
        network.addPipeline(PE_90,10,network.connectTo(0));
        network.addPipeline(PE_90,10,network.connectTo(1));
        network.addPipeline(PE_90,10,network.connectTo(1));
        network.addSprinkler(R_VAN_SST_1_5x9_1,396/2,network.connectTo(2));
        network.addSprinkler(R_VAN_SST_1_5x9_1,396/2,network.connectTo(3));
    }

    private static void setNetworkTwo(Network network){
        network.addPump(SHE40_200_55);
        network.addPipeline(PE_90,20,network.connectTo(0));
        network.addSprinkler(R_VAN_SST_1_5x9_1,396,network.connectTo(1));
    }
}
