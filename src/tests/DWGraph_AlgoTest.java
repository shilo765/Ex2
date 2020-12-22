package tests;

import api.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class DWGraph_AlgoTest {
    @Test
    public void copyCloneTest()
    {
        dw_graph_algorithms dwa=new DWGraph_Algo();
        dw_graph_algorithms dwa2;
        DWGraph_DS dwg=new DWGraph_DS();

        NodeData nd1=new NodeData();
        NodeData nd2=new NodeData();
        NodeData nd3=new NodeData();
        dwa.getGraph().addNode(nd1);
        dwa2=dwa;
        if(dwa2.getGraph().nodeSize()==0)
            fail("method getGraph not work properly");
        dwg=(DWGraph_DS)dwa.copy();
        if(!dwa.getGraph().equals(dwg))
            fail("method equals not work properly");
        dwg.removeNode(nd1.getKey());
        // dwa2.getGraph().removeNode(nd1.getKey());
        if(dwa.getGraph().nodeSize()!=1||dwg.nodeSize()!=0)
            fail("method copy not work properly");
        if(dwa.getGraph().equals(dwg))
            fail("method equals not work properly");
        dwa.getGraph().addNode(nd2);
        dwa.getGraph().addNode(nd3);
        dwa.getGraph().connect(nd1.getKey(),nd3.getKey(),8);
        dwa2=new DWGraph_Algo();
        dwa.save("graph");
        dwa2.load("graph");
        if(!dwa.equals(dwa2))
            fail("save or load method not work properly");


    }
    @Test
    public void shortestPathPathDistTest()
    {
        dw_graph_algorithms dwa=new DWGraph_Algo();
        NodeData nd1=new NodeData();
        NodeData nd2=new NodeData();
        NodeData nd3=new NodeData();
        dwa.getGraph().addNode(nd1);
        dwa.getGraph().addNode(nd2);
        dwa.getGraph().addNode(nd3);
        if(!dwa.shortestPath(nd1.getKey(),nd3.getKey()).isEmpty())
            fail("method shortestPath not work properly");
        dwa.getGraph().connect(nd1.getKey(),nd2.getKey(),5);
        dwa.getGraph().connect(nd2.getKey(),nd3.getKey(),3.5);
        if(dwa.shortestPathDist(nd1.getKey(), nd3.getKey())!=8.5)
            fail("method shortestPathDist not work properly");
        dwa.getGraph().connect(nd1.getKey(), nd3.getKey(),3);
        if(dwa.shortestPathDist(nd1.getKey(), nd3.getKey())!=3)
            fail("method shortestPathDist not work properly");
        if(dwa.shortestPath(nd1.getKey(),nd3.getKey()).contains(2))
            fail("method shortestPath not work properly");
    }
    @Test
    public void connectivity()
    {
        dw_graph_algorithms dwa=new DWGraph_Algo();
        NodeData nd1=new NodeData();
        NodeData nd2=new NodeData();
        NodeData nd3=new NodeData();
        if(!dwa.isConnected())
            fail("method isConnect not work properly");
        dwa.getGraph().addNode(nd1);
        if(!dwa.isConnected())
            fail("method isConnect not work properly");
        dwa.getGraph().addNode(nd2);
        if(dwa.isConnected())
            fail("method isConnect not work properly");
        dwa.getGraph().addNode(nd3);
        dwa.getGraph().connect(nd1.getKey(),nd2.getKey(),8);
        dwa.getGraph().connect(nd2.getKey(),nd3.getKey(),8);
        dwa.getGraph().connect(nd3.getKey(),nd1.getKey(),8);
        if(!dwa.isConnected())
            fail("method isConnect not work properly");
    }
}