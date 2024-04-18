package live.smoothing.ruleengine.node;

import live.smoothing.ruleengine.checker.Checker;
import live.smoothing.ruleengine.sensor.dto.SensorMessage;

/**
 * 항상 true를 반환하는 Checker
 *
 * @author 신민석
 */
public class AllChecker implements Checker {

    @Override
    public boolean check(SensorMessage message) {

        return true;
    }
}
