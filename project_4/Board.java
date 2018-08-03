import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinkedQueue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;


// Models a board in the 8-puzzle game or its generalization.
public class Board {
    //int side; 
    //size of 2-array
    private int N;
    private int [] []arrayCopy;
    
    private int hamming;
    private int manhattan;
    // Construct a board from an N-by-N array of tiles, where 
    // tiles[i][j] = tile at row i and column j, and 0 represents the blank 
    // square.
    public Board(int[][] tiles) {
       
        for (int k = 0; k < N; k ++) {
            for (int l = 0; l < N; l++) {
                   arrayCopy[k][l] = tiles[k][l];
                }
        }
        this.arrayCopy = arrayCopy;
        this.N = this.arrayCopy.length;
        this.manhattan = 0;
        this.hamming = 0;

        int sought = 0;
         
        for (int k = 0; k < N; k ++) {
            for (int l = 0; l < N; l++) {

               //Hamming distance 
               //since all blocks that are not inplace are summed up,
               //we create a sum for all 1's. 1 stands for notInPlace.
               //0's position at 9th place doesn't count.
                 
                 //sought is a tilePos in other functions,
                 //"supposed" value of each cell.
                 sought = (k == N - 1 && l == N - 1) ? 0 : sought + 1;
                 //goalNum = (k * arrayCopy.length) + (l + 1);
                 int got = arrayCopy[k][l];
                
                //0 in 9th position is not 0
                
                if (sought != 0 && got != sought) {
                    hamming++;
                }
                //we don't count distance for 0
                if (got != 0) {
                   
                   //for ex, 5 in place of 3:
                   //Math.abs (5 - 1 / 3 - 0   -->4/3
                   //    + Math.abd( 5 - 1 / 3 - 2 -->4/1
                   // = 4/3 + 4 = 5 1/3???/
                   manhattan += Math.abs ((got - 1) / N - k)
                                   + Math.abs((got - 1) % N - l);
                }
            }
        }  
    }
            
    // Tile at row i and column j.
    public int tileAt(int i, int j) {
             return arrayCopy[i][j];
    }

    
    // Size of this board.
    public int size() {
        //count number of i-s or number of j-s
        //3 in out example
        return N;
    }

    // Number of tiles out of place.
    public int hamming() {
          return this.hamming;
    }
    
   
    
   
    // Sum of Manhattan distances between tiles and goal.
    public int manhattan() {
       return this.manhattan;
    }
    

    // Is this board the goal board?
    public boolean isGoal() {
        return manhattan == 0;
    }
    
    // Is this board solvable?
    public boolean isSolvable() {
        //if number of tiles is odd
        if (N % 2 != 0) {
        //if number of inversions ia odd
         if (inversions() % 2 != 0) {
            
            return false;
         }
       }
       else {
          if (((blankPos() - 1) / N + inversions()) % 2 == 0) {
            return false;
          }
       }
       return true;
    }

    // Does this board equal that?
    public boolean equals(Board that) {
            if (this == that) return true;
            if (this == null) return false;
            if (this.getClass() != that.getClass()) {
              return false;
            }
        
            for (int k = 0; k < this.N; k ++) {
            
                for (int l = 0; l < this.N; l++) {
                    if (that.arrayCopy[k][l] != this.arrayCopy[k][l]) {
                        return false;
                    }
                    
                }
            }
            return true;
    }

    // All neighboring boards.
    public Iterable<Board> neighbors() {
        
        LinkedQueue <Board> q = new LinkedQueue <Board>();
        int blankPos = blankPos();
        //it can be moved up by i coordinate
        int i = (blankPos - 1) / N, j = blankPos - 1 % N;
        //move up
        if (i - 1 >= 0) {
            int[][]clone = cloneTiles();
            int temp = clone[i - 1][j];
            clone[i - 1][j] = clone[i][j];
            clone[i][j] = temp;
            q.enqueue(new Board(clone));
           
        }
        //move right
        if (j + 1 < N) {
            int[][]clone = cloneTiles();
            int temp = clone[i][j+1];
            clone[i][j + 1] = clone[i][j];
            clone[i][j] = temp;
            q.enqueue(new Board(clone));
           
        }
        //move down
        if (i + 1 < N) {
            int[][]clone = cloneTiles();
            int temp = clone[i + 1][j];
            clone[i + 1][j] = clone[i][j];
            clone[i][j] = temp;
            q.enqueue(new Board(clone));
           
        }
        //move left
        //j is at least 1
        if (j - 1 >= 0) {
            //cloneArray
            int[][]clone = cloneTiles();
            int temp = clone[i][j-1];
            //moving left
            clone[i][j - 1] = clone[i][j];
            clone[i][j] = temp;
            q.enqueue(new Board(clone));
           
        }
        return q;
     }


    // String representation of this board.
    public String toString() {
        String s = this.N + "\n";
        for (int i = 0; i < this.N; i++) {
            for (int j = 0; j < this.N; j++) {
                s += String.format("%2d", arrayCopy[i][j]);
                if (j < this.N - 1) {
                    s += " ";
                }
            }
                if (i < this.N - 1) {
                    s += "\n";
                }
            }
        return s;
    }

    // Helper method that returns the position (in row-major order) of the 
    // blank (zero) tile.
    private int blankPos() {
        int blankPos = 0;         
        for (int k = 0; k < this.N; k ++) {
            for (int l = 0; l < this.N; l++) {
                //count at criss-cross of row/column
                blankPos++;
                if(arrayCopy[k][l] == 0) {
                    return blankPos;
                }
            }
        }
        //cannot be reached as there is always a 0 position in [][]array.
        return -1;
    }

    // Helper method that returns the number of inversions.
    private int inversions() {

        int inversions = 0;
        int tile1Pos = 0;
        
        for (int k = 0; k < arrayCopy.length; k ++) {
            
            for (int l = 0; l < arrayCopy.length; l++) {
                 
                 int square1 = arrayCopy[k][l];
                 
                 //keeps track of values 0 through 8
                 tile1Pos++;
                 
                 int tile2Pos = 0;
                
                 for (int i = 0; i < N; i ++) {
            
                      for (int j = 0; j < N; j++) {
                        
                          int square2 = arrayCopy[i][j];
                          tile2Pos++;

                          if (square1 != 0 && square2 != 0 && square1 < square2
                              && tile2Pos > tile1Pos) {
                                 inversions++;
                          }    
                      }
                 }
            }
        }
        return inversions;    
    }

    // Helper method that clones the tiles[][] array in this board and 
    // returns it.
    private int[][] cloneTiles() {
      int [][] clone = new int[N][N];
         for (int k = 0; k < N; k ++) {
            for (int l = 0; l < N; l++) {
                 clone[k][l] = arrayCopy[k][l];   
            }    
               
            }
            return clone;
         }
         
    

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] tiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board board = new Board(tiles);
        StdOut.println(board.hamming());
        StdOut.println(board.manhattan());
        StdOut.println(board.isGoal());
        StdOut.println(board.isSolvable());
        for (Board neighbor : board.neighbors()) {
            StdOut.println(neighbor);
        }
    }
}
