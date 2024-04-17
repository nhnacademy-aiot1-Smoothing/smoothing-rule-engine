package live.smoothing.ruleengine.node;

import live.smoothing.ruleengine.sensor.dto.SensorMessage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NodeManager {

    private final Node receiverNode;

    public NodeManager() {
        receiverNode = new ReceiverNode("Receiver", 1);
        receiverNode.start();
    }

    public void putToReceiver(SensorMessage message) {
        try {
            receiverNode.getInputWire().put(message);
        } catch (InterruptedException e) {
            log.debug("ReceiverNode에 메시지를 전달하는데 실패했습니다.");
        }
    }
}
