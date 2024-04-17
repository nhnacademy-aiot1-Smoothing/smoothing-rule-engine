package live.smoothing.ruleengine.node;

import live.smoothing.ruleengine.sensor.dto.SensorMessage;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;

import java.util.Map;

public class TopicParsingNode extends Node {

    private static final Logger log = LoggerFactory.getLogger(TopicParsingNode.class);
    private final Map<String, String> map;

    protected TopicParsingNode(String nodeId, int outputPortCount, Map<String, String> map) {

        super(nodeId, outputPortCount);
        this.map = map;
    }

    /**
     * Runs this operation.
     */
    @Override
    @SneakyThrows
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {

            try {

                SensorMessage goal = tryGetMessage();

                String topic = goal.getAttribute("topic").toString();
                String payload = goal.getAttribute("payload").toString();

                for(String key : map.keySet()) {

                    String finalKey = map.get(key);
                    String topicValue = letParseString(key, topic);
                    goal.addAttribute(topicValue, finalKey);
                }

                goal.addAttribute("time", letParse("time", payload));
                goal.addAttribute("value", letParse("value", payload));

                for(int i = 0; i < getOutputPortCount(); i++) {

                    output(i, goal);
                }

            } catch(InterruptedException e) {
                log.info("토픽파싱노드 Interrupted");
            }
        }
    }

    private Object letParse(String key, String target) {

        JsonParser jsonParser = JsonParserFactory.getJsonParser();
        return jsonParser.parseMap(target).get(key);
    }

    private String letParseString(String key, String target) {

        String step = target.split(key+"/")[1];
        if(step.contains("/")) {
            return step.split("/")[0];
        }
        return step;
    }
}
