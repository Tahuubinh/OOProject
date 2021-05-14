package TestWithFiveUnit;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class MyGraph {
    Graph graph = new SingleGraph("MyGraph");
    HashMap<Integer, ArrayList<Integer>> allPath = new HashMap<>();    // Save all edge of the path
    ArrayList<ArrayList<Integer>> adjacencyGraph = new ArrayList<ArrayList<Integer>>();  // Save the data of graph
    ArrayList<Integer> currentPath = new ArrayList<>();

    HashMap<String, Integer> count = new HashMap<>(); // Count the times of edge that went
    int vertex = 0;
    int edge = 0;
    //boolean[] flag;   // Đánh dấu DFS

    public MyGraph() { }

    public void inputData() {
        Scanner input = null;
        File file = new File("output.txt");
        try {
            input = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        vertex = input.nextInt();  //Integer.parseInt(input.nextLine());
        edge = input.nextInt();        //Integer.parseInt(input.nextLine());

        for (int i = 0; i <= vertex; ++i) { // 0 -> 8
            adjacencyGraph.add(new ArrayList<Integer>());
        }

        for (int i = 0; i < edge; ++i) {
            int x = input.nextInt();  //Integer.parseInt(input.nextLine());
            int y = input.nextInt();  //Integer.parseInt(input.nextLine());
            String s = String.valueOf(x) + String.valueOf(y);
            count.put(s, 0);
            adjacencyGraph.get(x).add(y);
        }

        /*
        // Khởi tạo cho DFS
        flag = new boolean[vertex+1];
        for (int i = 1; i <= vertex; ++i) flag[i] = false;
        for (int i = 1; i <= vertex; ++i) {
            ArrayList<Integer> arrayList = new ArrayList<>();
            allPath.put(i, arrayList);
        }*/
    }

    /*
    public void DFS(int u) {
        currentPath.add(u);
        flag[u] = true;
        for (int j = 0; j < adjacencyGraph.get(u).size(); ++j) {
            int v = adjacencyGraph.get(u).get(j);
            if (flag[v] == false) {
                flag[v] = true;
                DFS(v);
                if (v == (vertex)) {
                    for (int i = 0; i < currentPath.size() - 1; ++i) {
                        ArrayList<Integer> arrayTemp = new ArrayList<>();
                        arrayTemp = allPath.get(currentPath.get(i));
                        if (!arrayTemp.contains(currentPath.get(i+1))) {
                            arrayTemp.add(currentPath.get(i+1));
                            allPath.put(currentPath.get(i), arrayTemp);
                        }
                    }
                }
                currentPath.remove(currentPath.size()-1);
                flag[v] = false;
            }
        }
    }*/

    public void drawGraph() {
        System.setProperty("org.graphstream.ui", "swing");
        for (int i = 1; i <= vertex; ++i) {
            graph.addNode(String.valueOf(i));
            graph.getNode(String.valueOf(i)).setAttribute("ui.style", "shape:circle; fill-color: yellow; size: 25px;");
            graph.getNode(String.valueOf(i)).setAttribute("ui.label", String.valueOf(i));
        }

        for (int i = 1; i <= vertex; ++i) {
            for (int j = 0; j < adjacencyGraph.get(i).size(); ++j) {
                String s = String.valueOf(i) + String.valueOf(adjacencyGraph.get(i).get(j));
                graph.addEdge(s, String.valueOf(i), String.valueOf(adjacencyGraph.get(i).get(j)), true);
                graph.getEdge(s).setAttribute("ui.style", "size: 2px;");
            }
        }

        graph.display();
    }

    public void simulation() {
        Scanner input = new Scanner(System.in);
        int u = 1; boolean flag = true;
        graph.getNode(String.valueOf(u)).setAttribute("ui.style", "shape:circle;fill-color: green;size: 25px;");
        while (flag) {
            if(adjacencyGraph.get(u).size() == 0 || u == vertex) {
                flag = false;
                System.out.println("Congratulation");
            } else {
                System.out.println("Select vertex: ");
                if (adjacencyGraph.get(u).size() == 0 && u != vertex) System.out.println("Not exist!!!");
                else {
                    for (int i = 0; i < adjacencyGraph.get(u).size(); ++i) {
                        int v = adjacencyGraph.get(u).get(i);
                        System.out.println("Select "+ v + ". " + u + " - " + v);
                    }
                    int choose = input.nextInt();
                    String s = String.valueOf(u) + String.valueOf(choose);
                    int tempCount = count.get(s);
                    ++tempCount;
                    count.put(s,tempCount);
                    graph.getNode(String.valueOf(choose)).setAttribute("ui.style", "shape:circle;fill-color: green;size: 25px;");
                    graph.getEdge(s).setAttribute("ui.style", "fill-color: rgb(102, 0, 255); size: 2px;");
                    if (tempCount != 1) graph.getEdge(s).setAttribute("ui.label", tempCount);
                    u = choose;
                }
            }
        }
    }

    /*
    public void simulationDFS() {
        DFS(1);
        Scanner input = new Scanner(System.in);
        int u = 1;
        int choose = 0;
        while (choose != vertex) {
            ArrayList<Integer> arrayTemp = new ArrayList<>();
            arrayTemp = allPath.get(u);
            for (int i = 0; i < arrayTemp.size(); ++i) {
                System.out.println(arrayTemp.get(i) + "." + u + " - " + arrayTemp.get(i));
            }
            choose = input.nextInt();
            String s = String.valueOf(u) + String.valueOf(choose);
            graph.getNode(String.valueOf(choose)).setAttribute("ui.style", "shape:circle;fill-color: green;size: 25px;");
            graph.getEdge(s).setAttribute("ui.style", "fill-color: rgb(102, 0, 255); size: 2px;");
            if (choose == vertex) System.out.println("Congratulation");;
            u = choose;
        }
    }*/

    // Ham TEST
    public static void main(String[] args) {
        MyGraph myGraph = new MyGraph();
        myGraph.inputData();
        myGraph.drawGraph();
        myGraph.simulation();
        //myGraph.simulationDFS();
    }
}
