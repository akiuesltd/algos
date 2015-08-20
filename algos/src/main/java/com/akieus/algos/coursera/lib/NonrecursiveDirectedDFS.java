package com.akieus.algos.coursera.lib; /******************************************************************************
 *  Compilation:  javac com.akieus.algos.coursera.lib.NonrecursiveDirectedDFS.java
 *  Execution:    java com.akieus.algos.coursera.lib.NonrecursiveDirectedDFS digraph.txt s
 *  Dependencies: com.akieus.algos.coursera.lib.Digraph.java com.akieus.algos.coursera.lib.Queue.java com.akieus.algos.coursera.lib.Stack.java com.akieus.algos.coursera.lib.StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/42directed/tinyDG.txt
 *
 *  Run nonrecurisve depth-first search on an directed graph.
 *  Runs in O(E + V) time.
 *
 *  Explores the vertices in exactly the same order as com.akieus.algos.coursera.lib.DirectedDFS.java.
 *
 *
 *  % java com.akieus.algos.coursera.lib.NonrecursiveDirectedDFS tinyDG.txt 1
 *  1
 *
 *  % java com.akieus.algos.coursera.lib.NonrecursiveDirectedDFS tinyDG.txt 2
 *  0 1 2 3 4 5
 *
 ******************************************************************************/

import java.util.Iterator;

/**
 *  The <tt>com.akieus.algos.coursera.lib.NonrecursiveDirectedDFS</tt> class represents a data type for finding
 *  the vertices reachable from a source vertex <em>s</em> in the digraph.
 *  <p>
 *  This implementation uses a nonrecursive version of depth-first search
 *  with an explicit stack.
 *  The constructor takes time proportional to <em>V</em> + <em>E</em>,
 *  where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 *  It uses extra space (not including the digraph) proportional to <em>V</em>.
 *  <p>
 *  For additional documentation, see <a href="/algs4/42digraph">Section 4.2</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class NonrecursiveDirectedDFS {
    private boolean[] marked;  // marked[v] = is there an s->v path?
    /**
     * Computes the vertices reachable from the source vertex <tt>s</tt> in the digraph <tt>G</tt>.
     * @param G the digraph
     * @param s the source vertex
     */
    public NonrecursiveDirectedDFS(Digraph G, int s) {
        marked = new boolean[G.V()];

        // to be able to iterate over each adjacency list, keeping track of which
        // vertex in each adjacency list needs to be explored next
        Iterator<Integer>[] adj = (Iterator<Integer>[]) new Iterator[G.V()];
        for (int v = 0; v < G.V(); v++)
            adj[v] = G.adj(v).iterator();

        // depth-first search using an explicit stack
        Stack<Integer> stack = new Stack<Integer>();
        marked[s] = true;
        stack.push(s);
        while (!stack.isEmpty()) {
            int v = stack.peek();
            if (adj[v].hasNext()) {
                int w = adj[v].next();
                // com.akieus.algos.coursera.lib.StdOut.printf("check %d\n", w);
                if (!marked[w]) {
                    // discovered vertex w for the first time
                    marked[w] = true;
                    // edgeTo[w] = v;
                    stack.push(w);
                    // com.akieus.algos.coursera.lib.StdOut.printf("dfs(%d)\n", w);
                }
            }
            else {
                // com.akieus.algos.coursera.lib.StdOut.printf("%d done\n", v);
                stack.pop();
            }
        }
    }

    /**
     * Is vertex <tt>v</tt> reachable from the source vertex <tt>s</tt>?
     * @param v the vertex
     * @return <tt>true</tt> if vertex <tt>v</tt> is reachable from the source vertex <tt>s</tt>,
     *    and <tt>false</tt> otherwise
     */
    public boolean marked(int v) {
        return marked[v];
    }

    /**
     * Unit tests the <tt>com.akieus.algos.coursera.lib.NonrecursiveDirectedDFS</tt> data type.
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        int s = Integer.parseInt(args[1]);
        NonrecursiveDirectedDFS dfs = new NonrecursiveDirectedDFS(G, s);
        for (int v = 0; v < G.V(); v++)
            if (dfs.marked(v))
                StdOut.print(v + " ");
        StdOut.println();
    }


}
