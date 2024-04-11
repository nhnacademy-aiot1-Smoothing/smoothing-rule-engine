package live.smoothing.ruleengine.mq.consumer;

public interface GatewayConsumer {

    /**
     * Gateway 의 Url 를 반환하는 메소드
     * @return Gateway Url
     */
    String getGatewayUri();

    /**
     * Gateway 의 name 을 반환하는 메소드
     * @return Gateway name
     */
    String getGatewayName();

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
