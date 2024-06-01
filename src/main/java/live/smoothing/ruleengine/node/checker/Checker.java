package live.smoothing.ruleengine.node.checker;

import live.smoothing.ruleengine.sensor.dto.SensorMessage;

public interface Checker {
    boolean check(SensorMessage message);
}
