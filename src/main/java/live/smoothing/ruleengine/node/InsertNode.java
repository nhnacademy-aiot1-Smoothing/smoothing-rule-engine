package live.smoothing.ruleengine.node;

import live.smoothing.ruleengine.node.inserter.Inserter;
import live.smoothing.ruleengine.sensor.dto.SensorMessage;

public class InsertNode extends Node {

    private final Inserter[] inserters;

    public InsertNode(String nodeId, int outputPortCount, Inserter[] inserters) {
        super(nodeId, outputPortCount);
        this.inserters = inserters;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                SensorMessage message = tryGetMessage();
                for (Inserter inserter : inserters) {
                    inserter.insert(message);
                }

                for (int i = 0; i < getOutputPortCount(); i++) {
                    output(i, message);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
