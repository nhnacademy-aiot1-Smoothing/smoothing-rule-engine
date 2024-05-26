package live.smoothing.ruleengine.node;

import live.smoothing.ruleengine.sensor.dto.SensorMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
public class ExtractNode extends Node {

    private final Set<String> keys;

    protected ExtractNode(String nodeId, int outputPortCount, Set<String> keys) {
        super(nodeId, outputPortCount);
        this.keys = keys;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                SensorMessage message = tryGetMessage();
                SensorMessage newMessage = new SensorMessage();
                message.getKeys().stream().filter(e -> !keys.contains(e)).forEach(e -> newMessage.addAttribute(e, message.getAttribute(e)));
                for (int i = 0; i < getOutputPortCount(); i++) {
                    output(i, newMessage);
                }
            } catch (Exception e) {
                log.error("ExtractNode : {}", e.getMessage());
            }
        }
    }
}
