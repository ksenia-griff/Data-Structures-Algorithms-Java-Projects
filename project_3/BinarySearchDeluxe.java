import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;
import java.util.Comparator;

// Implements binary search for clients that may want to know the index of 
// either the first or last key in a (sorted) collection of keys.
public class BinarySearchDeluxe {
    // The index of the first key in a[] that equals the search key, 
    // or -1 if no such key.
     public static <Key> int firstIndexOf(Key[] a, Key key, 
                                         Comparator<Key> comparator) {
                                             
        if (a == null || key == null || comparator == null) {
            throw new NullPointerException(); 
        }
            
        if (a.length <= 0) { return -1; } 
        
        //if (comparator.compare(a[0], key) == 0) { return 0; }
        
        int lo = 0;    
        int hi = a.length - 1;    
        int index = -1;
        
        while (lo <= hi) {
            
           //int mid = (lo + (hi - lo)/2);
           int mid = (lo + hi)/2;
           if (comparator.compare(key, a[mid]) < 0) { hi = mid - 1; }
         
           else if (comparator.compare(key, a[mid]) > 0) { lo = mid + 1; }
           //if key is found, need check left if key is there too
           //since we need 1st occurence
           else if (comparator.compare(key, a[mid]) == 0) {
                         
                       index = mid;
                       hi = mid-1;
                       //return index; 
                    }
           
        }
        
        return index; 
    } 
            
        
   
    // The index of the last key in a[] that equals the search key, 
    // or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a, Key key, 
                                         Comparator<Key> comparator) {
        if (a == null || key == null || comparator == null) {
           throw new NullPointerException(); 
        }
        
        if (a.length <= 0) { return -1; } 
        
        //if (comparator.compare(a[0], key) == 0) { return 0; }
        
        int lo = 0;  
        int hi = a.length - 1;
        int index = -1;
        
        while (lo <= hi) {
           
            int mid = (lo + hi)/2;
           //int mid = (lo + (hi - lo)/2);
           
           if (comparator.compare(key, a[mid]) < 0) { hi = mid - 1; }
           
           else if (comparator.compare(key, a[mid]) > 0) { lo = mid + 1; }
          
           else if (comparator.compare(key, a[mid]) == 0) {           
                      
                       index = mid; 
                       lo = mid+1;
                      // return index;
           }
           
            
        } 
        
        return index;   
          
       }
    
    // Test client. [DO NOT EDIT]
     public static void main(String[] args) {
        String filename = args[0];
        //prefix is the query string
        String prefix = args[1];
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        
        for (int i = 0; i < N; i++) {
            long weight = in.readLong(); 
            in.readChar(); 
            String query = in.readLine(); 
            terms[i] = new Term(query.trim(), weight); 
        }
        
        Arrays.sort(terms);
        Term term = new Term(prefix);
        Comparator<Term> prefixOrder = Term.byPrefixOrder(prefix.length());
        int i = BinarySearchDeluxe.firstIndexOf(terms, term, prefixOrder);
        int j = BinarySearchDeluxe.lastIndexOf(terms, term, prefixOrder);
        StdOut.println(i);
        StdOut.println(j);
        int count = i == -1 && j == -1 ? 0 : j - i + 1;
        StdOut.println(count);
    }
}
