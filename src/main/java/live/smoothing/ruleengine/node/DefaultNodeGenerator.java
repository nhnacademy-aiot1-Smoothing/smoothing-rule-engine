package live.smoothing.ruleengine.node;

import live.smoothing.ruleengine.common.Parameters;
import live.smoothing.ruleengine.mq.producer.NodeProducer;
import live.smoothing.ruleengine.node.checker.AllChecker;
import live.smoothing.ruleengine.node.checker.BiggerChecker;
import live.smoothing.ruleengine.node.checker.Checker;
import live.smoothing.ruleengine.node.checker.EqualChecker;
import live.smoothing.ruleengine.node.inserter.Inserter;
import live.smoothing.ruleengine.node.inserter.KeyValueInserter;
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

        Node firstSwitchNode = new SwitchNode(
                "switchNode1",
                3,
                new Checker[]{
                        new AllChecker(
                                new Parameters(Map.of())
                        ),
                        new EqualChecker(
                                new Parameters(Map.of("key", "place","value", "class_a")
                                )
                        ),
                        new EqualChecker(
                                new Parameters(Map.of("key", "event","value", "electrical_energy"))
                        )
                }
        );

        Node noojeonNode = new SwitchNode(
                "switchNode4",
                1,
                new Checker[]{
                        new EqualChecker(
                                new Parameters(Map.of("key", "description","value", "igr")
                                )
                        )
                }
        );

        Node noojeonValueCheckNode = new SwitchNode(
                "switchNode3",
                1,
                new Checker[]{
                        new BiggerChecker(
                                new Parameters(Map.of("key", "value","value", "0"))
                        )
                }
        );

        Node noojeonSplitNode = new SwitchNode(
                "switchNode5",
                2,
                new Checker[]{
                        new AllChecker(
                                new Parameters(Map.of())
                        ),
                        new AllChecker(
                                new Parameters(Map.of())
                        )
                }
        );

        Node noojeonMessageExtractNode = new ExtractNode(
                "extractNode3",
                1,
                Set.of()
        );

        Node noojeonMessageDtoNode = new InsertNode(
                "insertNode3",
                1,
                new Inserter[]{
                        new KeyValueInserter(
                                new Parameters(Map.of("key", "userRole","value", "ALL"))),
                        new KeyValueInserter(
                                new Parameters(Map.of("key","message","value","누전 발생"))
                        )
                }
        );

        Node noojeonMessageQueueNode = new MQNode(
                "MqNode3",
                "hook-queue",
                nodeProducer);

        Node noojeonExtractNode2 = new ExtractNode(
                "extractNode4",
                1,
                Set.of("topic","brokerId","value")
        );


        Node insetNode1 = new InsertNode(
                "insertNode1",
                1,
                new Inserter[]{
                        new TimeInserter(
                                new Parameters(Map.of("withNano", "false"))
                        ),
                        new KeyValueInserter(
                                new Parameters(Map.of("key", "sensorErrorType","value", "누전"))
                        )
                }
        );


        Node keyChangeNode11 = new KeyChangeNode(
                "keyChangeNode1",
                1,
                Map.of(
                        "value", "sensorValue","time","createdAt"
                )
        );

        Node mqNode5 = new MQNode(
                "mqNode5",
                "add-sensorError-queue",
                nodeProducer);

        firstSwitchNode.connect(2, noojeonNode.getInputWire());
        noojeonNode.connect(0, noojeonValueCheckNode.getInputWire());
        noojeonValueCheckNode.connect(0, noojeonSplitNode.getInputWire());
        noojeonSplitNode.connect(0, noojeonMessageExtractNode.getInputWire());
        noojeonMessageExtractNode.connect(0, noojeonMessageDtoNode.getInputWire());
        noojeonMessageDtoNode.connect(0, noojeonMessageQueueNode.getInputWire());

        noojeonSplitNode.connect(1, noojeonExtractNode2.getInputWire());
        noojeonExtractNode2.connect(0, insetNode1.getInputWire());
        insetNode1.connect(0, keyChangeNode11.getInputWire());
        keyChangeNode11.connect(0, mqNode5.getInputWire());

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
        parsingNode.connect(0, firstSwitchNode.getInputWire());
        firstSwitchNode.connect(0, influxDbInsertNode.getInputWire());
        firstSwitchNode.connect(1, keyChangeNode1.getInputWire());
        keyChangeNode1.connect(0, extractNode1.getInputWire());
        extractNode1.connect(0, insertNode1.getInputWire());
        insertNode1.connect(0, switchNode2.getInputWire());
        switchNode2.connect(0, mqNode1.getInputWire());
        switchNode2.connect(1, mqNode2.getInputWire());
        switchNode2.connect(2, mqNode3.getInputWire());

        receiverNode.start();
        parsingNode.start();
        firstSwitchNode.start();
        keyChangeNode1.start();
        extractNode1.start();
        switchNode2.start();
        influxDbInsertNode.start();
        insertNode1.start();
        mqNode1.start();
        mqNode2.start();
        mqNode3.start();

        noojeonNode.start();
        noojeonValueCheckNode.start();
        noojeonMessageExtractNode.start();
        noojeonMessageDtoNode.start();
        noojeonMessageQueueNode.start();

        noojeonSplitNode.start();
        noojeonExtractNode2.start();
        keyChangeNode11.start();
        insetNode1.start();
        mqNode5.start();
    }
}
