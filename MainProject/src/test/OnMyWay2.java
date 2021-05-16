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
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.swing_viewer.ViewPanel;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.Viewer.CloseFramePolicy;

public class OnMyWay2 extends GraphLinkedList{

	
	OnMyWay2(int vertices) {
		super(vertices);
		// TODO Auto-generated constructor stub
		Walked = new LinkedList[vertices + 1];
		vertexStack = new ArrayList<>();
		edgeStack = new ArrayList<>();
		stack2 = new ArrayList<>();
		signal = true;
	}
	
	public boolean getSignal() {
		return signal;
	}
	
	void runner() throws NoSuchElementException, IOException {
		//graph = new SingleGraph("Use");
    	graphDraw();
	}
	
	void clear() {
		for(int i = 1; i <= vertices; ++i) {
			visited[i] = false;
		}
		stack.clear();
		stack2.clear();
		vertexStack.clear();
		edgeStack.clear();
		graph = new SingleGraph("Use");
    	graphDraw();
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
			
			int templace = place;
			place = pl;
			stepForward();
			if (!signal)
				place = templace;
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
		while (vertexStack.size() != 0) {
			int temp = vertexStack.get(0);
			v[temp].setAttribute("ui.style", "shape:circle;fill-color: yellow;size: 30px;");
			v[temp].setAttribute("ui.label", Integer.toString(temp));
			vertexStack.remove(0);
		}
		
		while (edgeStack.size() != 0) {
			String tempString= edgeStack.get(0);
			edge=graph.getEdge(tempString);
			edge.setAttribute("ui.style", "fill-color: black; size: 0.8px;");
			edgeStack.remove(0);
		}
		
		
		if (stack.size() == 0) {
			stack.add(place);
			visited[place] = true;
			v[place].setAttribute("ui.style", "shape:circle;fill-color: green;size: 30px;");
			v[place].setAttribute("ui.label", Integer.toString(place));
		}
		else {
			String tempEdgeString = prePlace + " " + place;
			
		    if (isVisited(tempEdgeString)) {
		    	JOptionPane.showMessageDialog(null, "Sorry, You choose this way before", null, JOptionPane.INFORMATION_MESSAGE);
		    	signal = false;
		    }
		    else {
		    	signal = true;
				v[prePlace].setAttribute("ui.style", "shape:circle;fill-color: yellow;size: 30px;");
				v[prePlace].setAttribute("ui.label", Integer.toString(prePlace));
		    	stack2.add(tempEdgeString);
				stack.add(place);
				visited[place] = true;
				v[place].setAttribute("ui.style", "shape:circle;fill-color: green;size: 30px;");
				v[place].setAttribute("ui.label", Integer.toString(place));
	    		Edge edge=graph.getEdge(tempEdgeString);
	    		edge.setAttribute("ui.style", "fill-color: purple; size: 3px;");
		    }
		}
	}
	
	private void stepBack() {
		
		if (stack.size() > 1) {
			prePlace = stack.get(stack.size() - 2);
			v[prePlace].setAttribute("ui.style", "shape:circle;fill-color: green;size: 30px;");
			v[prePlace].setAttribute("ui.label", Integer.toString(prePlace));
			v[place].setAttribute("ui.style", "shape:circle;fill-color: red;size: 30px;");
			v[place].setAttribute("ui.label", Integer.toString(place));
			a = Integer.toString(stack.get(stack.size() - 2));
			b = Integer.toString(stack.get(stack.size() - 1));
			edge=graph.getEdge(a + " " + b);
			
			vertexStack.add(place);
			edgeStack.add(a + " " + b);
			stack2.remove(a + " " + b);
			
			edge.setAttribute("ui.style", "fill-color: red; size: 0.8px;");
			visited[place] = false;
			stack.remove(stack.size() - 1);
			place = stack.get(stack.size() - 1);
		}
		else if ((stack.size() == 1)) {
			visited[place] = false;
			stack.clear();
			v[place].setAttribute("ui.style", "shape:circle;fill-color: yellow;size: 30px;");
			v[place].setAttribute("ui.label", Integer.toString(place));
			
			while (edgeStack.size() != 0) {
				String tempString= edgeStack.get(0);
				edge=graph.getEdge(tempString);
				edge.setAttribute("ui.style", "fill-color: black; size: 0.8px;");
				edgeStack.remove(0);
			}
			while (vertexStack.size() != 0) {
				int temp = vertexStack.get(0);
				v[temp].setAttribute("ui.style", "shape:circle;fill-color: yellow;size: 30px;");
				v[temp].setAttribute("ui.label", Integer.toString(temp));
				vertexStack.remove(0);
			}
			stack2.clear();
			edgeStack.clear();
			vertexStack.clear();
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
	
	private boolean isVisited(String edge) {
		for (String i: stack2) {
			if (edge.equals(i))
				return true;
		}
		return false;
	}
	public LinkedList<Integer> getPlaceAdj(){
		return adjLists[place];
	}
}













