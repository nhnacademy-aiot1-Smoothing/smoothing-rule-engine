package live.smoothing.ruleengine.node;

import live.smoothing.ruleengine.node.sub.Wire;
import live.smoothing.ruleengine.sensor.dto.SensorMessage;
import live.smoothing.ruleengine.node.sub.Port;
import lombok.Getter;

import java.util.UUID;

/**
 * Flow Based 기반 공통 추상화 노드
 *
 * @author 박영준
 */
public abstract class Node implements Runnable{

    @Getter
    private final int outputPortCount;
    @Getter
    private final String nodeId;
    protected final Thread thread;
    private final Port inputPort;
    private final Port[] outputPorts;

    protected Node(String nodeId ,int outputPortCount) {
        this.outputPortCount = outputPortCount;
        this.nodeId = nodeId;
        this.thread = new Thread(this);
        this.inputPort = new Port();
        this.outputPorts = new Port[outputPortCount];

        for (int i = 0; i < outputPortCount; i++) {
            outputPorts[i] = new Port();
        }
    }

    /**
     * 노드 시작
     *
     */
    public void start() {
        thread.start();
    }

    /**
     * 포트와 연결되어 있는 들어오는 와이어를 반환
     *
     * @return
     */
    public Wire getInputWire() {
        return inputPort.getWire(0);
    }

    /**
     * 나가는 포트 와이어에 다른 노드의 들어오는 와이어를 연결
     *
     * @param index 나가는 포트 인덱스
     * @param inputWire 다른 노드 들어오는 와이어
     */
    public void connect(int index, Wire inputWire) {
        outputPorts[index].addWire(inputWire);
    }

    /**
     * 나가는 포트 와이어에 메시지 전달
     *
     * @param index 나가는 포트 인덱스
     * @param message 전달할 메시지
     * @throws InterruptedException 메시지 전달 실패시
     */
    protected void output(int index, SensorMessage message) throws InterruptedException {
        outputPorts[index].put(message);
    }

    /**
     * 들어오는 와이어에서 메시지 가져오기
     *
     * @return 가져온 메시지
     * @throws InterruptedException 메시지 가져오기 실패시
     */
    protected SensorMessage tryGetMessage() throws InterruptedException {
        return inputPort.get();
    }

}
