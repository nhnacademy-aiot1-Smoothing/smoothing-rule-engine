package live.smoothing.ruleengine.sensor.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 노드간 전달되는 센서 메시지
 *
 * @author 박영준
 */
public class SensorMessage {
    private final Map<String, Object> sensorAttributes;

    public SensorMessage() {
        this.sensorAttributes = new HashMap<>();
    }

    /**
     * 센서 메시지 맵에 있는 속성 반환
     *
     * @param key 속성 키
     * @return 속성 값
     */
    public Object getAttribute(String key) {
        return sensorAttributes.get(key);
    }

    /**
     * 센서 메시지 맵에 속성 추가
     *
     * @param key 속성 키
     * @param attribute 속성 값
     */
    public void addAttribute(String key, Object attribute) {
        sensorAttributes.put(key, attribute);
    }

    /**
     * 센서 메시지 맵 모든 키 반환
     *
     * @return 센서 메시지 맵 키
     */
    public Set<String> getKeys() {
        return sensorAttributes.keySet();
    }
}
