/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF full;
    private WeightedQuickUnionUF perc;
    private boolean[] open;
    private int size;
    private int noOpenedSite;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.size = n;
        this.full = new WeightedQuickUnionUF(size * size + 1);
        this.perc = new WeightedQuickUnionUF(size * size + 2);
        this.noOpenedSite = 0;
        this.open = new boolean[size * size + 2];
        open[0] = true; // vTop
        open[size * size + 1] = true; // vBottom
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            open[getCurrIndex(row, col)] = true;
            noOpenedSite++;
            if (row == 1) {
                full.union(getCurrIndex(row, col), 0);
                perc.union(getCurrIndex(row, col), 0);
            }
            if (row == size) {
                perc.union(getCurrIndex(row, col), size * size + 1);
            }
            if (row > 1 && isOpen(row - 1, col)) {
                full.union(getCurrIndex(row, col), getCurrIndex(row - 1, col));
                perc.union(getCurrIndex(row, col), getCurrIndex(row - 1, col));
            }
            if (row < size && isOpen(row + 1, col)) {
                full.union(getCurrIndex(row, col), getCurrIndex(row + 1, col));
                perc.union(getCurrIndex(row, col), getCurrIndex(row + 1, col));
            }
            if (col > 1 && isOpen(row, col - 1)) {
                full.union(getCurrIndex(row, col), getCurrIndex(row, col - 1));
                perc.union(getCurrIndex(row, col), getCurrIndex(row, col - 1));
            }
            if (col < size && isOpen(row, col + 1)) {
                full.union(getCurrIndex(row, col), getCurrIndex(row, col + 1));
                perc.union(getCurrIndex(row, col), getCurrIndex(row, col + 1));
            }
        }
        else {
            return;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return open[getCurrIndex(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return full.find(getCurrIndex(row, col)) == full.find(0);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.noOpenedSite;
    }

    // does the system percolate?
    public boolean percolates() {
        return perc.find(0) == perc.find(size * size + 1);
    }

    private int getCurrIndex(int row, int col) {
        if (row >= 1 && col >= 1 && row <= size && col <= size) {
            return size * (row - 1) + col;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
