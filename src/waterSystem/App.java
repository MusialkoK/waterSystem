package waterSystem;



import static waterSystem.models.PipelineList.*;
import static waterSystem.models.PumpsList.*;
import static waterSystem.models.SprinklerList.*;


public class App {
    public static void main(String[] args) {
        Network network=new Network();
        network.addPump(SHE40_200_55);
        network.addPipeline(PE_90,20,network.connectTo(0));
        network.addSprinkler(R_VAN_SST_1_5x9_1,396,network.connectTo(1));
        network.startPump();


    }
}
