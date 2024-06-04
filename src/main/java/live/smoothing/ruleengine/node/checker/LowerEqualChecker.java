package live.smoothing.ruleengine.node.checker;

import live.smoothing.ruleengine.common.Parameters;
import live.smoothing.ruleengine.sensor.dto.SensorMessage;

import java.util.Objects;

public class LowerEqualChecker implements Checker {
    private final String key;
    private final Double value;

    public LowerEqualChecker(Parameters parameters) {
        this.key = parameters.getParam("key");
        this.value = Double.valueOf(parameters.getParam("value"));
    }

    @Override
    public boolean check(SensorMessage message) {
        Object targetValue = message.getAttribute(key);

        Double messageValue = null;
        try {
            messageValue = Double.valueOf(targetValue.toString());
        } catch (Exception e) {
            return false;
        }


        return messageValue <= value;
    }
}
