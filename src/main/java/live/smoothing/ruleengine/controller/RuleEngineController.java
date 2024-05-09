package live.smoothing.ruleengine.controller;

import live.smoothing.ruleengine.RuleEngineManagement;
import live.smoothing.ruleengine.broker.dto.BrokerGenerateRequest;
import live.smoothing.ruleengine.sensor.dto.TopicRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ruleengine")
public class RuleEngineController {
    private final RuleEngineManagement ruleEngineManagement;

    @PostMapping("/broker")
    public ResponseEntity<Void> addBroker(@RequestBody BrokerGenerateRequest brokerGenerateRequest) {
        ruleEngineManagement.addBroker(brokerGenerateRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/broker/{brokerId}")
    public ResponseEntity<Void> deleteBroker(@RequestParam("brokerId") Integer brokerId) {
        ruleEngineManagement.removeBroker(brokerId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/topic")
    public ResponseEntity<Void> addTopic(@RequestBody TopicRequest topicRequest) throws Exception {
        ruleEngineManagement.subscribe(topicRequest.getBrokerId(), topicRequest.getTopic());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/topic")
    public ResponseEntity<Void> deleteTopic(@RequestBody TopicRequest topicRequest) throws Exception {
        ruleEngineManagement.unsubscribe(topicRequest.getBrokerId(), topicRequest.getTopic());
        return ResponseEntity.ok().build();
    }
}
