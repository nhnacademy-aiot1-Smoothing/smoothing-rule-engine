package live.smoothing.ruleengine.node.inserter;

import live.smoothing.ruleengine.sensor.dto.SensorMessage;

public interface Inserter {
    void insert(SensorMessage message);
}
