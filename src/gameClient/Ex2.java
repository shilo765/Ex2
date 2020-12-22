package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Ex2 implements Runnable{
    private static MyFrame _win;
    private static JPanel pan=new JPanel();
    private static Arena _ar;
    private static game_service gameSta;
    public static void main(String[] a) throws JSONException {
        int scenario_num = 23;
        game_service game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games
        	//int id = 314717125;
        	//game.login(id);
        gameSta=game;
        String g = gameSta.getGraph();
        String pks = gameSta.getPokemons();
        directed_weighted_graph gg = null;
        try {
            gg = getGraphFromJson(gameSta.getGraph()).getGraph();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        init(gameSta);

        gameSta.startGame();
        JLabel lab=new JLabel(String.valueOf(game.timeToEnd()));
        lab.setBounds(0,0,300,300);
        _win.add(lab);

        _win.setTitle(String.valueOf(game.timeToEnd()));
        _win.setResizable(true);
        MyPanel mtPan=new MyPanel();

        lab.setBounds(100,100,300,300);


        Thread client = new Thread(new Ex2());
        client.start();
    }

    @Override
    public void run() {
        directed_weighted_graph gg= null;
        try {
            gg = getGraphFromJson(gameSta.getGraph()).getGraph();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int ind=0;
        long dt=100;
        while(gameSta.isRunning()) {
            moveAgants(gameSta, gg);
            try {
                if(ind%1==0)
                {_win.repaint();
                    _win.setTitle(String.valueOf(gameSta.timeToEnd()));}
                //Thread.sleep(dt);
                ind++;
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        String res = gameSta.toString();

        System.out.println(res);
        System.exit(0);
    }
    /**
     * Moves each of the agents along the edge,
     * in case the agent is on a node the next destination (next edge) is chosen (randomly).
     * @param game
     * @param gg
     * @param
     */
    private static void moveAgants(game_service game, directed_weighted_graph gg) {
        String lg = game.move();
        List<CL_Agent> log = Arena.getAgents(lg, gg);
        _ar.setAgents(log);
        //ArrayList<OOP_Point3D> rs = new ArrayList<OOP_Point3D>();
        String fs =  game.getPokemons();
        List<CL_Pokemon> ffs = Arena.json2Pokemons(fs);
        for(int a = 0;a<ffs.size();a++) { Arena.updateEdge(ffs.get(a),gg);}
        _ar.setPokemons(ffs);
        for(int i=0;i<log.size();i++) {
            CL_Agent ag = log.get(i);
            int id = ag.getID();
            int dest = ag.getNextNode();
            int src = ag.getSrcNode();
            double v = ag.getValue();
            if(dest==-1) {
                dest = nextNode(gg, src,ffs);
                game.chooseNextEdge(ag.getID(), dest);
                System.out.println("Agent: "+id+", val: "+v+"   turned to node: "+dest);
            }
        }
    }
    /**
     * a very simple random walk implementation!
     * @param g
     * @param src
     * @return
     */
    private static int nextNode(directed_weighted_graph g, int src,List<CL_Pokemon> l1) {
        System.out.println(_ar.getAgents());
        DWGraph_Algo dwa=new DWGraph_Algo();
        dwa.init(g);
        int minDist=-5;
        int last=0;
        int srcPoke,destPoke;
        if(src==8)
            System.out.println("hi");
        for (CL_Pokemon poke:l1)
        {
            if(poke.getType()<0){
                last=-1;
                destPoke=Math.min(poke.get_edge().getSrc(),poke.get_edge().getDest());
                srcPoke=Math.max(poke.get_edge().getSrc(),poke.get_edge().getDest());
            }
            else{
                last=1;
                destPoke=Math.max(poke.get_edge().getSrc(),poke.get_edge().getDest());
                srcPoke=Math.min(poke.get_edge().getSrc(),poke.get_edge().getDest());
            }
            if(src==srcPoke)
                return  destPoke;
            else{
                if(dwa.shortestPathDist(src,srcPoke)<dwa.shortestPathDist(src,minDist)||minDist==-5)
                    minDist=srcPoke;
            }
        }
        List<node_data> lN=dwa.shortestPath(src,minDist);
		if(lN.size()<1)
		    if(last==-1)
			return src-1;
		    else
		        return src+1;
        minDist= lN.get(1).getKey();;
        return minDist;
    }
    private static void init(game_service game) throws JSONException {
        String g = game.getGraph();
        String fs = game.getPokemons();
        directed_weighted_graph gg = getGraphFromJson(game.getGraph()).getGraph();
        //gg.init(g);
        _ar = new Arena();
        _ar.setGraph(gg);
        _ar.setPokemons(Arena.json2Pokemons(fs));
        _win = new MyFrame("test Ex2");
        _win.setSize(1000, 700);
        _win.update(_ar);


        _win.show();
        String info = game.toString();
        JSONObject line;
        try {
            line = new JSONObject(info);
            JSONObject ttt = line.getJSONObject("GameServer");
            int rs = ttt.getInt("agents");
            System.out.println(info);
            System.out.println(game.getPokemons());
            int src_node = 0;  // arbitrary node, you should start at one of the pokemon
            ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());
            for(int a = 0;a<cl_fs.size();a++) { Arena.updateEdge(cl_fs.get(a),gg);}
            for(int a = 0;a<rs;a++) {
                int ind = a%cl_fs.size();
                CL_Pokemon c = cl_fs.get(ind);
                int nn = c.get_edge().getDest();
                if(c.getType()<0 ) {nn = c.get_edge().getSrc();}

                game.addAgent(nn);
            }
        }
        catch (JSONException e) {e.printStackTrace();}
    }
    /**this method make a DWGraph_Algo from json string*/
    public static DWGraph_Algo getGraphFromJson(String json) throws JSONException {
        NodeData n1= new NodeData();
        JSONObject obj=new JSONObject(json);
        JSONArray nodes= obj.getJSONArray("Nodes");
        DWGraph_DS dws=new DWGraph_DS();
        for(int i=0;i< nodes.length();i++)
        {
            n1= new NodeData();
            GeoLocation g1=new GeoLocation();
            JSONObject node =nodes.getJSONObject(i);
            n1.setKey(node.getInt("id"));
            String str=(node.getString("pos"));
            g1.setX(Double.parseDouble(str.split(",")[0]));
            g1.setY(Double.parseDouble(str.split(",")[1]));
            g1.setZ(Double.parseDouble(str.split(",")[2]));
            n1.setLocation(g1);
            dws.addNode(n1);
        }
        EdgeData ed1;
        JSONArray edges= obj.getJSONArray("Edges");
        for(int i=0;i< edges.length();i++)
        {
            JSONObject edge=edges.getJSONObject(i);
            dws.connect(edge.getInt("src"),edge.getInt("dest"),edge.getDouble("w"));
        }
        DWGraph_Algo dwa=new DWGraph_Algo();
        dwa.init(dws);
        return dwa;
    }
}