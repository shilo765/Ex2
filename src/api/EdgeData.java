package api;

import java.util.Objects;

public class EdgeData implements edge_data {
    int src,dest,tag=-1;
    double weight;
    String info="";

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EdgeData edgeData = (EdgeData) o;
        return src == edgeData.src &&
                dest == edgeData.dest &&
                tag == edgeData.tag &&
                Double.compare(edgeData.weight, weight) == 0 &&
                Objects.equals(info, edgeData.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(src, dest, tag, weight, info);
    }

    public EdgeData(int src, int dest, double weight)
    {
        this.src=src;
        this.dest=dest;
        this.weight=weight;
    }
    @Override
    public int getSrc() {
        return this.src;
    }

    @Override
    public int getDest() {
        return this.dest;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info=s;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag=t;
    }

    public edge_data copy() {
        EdgeData ed=new EdgeData(this.src,this.dest,this.weight);
        ed.tag=this.tag;
        ed.info=this.info;
        return ed;
    }
}