import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

// Models an N-by-N percolation system.
public class Percolation {
    //Class level variables
    
    //size of 1 side of the percolation matrix
    private int size;
    
    //Source an Sink cells are needed for concluding if 
    //the system is Full,which is to define if it percolates.
    //the Source cell
    private int top;
    //the Sink cell
    private int bottom;
    //count of connected sites
    private int openSites;
    
    //boolean 2d array representing the matrix
    //for indicating opening potential status of each cell.
    private boolean [][] percMatrix;
    
    //instantiating an object
    //that will help either define if the 2 sites are connected
    //or, if not, establishes a connection between the sites.
    private WeightedQuickUnionUF unFind;
    
    //For corner-connected cells or cells being detached from open cells
    //altogether. No connection by side (no common side) with a neighbouring
    //cell;
    private WeightedQuickUnionUF backwash;
    //checks if size is a natural number
 
    
    // Create an N-by-N grid, with all sites blocked.
    public Percolation(int N) {
       this.size = N;
       //the 2d array will say whether the cell is Connnected or Not
       //by T and F values.
       this.percMatrix = new boolean [size][size];
       this.unFind = new WeightedQuickUnionUF(size * size + 2);
       this.backwash = new WeightedQuickUnionUF(size * size + 1);
       
       //encoding top and bottom with order values.
       this.top = 0;
       this.bottom = size * size + 1;
       //Requirements-check:if the size of 1 side is a natural number.
       if (N <= 0) {
           throw new IllegalArgumentException(
           "Size must be a positive number. Try again.");
        }
       
       //connect sites to Top and Bottom
       //for checking Fullness.
       for (int iter = 0; iter < size; iter++) {
           //connect top and bottom to first rows and last rows
           unFind.union(encode(0, iter), top);
           unFind.union(encode(size-1, iter), bottom);
           
           //for the backwash problem, connect only top
           backwash.union(encode(0, iter), top);
    }

}
    // Open site (i, j) if it is not open already.
    public void open(int i, int j) {
        
        //check if the indices to row/column are within the size range.
        if (i < 0 || j < 0 || i >= size || j >= size) {
            throw new IndexOutOfBoundsException("Wrong row/column index.");
        }
        
          //If not open,
          if (!isOpen(i, j)) {
          //open 
          percMatrix[i][j] = true;   
          openSites++;
        
        //Each side checks the corner cases and whether there is a cell
        //which to connnect to. Then it also checks whether the
        //nieghbouring cell is open.
        //These 2 conditions allow opening the site.
        
        //Left
        if (j -1 >= 0 && percMatrix[i][j-1]) {
           unFind.union(encode(i, j), encode(i, j-1));  
           backwash.union(encode(i, j), encode(i, j-1));  
        }
        
         //Right
        if (j + 1 < size  && percMatrix[i][j+1]) {
           unFind.union(encode(i, j), encode(i, j+1));  
           backwash.union(encode(i, j), encode(i, j+1));  
        }
        
         //Top
        if (i - 1 >= 0 && percMatrix[i-1][j]) {
           unFind.union(encode(i, j), encode(i-1, j));  
           backwash.union(encode(i, j), encode(i-1, j));  
        }
        
         //Bottom
        if (i + 1 < size  && percMatrix[i+1][j]) {
           unFind.union(encode(i, j), encode(i + 1, j));  
           backwash.union(encode(i, j), encode(i + 1, j));  
        }
        
    }

   }
    // Is site (i, j) open?
    public boolean isOpen(int i, int j) {
      //check if the indices to row/column are within the size range.
        if (i < 0 || j < 0 || i >= size || j >= size) {
            throw new IndexOutOfBoundsException("Wrong row/column index.");
        }
        
        if (!percMatrix[i][j]) {
             return false;
            }
        return true;
            
    }

    //Is site (i, j) full?
    public boolean isFull(int i, int j) {
    //check if the indices to row/column are within the size range.
        if (i < 0 || j < 0 || i >= size || j >= size) {
            throw new IndexOutOfBoundsException("Wrong row/column index.");
        }
        
    //Being full means being open and connected to the source.
      if (percMatrix[i][j] && unFind.connected(encode(i, j), top)) {
          return true;
        }

          return false;

    }

    //Number of open sites.
    public int numberOfOpenSites() {
        return openSites;
    }

    // Does the system percolate?
    //Percolates only if the sites are open from top to bottom, 
    //if there is an open path.
    public boolean percolates() {
        if (unFind.connected(top, bottom)) {
            return true;
        }
    return false;
    }

    // An integer ID (1...N) for site (i, j).
    private int encode(int i, int j) {
        return (i * size + (j +1));
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        //object of a percolation matrix is instantiated taking 
        //the size of side.
        Percolation perc = new Percolation(N);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
        }
        StdOut.println(perc.numberOfOpenSites() + " open sites");
        if (perc.percolates()) {
            StdOut.println("percolates");
        }
        else {
            StdOut.println("does not percolate");
        }
        
        // Check if site (i, j) optionally specified on the command line
        // is full.
        if (args.length == 3) {
            int i = Integer.parseInt(args[1]);
            int j = Integer.parseInt(args[2]);
            StdOut.println(perc.isFull(i, j));
        }
    }
}
