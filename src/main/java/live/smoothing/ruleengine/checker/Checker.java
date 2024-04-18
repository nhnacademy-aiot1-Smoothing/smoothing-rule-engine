package live.smoothing.ruleengine.checker;

import live.smoothing.ruleengine.sensor.dto.SensorMessage;

public interface Checker {
    boolean check(SensorMessage message);
}
