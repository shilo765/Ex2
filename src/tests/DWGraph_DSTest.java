package tests;

import api.DWGraph_DS;
import api.NodeData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class DWGraph_DSTest {
    @Test
    public void basicFunTest()
    {
        DWGraph_DS dwg=new DWGraph_DS();
        NodeData nd=new NodeData();
        NodeData nd2=new NodeData();
        if(!dwg.getV().isEmpty())
            fail("method getV not work properly");
        dwg.addNode(nd);
        dwg.addNode(nd2);
        if(!dwg.getE(nd.getKey()).isEmpty())
            fail("method getE not work properly");
        if(dwg.getV().isEmpty())
            fail("method getV not work properly");
        if(dwg.getNode(1)==null)
            fail("getNode method not work properly");
        dwg.connect(nd.getKey(),nd2.getKey(),8);
        if(dwg.getE(nd.getKey()).isEmpty())
            fail("method connect not work properly");
        dwg.removeEdge(nd.getKey(),nd2.getKey());
        if(!dwg.getE(nd.getKey()).isEmpty())
            fail("method removeEdge not work properly");
        dwg.removeNode(nd.getKey());
        dwg.removeNode(nd2.getKey());
        if(dwg.nodeSize()!=0)
            fail("method removeNode not work properly");


    }
}