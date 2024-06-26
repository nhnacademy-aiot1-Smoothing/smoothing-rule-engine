package live.smoothing.ruleengine.node.checker;

import live.smoothing.ruleengine.common.Parameters;
import live.smoothing.ruleengine.sensor.dto.SensorMessage;

/**
 * 항상 true를 반환하는 Checker
 *
 * @author 신민석
 */
public class AllChecker implements Checker {

    public AllChecker(Parameters parameters) {

    }

    /**
     * 항상 true를 반환
     * @param message
     * @return
     */
    @Override
    public boolean check(SensorMessage message) {

        return true;
    }
}
