package live.smoothing.ruleengine.node.inserter;

import live.smoothing.ruleengine.common.Parameters;
import live.smoothing.ruleengine.sensor.dto.SensorMessage;

import java.time.LocalDateTime;

public class TimeInserter implements Inserter {

    private final boolean withNano;

    public TimeInserter(Parameters parameters) {
        withNano = parameters.getParam("withNano") != null && parameters.getParam("withNano").equals("true");
    }

    @Override
    public void insert(SensorMessage message) {
        if (withNano) {
            message.addAttribute("time", LocalDateTime.now().toString());
        } else {
            message.addAttribute("time", LocalDateTime.now().withNano(0).toString());
        }
    }
}
