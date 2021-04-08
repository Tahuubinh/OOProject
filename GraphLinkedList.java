package OOProject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import org.graphstream.graph.Node;
import org.graphstream.graph.BreadthFirstIterator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.stream.file.FileSinkImages.OutputPolicy;
import org.graphstream.stream.file.FileSinkImages.OutputType;
import org.graphstream.ui.view.Viewer;

public class GraphLinkedList {
	protected LinkedList<Integer> adjLists[];
	protected boolean visited[];
	protected int vertices;
	protected ArrayList<Integer> stack;
	protected int count = 0;
	protected SingleGraph graph;
	protected Node[] v;
	// Graph creation
	GraphLinkedList(int vertices) {
		this.vertices = vertices;
		stack = new ArrayList<Integer>();
	    adjLists = new LinkedList[vertices + 1];
	    visited = new boolean[vertices + 1];
	    v = new Node[vertices + 1];
	    for (int i = 0; i < vertices + 1; i++)
	    	adjLists[i] = new LinkedList<Integer>();
	}

	// Add edges
	void addEdge(int src, int dest) {
		adjLists[src].add(dest);
	}
	public void graphDraw() {
		System.setProperty("org.graphstream.ui", "swing");
		graph = new SingleGraph("Use");
		for (int i = 1; i <= vertices; ++i) {
			graph.addNode(Integer.toString(i));
		}
		for (int i = 1; i <= vertices; ++i) {
			String iString = Integer.toString(i);
			if (adjLists[i].size() > 0) {
				for (int j: adjLists[i])
					graph.addEdge(iString + " " + Integer.toString(j), iString, Integer.toString(j), true);
			}
			v[i] = graph.getNode(iString);
		}
		
		for (int i = 1; i <= vertices; ++i) {
			v[i].setAttribute("ui.style", "shape:circle;fill-color: yellow;size: 30px;");
			v[i].setAttribute("ui.label", Integer.toString(i)); 
		}
		
		
		
	}
	void graphShow() {
		graphDraw();
		graph.display();
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		graph.setAttribute("ui.screenshot", "D:\\Projects\\Eclipse Projects\\gs-ui-swing\\src\\OOProject\\Pict\\d.jpg");

	}
	
}














