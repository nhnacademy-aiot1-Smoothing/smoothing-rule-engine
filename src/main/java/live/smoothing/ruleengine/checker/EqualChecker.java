package live.smoothing.ruleengine.checker;

import live.smoothing.ruleengine.common.Parameters;
import live.smoothing.ruleengine.sensor.dto.SensorMessage;

public class EqualChecker implements Checker {

    private final String key;
    private final String value;

    public EqualChecker(Parameters parameters) {
        this.key = parameters.getParam("key");
        this.value= parameters.getParam("value");
    }

    @Override
    public boolean check(SensorMessage message) {
        String targetValue = message.getAttribute(key);
        if (Objects.isNull(targetValue)) {
            return false;
        }
        
        return value.equals(targetValue);
    }
}
