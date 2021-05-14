package TestWithFiveUnit;

import java.awt.Event;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

public class OnMyWayabc extends GraphLinkedList{

	private ArrayList<Integer> vertexStack;
	private ArrayList<String> edgeStack;
	private ArrayList<String> stack2;
	private int place = 1;
	private int prePlace = 0;
	private String command;
	private Scanner scanner;
	private Scanner sc;
	private Iterator<Integer> ite;
	private Edge edge;
	private String a;
	private String b;
	private ArrayList<Integer> vertex = new ArrayList<Integer>();
	private LinkedList<Integer> Walked[];
	HashMap<String, Integer> count = new HashMap<>(); // Count the times of edge that went
	private boolean signal;
	
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
		graph = new SingleGraph("Use");
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
		//graph = new SingleGraph("Use");
    	//graphDraw();
	}
	void addOption(int i, int pl) throws IOException { // i có 2 trạng thái là 1 và 0, 1 là đi tới, 0 là đi lùi, "pl" là tên đỉnh cần tiến tới
		if(stack.size() > 0) { //stack là mảng stack lưu các đỉnh đã ấn 
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
	
	
	public ViewPanel getViewer() { //cập nhật đồ thị mới vào frame
		SwingViewer viewer = new SwingViewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
    	viewer.enableAutoLayout();
        ViewPanel view = (ViewPanel) viewer.addDefaultView(false);
        
        return view;
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
}
