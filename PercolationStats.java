import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
   private double[] thresholds;
   private final double CONF = 1.96;
   private double mean;
   private double stdev;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
    	if(n < 1 || trials < 1) {
    		throw new IllegalArgumentException("Illegal entry");
    	}
    	thresholds = new double[trials];
    	simulate(n);
    }
    
    //run simulation
    private void simulate(int n) {
    	for(int x = 0; x < thresholds.length; x ++) {
    		Percolation percolation = new Percolation(n);
    		while(!percolation.percolates()) {
    			int random = StdRandom.uniform(n * n) + 1;
    			if (random % n == 0) {
    				int row = 0;
    				if (random/n == 0) {
    					row = 1;
    				}
    				else {
    					row = random/n;
    				}
    				percolation.open(row, n);	
    			}
    			else {
    			percolation.open((random/n) + 1, (random % n));
    			}
    		}
    		thresholds[x] = (double)percolation.numberOfOpenSites() / (n * n);
    		
    	}
    	this.mean = StdStats.mean(thresholds);
    	this.stdev = StdStats.stddev(thresholds);
    }
    // sample mean of percolation threshold
    public double mean() {
    	return this.mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
    	return this.stdev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
    	return mean - ((CONF * stdev)/Math.sqrt(thresholds.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
    	return mean() + ((CONF * stddev())/Math.sqrt(thresholds.length));
    }

   // test client (see below)
   public static void main(String[] args) {
	   PercolationStats percStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1])); 
	   System.out.println("mean = " +percStats.mean());
	   System.out.println("stdev = " +percStats.stddev());
	   System.out.println("95% confidence interval = " + "[" +percStats.confidenceLo() + ", " + percStats.confidenceHi() + "]");
   }

}