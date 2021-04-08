package OOProject;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

public class test {
	public static void main(String args[]) {
		System.setProperty("org.graphstream.ui", "swing");
		
		Graph graph = new SingleGraph("Tutorial 1");
		graph.setStrict(false);
		graph.setAutoCreate( true );
		
		
		graph.addNode("11");
		graph.addNode("12");
		graph.addNode("15");
		graph.addEdge("a", "11", "12", true);
		graph.addEdge("b", "12", "15", true);
		graph.addEdge("c", "15", "11", true);

		for(Node n:graph) {
			System.out.println(n.getId());
		}
		
		graph.edges().forEach(e -> {
			System.out.println(e.getId());
		});
		
		int n = graph.getNodeCount();
		int adjacencyMatrix[][] = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				adjacencyMatrix[i][j] = graph.getNode(i).hasEdgeBetween(j) ? 1 : 0;
		
		for (int[] row: adjacencyMatrix)
		{
			for (int cell: row)
				System.out.print(cell+"\t");
			System.out.println();
		}
		
		
		graph.display();
	}
}












