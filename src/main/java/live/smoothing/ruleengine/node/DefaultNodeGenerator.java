package live.smoothing.ruleengine.node;

import live.smoothing.ruleengine.common.Parameters;
import live.smoothing.ruleengine.mq.producer.NodeProducer;
import live.smoothing.ruleengine.node.checker.AllChecker;
import live.smoothing.ruleengine.node.checker.Checker;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
public class DefaultNodeGenerator implements NodeGenerator {

    private final NodeProducer nodeProducer;

    @Override
    public void init(Node receiverNode) {
        Node parsingNode = new TopicParsingNode (
                "parsingNode",
                1,
                Map.of(
                        "s", "site",
                        "b", "branch",
                        "p", "place",
                        "e", "event",
                        "d", "device",
                        "de", "description",
                        "ph", "phase",
                        "t", "type"
                      )
                );

        Node switchNode = new SwitchNode(
                "switchNode",
                1,
                new Checker[] {
                        new AllChecker(
                                new Parameters(Map.of())
                                )
                }
        );

        Node influxDbInsertNode1 = new InfluxDbInsertNode(
                "influxDbInsertNode",
                0,
                "http://133.186.221.174:8086",
                "sGwZkcFWUdCloR3qyI_6hwg_mBb6Y2NB1ZTsQxCS1AE2nBFBsDMVjvqIupjfgPNcnpM6rd4M2PaGWul5Fre45Q==",
                "smoothing",
                "raw"
        );

        Node influxDbInsertNode2 = new InfluxDbInsertNode(
                "influxDbInsertNode",
                0,
                "http://133.186.221.174:8086",
                "sGwZkcFWUdCloR3qyI_6hwg_mBb6Y2NB1ZTsQxCS1AE2nBFBsDMVjvqIupjfgPNcnpM6rd4M2PaGWul5Fre45Q==",
                "smoothing",
                "raw"
        );

        KeyChangeNode keyChangeNode = new KeyChangeNode(
                "keyChangeNode",
                1,
                Map.of(
                        "place","location"
                )
        );

        ExtractNode extractNode = new ExtractNode(
                "extractNode",
                1,
                Set.of("device","value","location","time")
        );

        Node mqNode = new MQNode(
                "mqNode",
                "smoothing",
                nodeProducer);


        receiverNode.connect(0, parsingNode.getInputWire());
        parsingNode.connect(0, switchNode.getInputWire());
        switchNode.connect(0, influxDbInsertNode1.getInputWire());
        switchNode.connect(0, influxDbInsertNode2.getInputWire());
        switchNode.connect(0, keyChangeNode.getInputWire());
        keyChangeNode.connect(0, extractNode.getInputWire());
        extractNode.connect(0, mqNode.getInputWire());

        receiverNode.start();
        parsingNode.start();
        switchNode.start();
        influxDbInsertNode1.start();
        influxDbInsertNode2.start();
        keyChangeNode.start();
        extractNode.start();
        mqNode.start();
    }
}
