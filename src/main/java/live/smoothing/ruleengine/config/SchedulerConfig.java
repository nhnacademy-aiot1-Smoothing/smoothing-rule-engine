package live.smoothing.ruleengine.config;

import live.smoothing.ruleengine.RuleEngineManagement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SchedulerConfig {

    private final RuleEngineManagement ruleEngineManagement;

    @Scheduled(fixedDelay = 300000, initialDelay = 10000)
    public void ruleEngineSync() {
        log.info("Rule Engine Sync Start");
        ruleEngineManagement.synchronize();
        log.info("Rule Engine Sync End");
    }
}
