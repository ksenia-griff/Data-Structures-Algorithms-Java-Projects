import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinkedQueue;
import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

// An immutable data type for computing shortest common ancestors.
public class ShortestCommonAncestor {
        private final Digraph G;



    // Construct a ShortestCommonAncestor object given a rooted DAG.
    public ShortestCommonAncestor(Digraph G) {
        if (G == null) {
           throw new NullPointerException("G is null");
        }
         if (false) {
           throw new IllegalArgumentException("G is not a rooted DAG");
        }
 
        this.G = new Digraph(G);
    }

   

    // Shortest common ancestor of vertices v and w.
    public int ancestor(int v, int w) {

        if (v < 0 || v >= G.V()) {
           throw new IndexOutOfBoundsException("v is out of bounds"); 
        } 

        if (w < 0 || w >= G.V()) {
           throw new IndexOutOfBoundsException("w is out of bounds"); 
        } 

        SeparateChainingHashST<Integer, Integer> vDistFrom  = distFrom(v);
        SeparateChainingHashST<Integer, Integer> wDistFrom  = distFrom(w);
        int minDistance = Integer.MAX_VALUE;
        int ancestor = -1;
        
        for (int x : wDistFrom.keys()){
            if (vDistFrom.contains(x)){
                int dist= vDistFrom.get(x) + wDistFrom.get(u);
                if (dist < minDistance){
                    minDistance = distance;
                    ancestor = x;
                }
            }
        }
        return ancestor;
     }
    

    // Length of the shortest ancestral path of vertex subsets A and B.
    public int length(Iterable<Integer> A, Iterable<Integer> B) {
        /*
         * make new triad by calling it get triad[0] distance between w and    ancestor and distance from
         * v and ancestor
         * add those things together
         */
       if (A == null)  {
            throw new NullPointerException("A is null");
       }
       if (B == null)  {
            throw new NullPointerException("B is null");
       }
       if (!A.iterator().hasNext())  {
            throw new IllegalArgumentException("A is empty");
       }
       if (!B.iterator().hasNext())  {
            throw new IllegalArgumentException("B is empty");
       }
       return distFrom(triad[1]).get(triad[0]) 
           +  distFrom(triad[2]).get(triad[0]);

    }

    // A shortest common ancestor of vertex subsets A and B.
    public int ancestor(Iterable<Integer> A, Iterable<Integer> B) {
       if (A == null)  {
            throw new NullPointerException("A is null");
       }
       if (B == null)  {
            throw new NullPointerException("B is null");
       }
       if (!A.iterator().hasNext())  {
            throw new IllegalArgumentException("A is empty");
       }
       if (!B.iterator().hasNext())  {
            throw new IllegalArgumentException("B is empty");
       }
       return triad(A,B)[0];
    }

    // Helper: Return a map of vertices reachable from v and their 
    // respective shortest distances from v.
    private SeparateChainingHashST<Integer, Integer> distFrom(int v) {
        
        SeparateChainingHashST<Integer, Integer> st =
             new SeparateChainingHashST<Integer, Integer>();
        LinkedQueue<Integer> q = new LinkedQueue<Integer>();
        st.put(v, 0);
        q.enqueue(v);  
        while (!q.isEmpty()){
            int x = q.dequeue();
            for (int y : G.adj(x)){
                if (!st.contains(y)){
                    st.put(y, st.get(x) + 1);
                    q.enqueue(y);
                }
            }
        }
        return st;
    }

    // Helper: Return an array consisting of a shortest common ancestor a 
    // of vertex subsets A and B, and vertex v from A and vertex w from B 
    // such that the path v-a-w is the shortest ancestral path of A and B.
    private int[] triad(Iterable<Integer> A, Iterable<Integer> B) {
      int minLength = Integer.MAX_VALUE;
      int ancestor = -1, v = -1, w = -1;
      for(int a : A) {
        for(int b : B){
             int l = length(a, b);
             if (l < minLength){
                 minLength = 1;
                 ancestor = ancestor(a, b);
                 v = a;
                 w = b;
            }
          }
       }
       return new int[] {ancestor, v, w};
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        ShortestCommonAncestor sca = new ShortestCommonAncestor(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sca.length(v, w);
            int ancestor = sca.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
