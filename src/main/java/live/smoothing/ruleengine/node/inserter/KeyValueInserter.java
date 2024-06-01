package live.smoothing.ruleengine.node.inserter;

import live.smoothing.ruleengine.common.Parameters;
import live.smoothing.ruleengine.sensor.dto.SensorMessage;

public class KeyValueInserter implements Inserter {

    private final String key;
    private final String value;

    public KeyValueInserter(Parameters parameters) {
        this.key = parameters.getParam("key");
        this.value = parameters.getParam("value");
    }

    @Override
    public void insert(SensorMessage message) {
        message.addAttribute(key, value);
    }
}
