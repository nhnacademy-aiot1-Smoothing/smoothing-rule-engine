package live.smoothing.ruleengine.node;

import live.smoothing.ruleengine.common.Parameters;
import live.smoothing.ruleengine.node.checker.AllChecker;
import live.smoothing.ruleengine.node.checker.Checker;
import live.smoothing.ruleengine.node.checker.EqualChecker;
import live.smoothing.ruleengine.sensor.dto.SensorMessage;
import live.smoothing.ruleengine.sensor.entity.SensorData;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class NodeManager {
    @Getter
    private final Node receiverNode;
    private final Map<String, Node> defaultNodes = new HashMap<>();
    private final Map<String, List<Node>> copiedNodes = new HashMap<>();

    public NodeManager() {
        receiverNode = new ReceiverNode("receiver", 1);
        //생성 후 변경
        receiverNode.start();

        TopicParsingNode topicParsingNode = new TopicParsingNode("topicParsing", 1,
                Map.of("p","place","s","site","e","event")
        );
        receiverNode.connect(0, topicParsingNode.getInputWire());
        topicParsingNode.start();
        Checker[] checkers = new Checker[2];
        checkers[0] = new EqualChecker(new Parameters(Map.of("key","event","value","humidity")));
        checkers[1] = new AllChecker(new Parameters(Map.of("place","building","site","seoul")));
        Node switchNode = new SwitchNode("switch", 2, checkers);
        topicParsingNode.connect(0, switchNode.getInputWire());
        switchNode.start();
        Node t1 = new ReceiverNode("t1", 0);
        Node t2 = new ReceiverNode("t2", 0);
        switchNode.connect(0, t1.getInputWire());
        switchNode.connect(1, t2.getInputWire());
        t1.start();
        t2.start();
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
