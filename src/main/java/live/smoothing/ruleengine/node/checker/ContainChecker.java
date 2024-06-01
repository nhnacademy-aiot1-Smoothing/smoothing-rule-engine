package live.smoothing.ruleengine.node.checker;

import live.smoothing.ruleengine.common.Parameters;
import live.smoothing.ruleengine.sensor.dto.SensorMessage;

public class ContainChecker implements Checker{

    private final String key;
    private final String value;

    public ContainChecker(Parameters parameters) {
        this.key = parameters.getParam("key");
        this.value= parameters.getParam("value");
    }

    @Override
    public boolean check(SensorMessage message) {
        Object targetValue = message.getAttribute(key);
        if (targetValue == null) {
            return false;
        }
        return targetValue.toString().contains(value);
    }
}
