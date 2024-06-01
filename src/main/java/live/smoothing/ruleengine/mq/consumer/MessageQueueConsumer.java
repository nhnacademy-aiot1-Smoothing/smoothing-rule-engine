package live.smoothing.ruleengine.mq.consumer;

import live.smoothing.ruleengine.RuleEngineManagement;

public abstract class MessageQueueConsumer {

    protected final RuleEngineManagement ruleEngineManagement;

    public MessageQueueConsumer(RuleEngineManagement ruleEngineManagement) {
        this.ruleEngineManagement = ruleEngineManagement;
    }

}
