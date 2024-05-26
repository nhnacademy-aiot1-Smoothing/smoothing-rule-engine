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
                message.getKeys().stream().filter(e -> !keys.contains(e)).forEach(message::deleteAttribute);
                for (int i = 0; i < getOutputPortCount(); i++) {
                    output(i, message);
                }
            } catch (Exception e) {
                log.error("ReceiverNode에서 메시지를 가져오는데 실패했습니다.");
            }
        }
    }
}
