package api;



import java.util.Objects;
public class NodeData implements node_data,Comparable<NodeData> {
    private static int keyCount = 0;
    private int key = -1;
    private int tag = -1;
    private String info = "-1";
    private static double NodeEdges = 0.0D;
    private int lastNei = -1;
    //private HashMap<Integer, node_data> nei;
    public static int neiCount;
    //private HashMap<Integer,Double> weightMap;
    private GeoLocation gl=new GeoLocation();

    public NodeData() {
        this.key = ++keyCount;
        //  this.nei = new HashMap();
        // this.weightMap=new HashMap<>();
        neiCount = 0;
    }
    public void setKey(int key)
    {
        this.key=key;
    }
    //public void addToWeightMap(int node1,double w){this.weightMap.put(node1,w);}

//   public double getWeight(int node1)
//    {

//        return this.weightMap.get(node1);
//    }
//    public HashMap getWeightMap(){ return this.weightMap;}

//    public void removeWeight(int node1){this.weightMap.remove(node1);}

    public int getLastNei() {
        return this.lastNei;
    }

    public void setLastNei(int value) {
        this.lastNei = value;
    }
    @Override
    public int getKey() {
        return this.key == -1 ? 0 : this.key;
    }

    @Override
    public geo_location getLocation() {
        return this.gl;
    }

    @Override
    public void setLocation(geo_location p) {
        this.gl=(GeoLocation)p;
    }

    @Override
    public double getWeight() {
        return 0;
    }

    @Override
    public void setWeight(double w) {
        System.out.println("dd");
    }

//    public Collection<node_data> getNi() {
//        return this.nei.values();
//    }

//    public boolean hasNi(int key) {
//        return this.nei.values().iterator().hasNext();
//    }

//    public void addNi(NodeData t) {
//        if(t!=null)
//        {
//            if (!this.equals(t)) {
//                this.nei.put(t.getKey(), t);
//                ((NodeData)t).getHashNei().put(this.key,this);
//                ++neiCount;
//            }}
//
//    }
//
//    public void removeNode(NodeData node) {
//        if (this.nei.containsKey(node.getKey())) {
//            this.nei.remove(node.getKey());
//            --neiCount;
//        }
//
//    }

    public int getNeiSize() {
        return neiCount;
    }
    @Override
    public int getTag() {
        return this.tag;
    }
    @Override
    public String getInfo() {
        return this.info;
    }

    //public HashMap getHashNei(){return this.nei;}

    public NodeData copy() {
        NodeData n = new NodeData();
        n.setKey(this.key);
        n.setInfo(this.info);
        n.setTag(this.tag);
        n.setLastNei(this.lastNei);
        //  Iterator var3 = this.getNi().iterator();

//        while(var3.hasNext()) {
//            node_data m = (node_data)var3.next();
//            n.getHashNei().put(m.getKey(), m);
//        }
//        Iterator var4 = this.weightMap.values().iterator();
//        while(var3.hasNext()) {
//            NodeData m = (NodeData) var3.next();
//            n.getWeightMap().put(m.getKey(),n.getWeight(m.getKey()));
//        }

        return this;
    }
    @Override
    public void setInfo(String s) {
        this.info = s;
    }
    @Override
    public void setTag(int t) {
        this.tag = t;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeData nodeData = (NodeData) o;
        return key == nodeData.key &&
                Double.compare(nodeData.tag, tag) == 0 &&
                lastNei == nodeData.lastNei &&
                info.equals(nodeData.info) ;
//                nei.equals(nodeData.nei) &&
//                weightMap.equals(nodeData.weightMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, tag, info, lastNei);
    }

    @Override
    public String toString() {
        return "NodeData{" +
                "key=" + key +
                ", tag=" + tag +
                ", info='" + info + '\'' +
                ", lastNei=" + lastNei +
                ", gl=" + gl +
                '}';
    }

//    public String toString() {
//        String hashString="";
//        for(double w1: weightMap.values())
//            for(int k1: weightMap.keySet())
//                hashString+=k1+"+"+w1+",";
//        String neiString="";
//        for(node_data n1: nei.values())
//            neiString+=n1.getKey()+",";
//        return "NodeInfo{" +
//                "key=" + key +
//                ", tag=" + tag +
//                ", info='" + info + '\'' +
//                ", lastNei=" + lastNei +
//                ", nei=" + neiString +
//                ", weightMap=" + hashString+
//                "}"+"\n"; }


    @Override
    public int compareTo(NodeData n1)
    {
        if(this.getTag()>n1.getTag())
            return 1;
        if (this.getTag()<n1.getTag())
            return -1;
        return 0;
    }
    public double getNodesEdges() {
        return NodeEdges;
    }
}