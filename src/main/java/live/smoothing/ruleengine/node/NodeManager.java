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

        Checker[] checkers = new Checker[1];
        checkers[0] = new AllChecker(new Parameters(Map.of()));
        Node s = new SwitchNode("switch", 1, checkers);

        topicParsingNode.connect(0, s.getInputWire());
        s.start();

        Node i1 = new InfluxDbInsertNode("influxDbInsert", 0,
        "http://133.186.251.19:8086",
        "aJVmO4mV3F9HnIq0zFuOE4tgJ3SamMeA1zPCY4IruPeRH8cjHjz6IqozDbVVHtk8P1ZSJzxwotZITWDnl2W6Tg==",
        "smoothing",
        "smoothing");

        Node i2 = new InfluxDbInsertNode("influxDbInsert", 0,
                "http://133.186.251.19:8086",
                "aJVmO4mV3F9HnIq0zFuOE4tgJ3SamMeA1zPCY4IruPeRH8cjHjz6IqozDbVVHtk8P1ZSJzxwotZITWDnl2W6Tg==",
                "smoothing",
                "smoothing");

        s.connect(0, i1.getInputWire());
        s.connect(0, i2.getInputWire());
        i1.start();
        i2.start();
        for(int i = 0;i<8;i++){
            Node n = new InfluxDbInsertNode("influxDbInsert", 0,
                    "http://133.186.251.19:8086",
                    "aJVmO4mV3F9HnIq0zFuOE4tgJ3SamMeA1zPCY4IruPeRH8cjHjz6IqozDbVVHtk8P1ZSJzxwotZITWDnl2W6Tg==",
                    "smoothing",
                    "smoothing");
            s.connect(0, n.getInputWire());
            n.start();
        }
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
