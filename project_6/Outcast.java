import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

// An immutable data type for outcast detection.
public class Outcast {
    
    private final WordNet wordnet;

    // Construct an Outcast object given a WordNet object.
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    // The outcast noun from nouns.
    public String outcast(String[] nouns) {
        
       
        String outcast = "";
        int maxDistance = 0;
        
        for (int i = 0; i < nouns.length; i++) {
            int distance = 0;
            for (int j = 0; j < nouns.length; j++) {
                distance+=wordnet.distnace(nouns[i], nouns[j])
            }
            
            if (distance > maxDistance) {
                  maxDistance = distance;
                  outcast = nouns[i];
            }
        }
        return outcast;
    }
    
 

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println("outcast(" + args[t] + ") = "
                           + outcast.outcast(nouns));
        }
    }
}
