package live.smoothing.ruleengine.node;

import live.smoothing.ruleengine.common.Parameters;
import live.smoothing.ruleengine.mq.producer.NodeProducer;
import live.smoothing.ruleengine.node.checker.AllChecker;
import live.smoothing.ruleengine.node.checker.Checker;
import live.smoothing.ruleengine.node.checker.EqualChecker;
import live.smoothing.ruleengine.node.inserter.Inserter;
import live.smoothing.ruleengine.node.inserter.TimeInserter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
public class DefaultNodeGenerator implements NodeGenerator {

    private final NodeProducer nodeProducer;

    @Override
    public void init(Node receiverNode) {
        Node parsingNode = new TopicParsingNode(
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

        Node switchNode1 = new SwitchNode(
                "switchNode1",
                2,
                new Checker[]{
                        new AllChecker(
                                new Parameters(Map.of())
                        ),
                        new EqualChecker(
                                new Parameters(Map.of("key", "place","value", "class_a")
                                )
                        )
//                        ,
//                        new EqualChecker(
//                                new Parameters(Map.of("key", "event","value", "electrical_energy"))
//                        )
                }
        );

        Node switchNode2 = new SwitchNode(
                "switchNode2",
                3,
                new Checker[]{
                        new EqualChecker(
                                new Parameters(Map.of("key", "event","value", "occupancy")
                                )
                        ),
                        new EqualChecker(
                                new Parameters(Map.of("key", "event","value", "illumination")
                                )
                        ),
                        new EqualChecker(
                                new Parameters(Map.of("key", "event","value", "magnet_status")
                                )
                        )
                }
        );

        Node influxDbInsertNode = new InfluxDbInsertNode(
                "influxDbInsertNode",
                0,
                "http://133.186.221.174:8086",
                "sGwZkcFWUdCloR3qyI_6hwg_mBb6Y2NB1ZTsQxCS1AE2nBFBsDMVjvqIupjfgPNcnpM6rd4M2PaGWul5Fre45Q==",
                "smoothing",
                "raw"
        );

        Node keyChangeNode1 = new KeyChangeNode(
                "keyChangeNode1",
                1,
                Map.of(
                        "place", "location"
                )
        );

        Node extractNode1 = new ExtractNode(
                "extractNode1",
                1,
                Set.of("device", "value", "location", "time","event")
        );

        Node insertNode1 = new InsertNode(
                "insertNode1",
                1,
                new Inserter[]{
                        new TimeInserter(
                                new Parameters(Map.of("withNano", "false"))
                        )
                }
        );

        Node mqNode1 = new MQNode(
                "mqNode1",
                "occupancy-queue",
                nodeProducer);

        Node mqNode2 = new MQNode(
                "mqNode1",
                "illumination-queue",
                nodeProducer);

        Node mqNode3 = new MQNode(
                "mqNode1",
                "magnet-queue",
                nodeProducer);



        receiverNode.connect(0, parsingNode.getInputWire());
        parsingNode.connect(0, switchNode1.getInputWire());
        switchNode1.connect(0, influxDbInsertNode.getInputWire());
        switchNode1.connect(1, keyChangeNode1.getInputWire());
        keyChangeNode1.connect(0, extractNode1.getInputWire());
        extractNode1.connect(0, insertNode1.getInputWire());
        insertNode1.connect(0, switchNode2.getInputWire());
        switchNode2.connect(0, mqNode1.getInputWire());
        switchNode2.connect(1, mqNode2.getInputWire());
        switchNode2.connect(2, mqNode3.getInputWire());

        receiverNode.start();
        parsingNode.start();
        switchNode1.start();
        keyChangeNode1.start();
        extractNode1.start();
        switchNode2.start();
        influxDbInsertNode.start();
        insertNode1.start();
        mqNode1.start();
        mqNode2.start();
        mqNode3.start();
    }
}
