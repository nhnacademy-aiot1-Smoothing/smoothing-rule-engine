package live.smoothing.ruleengine.node;

import live.smoothing.ruleengine.sensor.dto.SensorMessage;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;

import java.util.Map;

@Slf4j
public class TopicParsingNode extends Node {
    private static final String ATTRIBUTE_TIME = "time";
    private static final String ATTRIBUTE_VALUE = "value";
    private static final String ATTRIBUTE_TOPIC = "topic";
    private static final String ATTRIBUTE_PAYLOAD = "payload";

    private final Map<String, String> map;

    protected TopicParsingNode(String nodeId, int outputPortCount, Map<String, String> map) {

        super(nodeId, outputPortCount);
        this.map = map;
    }

    @Override
    @SneakyThrows
    public void run() {

        while (!Thread.currentThread().isInterrupted()) {
            try {
                processMessage();
            } catch (InterruptedException e) {
                log.info("[토픽파싱노드]: Thread interrupted");
            }
        }
    }

    private void processMessage() throws InterruptedException {
        SensorMessage message = tryGetMessage();
        parseTopicAndPayload(message);
        outputMessageToManyNodes(message);
    }

    private void parseTopicAndPayload(SensorMessage message) {
        String topic = message.getAttribute(ATTRIBUTE_TOPIC);
        String payload = message.getAttribute(ATTRIBUTE_PAYLOAD);

        for (String key : map.keySet()) {
            String finalKey = map.get(key);
            String topicValue = parseJson(key, topic);

            message.addAttribute(finalKey, topicValue);
        }

        String time = parseJson(ATTRIBUTE_TIME, payload);
        String value = parseJson(ATTRIBUTE_VALUE, payload);

        message.addAttribute(ATTRIBUTE_TIME, time);
        message.addAttribute(ATTRIBUTE_VALUE, value);
    }

    private void outputMessageToManyNodes(SensorMessage message) throws InterruptedException {
        for (int i = 0; i < getOutputPortCount(); i++) {
            output(i, message);
        }
    }

    private static String parseJson(String key, String target) {
        JsonParser jsonParser = JsonParserFactory.getJsonParser();
        return jsonParser.parseMap(target).get(key).toString();
    }
}
