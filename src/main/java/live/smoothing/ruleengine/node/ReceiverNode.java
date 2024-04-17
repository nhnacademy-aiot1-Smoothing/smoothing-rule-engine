package live.smoothing.ruleengine.node;

public class ReceiverNode extends Node {
    private int outputPortCount;

    protected ReceiverNode(String nodeId, int outputPortCount) {
        super(nodeId, outputPortCount);
        this.outputPortCount = outputPortCount;

    }

    @Override
    public void run() {
        for(int i = 0; i < outputPortCount; i++) {
            try {
                SensorMessage message = getInputWire().get();
                outputPorts[i].getWire(0).put(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
