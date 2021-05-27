package test;

import java.awt.Cursor;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.graphstream.algorithm.APSP.APSPInfo;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.AttributeSink;
import org.graphstream.stream.ElementSink;
import org.graphstream.stream.Sink;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.stream.file.FileSinkImages.LayoutPolicy;
import org.graphstream.stream.file.FileSinkImages.OutputType;
import org.graphstream.stream.file.images.Resolutions;
import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.layout.Layout;
import org.graphstream.ui.swing.util.SwingFileSinkImages;
import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.swing_viewer.ViewPanel;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.Viewer.CloseFramePolicy;
import org.graphstream.ui.view.camera.Camera;
import org.graphstream.ui.view.util.InteractiveElement;

public class OnMyWayabc extends DFS{

	private static boolean mutex1;
	private String path1;
	private static int countDFS1;
	private static ViewPanel view;
	private static SwingViewer viewer;
	HashMap<String, Integer> count = new HashMap<>(); // Count the times of edge that went
	public String RandomPath=""; //D
	
	
	OnMyWayabc(int vertices) {
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
    	//graphDraw();
		graph = new SingleGraph("Use");
		graph.setAttribute( "ui.antialias" );
		graph.setAttribute( "ui.quality" );
//		graph.setAttribute( "ui.stylesheet", styleSheet );
		
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
    	viewer = new SwingViewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
    	viewer.enableAutoLayout();
    	
        view = (ViewPanel) viewer.addDefaultView(false);
//        
        

	}
	
	void clear() {
		for(int i = 1; i <= vertices; ++i) {
			visited[i] = false;
		}
		stack.clear();
		
		stack2.clear();
		vertexStack.clear();
		edgeStack.clear();

		graph.edges().forEach(edge -> {
			edge.setAttribute("ui.style", "fill-color: black; size: 1px;");
		});
//		graph.setAttribute( "ui.stylesheet", styleSheet );

		for(Node node: graph){
			node.setAttribute("ui.style", "shape:circle;fill-color: yellow;size: 30px;");
		}
		RandomPath = "";

	}
	void addOption(int i, int pl) throws IOException { // i có 2 trạng thái là 1 và 0, 1 là đi tới, 0 là đi lùi, "pl" là tên đỉnh cần tiến tới
		if(stack.size() > 0) { //stack là mảng stack lưu các đỉnh đã ấn 
			if(!adjLists[place].contains(pl)) {
				JOptionPane.showMessageDialog(null, "Can't move to node " + pl, "ERROR", JOptionPane.ERROR_MESSAGE);
				return;
			}
			prePlace = place; // nếu số đỉnh ở trong stack > 0, ta lưu đỉnh đã được tiến tới trước đỉnh "pl" và prePlace
		}
		
		if(i ==1 ) {
		
			int templace = place; // tamplace là vị trí prePlace
			place = pl; // đỉnh hiện tại = "pl"
			stepForward(); //tiến hành thủ tục đi tới
			if (!signal) //nếu không đi tới đc, vị trí quay lại vị trí templace
				place = templace;
		}
		else {
			return;
		}
	}
	
