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
                    goal.addAttribute(finalKey, topicValue);
                }

                goal.addAttribute("time", letParse("time", payload));
                goal.addAttribute("value", letParse("value", payload));
                //todo
                goal.deleteAttribute("payload");
                for(int i = 0; i < getOutputPortCount(); i++) {
                    output(i, goal);
                }

            } catch(RuntimeException e) {
                log.error("{}",e.getCause());
                log.info("토픽파싱노드 Interrupted");
            }
        }
    }

    private Object letParse(String key, String target) {

        com.google.gson.JsonParser jsonParser = new com.google.gson.JsonParser();
        return jsonParser.parse(target).getAsJsonObject().get(key);
    }

    private String letParseString(String key, String target) {

        String step = target.split("/"+key+"/")[1];
        if(step.contains("/")) {
            return step.split("/")[0];
        }
        return step;
    }

    public static void main(String[] args) {
        TopicParsingNode topicParsingNode = new TopicParsingNode("topicParsing", 0,
                Map.of("p","place","s","site","e","event")
        );
        System.out.println(topicParsingNode.letParseString("e","data/s/nhnacademy/b/gyeongnam/p/office/d/gems-3500/e/electrical_energy/t/ac_indoor_unit/ph/1/de/volt_unbalance"));
    }
}
