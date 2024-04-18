package live.smoothing.ruleengine.node.sub;

import live.smoothing.ruleengine.sensor.dto.SensorMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 노드에 포함된 포트
 *
 * @author 박영준
 */
@Slf4j
public class Port {
    int currentWireIndex = 0;
    List<Wire> wires;

    public Port(int wireCount) {
        this.wires = new ArrayList<>();
        for (int i = 0; i < wireCount; i++) {
            wires.add(new Wire());
        }
    }

    /**
     * 와이어에 Round Robin 방식으로 순차적 메시지 전달
     *
     * @param message 전달할 메시지
     * @throws InterruptedException 메시지 전달 실패시
     */
    public void put(SensorMessage message) throws InterruptedException {
        if (currentWireIndex == wires.size()) {
            currentWireIndex = 0;
        }

        wires.get(currentWireIndex++).put(message);
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
