import java.util.*;

public class BellmanFord {
    static class Edge {
        int src, dest, weight;

        Edge(int src, int dest, int weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }
    }

    static class Graph {
        int V, E;
        Edge[] edges;

        Graph(int V, int E) {
            this.V = V;
            this.E = E;
            edges = new Edge[E];
        }

        void addEdge(int index, int src, int dest, int weight) {
            edges[index] = new Edge(src, dest, weight);
        }

        void bellmanFord(int src) {
            int[] dist = new int[V];
            Arrays.fill(dist, Integer.MAX_VALUE);
            dist[src] = 0;

            for (int i = 1; i < V; i++) { // Relax all edges |V| - 1 times
                for (Edge edge : edges) {
                    int u = edge.src, v = edge.dest, w = edge.weight;
                    if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]) {
                        dist[v] = dist[u] + w;
                    }
                }
            }

            // Check for negative-weight cycles
            for (Edge edge : edges) {
                int u = edge.src, v = edge.dest, w = edge.weight;
                if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]) {
                    System.out.println("Graph contains a negative-weight cycle");
                    return;
                }
            }

            printSolution(dist, src);
        }

        void printSolution(int[] dist, int src) {
            System.out.println("Vertex\tDistance from Source " + src);
            for (int i = 0; i < V; i++) {
                System.out.println(i + "\t\t" + (dist[i] == Integer.MAX_VALUE ? "∞" : dist[i]));
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of vertices: ");
        int V = scanner.nextInt();
        System.out.print("Enter the number of edges: ");
        int E = scanner.nextInt();

        Graph graph = new Graph(V, E);

        System.out.println("Enter edges in the format (source destination weight):");
        for (int i = 0; i < E; i++) {
            int src = scanner.nextInt();
            int dest = scanner.nextInt();
            int weight = scanner.nextInt();
            graph.addEdge(i, src, dest, weight);
        }

        System.out.print("Enter the source vertex: ");
        int src = scanner.nextInt();

        graph.bellmanFord(src);
        scanner.close();
    }
}
