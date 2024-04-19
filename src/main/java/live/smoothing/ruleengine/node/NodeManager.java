package live.smoothing.ruleengine.node;

import live.smoothing.ruleengine.sensor.dto.SensorMessage;
import live.smoothing.ruleengine.sensor.entity.SensorData;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class NodeManager {

    private final Node receiverNode;
    private final Map<String, Node> defaultNodes = new HashMap<>();
    private final Map<String, List<Node>> copiedNodes = new HashMap<>();

    public NodeManager(NodeGenerator nodeGenerator) {
        receiverNode = new ReceiverNode("receiver", 1);
        //생성 후 변경
        nodeGenerator.init(receiverNode);
    }

    public void putToReceiver(SensorData sensorData) {
        try {
            receiverNode.getInputWire().put(sensorDataToSensorMessage(sensorData));
        } catch (InterruptedException e) {
            log.debug("ReceiverNode에 메시지를 전달하는데 실패했습니다.");
        }
    }

    private SensorMessage sensorDataToSensorMessage(SensorData sensorData) {
        SensorMessage message = new SensorMessage();
        message.addAttribute("topic", sensorData.getTopic());
        message.addAttribute("payload", sensorData.getPayload());
        return message;
    }
}
