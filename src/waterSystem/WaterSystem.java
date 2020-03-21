package waterSystem;

public class WaterSystem {
	static WaterDevice[] netElements = new WaterDevice[20];
	Pump pump;
	Node[] nodes = new Node[10];
	Sprinkler[] sprinklers = new Sprinkler[10];
	Pipeline[] pipelines = new Pipeline[10];
	int numberOfElements=0;
	int numberOfNodes=0;
	int numberOfSprinklers=0;
	int numberOfPipelines=0;
	int[][] adjacencyMatrix;
	int[] typesArray;
	static FlowDirection direction;
	
	public static void main(String[] args) {
		WaterSystem ws=new WaterSystem();
		ws.numberOfPipelines=3;
		ws.numberOfElements=4;
		
		ws.setDefaultAdjacencyMatrix();
		ws.getAdjacencyMatrix();
		ws.setDefaultTypesArray();
		ws.createDefaultNetwork();

		ws.manualWork(6);
		//System.out.println("");
		}
	
	public void manualWork(int rounds) {
		direction= FlowDirection.DIRECT;
		this.pump.startPump(direction);
		this.pump.getRWP();
		for(int i=0; i<rounds; i++) {
			this.transfer(netElements[0]);
			netElements[1].setDeviceStatus(direction);
			netElements[1].distributeWater(direction);
			netElements[1].setDeviceStatus(direction);
			this.transfer(netElements[1]);
			netElements[2].setDeviceStatus(direction);
			netElements[2].distributeWater(direction);
			netElements[3].setDeviceStatus(direction);
			netElements[3].distributeWater(direction); 
			this.changeCalculationDirection();
			netElements[2].setDeviceStatus(direction);
			this.transfer(netElements[2]);
			netElements[3].setDeviceStatus(direction);
			this.transfer(netElements[3]);
			netElements[1].setDeviceStatus(direction);
			netElements[1].distributeWater(direction);
			netElements[1].setDeviceStatus(direction);
			this.transfer(netElements[1]);
			netElements[0].setDeviceStatus(direction);
			netElements[0].distributeWater(direction);
			this.changeCalculationDirection();
			netElements[0].setDeviceStatus(direction);
			this.pump.getRWP();
			System.out.println();
		}

	}
	
	public void changeCalculationDirection() {
		if(direction== FlowDirection.DIRECT) {
			direction= FlowDirection.REVERSE;
		}else {
			direction= FlowDirection.DIRECT;
		}
	}

	public void getAdjacencyMatrix() {
		for (int[] matrix : adjacencyMatrix) {
			for (int j = 0; j < adjacencyMatrix.length; j++) {
				if (j != adjacencyMatrix.length - 1) {
					System.out.print(matrix[j] + " ");
				} else {
					System.out.println(matrix[j]);
				}
			}
		}
	}
	
	public void setDefaultAdjacencyMatrix() {
		adjacencyMatrix = new int[numberOfElements][numberOfElements];
		adjacencyMatrix[0] = new int[] {-1,-1,-1,-1};
		adjacencyMatrix[1] = new int[] {0, -1, -1, -1};
		adjacencyMatrix[2] = new int[] {-1,1,-1,-1};
		adjacencyMatrix[3] = new int[] {-1,2,-1,-1};
	}
	
	public void setDefaultTypesArray() {
		// 0 - pump; 1 - node; 2 - sprinkler
		typesArray= new int[numberOfElements];
		typesArray[0]=0;
		typesArray[1]=1;
		typesArray[2]=2;
		typesArray[3]=2;
	}
	
	public void createDefaultNetwork() {
		for (int i = 0; i < numberOfElements; i++) {
			if(typesArray[i]==0) {
				pump = new Pump("default",i);
				netElements[i] = pump;
			}else if(typesArray[i]==1) {
				nodes[numberOfNodes] = new Node("default",i);
				netElements[i] = nodes[numberOfNodes];
				numberOfNodes++;
			}else {
				sprinklers[numberOfSprinklers] = new Sprinkler("default",i);
				netElements[i] = sprinklers[numberOfSprinklers];
				numberOfSprinklers++;
			}
		}
		
		for(int i =0; i<numberOfPipelines;i++) {
			pipelines[i]= new Pipeline("default",i);
		}
		
		for(int i=0; i<numberOfElements; i++) {
			for(int j=0; j<numberOfElements; j++) {
				if(adjacencyMatrix[j][i]!=-1) {
					WaterDevice wd=netElements[j];
					Pipeline p=pipelines[adjacencyMatrix[j][i]];
					netElements[i].setOutGate(wd, p);
					}
				}
			}
			
			for(int i=0; i<numberOfElements; i++) {
				for(int j=0; j<numberOfElements; j++) {
					if(adjacencyMatrix[i][j]!=-1) {
						WaterDevice wd=netElements[j];
						Pipeline p=pipelines[adjacencyMatrix[i][j]];
						netElements[i].setInGate(wd, p);
					}
				}
			}
	}
	
	
	public void transfer(WaterDevice wd) {
		if(wd.allowTransfer) {
			switch(direction) {
			case DIRECT:
				for(int i=0; i<wd.activeOutGates; i++) {
					Pipeline pipe=wd.outGates[i].withPipeline;
					WaterDevice dest=wd.outGates[i].toDevice;
					int gatePlace=dest.findGate(direction, wd);
					pipe.setNewWorkingPointBy(FlowPressure.FLOW, wd.outGates[i].getFlow());
					dest.inGates[gatePlace].setWorkingPoint(wd.outGates[i].getFlow(), wd.outGates[i].getPressure()+pipe.getPressure());
					dest.inGates[gatePlace].setGateOpen(false);
					wd.setAllowTransfer(false);
				}
				break;
			case REVERSE:
				for(int i=0; i<wd.activeInGates; i++) {
					Pipeline pipe=wd.inGates[i].withPipeline;
					WaterDevice dest=wd.inGates[i].toDevice;
					int gatePlace=dest.findGate(direction, wd);
					pipe.setNewWorkingPointBy(FlowPressure.FLOW, wd.inGates[i].getFlow());
					dest.outGates[gatePlace].setWorkingPoint(wd.inGates[i].getFlow(), wd.inGates[i].getPressure()-pipe.getPressure());
					dest.outGates[gatePlace].setGateOpen(false);
					wd.setAllowTransfer(false);
				}
				break;
			}
						
			}

			
			
		}
	
}
