package OOProject;

import java.util.*;

public class DFSBegin2End extends GraphLinkedList{
	

    DFSBegin2End(int vertices) {
		super(vertices);
		// TODO Auto-generated constructor stub
	}
	// DFS algorithm
    void runDFS(int vertex) {
	    visited[vertex] = true;
	    stack.add(vertex);
	    if (vertex == vertices) {
	    	count++;
	    	for (int i:stack)
		    	System.out.print(i + " ");
	    	System.out.println();
	    	visited[vertex] = false;
		    stack.remove(stack.size() - 1);
	    	return;
	    }
	    Iterator<Integer> ite = adjLists[vertex].listIterator();
	    while (ite.hasNext()) {
	        int adj = ite.next();
	        if (!visited[adj])
	        	runDFS(adj);
	    }
	    visited[vertex] = false;
	    stack.remove(stack.size() - 1);
    }  
    void runDFS() {
    	runDFS(1);
    	if (count == 0){
    		System.out.println("No path!");
    	}
    	count = 0;
    }

    public static void main(String args[]) {
    	DFSBegin2End g = new DFSBegin2End(4);
	
	    /*g.addEdge(1, 2);
	    g.addEdge(1, 3);
	    g.addEdge(2, 3);
	    g.addEdge(3, 4);
	    g.addEdge(4, 2);
	    g.addEdge(2, 4);*/
	    g.addEdge(1, 2);
	    g.addEdge(3, 4);
	    ArrayList<Integer> arrayList = new ArrayList<>();
	    arrayList.add(1);
	    arrayList.add(3);
	    arrayList.add(2);
	    arrayList.remove(arrayList.size() - 1);
	    for (int i:arrayList)
	    	System.out.println(i + " ");
	    System.out.println("Following is Depth First Traversal");
	
	    g.runDFS();
    }
}
