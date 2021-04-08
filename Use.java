package OOProject;

import org.graphstream.graph.Node;

import java.util.concurrent.TimeUnit;

import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.stream.file.FileSinkImages.OutputPolicy;
import org.graphstream.stream.file.FileSinkImages.OutputType;
import org.graphstream.ui.swing_viewer.ViewPanel;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

public class Use 
{
	public static void main(String args[])
	 {
		System.setProperty("org.graphstream.ui", "swing");
		SingleGraph graph = new SingleGraph("Use");
		graph.setAutoCreate(true);
        graph.setStrict(false);
		
		graph.addNode("1");
		graph.addNode("2");
		graph.addNode("3");
		graph.addNode("4");
		graph.addEdge("1 2", "1", "2",true);
		graph.addEdge("1 3", "1", "3",true);
		graph.addEdge("2 1", "2", "1",true);
		graph.addEdge("2 4", "2", "4",true);
		graph.addEdge("3 4", "3", "4",true);
		Node[] e = new Node[6];
		Node v1=graph.getNode("1");
		Node v2=graph.getNode("2");
		Node v3=graph.getNode("3");
		Node v4=graph.getNode("4");
		
		Edge e1=graph.getEdge("1 2");
		double speedMax = e1.getNumber("speedMax") / 130.0;
		e1.setAttribute("ui.style", "fill-color: purple;");
		
		
		v1.setAttribute("ui.style", "shape:circle;fill-color: yellow;size: 30px;");
		v1.setAttribute("ui.label", "1"); 
		v2.setAttribute("ui.style", "shape:circle;fill-color: yellow;size: 30px;");
		v2.setAttribute("ui.label", "2"); 
		v3.setAttribute("ui.style", "shape:circle;fill-color: yellow;size: 30px;");
		v3.setAttribute("ui.label", "3"); 
		v4.setAttribute("ui.style", "shape:circle;fill-color: yellow;size: 30px;");
		v4.setAttribute("ui.label", "4"); 
		//v6.setAttribute("ui.style", "shape:circle;fill-color: green;size: 30px; stroke-mode: plain;");
		
		try {
			TimeUnit.SECONDS.sleep(1);
			graph.display();
			TimeUnit.SECONDS.sleep(1);
			graph.setAttribute("ui.screenshot", "D:\\Projects\\Eclipse Projects\\gs-ui-swing\\src\\OOProject\\Pict\\c.jpg");
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		/*try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}*/
		graph = new SingleGraph("Use");
		graph.setAutoCreate(true);
        graph.setStrict(false);
		
		graph.addNode("1");
		graph.addNode("2");
		graph.addNode("3");
		graph.addNode("4");
		graph.addEdge("1 2", "1", "2",true);
		graph.addEdge("1 3", "1", "3",true);
		graph.addEdge("2 1", "2", "1",true);
		graph.addEdge("2 4", "2", "4",true);
		graph.addEdge("3 4", "3", "4",true);
		e = new Node[6];
		v1=graph.getNode("1");
		v2=graph.getNode("2");
		v3=graph.getNode("3");
		v4=graph.getNode("4");
		
		e1=graph.getEdge("1 2");
		speedMax = e1.getNumber("speedMax") / 130.0;
		e1.setAttribute("ui.style", "fill-color: purple;");
		
		v1.setAttribute("ui.style", "shape:circle;fill-color: yellow;size: 30px;");
		v1.setAttribute("ui.label", "1"); 
		v2.setAttribute("ui.style", "shape:circle;fill-color: yellow;size: 30px;");
		v2.setAttribute("ui.label", "2"); 
		v3.setAttribute("ui.style", "shape:circle;fill-color: yellow;size: 30px;");
		v3.setAttribute("ui.label", "3"); 
		v4.setAttribute("ui.style", "shape:circle;fill-color: yellow;size: 30px;");
		v4.setAttribute("ui.label", "4"); 
		//v6.setAttribute("ui.style", "shape:circle;fill-color: green;size: 30px; stroke-mode: plain;");
		v4.setAttribute("ui.style", "shape:circle;fill-color: green;size: 30px; stroke-mode: plain;");
		try {
			TimeUnit.SECONDS.sleep(1);
			graph.display();
			TimeUnit.SECONDS.sleep(1);
			graph.setAttribute("ui.screenshot", "D:\\Projects\\Eclipse Projects\\gs-ui-swing\\src\\OOProject\\Pict\\d.jpg");
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}
}




