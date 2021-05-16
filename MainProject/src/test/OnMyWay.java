package test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.swing_viewer.ViewPanel;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.Viewer.CloseFramePolicy;

public class OnMyWay extends GraphLinkedList{

	private ArrayList<Integer> vertex = new ArrayList<Integer>();
	OnMyWay(int vertices) {
		super(vertices);
		// TODO Auto-generated constructor stub
	}
	
	void runner() throws NoSuchElementException, IOException {
    	graphDraw();
	}
	
	void clear() {
		for(int i = 1; i <= vertices; ++i) {
			visited[i] = false;
		}
		stack.clear();
		
		for (String tempEdgeString: stack2) {
			Edge edge=graph.getEdge(tempEdgeString);
			edge.setAttribute("ui.style", "fill-color: black; size: 1px;");
		}
		
		stack2.clear();
		vertexStack.clear();
		edgeStack.clear();
		for(int i = 1; i <= vertices; ++i) {

        	v[i] = graph.getNode(Integer.toString(i));
        	v[i].setAttribute("ui.style", "shape:circle;fill-color: yellow;size: 30px;");
    		
        }
	}
	void addOption(int i, int pl) throws IOException {
		if(stack.size() > 0 && (i==1)) {
			if(!adjLists[place].contains(pl)) {
				JOptionPane.showMessageDialog(null, "Can't move to node " + pl, "ERROR", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		
		if(stack.size() > 0) {
			prePlace = place;
		}
		
		if(i ==1 ) {
			place = pl;
			stepForward();
			
		}
		else {
			if(stack.size() == 0) {
				return;
			}
			place =stack.get(stack.size() -1);
			stepBack();
		}
	}
	
	private void stepForward() {
		
		if (stack.size() == 0) {
			stack.add(place);
			visited[place] = true;
			v[place].setAttribute("ui.style", "shape:circle;fill-color: green;size: 30px;");
			v[place].setAttribute("ui.label", Integer.toString(place));
		}
		else {
			ite = adjLists[prePlace].listIterator();
			int countemp = 0;
		    while (ite.hasNext()) {
		        int adj = ite.next();
		        if (!visited[adj]) {
		        	countemp = 1;
		        }
		    }
		    if (countemp == 0)
		    	JOptionPane.showMessageDialog(null, "Sorry, there is not no way to choose!", null, JOptionPane.INFORMATION_MESSAGE);
		    else {
					if ((visited[place]) || !isAdjacent(stack.get(stack.size()-1), place)) {
						return;
					}
				stack.add(place);
				visited[place] = true;
				v[place].setAttribute("ui.style", "shape:circle;fill-color: green;size: 30px;");
				v[place].setAttribute("ui.label", Integer.toString(place));
				String a = Integer.toString(stack.get(stack.size() - 2));
	    		String b = Integer.toString(stack.get(stack.size() - 1));
	    		Edge edge=graph.getEdge(a + " " + b);
	    		edge.setAttribute("ui.style", "fill-color: purple; size: 3px;");
		    }
		}
	}
	
	private void stepBack() {
		
		if (stack.size() > 1) {
			v[place].setAttribute("ui.style", "shape:circle;fill-color: yellow;size: 30px;");
			v[place].setAttribute("ui.label", Integer.toString(place));
			a = Integer.toString(stack.get(stack.size() - 2));
			b = Integer.toString(stack.get(stack.size() - 1));
			edge=graph.getEdge(a + " " + b);
			edge.setAttribute("ui.style", "fill-color: black; size: 0.8px;");
			visited[place] = false;
			stack.remove(stack.size() - 1);
			place = stack.get(stack.size() - 1);
		}
		else {
			visited[place] = false;
			stack.clear();
			v[place].setAttribute("ui.style", "shape:circle;fill-color: yellow;size: 30px;");
			v[place].setAttribute("ui.label", Integer.toString(place));
		}
	}
	private boolean isAdjacent(int a, int b) {
		for (int i: adjLists[a]) {
			if (i == b) {
				return true;
			}
		}
		return false;
	}
	
	public ViewPanel getViewer() { //cập nhật đồ thị mới vào frame
		SwingViewer viewer = new SwingViewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
    	viewer.enableAutoLayout();
        ViewPanel view = (ViewPanel) viewer.addDefaultView(false);
        
        return view;
	}
	
	public ArrayList<Integer> getVertex() {
		vertex.clear();
		if(stack.size() > 0) {
		ite = adjLists[place].iterator();
			while (ite.hasNext()) {
		        int adj = ite.next();
		        if (!visited[adj]) {
		        	vertex.add(adj);
		        }
		    }
	
		}
		else {
			for(int i = 1; i <= vertices; ++i) {
				vertex.add(i);
			}
		}
		return vertex;
	}
	
}













