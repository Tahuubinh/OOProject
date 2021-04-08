package OOProject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import javax.swing.text.View;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

public class DFSArbitary  extends GraphLinkedList{
	

	DFSArbitary (int vertices) {
		super(vertices);
		// TODO Auto-generated constructor stub
	}
	// DFS algorithm
    void runDFS(int vertex, int end, boolean c) {
	    visited[vertex] = true;
	    stack.add(vertex);
	    
	    //Print results
	    if (vertex == end) {
	    	graph = new SingleGraph("Use");
	    	graphDraw();
	    	count++;
	    	for (int i:stack)
		    	System.out.print(i + " ");
	    	for (int i = 0; i < stack.size(); ++i) {
	    		
	    		//take the node
	    		int node_index_temp = stack.get(i);
	    		
	    		v[node_index_temp].setAttribute("ui.style", "shape:circle;fill-color: green;size: 30px; stroke-mode: plain;");
	    		
	    		if (i == stack.size() - 1)
	    			continue;
	    		int node_index_next = stack.get(i + 1);
	    		String a = Integer.toString(node_index_temp);
	    		System.err.println(a);
	    		String b = Integer.toString(node_index_next);
	    		System.err.println(b);
	    		Edge edge=graph.getEdge(a + " " + b);
	    		edge.setAttribute("ui.style", "fill-color: purple; size: 3px;");
	    	}
	    	graph.display();

			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			graph.setAttribute("ui.screenshot", "D:\\Projects\\Eclipse Projects\\gs-ui-swing\\src\\OOProject\\Pict\\"
								+Integer.toString(count)+".jpg");
	    	System.out.println();
	    	visited[vertex] = false;
		    stack.remove(stack.size() - 1);
	    	return;
	    }
	    //--------------------------------------------------------------------------------------------------------------------
	    Iterator<Integer> ite = adjLists[vertex].listIterator();
	    while (ite.hasNext()) {
	        int adj = ite.next();
	        if (!visited[adj])
	        	runDFS(adj, end, true);
	    }
	    visited[vertex] = false;
	    stack.remove(stack.size() - 1);
    }  
    void runDFS(int vertex, int end) {
    	runDFS(vertex, end, true);
    	if (count == 0){
    		System.out.println("No path!");
    	} 
    	//empty the stack here
    	stack.clear();
    	count = 0;
    }

    public static void main(String args[]) {
    	DFSArbitary  g = new DFSArbitary (4);
	
	    /*g.addEdge(1, 2);
	    g.addEdge(1, 3);
	    g.addEdge(2, 3);
	    g.addEdge(3, 4);
	    g.addEdge(4, 2);
	    g.addEdge(2, 4);*/
	    g.addEdge(1, 2);
	    g.addEdge(1, 3);
	    g.addEdge(2, 4);
	    g.addEdge(3, 4);
	    g.addEdge(2, 1);
	    ArrayList<Integer> arrayList = new ArrayList<>();
	    arrayList.add(1);
	    arrayList.add(3);
	    arrayList.add(2);
	    arrayList.remove(arrayList.size() - 1);
	    for (int i:arrayList)
	    	System.out.println(i + " ");
	    System.out.println("Following is Depth First Traversal");
	
	    g.runDFS(2, 4);
    }
}

