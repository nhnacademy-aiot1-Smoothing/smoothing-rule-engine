package live.smoothing.ruleengine.node;

import live.smoothing.ruleengine.common.Parameters;
import live.smoothing.ruleengine.node.checker.AllChecker;
import live.smoothing.ruleengine.node.checker.Checker;

import java.util.Map;

public class DefaultNodeGenerator implements NodeGenerator {
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


        receiverNode.connect(0, parsingNode.getInputWire());
        parsingNode.connect(0, switchNode.getInputWire());
        switchNode.connect(0, influxDbInsertNode1.getInputWire());
        switchNode.connect(0, influxDbInsertNode2.getInputWire());

        receiverNode.start();
        parsingNode.start();
        switchNode.start();
        influxDbInsertNode1.start();
        influxDbInsertNode2.start();
    }
}
