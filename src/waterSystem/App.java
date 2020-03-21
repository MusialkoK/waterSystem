package waterSystem;

public class App {
    public static void main(String[] args) {
        Network network=new Network();

        NetworkElement startNode = new NetworkElementObject();
        NetworkElement node1 = new NetworkElementObject(startNode);
        NetworkElement node2 = new NetworkElementObject(startNode);
        NetworkElement node3 = new NetworkElementObject(startNode);

        startNode.update("how how how");
        System.out.println(node1.getValue());
        startNode.update("hey hey hey");
        System.out.println(node2.getValue());
        System.out.println(node3.getValue());
    }
}
