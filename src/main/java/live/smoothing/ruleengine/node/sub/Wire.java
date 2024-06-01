package live.smoothing.ruleengine.node.sub;

import live.smoothing.ruleengine.sensor.dto.SensorMessage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 노드간 메시지 전달을 위한 와이어
 *
 * @author 박영준
 */
public class Wire {
    private final BlockingQueue<SensorMessage> queue;

    public Wire() {
        this.queue = new LinkedBlockingQueue<>();
    }

    /**
     * 큐에 메시지 추가
     *
     * @param message 추가할 메시지
     * @throws InterruptedException 메시지 추가 실패시
     */
    public void put(SensorMessage message) throws InterruptedException {
        queue.put(message);
    }

    /**
     * 큐에서 메시지 가져오기
     *
     * @return 가져온 메시지
     * @throws InterruptedException 메시지 가져오기 실패시
     */
    public SensorMessage get() throws InterruptedException {
        return queue.take();
    }

    public int size(){
        return queue.size();
    }
}
