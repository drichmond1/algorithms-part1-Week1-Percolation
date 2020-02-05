

import edu.princeton.cs.algs4.WeightedQuickUnionUF;;

public class Percolation {
	private int [][] grid;
	private int numberOfOpenSites = 0;
	private WeightedQuickUnionUF wquf;
	

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
    	if (n < 1) {
    		throw new IllegalArgumentException("number of cells should be greater than 0");
    	}
    	grid = new int [n][n]; 
    	
    	//virtual vertices
    	wquf = new WeightedQuickUnionUF((n * n) + 2);
    	for (int x = 0; x < n ; x++) {
    		wquf.union( x, (n * n)); //top
    		wquf.union( x + (((n * n) - (n))), (n * n) + 1); //bottom
    	}
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
    	if (row < 1 || row > grid[0].length || col < 1 || col > grid[0].length ) {
    		throw new IllegalArgumentException("invalid site location");
    	}
    	
    	if (!isOpen(row,col)) {
    	grid[row - 1][col - 1] = 1;
    	
    	//union with left cell
    	if (col > 1 && isOpen(row, col -1)) {
    		wquf.union((col - 1) + (grid[0].length * (row - 1)), (col - 2) + (grid[0].length *( row - 1)));
    	}
    	
    	//union with top cell
    	if (row > 1 && isOpen(row -1, col)) {
    		wquf.union((col - 1) + (grid.length * (row - 1)) , (col - 1) + (grid.length * (row - 2)));
    	} 
    	
    	//union with right cell
    	if (col < grid[0].length && isOpen(row , col + 1)) {
    		wquf.union((col - 1) + (grid.length * (row - 1)) , col + (grid.length * (row - 1)));
    	} 
    	
    	//union with bottom cell
    	if (row < grid[0].length && isOpen(row + 1 , col)) {
    		wquf.union((col - 1) + (grid.length * (row - 1)) , (col - 1) + (grid.length * row));
    	} 
	
    	numberOfOpenSites ++;
    	}
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
    	if (row < 1 || row > grid[0].length || col < 1 || col > grid[0].length ) {
    		throw new IllegalArgumentException("invalid site location");
    	}
    	return grid[row - 1][col - 1] == 1  ;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
    	if (row < 1 || row > grid[0].length || col < 1 || col > grid[0].length ) {
    		throw new IllegalArgumentException("invalid site location");
    	}
    	
    	if (!isOpen(row,col)) {
    		return false;
    	}
    	System.out.println( wquf.find(grid.length * grid.length) + " and " + wquf.find(col + ((row - 1) * grid.length)));

    	return wquf.find(grid.length * grid.length) == wquf.find((col - 1) + ((row - 1) * grid.length));
    	
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
    	return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
    	if(numberOfOpenSites < 1) {
    		return false;
    	}
    	return wquf.find(grid.length * grid.length) == wquf.find((grid.length * grid.length) + 1);
    }
    public void print() {
    	for (int row = 0; row < grid.length; row ++) {
    		
    		for (int col = 0; col < grid[row].length; col ++) {
    			System.out.print(grid[row][col] + " ");
    		}
    		System.out.println();
    		
    	}
    	
    		for (int x = 0; x < ((grid.length * grid.length)+ 2); x ++) {
    		
    		System.out.println(wquf.find(x));
    		
    	}
    	
    	System.out.println("virtuals " + wquf.find(grid.length * grid.length) + " and " + wquf.find((grid.length * grid.length) + 1) );
    	
    }

    public static void main(String[] args) {
    	Percolation p = new Percolation(3);
    	//p.print();
    	p.open(1, 1);
    	p.open(2, 1);
    	p.open(3, 1);
    	p.open(3,3);
    	p.print();
    	System.out.println(p.isFull(3, 3));
    	System.out.println(p.percolates());
 	  }

}