package live.smoothing.ruleengine.node;

import live.smoothing.ruleengine.node.checker.Checker;
import live.smoothing.ruleengine.sensor.dto.SensorMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
                log.error("switch node message : {}", message);

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
