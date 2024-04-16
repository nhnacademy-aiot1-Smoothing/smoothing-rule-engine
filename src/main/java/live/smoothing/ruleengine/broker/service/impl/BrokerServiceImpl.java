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

        if(!brokerRepository.existsByBrokerIpOrBrokerName(ip, name)) {

            Broker broker = new Broker(null, request.getBrokerIp(), request.getBrokerPort(), request.getBrokerName(), request.getBrokerType());
            return brokerRepository.save(broker);

        }

        throw new IllegalArgumentException("게이트웨이가 이미 존재합니다.");
    }

    @Override
    public String getBrokerName(Integer brokerId) {

        return brokerRepository.findById(brokerId)
                .orElseThrow(() -> new EntityNotFoundException("게이트웨이 없음"))
                .getBrokerName();
    }

    @Override
    public List<Broker> getBrokers() {

        return brokerRepository.findAll();
    }
}
