package live.smoothing.ruleengine.port;

import live.smoothing.ruleengine.sensor.dto.SensorMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * 노드에 포함된 포트
 *
 * @author 박영준
 */
public class Port {
    int currentWireIndex = 0;
    List<Wire> wires;

    public Port() {
        this(0);
    }

    public Port(int wireCount) {
        this.wires = new ArrayList<>(wireCount);
        for (int i = 0; i < wireCount; i++) {
            wires.add(new Wire());
        }
    }

    /**
     * 메시지 전달
     *
     * @param message 전달할 메시지
     * @throws InterruptedException 메시지 전달 실패시
     */
    public void put(SensorMessage message) throws InterruptedException {
        wires.get(currentWireIndex % wires.size()).put(message);
        currentWireIndex++;
    }

    /**
     * 메시지 수신
     *
     * @return 수신한 메시지
     * @throws InterruptedException 메시지 수신 실패시
     */
    public SensorMessage get() throws InterruptedException {
        return wires.get(0).get();
    }

    /**
     * 와이어 추가
     *
     * @param wire 추가할 와이어
     */
    public void addWire(Wire wire) {
        wires.add(wire);
    }

    /**
     * 와이어 반환
     *
     * @param index 와이어 인덱스
     * @return 와이어
     */
    public Wire getWire(int index) {
        return wires.get(index);
    }
}
