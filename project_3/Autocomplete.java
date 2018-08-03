import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;
import edu.princeton.cs.algs4.Quick3way;

// A data type that provides autocomplete functionality for a given set of 
// string and weights, using Term and BinarySearchDeluxe. 
public class Autocomplete {
    
    private Term[] terms;
    
    // Initialize the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        
        if (terms == null) { throw new NullPointerException(); }
        
        this.terms = new Term[terms.length];
        for (int i = 0; i < terms.length; i++) {
              this.terms[i] = terms[i];
              Quick3way.sort(this.terms);
         }
        
    }

    // All terms that start with the given prefix, in descending order of
    // weight.
    public Term[] allMatches(String prefix) {
        
        if (prefix == null) { throw new NullPointerException(); }
         
    	
    	int firstIndex = BinarySearchDeluxe.
    	  firstIndexOf(terms, new Term(prefix, 0), //KEY THAT NEEDS TO BE MATCHED, with Prefix as String
    	  Term.byPrefixOrder(prefix.length()));
    	
    	if (firstIndex == -1) {
    	    return new Term[0];
    	}
    	
    	int lastIndex  = BinarySearchDeluxe.
    	lastIndexOf(
    	     terms, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));
    	
    	//new array             
    	Term[] allmatches = new Term[1 + lastIndex - firstIndex];
    	
    	for (int i = 0; i < allmatches.length; i++) {
    	    //goes on for length of allmatches: exclusive term that is found in general terms array.
    	    //and is copied from terms into a separate array.
    	    allmatches[i] = terms[firstIndex++];
       }

    	Arrays.sort(allmatches, Term.byReverseWeightOrder());
    	
        return allmatches;
         
    }

    // The number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {
        
         if (prefix == null) { throw new NullPointerException(); }
         return 1
         + 
	   BinarySearchDeluxe.lastIndexOf(
	      terms, new Term(prefix, 0), Term.byPrefixOrder(prefix.length())) 
	   - 
           BinarySearchDeluxe.firstIndexOf(
              terms, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));
    }
    

    // Entry point. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        
        for (int i = 0; i < N; i++) {
            long weight = in.readLong(); 
            in.readChar(); 
            String query = in.readLine(); 
            terms[i] = new Term(query.trim(), weight); 
        }
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            for (int i = 0; i < Math.min(k, results.length); i++) {
                StdOut.println(results[i]);
            }
        }
    }
}
