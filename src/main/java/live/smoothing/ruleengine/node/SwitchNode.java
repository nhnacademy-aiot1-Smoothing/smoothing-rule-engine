package live.smoothing.ruleengine.node;

import live.smoothing.ruleengine.checker.Checker;
import live.smoothing.ruleengine.sensor.dto.SensorMessage;

public class SwitchNode extends Node {

    private final Checker[] checkers;

    protected SwitchNode(String nodeId, int outputPortCount, Checker[] checkers) {
        super(nodeId, outputPortCount);
        this.checkers = checkers;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                SensorMessage message = tryGetMessage();

                for (int i = 0; i < getOutputPortCount(); i++) {
                    if (checkers[i].check(message)) {
                        output(i, message);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
