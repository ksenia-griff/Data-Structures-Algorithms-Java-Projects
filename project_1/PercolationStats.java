import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

// Estimates percolation threshold for an N-by-N percolation system.
public class PercolationStats {
    //Class level variables
    private Percolation percMatrix;
    private int experiments;
    private int size;
    //proportion of opened sites to all sites
    //in order to record the # of opened sites required 
    //to make system percolate
    private double [] quotas;
    
    // Perform T independent experiments (Monte Carlo simulations) on an 
    // N-by-N grid.
    public PercolationStats(int N, int T) {
        this.size = N;
        this.experiments = T;


       //Checking for illegal size of matrix and the number of experiments
       if (this.size <= 0 || this.experiments <= 0) {
          throw new IllegalArgumentException("Illegal argument.");
       }
       
        this.quotas = new double [experiments];
        for (int experiment = 0; experiment < experiments; experiment++) {
             Percolation percMatrix = new Percolation(this.size);      
             int openSites = 0;
    
                while (!percMatrix.percolates()) {
                      //Picking row/column at random
                      int i = StdRandom.uniform(0, size);
                      int j = StdRandom.uniform(0, size);
                      if (!percMatrix.isOpen(i, j)) {
                         percMatrix.open(i, j);
                         openSites++;
                        }
                }
                //record # of opened sites needed for 1 particular experiemnt
                double quota = (double) openSites / (size * size);
                quotas[experiment] = quota;
        }
        
    }
    
    // Sample mean of percolation threshold.
    public double mean() {
        return StdStats.mean(quotas);
    }

    // Sample standard deviation of percolation threshold.
    public double stddev() {
        return StdStats.stddev(quotas);
    }

    // Low endpoint of the 95% confidence interval.
    public double confidenceLow() {
        return mean() 
        - ((1.96 * stddev()) / Math.sqrt(experiments));
    }

    // High endpoint of the 95% confidence interval.
    public double confidenceHigh() {
        return mean() 
        + ((1.96 * stddev()) / Math.sqrt(experiments));
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        //size of side of a square matrix
        int N = Integer.parseInt(args[0]);
        //number of independent experiments
        int T = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(N, T);
        StdOut.printf("mean           = %f\n", stats.mean());
        StdOut.printf("stddev         = %f\n", stats.stddev());
        StdOut.printf("confidenceLow  = %f\n", stats.confidenceLow());
        StdOut.printf("confidenceHigh = %f\n", stats.confidenceHigh());
    }
}
