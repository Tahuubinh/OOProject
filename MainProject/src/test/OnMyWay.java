package test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.swing_viewer.ViewPanel;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.Viewer.CloseFramePolicy;

public class OnMyWay extends GraphLinkedList{
	private ViewPanel view;
	private ArrayList<Integer> vertex = new ArrayList<Integer>();
      private int fakePlace;
      private int countEnd = 0;
      private ArrayList<Integer> predictNode = new ArrayList<Integer>();
      private ArrayList<String> predictEdge = new ArrayList<String>();
	OnMyWay(int vertices) {
		super(vertices);
		// TODO Auto-generated constructor stub
	}
	
	void runner(SingleGraph graph, ViewPanel view) throws NoSuchElementException, IOException {
		graphDraw1(graph);
    	this.view = view;
	}
	
	void clear() {
		for(int i = 1; i <= vertices; ++i) {
			visited[i] = false;
		}
		stack.clear();
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
			v[fakePlace].setAttribute("ui.style", "shape:circle;fill-color: green;size: 30px;");
		}
		
		if(i ==1 ) {
			place = pl;
			stepForward();
//			
//		}
//              // Them int i = 2;
//                else if(i == 2){
                   
                   
              }
		else {
			if(stack.size() == 0) {
				return;
			}
			place = stack.get(stack.size() -1);
			stepBack();
		}
		
	}
	void predictPath(int aim) {
//		System.out.println(place + " aim ");
		fakePlace = place;
        DFSforOMW(fakePlace, aim);
        v[fakePlace].setAttribute("ui.style", "shape:circle;fill-color: cyan;size: 30px;");
	}
       // them ham OnMyWay 4
	void clearPredictPath() {
		for(int i = 0; i < predictEdge.size(); ++i) {
//			System.out.println(predictEdge.get(i));
    		Edge edge=graph.getEdge(predictEdge.get(i));
    		edge.setAttribute("ui.style", "fill-color: black; size: 0.8px;");
        }
		for(int i = 1; i < stack.size(); ++i) {
			String a = stack.get(i-1) + " " + stack.get(i);
			Edge edge=graph.getEdge(a);
    		edge.setAttribute("ui.style", "fill-color: purple; size: 3px;");
		}
//		System.out.println("? " + predictEdge.size());
        predictEdge.clear();
        countEnd = 0;
	}
     private void DFSforOMW(int vertex, int end) {
	    visited[vertex] = true;
	    predictNode.add(vertex);
//	    System.out.println(vertex + " -> " + end);
	    if (vertex == end) {
	       
//	    	   for (int i = 1; i <= vertices; ++i) {
//	   			String iString = Integer.toString(i);
//	   			if (adjLists[i].size() > 0) {
//	   				for (int j: adjLists[i]) {
//	   					Edge edge=graph.getEdge(iString + " " + Integer.toString(j));
//	   					edge.setAttribute("ui.style", "fill-color: black; size: 1px;");
//	   				}
//	   			}
//	   			v[i] = graph.getNode(iString);
//	   		}
	   		
//	   		for (int i = 1; i <= vertices; ++i) {
//	   			v[i].setAttribute("ui.style", "shape:circle;fill-color: yellow;size: 30px;");
//	   			v[i].setAttribute("ui.label", Integer.toString(i)); 
//	   		}
	        
               // To mau tien doan              
	    	for (int i = 0; i < predictNode.size(); ++i) {
	    		
	    		//take the node
	    		int node_index_temp = predictNode.get(i);
//	    		System.out.print(node_index_temp + " ");
	    		visited[node_index_temp] = false;
//	    		if(i == 0){
//                          v[node_index_temp].setAttribute("ui.style", "shape:circle;fill-color: green;size: 30px;");
//                          v[node_index_temp].setAttribute("ui.label", Integer.toString(i));
//                        }
//                        else if(i > 0){
//                          v[node_index_temp].setAttribute("ui.style", "shape:circle;fill-color: purple;size: 30px; stroke-mode: plain;");  
//                        }  
	    		if (i == predictNode.size() - 1)
	    			continue;
	    		int node_index_next = predictNode.get(i + 1);
	    		String a = Integer.toString(node_index_temp);
	    		String b = Integer.toString(node_index_next);
	    		Edge edge=graph.getEdge(a + " " + b);
	    		edge.setAttribute("ui.style", "fill-color: red; size: 1px;");
	    		predictEdge.add(a+" "+b);
	    	}
	    	
              // thoi gian cho
               
                // Dat lai mau   
//                for (int i = 0; i < predictNode.size(); ++i) {
//	    		
//	    		//take the node
//	    		int node_index_temp = predictNode.get(i);
////	    		if(i == 0){
////                          v[node_index_temp].setAttribute("ui.style", "shape:circle;fill-color: green;size: 30px;");
////                          v[node_index_temp].setAttribute("ui.label", Integer.toString(i));
////                        
////                        }
////                        else if(i > 0){
////                          v[node_index_temp].setAttribute("ui.style", "shape:circle;fill-color: black;size: 30px;");  
////                          v[node_index_temp].setAttribute("ui.label", Integer.toString(i));  
////                        }  
//	    		if (i == predictNode.size() - 1)
//	    			continue;
//	    		int node_index_next = predictNode.get(i + 1);
//	    		String a = Integer.toString(node_index_temp);
//	    		String b = Integer.toString(node_index_next);
//	    		Edge edge=graph.getEdge(a + " " + b);
//	    		edge.setAttribute("ui.style", "fill-color: blue; size: 0.8px;");
//	    	}

//	    	System.out.println();
	    	visited[vertex] = false;
		predictNode.remove(predictNode.size() - 1);
	    	countEnd = 1;
//	    	System.out.println("??? " + predictNode.size());
                return;
	    }
	    //--------------------------------------------------------------------------------------------------------------------
	    Iterator<Integer> ite = adjLists[vertex].listIterator();
	    while (ite.hasNext()) {
	        int adj = ite.next();
	        if (!visited[adj])
	        	DFSforOMW(adj, end);
                        if(countEnd == 1) break;
                            
	    }
	    visited[vertex] = false;
	    predictNode.remove(predictNode.size() - 1);
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
	
	public ViewPanel getViewer() { //c?p nh?t d? th? m?i vào frame
//		SwingViewer viewer = new SwingViewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
//    	viewer.enableAutoLayout();
//        ViewPanel view = (ViewPanel) viewer.addDefaultView(false);
        
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
