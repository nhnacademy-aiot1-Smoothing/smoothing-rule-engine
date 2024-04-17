package live.smoothing.ruleengine.node;

import live.smoothing.ruleengine.sensor.dto.SensorMessage;

/**
 * Receiver Node
 * 하는 거 없음
 *
 * @author 우혜승
 */
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
                process(message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
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
