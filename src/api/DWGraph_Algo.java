package api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.*;

public class DWGraph_Algo implements dw_graph_algorithms{
    directed_weighted_graph dwg=new DWGraph_DS();
    private Queue<node_data> pq=new LinkedList<>();
    private static int nodeCount;

    @Override
    public void init(directed_weighted_graph g) {
        this.dwg=g;
    }

    @Override
    public directed_weighted_graph getGraph() {
        return this.dwg;
    }

    @Override
    public directed_weighted_graph copy() {
        DWGraph_DS dwg2=new DWGraph_DS();
        return ((DWGraph_DS)this.dwg).copy();
    }

    @Override
    public boolean isConnected() {
        if (this.dwg.getV().size()==1||this.dwg.getV().size()==0)
            return true;
        node_data tempNd=null;
        boolean b1=true;
        for(node_data n1:this.dwg.getV())
        {
            if(tempNd==null)
                tempNd=n1;
            else
            {
                b1=b1&&(shortestPathDist(tempNd.getKey(),n1.getKey())!=-1);
            }
        }
        if(!b1)
            return false;
        ((DWGraph_DS)dwg).reverse();
        for(node_data n1:this.dwg.getV())
        {
            if(tempNd==null)
                tempNd=n1;
            else
            {
                b1=b1&&(shortestPathDist(tempNd.getKey(),n1.getKey())!=-1);
            }
        }
        ((DWGraph_DS)dwg).reverse();
        return b1;
    }
    /**set all the info to be -1 like defult*/
    public void setAllInfo()
    {
        for(node_data n1 :dwg.getV())
            n1.setInfo("-1");
    }
    @Override
    public double shortestPathDist(int src, int dest) {
        this.pq.clear();
        setAllInfo();
        nodeCount=0;

        node_data temp=new NodeData();
        if(!dwg.getV().contains(dwg.getNode(src))||!dwg.getV().contains(dwg.getNode(dest)))
            return -1;
        nodeCount=1;
        if(src==dest)
            return 0;
        dwg.getNode(src).setInfo("0");
        pq.add(dwg.getNode(src));
        while (!pq.isEmpty())
        {

            temp=pq.poll();
            for (edge_data e1: dwg.getE(temp.getKey())) {
                if(dwg.getNode(e1.getDest()).getInfo().equals("-1")||Double.parseDouble(dwg.getNode(e1.getDest()).getInfo())>Double.parseDouble(temp.getInfo())+e1.getWeight())
                {
                    (dwg.getNode(e1.getDest())).setTag(temp.getKey());
                    if(dwg.getNode(e1.getDest()).getInfo().equals("-1"))
                        nodeCount++;
                    else
                        pq.remove(dwg.getNode(e1.getDest()));
                    dwg.getNode(e1.getDest()).setInfo((Double.parseDouble(temp.getInfo())+e1.getWeight())+"");
                    pq.add(dwg.getNode(e1.getDest()));
                }
            }
        }
        return Double.parseDouble(dwg.getNode(dest).getInfo());
    }
    @Override

    public List<node_data> shortestPath(int src, int dest) {
        LinkedList<node_data> l1=new LinkedList<>();
        double temp=shortestPathDist(src,dest);
        node_data n1=new NodeData();
        if(src<0||dest<1)
            return l1;
        if(dwg.getV().size()==0)
            return l1;
        if(!dwg.getV().contains(dwg.getNode(src))||!dwg.getV().contains(dwg.getNode(dest)))
            return l1;
        if(src==dest){
            l1.addFirst(dwg.getNode(src));
            return l1;}
        if(temp==-1)
            return l1;
        n1=dwg.getNode(dest);
        while(n1.getKey()!=src)
        {
            l1.addFirst(n1);
            n1= dwg.getNode(n1.getTag());
        }
        l1.addFirst(n1);
        return l1;
    }

    @Override
    public boolean save(String file) {

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(".\\"+file+".json"));
            JSONObject dWGraphAlgo  = new JSONObject();
            JSONObject dWGraphDs  = new JSONObject();
            dWGraphAlgo.put("graph",dWGraphDs);
            dWGraphDs.put("Edges",((DWGraph_DS)this.getGraph()).getAll_E().toArray());
            dWGraphDs.put("Nodes",((DWGraph_DS)this.getGraph()).getV().toArray());
            System.out.println(dWGraphAlgo.toString());
            bw.write(dWGraphAlgo.toString());
            bw.close();
            return true;
        }
        catch (IOException | JSONException e)
        {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean load(String file) {
        try {
            directed_weighted_graph dwG=new DWGraph_DS();
//            JsonParser parser = new JsonParser();
            DWGraph_Algo dwa = new DWGraph_Algo();
            BufferedReader bf=new BufferedReader(new FileReader(".\\" + file + ".json"));
            String str=bf.readLine();

            JSONObject DWgraphAlgo=new JSONObject(str);
            JSONObject DwgarphDs=DWgraphAlgo.getJSONObject("graph");
            JSONArray nodes=DwgarphDs.getJSONArray("Nodes");
            NodeData n1;
            for(int i=0;i<nodes.length();i++)
            {
                JSONObject node =nodes.getJSONObject(i);
                n1=new NodeData();
                n1.setKey(node.getInt("key"));
                n1.setTag(node.getInt("tag"));
                n1.setInfo(node.getString("info"));
                dwg.addNode(n1);
            }
            JSONArray edges=DwgarphDs.getJSONArray("Edges");
            for(int i=0;i<edges.length();i++)
            {
                JSONObject edge =edges.getJSONObject(i);

                dwg.connect(edge.getInt("src"),edge.getInt("dest"),edge.getDouble("weight"));
            }
            dwa.init(dwg);
            return true;
        }
        catch (FileNotFoundException | JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DWGraph_Algo that = (DWGraph_Algo) o;
        return dwg.equals(that.dwg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dwg);
    }
}