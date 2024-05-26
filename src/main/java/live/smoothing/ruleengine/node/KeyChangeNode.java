package live.smoothing.ruleengine.node;

import live.smoothing.ruleengine.sensor.dto.SensorMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;

@Slf4j
public class KeyChangeNode extends Node {

    private final Set<Map.Entry<String, String>> entry;

    protected KeyChangeNode(String nodeId, int outputPortCount, Map<String, String> keys) {
        super(nodeId, outputPortCount);
        this.entry = keys.entrySet();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                SensorMessage message = tryGetMessage();
                entry.forEach(e->{
                    if(message.getAttribute(e.getKey()) != null) {
                        message.addAttribute(e.getValue(), message.getAttribute(e.getKey()));
                        message.deleteAttribute(e.getKey());
                    }
                });
                for (int i = 0; i < getOutputPortCount(); i++) {
                    output(i, message);
                }
            } catch (InterruptedException e) {
                log.error("ReceiverNode에서 메시지를 가져오는데 실패했습니다.");
            }
        }
    }
}
