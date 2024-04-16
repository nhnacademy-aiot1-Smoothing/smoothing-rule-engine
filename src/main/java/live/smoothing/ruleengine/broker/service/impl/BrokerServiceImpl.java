package live.smoothing.ruleengine.broker.service.impl;

import live.smoothing.ruleengine.broker.dto.BrokerGenerateRequest;
import live.smoothing.ruleengine.broker.entity.Broker;
import live.smoothing.ruleengine.broker.repository.BrokerRepository;
import live.smoothing.ruleengine.broker.service.BrokerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@AllArgsConstructor
@Service("brokerService")
public class BrokerServiceImpl implements BrokerService {

    private final BrokerRepository brokerRepository;

    @Override
    public Broker addBroker(BrokerGenerateRequest request) {

        String ip = request.getBrokerIp();
        String name = request.getBrokerName();

        if (!brokerRepository.existsByBrokerIpOrBrokerName(ip, name)) {

            Broker broker = Broker.builder()
                    .brokerIp(request.getBrokerIp())
                    .brokerPort(request.getBrokerPort())
                    .brokerName(request.getBrokerName())
                    .protocolType(request.getProtocolType())
                    .build();
            return brokerRepository.save(broker);

        }

        throw new IllegalArgumentException("브로커가 이미 존재합니다.");
    }

    @Override
    public String getBrokerName(Integer brokerId) {

        return brokerRepository.findById(brokerId)
                .orElseThrow(() -> new EntityNotFoundException("브로커 없음"))
                .getBrokerName();
    }

    @Override
    public List<Broker> getBrokers() {

        return brokerRepository.findAll();
    }
}
