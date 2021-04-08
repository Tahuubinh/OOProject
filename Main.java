package OOProject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.graphstream.graph.Node;
import org.graphstream.graph.BreadthFirstIterator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.stream.file.FileSinkImages.OutputPolicy;
import org.graphstream.stream.file.FileSinkImages.OutputType;

public class Main {
	public static void main(String args[]) {

        String data = "1 2 3\n"
        		+ "2 3 4\n"
        		+ "3 4";

        try {
            // Creates a Writer using FileWriter
            Writer output = new FileWriter("output.txt");


            // Writes string to the file
            output.write(data);

            // Closes the writer
            output.close();
        }

        catch (Exception e) {
            e.getStackTrace();
        }
        
     // Creates an array of character
        char[] array = new char[100];
        // Read file into an arraylist
    	ArrayList<String> listOfLines = new ArrayList<>(); 
        try {
        	BufferedReader bufReader = new BufferedReader(new FileReader("output.txt")); 
        	String line = bufReader.readLine(); 
        	while (line != null) {
        		listOfLines.add(line);
        		line = bufReader.readLine();
        	} 
        	bufReader.close();

        }

        catch(Exception e) {
            e.getStackTrace();
        }
        System.out.println("Data in the stream:");
        int size = listOfLines.size();
        int max = 0; // file index of the last vertex
        
        int[][] allIntArr = new int[size][];
        
        //Read to list of integers for each line
        for (int i = 0; i < size; i++) {
        	String[] arrOfStr = listOfLines.get(i).split(" ");
        	int arrOfStrlength = arrOfStr.length;
        	allIntArr[i] = new int[arrOfStrlength];
        	
        	for (int j = 0; j < arrOfStrlength; j++) {
        		allIntArr[i][j] = Integer.parseInt(arrOfStr[j]);
        	}
        	//find max
        	if (max < allIntArr[i][arrOfStrlength - 1])
        		max = allIntArr[i][arrOfStrlength - 1];
        }

        //graph
        DFSArbitary g = new DFSArbitary(max);
        for (int i = 0; i < size; i++) {
        	for (int j = 1; j < allIntArr[i].length; j++) {
        		g.addEdge(allIntArr[i][0], allIntArr[i][j]);
        	}
        }
        g.graphShow();
        
        g.runDFS(2, 4);
    }
}
