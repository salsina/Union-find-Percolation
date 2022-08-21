public class Percolation {

    // creates n-by-n grid, with all sites initially blocked
    private int[] id;
    private int[] sz;
    private int[] opens;
    private int N;

    public Percolation(int n){
        id = new int[n*n+2];
        sz = new int[n*n+2];
        opens = new int[n*n+2];
        N = n;

        for (int i=0; i<n*n+2; i++){
            id[i] = i;
            sz[i] = 1;
            opens[i] = 0;
        }

        for(int i =1; i< n+1; i++){
            union(0, i);
            union(n*n + 1, n*n + 1 - i);
        }
    }

    private int root(int i){
        while (id[i] != i)
            i = id[i];
        return i;
    }

    private void union(int p, int q){
        int i = root(p);
        int j = root(q);
        if (i == j) return;
        if (sz[i] < sz[j]) { id[i] = j; sz[j] += sz[i]; }
        else { id[j] = i; sz[i] += sz[j]; }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        if (!isOpen(row, col)){
            int Id = (row - 1) * N + col;
            opens[Id] = 1;

            if(row > 1 && isOpen(row - 1, col))
                union(Id, Id - N);
            if(row < N && isOpen(row + 1, col))
                union(Id, Id + N);
            if(col > 1 && isOpen(row, col - 1))
                union(Id, Id - 1);
            if(col < N && isOpen(row, col + 1))
                union(Id, Id + 1);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        if (row < 1 || col < 1 || row > N || col > N)
            throw new IllegalArgumentException();

        return opens[(row - 1) * N + col] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        if (row < 1 || col < 1 || row > N || col > N)
            throw new IllegalArgumentException();

        return opens[(row - 1) * N + col] == 0;
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        int num = 0;
        for (int i = 1; i < opens.length -1 ; i++){
            if (opens[i] == 1)
                num += 1;
        }
        return num;
    }

    // does the system percolate?
    public boolean percolates(){
        return root(0) == root(N*N + 1);
    }

    // test client (optional)
    public static void main(String[] args){}
}