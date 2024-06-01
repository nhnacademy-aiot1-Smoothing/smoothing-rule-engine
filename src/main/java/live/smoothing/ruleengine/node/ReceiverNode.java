package live.smoothing.ruleengine.node;

import live.smoothing.ruleengine.sensor.dto.SensorMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * Receiver Node
 * 하는 거 없음
 *
 * @author 우혜승
 */
@Slf4j
public class ReceiverNode extends Node {

    protected ReceiverNode(String nodeId, int outputPortCount) {
        super(nodeId, outputPortCount);

    }

    /**
     * 메시지 수신
     */
    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            try {
                SensorMessage message = tryGetMessage();
//                log.error("reveiver node message : {}", message);
                process(message);
            } catch (InterruptedException e) {
                log.debug("ReceiverNode에서 메시지를 가져오는데 실패했습니다.");
            }
        }

    }

    /**
     * 수신 후 메시지 다음 노드로 전달
     * @param message 수신한 메시지
     */
    private void process(SensorMessage message) {
        for(int i = 0; i < getOutputPortCount(); i++) {
            try {
                output(i, message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
