package test;

import java.awt.desktop.SystemSleepEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import org.graphstream.graph.Node;
import org.graphstream.graph.BreadthFirstIterator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.stream.file.FileSinkImages.LayoutPolicy;
import org.graphstream.stream.file.FileSinkImages.OutputPolicy;
import org.graphstream.stream.file.FileSinkImages.OutputType;
import org.graphstream.stream.file.images.Resolutions;
import org.graphstream.ui.swing.util.SwingFileSinkImages;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.Viewer.CloseFramePolicy;

public class GraphLinkedList{
	protected LinkedList<Integer> adjLists[];
	protected boolean visited[];
	protected int vertices;
	protected ArrayList<Integer> stack;
	protected int count = 0;
	protected SingleGraph graph;
	protected Node[] v;

	protected Edge edge;
	private String path;
	protected ArrayList<Integer> vertexStack;
	protected ArrayList<String> edgeStack;
	protected ArrayList<String> stack2;
	protected int place = 1;
	protected int prePlace = 0;
	protected String command;
	protected Scanner scanner;
	protected Scanner sc;
	protected Iterator<Integer> ite;
	protected String a;
	protected String b;
	protected ArrayList<Integer> vertex = new ArrayList<Integer>();
	protected LinkedList<Integer> Walked[];
	protected boolean signal;
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

	GraphLinkedList(int vertices, Graph graph) {
		this.vertices = vertices;
		this.graph = (SingleGraph) graph;
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
	// vẽ đồ thị
	void graphDraw() {
		System.setProperty("org.graphstream.ui", "swing");
		graph = new SingleGraph("Use");
		//graph.setAttribute( "ui.stylesheet", styleSheet );
		graph.setAttribute( "ui.antialias" );
		graph.setAttribute( "ui.quality" );
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
	
	void graphDraw1() {
		System.setProperty("org.graphstream.ui", "swing");
		//graph = new SingleGraph("Use");
		graph.setAttribute( "ui.stylesheet", styleSheet );
		graph.setAttribute( "ui.antialias" );
		graph.setAttribute( "ui.quality" );
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
	//xuất đồ thị dướng dạng file ảnh png
	void graphShow() throws IOException {
		graphDraw();
		
		
		 FileSinkImages pic = new SwingFileSinkImages(OutputType.PNG, Resolutions.VGA);
		 
		 pic.setLayoutPolicy(LayoutPolicy.COMPUTED_FULLY_AT_NEW_IMAGE);
		 try {
		 pic.writeAll(graph, "pic_graph\\" + path + ".png");
		 } catch (IOException e) {
			// TODO: handle exception
			 e.printStackTrace();
		}

	}
	void graphDrawDFS() {
		FileSinkImages pic = new SwingFileSinkImages(OutputType.PNG, Resolutions.VGA);
		 
		 pic.setLayoutPolicy(LayoutPolicy.COMPUTED_FULLY_AT_NEW_IMAGE);
		 try {
		 pic.writeAll(graph, "pic_graph\\" + path + ".png");
		 } catch (IOException e) {
			// TODO: handle exception
			 e.printStackTrace();
		}
	}
	// nhận tên của graph để xuất ảnh với tên tương ứng
	public void graphString(String path) throws IOException {
		this.path = path;
		graphShow();
	}
	private String styleSheet = 
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














