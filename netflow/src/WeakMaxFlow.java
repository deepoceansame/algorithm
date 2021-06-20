import java.util.ArrayList;

public class WeakMaxFlow {
    private static class MyEWDGraph{
        int V;
        int E;
        ArrayList<WDEdge>[] adj;

        public MyEWDGraph(int v,int e){
            V=v;
            E=e;
            adj=new ArrayList[v];
            for (int i=0;i<v;i++){
                adj[i]=new ArrayList<>(50);
            }
        }

        public void addEdge(WDEdge edge){
            adj[edge.from].add(edge);
        }

        public ArrayList<WDEdge> adj(int v){
            return adj[v];
        }
    }

    private static class WDEdge implements Comparable<WDEdge> {
        int from;
        int to;
        int weight;
        int flow;
        public WDEdge(int from,int to,int weight){
            this.from=from;
            this.to=to;
            this.weight=weight;
            flow=0;
        }

        @Override
        public int compareTo(WDEdge o) {
            if (weight>o.weight)
                return 1;
            else if (weight<o.weight)
                return -1;
            else
                return 0;
        }
    }

    private static class MaxFlow{
        MyEWDGraph g;
        MyEWDGraph gf;
        int[] qian;
        int[] isvisited;

        public MaxFlow(MyEWDGraph g){
            this.g=g;
            gf=new MyEWDGraph(g.V,g.E);
            for (int i=0;i<g.V;i++){
                for (WDEdge e:g.adj(i)){
                    gf.addEdge(new WDEdge(i,e.to,e.weight));
                }
            }
            qian=new int[g.V];
            isvisited=new int[g.V];
        }

        public boolean path(int v,int zhi){
            isvisited[v]=1;
            int to=0;
            for (WDEdge e:gf.adj(v)){
                to=e.to;
                if (isvisited[to]==0 && e.weight>=zhi){
                    if (to==gf.V-1){
                        qian[gf.V-1]=v;
                        return true;
                    }
                    else{
                        qian[to]=v;
                        if (path(to,zhi)){
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        public int[] getPath(){
            ArrayList<Integer> pa=new ArrayList<>();
            int vv=g.V-1;
            while(true){
                pa.add(vv);
                if (vv==0){
                    break;
                }
                vv=qian[vv];
            }
            int[] path=new int[pa.size()];
            for (int i=0;i<pa.size();i++){
                path[i]=pa.get(pa.size()-i-1);
            }
            return path;
        }

        public int getBottleNeck(int[] path){
            int from=-1;
            int to=-1;
            int bottleneck=Integer.MAX_VALUE;
            for (int i=0;i<path.length-1;i++){
                from=path[i];
                to=path[i+1];
                for (WDEdge e:gf.adj(from)){
                    if (e.to==to){
                        bottleneck=Math.min(bottleneck,e.weight);
                    }
                }
            }
            return bottleneck;
        }

        public boolean isWard(int from,int to){
            boolean iswar=false;
            for (WDEdge e: g.adj(from)){
                if (e.to==to){
                    iswar=true;
                    break;
                }
            }
            return iswar;
        }

        public void maxflow(){
            int delta=0;
            for (WDEdge e:g.adj(0)){
                delta=Math.max(delta,e.weight);
            }
            int[] path=null;
            int bottleneck=0;
            int remain=0;
            int flow=0;
            boolean findedge=false;
            while (delta>=1){
                for (int j=0;j<g.V;j++){
                    isvisited[j]=0;
                }
                while (path(0,delta)){
                    path=getPath();
                    bottleneck=getBottleNeck(path);
                    for (int i=0;i<path.length-1;i++){
                        if (isWard(path[i],path[i+1])){
                            for (WDEdge e:g.adj(path[i])){
                                if (e.to==path[i+1]){
                                    e.flow+=bottleneck;
                                    flow=e.flow;
                                    remain=e.weight-bottleneck;
                                    break;
                                }
                            }
                            //path[i]->pth[i+1] remain
                            for (WDEdge e:gf.adj(path[i])){
                                if (e.to==path[i+1]){
                                    e.weight=remain;
                                    break;
                                }
                            }
                            //path[i+1]->path[i] flow
                            for (WDEdge e: gf.adj(path[i+1])){
                                if (e.to==path[i]){
                                    findedge=true;
                                    e.weight=flow;
                                    break;
                                }
                            }
                            if (!findedge){
                                gf.addEdge(new WDEdge(path[i+1],path[i],flow));
                            }
                            findedge=false;
                        }
                        else{
                            for (WDEdge e:g.adj(path[i+1])){
                                if (e.to==path[i]){
                                    e.flow-=bottleneck;
                                    flow=e.flow;
                                    remain=e.weight-bottleneck;
                                    break;
                                }
                            }

                            //path[i]->pth[i+1] flow
                            for (WDEdge e:gf.adj(path[i])){
                                if (e.to==path[i+1]){
                                    e.weight=flow;
                                    break;
                                }
                            }
                            //path[i+1]->path[i] remain
                            for (WDEdge e: gf.adj(path[i+1])){
                                if (e.to==path[i]){
                                    findedge=true;
                                    e.weight=remain;
                                    break;
                                }
                            }
                            if (!findedge){
                                gf.addEdge(new WDEdge(path[i+1],path[i],flow));
                            }
                            findedge=false;
                        }
                    }
                }
                delta=delta/2;
            }
        }


    }


    public static void main(String[] args) {
        MyEWDGraph g=new MyEWDGraph(4,5);
        g.addEdge(new WDEdge(0,1,100));
        g.addEdge(new WDEdge(0,2,100));
        g.addEdge(new WDEdge(1,2,1));
        g.addEdge(new WDEdge(2,3,100));
        g.addEdge(new WDEdge(1,3,100));
        MaxFlow mf=new MaxFlow(g);
        mf.maxflow();
    }
}
