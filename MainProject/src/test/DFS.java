package test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;
import javax.swing.text.View;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.stream.file.FileSinkImages.LayoutPolicy;
import org.graphstream.stream.file.FileSinkImages.OutputType;
import org.graphstream.stream.file.images.Resolutions;
import org.graphstream.ui.swing.util.SwingFileSinkImages;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.Viewer.CloseFramePolicy;

public class DFS extends GraphLinkedList{
	
	private String path;
	DFS (int vertices) {
		super(vertices);
		// TODO Auto-generated constructor stub
	}
	// DFS algorithm
    void runDFS(int vertex, int end, boolean c) {
	    visited[vertex] = true;
	    stack.add(vertex);
	    
	    //Print results
	    if (vertex == end) {
	    	graphDraw();
	    	count++;
	    	for (int i = 0; i < stack.size(); ++i) {
	    		
	    		//take the node
	    		int node_index_temp = stack.get(i);
	    		
	    		v[node_index_temp].setAttribute("ui.style", "shape:circle;fill-color: green;size: 30px; stroke-mode: plain;");
	    		
	    		if (i == stack.size() - 1)
	    			continue;
	    		int node_index_next = stack.get(i + 1);
	    		String a = Integer.toString(node_index_temp);
	    		String b = Integer.toString(node_index_next);
	    		Edge edge=graph.getEdge(a + " " + b);
	    		edge.setAttribute("ui.style", "fill-color: purple; size: 3px;");
	    	}
	    	 FileSinkImages pic = new SwingFileSinkImages(OutputType.PNG, Resolutions.VGA);
			 
			 pic.setLayoutPolicy(LayoutPolicy.COMPUTED_FULLY_AT_NEW_IMAGE);
			 try {
			 pic.writeAll(graph, "pic_graph\\" + path + "_" + Integer.toString(count) +".png");
			 } catch (IOException e) {
				// TODO: handle exception
				 e.printStackTrace();
			}
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
    // chạy thuật DFS
    void runDFS(int vertex, int end) {
    	runDFS(vertex, end, true);
    	if (count == 0){
    		JOptionPane.showMessageDialog(null, "No path!", "vertex " + vertex + " and vertex " + end, JOptionPane.INFORMATION_MESSAGE);
    	} 
    	//empty the stack here
    	stack.clear();
    	count = 0;
    	
    	
    }
    // path là tên của file ảnh của thuật DFS và vị trí lưu nó
    void runDFS(int vertex, int end, String path) {
    	this.path = path;
    	runDFS(vertex, end);

    }

}

