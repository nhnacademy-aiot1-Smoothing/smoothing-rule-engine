package live.smoothing.ruleengine.node.checker;

import live.smoothing.ruleengine.common.Parameters;
import live.smoothing.ruleengine.sensor.dto.SensorMessage;

import java.util.Objects;

public class LowerEqualChecker implements Checker {
    private final String key;
    private final Long value;

    public LowerEqualChecker(Parameters parameters) {
        this.key = parameters.getParam("key");
        this.value = Long.valueOf(parameters.getParam("value"));
    }

    @Override
    public boolean check(SensorMessage message) {
        Object targetValue = message.getAttribute(key);
        if (Objects.isNull(targetValue) || !(targetValue instanceof Long || targetValue instanceof Integer || targetValue instanceof Short || targetValue instanceof Byte || targetValue instanceof Double || targetValue instanceof Float)) {
            return false;
        }

        return (Long) targetValue <= value;
    }
}