	public void tempEdgeString() {
		for(int i = 1; i <= vertices;++i) {
			for(int j = 0; j < adjLists[i].size(); ++j) {
				String tempEdgeString = i + " " + adjLists[i].get(j); // tên của cạnh được chọn
				count.put(tempEdgeString, 0);
				
			}
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
		
		
		if (stack.size() == 0) {// set thuộc tích cho đỉnh đầu tiên được chọn
			stack.add(place);
			visited[place] = true;
			v[place].setAttribute("ui.style", "shape:circle;fill-color: green;size: 30px;");
			v[place].setAttribute("ui.label", Integer.toString(place));
			tempEdgeString();
		}
		else {
			String tempEdgeString = prePlace + " " + place; // tên của cạnh được chọn

		    	signal = true;
				v[prePlace].setAttribute("ui.style", "shape:circle;fill-color: yellow;size: 30px;");
				v[prePlace].setAttribute("ui.label", Integer.toString(prePlace));
		    	stack2.add(tempEdgeString);
				stack.add(place);
				visited[place] = true;
				v[place].setAttribute("ui.style", "shape:circle;fill-color: green;size: 30px;");
				v[place].setAttribute("ui.label", Integer.toString(place));
				
	    		Edge edge=graph.getEdge(tempEdgeString);
                count.put(tempEdgeString,count.get(tempEdgeString)+1);
                if (count.get(tempEdgeString) > 1) edge.setAttribute("ui.label", count.get(tempEdgeString));
	    		edge.setAttribute("ui.style", "fill-color: purple; size: 3px;");
		}
	}
	
	boolean aa = true;
	public ViewPanel getViewer() { //cập nhật đồ thị mới vào frame
        
        return view;
	}
	
	public SwingViewer getSwingViewer() { //cập nhật đồ thị mới vào frame
        
        return viewer;
	}
	
	public SingleGraph getGraph() {
		return graph;
	}
	public ArrayList<Integer> getVertex() { //cập nhật các đỉnh có thể đi từ đỉnh đang đứng
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
	
	private boolean isVisited(String edge) { // kiểm tra xem cạnh đc đi chưa
		for (String i: stack2) {
			if (edge.equals(i))
				return true;
		}
		return false;
	}
	public LinkedList<Integer> getPlaceAdj(){
		return adjLists[place]; // nhận mảng của các phần tử mà đỉnh place có thể đi
	}
	public String getLabel() { // hiện thị đường đi vào văn bản và số lần đã đi qua cạnh đó
		String a = "";
		if(stack.size() > 1) 
			a = prePlace + "->" + place + " (" + count.get(prePlace + " " + place) + ")\n";
		return a;
	}
	public void takePicture(String s) {
		FileSinkImages pic = new SwingFileSinkImages(OutputType.PNG, Resolutions.VGA);
		 
		 pic.setLayoutPolicy(LayoutPolicy.COMPUTED_FULLY_AT_NEW_IMAGE);
		 try {
			 pic.writeAll(graph, "pic_graph\\" + s +".png");
		 } catch (IOException e) {
			// TODO: handle exception
			 e.printStackTrace();
		}
	}
	
	public static void capView(String result) {
		BufferedImage bi = new BufferedImage(view.getWidth(), view.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.createGraphics();
        view.print(g);
        g.dispose();
        try {
            ImageIO.write(bi, "png", new File("pic_graph\\"+result+".png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	
	void runDFS1(int vertex, int end, boolean c) {
	    visited[vertex] = true;
	    stack.add(vertex);
	    
	    //Print results
	    if (vertex == end) {
	    	graphPaint();
	    	countDFS1++;
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
		    		mutex1 = false;
		    	}
		    	 /*FileSinkImages pic = new SwingFileSinkImages(OutputType.PNG, Resolutions.VGA);
				 
				 pic.setLayoutPolicy(LayoutPolicy.COMPUTED_FULLY_AT_NEW_IMAGE);
				 try {
				 pic.writeAll(graph, "pic_graph\\" + path + "_" + Integer.toString(countDFS) +".png");
				 } catch (IOException e) {
					// TODO: handle exception
					 e.printStackTrace();
				}
				graph.setAttribute("ui.screenshot", "pic_graph/" + path + "_" + Integer.toString(countDFS)+ ".png");
				mutex = true;
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					capView(path + "_" + Integer.toString(countDFS));
				}*/
				//capView(path + "_" + Integer.toString(countDFS));
		    	int reply = JOptionPane.showConfirmDialog(null, "Do you want to save?", null, JOptionPane.YES_NO_CANCEL_OPTION);
		    	if (reply == JOptionPane.YES_OPTION) {
		    		capView(path1 + "_" + Integer.toString(countDFS1));
		    	}
		    	else if (reply == JOptionPane.NO_OPTION) {
		    	}
		    	else {
		    		countDFS1 = 150;
		    	}
        		
			
			 
	    	System.out.println();
	    	visited[vertex] = false;
		    stack.remove(stack.size() - 1);
	    	return;
	    }
	    //--------------------------------------------------------------------------------------------------------------------
	    Iterator<Integer> ite = adjLists[vertex].listIterator();
	    while (ite.hasNext()) {
	    	if(countDFS1 > 20)
                break;
	        int adj = ite.next();
	        if (!visited[adj])
	        	runDFS1(adj, end, true);
	    }
	    visited[vertex] = false;
	    stack.remove(stack.size() - 1);
    }  
    // chạy thuật DFS
    void runDFS1(int vertex, int end) {
    	runDFS1(vertex, end, true);
    	if (countDFS1 == 0){
    		JOptionPane.showMessageDialog(null, "No path!", "vertex " + vertex + " to vertex " + end, JOptionPane.INFORMATION_MESSAGE);
    	} 
    	else if (countDFS1 < 101){
    		JOptionPane.showMessageDialog(null, "There are " + countDFS1 + " path(s)", "vertex " + vertex + " to vertex " + end, JOptionPane.INFORMATION_MESSAGE);
    	}
    	else if (countDFS1 == 150){
    		JOptionPane.showMessageDialog(null, "Stop counting", "vertex " + vertex + " to vertex " + end, JOptionPane.INFORMATION_MESSAGE);
    	}
    	else {
    		JOptionPane.showMessageDialog(null, "There are more than 100 path(s)", "vertex " + vertex + " to vertex " + end, JOptionPane.INFORMATION_MESSAGE);
    	}
    	//empty the stack here
    	stack.clear();
    	countDFS1 = 0;
    	
    	
    }
    // path là tên của file ảnh của thuật DFS và vị trí lưu nó
    void runDFS1(int vertex, int end, String path) {
    	this.path1 = path;
    	runDFS1(vertex, end);

    }
    
    void graphPaint() {
		for (int i = 1; i <= vertices; ++i) {
			String iString = Integer.toString(i);
			if (adjLists[i].size() > 0) {
				for (int j: adjLists[i]) {
					Edge edge=graph.getEdge(iString + " " + Integer.toString(j));
					edge.setAttribute("ui.style", "fill-color: black; size: 1px;");
				}
			}
			v[i] = graph.getNode(iString);
		}
		
		for (int i = 1; i <= vertices; ++i) {
			v[i].setAttribute("ui.style", "shape:circle;fill-color: yellow;size: 30px;");
			v[i].setAttribute("ui.label", Integer.toString(i)); 
		}
		
	}
    public ArrayList<Integer> getStack() {
    	return this.stack;
    }
    
    public void setStack(int node) {
    	String edge = stack.get(stack.size() -1 ) + " " + node;
    	this.stack.add(node);
    	this.stack2.add(edge);
    }
	//D
//	void clearAuto(){
//		graph.edges().forEach(edge -> {
//			edge.setAttribute("ui.style", "fill-color: black; size: 1px;");
//		});
//
//		for(Node node: graph){
//			node.setAttribute("ui.style", "shape:circle;fill-color: yellow;size: 30px;");
//		}
//		RandomPath = "";
//	}
    public static String styleSheet = 
 			"graph {"+
			"	canvas-color: black;"+
			"		fill-mode: gradient-vertical;"+
			"		fill-color: black, #004;"+
			"		padding: 60px;"+
			"	}"+
			"node {"+
			"	shape: circle;"+
			"	size: 14px;"+
			"	fill-mode: gradient-radial;"+
			"	fill-color: #FFFA, #FFF0;"+
			"	stroke-mode: none;"+
			"	shadow-mode: gradient-radial;"+
			"	shadow-color: #FFF9, #FFF0;"+
			"	shadow-width: 10px;"+
			"	shadow-offset: 0px, 0px;"+
			"}"+
			"node:clicked {"+
			"	fill-color: #F00A, #F000;"+
			"}"+
			"node:selected {"+
			"	fill-color: #00FA, #00F0;"+
			"}"+
			"edge {"+
			"	shape: line;"+
			"	size: 1px;"+
			"	fill-color: #FFF3;"+
			"	fill-mode: plain;"+
			"	arrow-shape: none;"+
			"}"+
			"sprite {"+
			"	shape: circle;"+
			"	fill-mode: gradient-radial;"+
			"	fill-color: #FFF8, #FFF0;"+
			"}";
}










