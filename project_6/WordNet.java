import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.DirectedCycle;

// An immutable WordNet data type.
public class WordNet {
    private final RedBlackBST<String, SET <Integer> st;
    private final RedBlackBST< Integer, String> rst;
    private final ShortestCommonAncestor sca;
    
    // Construct a WordNet object given the names of the input (synset and
    // hypernym) files.
    public WordNet(String synsets, String hypernyms) {
        
        if (synsets == null || hypernyms == null) {
            throw new NullPointerException(
            "Synset or hypernum filename string cannot be null.");
        }
    
       st = new RedBlackBST<String, SET<Integer>>();
       rst = new RedBlackBST<Integer, String>(); 
       int V = 0;
       In in = new In(synsets);
 
       while (!in.isEmpty()) {
            
            String line = in.readLine();
            String[] toks = line.split(",");
            V = Integer.parseInt(toks[0]);
            String[] synset = toks[1].split("\\s");

            for (String noun: synset) {  
               if(!st.contains(noun)) {
                 st.put(noun, new SET<Integer>()); 
               }
               st.get(noun).add(V);
            }
            rst.put(V, toks[1]);

        in.close();
        
        Digraph G = new Digraph(V+1);
        in = new In(hypernyms);  
        while (!in.isEmpty()) {
              String line = in.readLine();
              String[] toks = line.split(",");
              int v = Integer.parseInt(toks[0]);
              
              for (int i = 1; i < toks.length; i++) {
                   int w = Integer.parseInt(toks[i]);
                   G.addEdge(v,w);
              }
        }
        
      in.close();
      sca = new ShortestCommonAncesotr(G);

    // All WordNet nouns.
    public Iterable<String> nouns() {
        return st.keys();
    }

    // Is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) 
           throw new NullPointerException("Word string cannot be null.");
        return tree.contains(word);
    }

    // A synset that is a shortest common ancestor of noun1 and noun2.
    public String sca(String noun1, String noun2) {
       
        if (noun1 == null || noun2 == null)
          throw new NullPointerException
          ("NounA or NounB string cannot be null.");
          
        if (!isNoun(noun1) || !isNoun(noun2))
            throw new IllegalArgumentException
            ("NounA and NounB must both be WordNet nouns.");  
        return rst.get(sca.anscestor(st.get(noun1), st.get(noun2)));
   }

    // Distance between noun1 and noun2.
    public int distance(String noun1, String noun2) {
        
        if (noun1 == null || noun2 == null)
          throw new NullPointerException
          ("NounA or NounB string cannot be null.");
          
        if (!isNoun(noun1) || !isNoun(noun2))
            throw new IllegalArgumentException
            ("NounA and NounB must both be WordNet nouns.");
            
        return sca.length(st.get(noun1), st.get(noun2));
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        String word1 = args[2];
        String word2 = args[3];  
        
        int nouns = 0;
        
        for (String noun : wordnet.nouns()) {
            nouns++;
        }
        
        StdOut.println("# of nouns = " + nouns);
        StdOut.println("isNoun(" + word1 + ") = " + wordnet.isNoun(word1));
        StdOut.println("isNoun(" + word2 + ") = " + wordnet.isNoun(word2));
        StdOut.println("isNoun(" + (word1 + " " + word2) + ") = "
                       + wordnet.isNoun(word1 + " " + word2));
        StdOut.println("sca(" + word1 + ", " + word2 + ") = "
                       + wordnet.sca(word1, word2));
        StdOut.println("distance(" + word1 + ", " + word2 + ") = "
                       + wordnet.distance(word1, word2));
    }
}
