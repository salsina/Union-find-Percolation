import java.util.ArrayList;
import java.util.List;


public class PercolationStats {

    // perform independent trials on an n-by-n grid
    private int N, Trials;
    private Percolation percolation;
    private List<Integer> tests = new ArrayList<>();

    public PercolationStats(int n, int trials){
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();
        N = n;
        Trials = trials;


        for (int i=0; i<trials; i++) {
            percolation = new Percolation(N);
            int res = test();
            tests.add(res);
        }
    }

    private int test(){
        int max = N;
        int min = 1;
        for (int i=1; i<N*N+1; i++){
            int random_row = (int)Math.floor(Math.random()*(max-min+1)+min);
            int random_col = (int)Math.floor(Math.random()*(max-min+1)+min);
            while (percolation.isOpen(random_row, random_col)){
                random_row = (int)Math.floor(Math.random()*(max-min+1)+min);
                random_col = (int)Math.floor(Math.random()*(max-min+1)+min);
            }
            percolation.open(random_row, random_col);

            if (percolation.percolates())
                return i;
        }
        return N*N;
    }

    // sample mean of percolation threshold
    public double mean(){
        double sum = 0;
        for (int i =0; i< Trials; i++){
            sum += tests.get(i);
        }
        return (sum / Trials);
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        double standardDeviation = 0.0;

        double mean = mean();

        for(double num: tests) {
            standardDeviation += Math.pow(num - mean, 2);
        }

        return Math.sqrt(standardDeviation/Trials);
    }

    public static void main(String[] args){
        int n = Integer.parseInt(args[0]);
        PercolationStats ps = new PercolationStats(n, Integer.parseInt(args[1]));
        System.out.println("mean " + ps.mean()/ (n*n));
        System.out.println("std " + ps.stddev() / (n*n));

    }

}
