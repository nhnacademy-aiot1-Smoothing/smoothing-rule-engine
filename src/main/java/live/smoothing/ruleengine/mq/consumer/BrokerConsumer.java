package live.smoothing.ruleengine.mq.consumer;

import live.smoothing.ruleengine.RuleEngineManagement;

public interface BrokerConsumer {

    /**
     * Broker 의 Url 를 반환하는 메소드
     * @return Broker Url
     */
    String getBrokerUri();

    /**
     * Broker 의 Id 를 반환하는 메소드
     * @return Broker Id
     */
    int getBrokerId();

    /**
     * RuleEngineManagement 를 설정하는 메소드
     * @param ruleEngineManagement RuleEngineManagement
     */
    void setRuleEngineManagement(RuleEngineManagement ruleEngineManagement);
    /**
     * message queue 의 topic 을 구독하기 위한 메소드
     */
    void subscribe(String topic) throws Exception;

    /**
     * message queue 의 topic 을 구독을 중지하기 위한 메소드
     */
    void unsubscribe(String topic) throws Exception;

    /**
     * message queue 를 구독하기 위한 메소드
     */
    void start() throws Exception;

    /**
     * message queue 구독을 중지하기 위한 메소드
     */
    void stop() throws Exception;

}
