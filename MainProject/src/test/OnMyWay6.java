package test;

//import org.apache.commons.math3.util.Pair;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.swing_viewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;
import java.util.Timer;


public class OnMyWay6 extends project{
    int signal = 0;
//    Graph graph = new SingleGraph("graph");

    // Các Swing UI dùng chung cho Bài 6 này.
//    ViewPanel view; // Hiển thị đồ thị
    JButton[] buttonBai6 = new JButton[max+1];
    JPanel panelSouthInPanelWest = new JPanel();
    JPanel panelSouthInPanelEast = new JPanel();
    JScrollPane panelScrollContainButton = new JScrollPane(panelSouthInPanelEast);
    JButton stopButton = new JButton("Stop"); // stop simulation graph
    JPanel panelEast = new JPanel();
    JFrame frameBai6 = new JFrame();
    JTable tableInScrollPane;
    JButton nextButton;

    String[] columns = {"Số thứ tự","Đường đi", "Trọng số"};
    DefaultTableModel model = new DefaultTableModel(columns,0);

    int answer; // Lựa chọn "Tự đông" = 0; "Thủ công" = 1

    // CTDL lưu dữ liệu đồ thị, lưu trọng số của cạnh
    HashMap<Integer, ArrayList<Integer>> adjacencyGraph = new HashMap<>(); // Danh sách các cạnh kề với Integer -> Dùng để lưu dữ liệu
    HashMap<String, Integer> weightGraph = new HashMap<>(); // Ví dụ: trọng số canh 2 -> 3 là 8 thì lưu vào hashmap là: ("23",8)

    // CTDL cho DFSBai6
    boolean[] flag = new boolean[max+1]; // Đánh dấu cho thuật toán DFS auto
    ArrayList<Integer> currentPath = new ArrayList<>(); // Lưu đường đi hiện tại của thuật DFS auto
    int countPath = 0; // Đếm số đường đi trong duyệt DFS
    HashMap<Integer, ArrayList<Integer>> allPathDFSBai6 = new HashMap<>();
    int dem = 1;

    // CTDL cho AnyPath
    int sumCost = 0; // Chi phí đường đi cua thuat AnyPath
    Stack<Integer> stack = new Stack<>(); // Dùng để lưu đường đi. Dùng CTDL này nhằm phục vụ cho việc "lùi lại"
    static int countTableOfAnyPath = 0;
    int xyz;
    public OnMyWay6(){
        // Đẩy dữ liệu vào HashMap adjacencyGraph
        for (int i = 0; i < size; ++i) {
            ArrayList<Integer> arrayTemp = new ArrayList<>();
            for (int j = 0; j < allIntArr[i].length; ++j) {
                if (j == 0) adjacencyGraph.put(allIntArr[i][j], arrayTemp);
                else arrayTemp.add(allIntArr[i][j]);
            }
        }

        // Set trong so cho graph

        // Khởi tạo lại cho thuật toán DFS (khởi tạo ban đầu)
        for (int i = 1; i <= max; ++i) {
            flag[i] = false;
        }
        countPath = 0;
        currentPath.clear();


        // Khởi tạo ban đầu cho anyPath()
        stack.clear();
        countTableOfAnyPath = 0;

        // drawGraph
//        drawGraph();
//        getViewer();
        view.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent mwe) {
                project.zoomGraphMouseWheelMoved(mwe, view);
            }
        });
        setWeightGraph();
        setUI();
    }

    public void setInitialDrawGraph() {
        for (int i = 1; i <= max; ++i) {
            graph.getNode(String.valueOf(i)).setAttribute("ui.style", "shape:circle; fill-color: yellow; size: 25px;");
        }

        Set<Integer> set = adjacencyGraph.keySet();
        for (Integer key:set) {
            for (int i = 0; i < adjacencyGraph.get(key).size(); ++i) {
                graph.getEdge(Integer.toString(key) + " " + Integer.toString(adjacencyGraph.get(key).get(i))).setAttribute("ui.style", "fill-color: black;size: 1px;");
            }
        }
    }

//    public void drawGraph() {
//        for (int i = 1; i <= max; ++i) {
//            graph.addNode(Integer.toString(i));
//            graph.getNode(String.valueOf(i)).setAttribute("ui.style", "shape:circle; fill-color: yellow; size: 25px;");
//            graph.getNode(Integer.toString(i)).setAttribute("ui.label", Integer.toString(i));
//        }
//
//        Set<Integer> set = adjacencyGraph.keySet();
//        for (Integer key:set) {
//            for (int i = 0; i < adjacencyGraph.get(key).size(); ++i) {
//                graph.addEdge(Integer.toString(key) + Integer.toString(adjacencyGraph.get(key).get(i)),
//                        Integer.toString(key), Integer.toString(adjacencyGraph.get(key).get(i)), true);
//                graph.getEdge(Integer.toString(key) + Integer.toString(adjacencyGraph.get(key).get(i))).setAttribute("ui.style", "size: 1px;");
//            }
//        }
//    }
//
//    public void getViewer() { //cập nhật đồ thị mới vào frame
//        SwingViewer viewer = new SwingViewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
//        viewer.enableAutoLayout();
//        view = (ViewPanel) viewer.addDefaultView(false);
//    }

    public void setWeightGraph() {
        JFrame frameWeightGraph = new JFrame();
        Set<Integer> set = adjacencyGraph.keySet();
        for (Integer key:set) {
            for (int i = 0; i < adjacencyGraph.get(key).size(); ++i) {
                int weight = 1 + (int)(Math.random() * ((99 - 1) + 1));
                weightGraph.put(Integer.toString(key) + Integer.toString(adjacencyGraph.get(key).get(i)),weight);
            }
        }
    }

    public void setUI() {
        frame.setVisible(false);
        frameBai6.setTitle("Bài 6");
        frameBai6.setLayout(new BorderLayout());

        ////////////////////////////// 1.Setup Panel West//////////////////////////////////
        JPanel panelWest = new JPanel();
        panelWest.setLayout(new BorderLayout());
        Toolkit tk = Toolkit.getDefaultToolkit();
        panelWest.setPreferredSize(new Dimension((int) (tk.getScreenSize().getWidth()/3), (int) tk.getScreenSize().getHeight()));
        panelWest.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        //panelWest.setBackground(Color.BLUE);
        //----------------------------1.1.Tiêu đề: LOG----------------------------------//

        JLabel labelNorthInPanelWest = new JLabel();
        labelNorthInPanelWest.setPreferredSize(new Dimension((int) (tk.getScreenSize().getWidth()/3), (int) tk.getScreenSize().getHeight()/10));
        labelNorthInPanelWest.setText("PATH LOG");
        labelNorthInPanelWest.setBackground(Color.GRAY);
        labelNorthInPanelWest.setOpaque(true);
        labelNorthInPanelWest.setFont(new Font("Calibri",Font.BOLD, 30));
        labelNorthInPanelWest.setHorizontalAlignment((int) JFrame.CENTER_ALIGNMENT);

        //------------------------------1.2.ScrollPane--------------------------------//
        // Bổ sung thêm chức năng tìm kiếm
        JPanel panelInPanelWest = new JPanel();
        //---------Scroll Pane
        tableInScrollPane = new JTable(model);
        //---------- Căn giữa cho Cell của Table
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for(int i = 0; i < 3; ++i) {
            tableInScrollPane.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        JScrollPane scrollPaneCenterInPanelWest = new JScrollPane(tableInScrollPane);
        scrollPaneCenterInPanelWest.setPreferredSize(new Dimension((int) (tk.getScreenSize().getWidth()/3) - 10, 650));
        //------- Hoan thanh Scroll Pane

        //---------Panel contain Label, TextField
        JPanel panelFind = new JPanel();

        JLabel labelFind = new JLabel();
        labelFind.setText("Find path: ");
        labelFind.setFont(new Font("Calibri",Font.BOLD, 20));

        JTextField textFieldFind = new JTextField();
        textFieldFind.setPreferredSize(new Dimension(300,20));
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(tableInScrollPane.getModel());
        String text = textFieldFind.getText();
        textFieldFind.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = textFieldFind.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = textFieldFind.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        tableInScrollPane.setRowSorter(rowSorter);

        panelFind.add(labelFind, BorderLayout.WEST);
        panelFind.add(textFieldFind, BorderLayout.CENTER);
        //----------Hoàn thành Panel contain Label, TextField

        panelInPanelWest.add(scrollPaneCenterInPanelWest, BorderLayout.CENTER);
        panelInPanelWest.add(panelFind, BorderLayout.SOUTH);
        //-------------------------1.3.Thanh cuối: chứa Menu, Clear,...-----------------------------//
        JButton clearButton = new JButton("Clear");
        JButton dataButton = new JButton("Data"); // khôi phục lại đồ thị ban đầu
        JButton menuButton = new JButton(); // quay lại frame chọn bài
        //JButton stopButton = new JButton("Stop"); // stop simulation graph
        menuButton.setBounds(10, 10, 208, 29);
        menuButton.setBackground(Color.CYAN);
        BufferedImage menuBf = null;
        try {
            menuBf = ImageIO.read(new File("label_button\\menu.png"));
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        Image menudImg = menuBf.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon menuImg = new ImageIcon(menudImg);
        menuButton.setIcon(menuImg);
        panelSouthInPanelWest.add(menuButton);
        panelSouthInPanelWest.add(clearButton);
        panelSouthInPanelWest.add(dataButton);
        panelSouthInPanelWest.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        //panelSouthInPanelWest.add(stopButton);

        panelWest.add(labelNorthInPanelWest, BorderLayout.NORTH);
        panelWest.add(panelInPanelWest, BorderLayout.CENTER);
        panelWest.add(panelSouthInPanelWest, BorderLayout.SOUTH);
        //-----------------------------------------------------------------------------------------//
        /////////////////////////////COMPLETE 1.Panel West//////////////////////////////////////

        /////////////////////////////2.Setup Panel East/////////////////////////////////////

        panelEast.setLayout(new BorderLayout());
        panelEast.setPreferredSize(new Dimension((int) (2*tk.getScreenSize().getWidth()/3), (int) tk.getScreenSize().getHeight()));
        panelEast.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        panelSouthInPanelEast.setLayout(new FlowLayout());
        panelScrollContainButton.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        //panelSouthInPanelEast.setBackground(Color.GRAY);

        panelEast.add(view, BorderLayout.CENTER);

        /////////////////////////////COMPLETE 2.Panel East////////////////////////////////////
        frameBai6.getContentPane().add(panelWest,BorderLayout.WEST);
        frameBai6.getContentPane().add(panelEast);

        frameBai6.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frameBai6.setResizable(true);
        frameBai6.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameBai6.setVisible(false);

        //////////////////////////////SETUP BUTTON in Panel West//////////////////////////////
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //frameBai6.setVisible(false);
                frameBai6.dispose();
                //Clear: Set status of graph return initial
                for (int i = 1; i <= max; ++i) {
                    flag[i] = false;
                }
                countPath = 0;
                currentPath.clear();


                // Khởi tạo ban đầu cho anyPath()
                stack.clear();
                countTableOfAnyPath = 0;
                for (int i = 1; i <= max; ++i) {
                    graph.getNode(String.valueOf(i)).setAttribute("ui.style", "shape:circle; fill-color: yellow; size: 25px;");
                }
                Set<Integer> set = adjacencyGraph.keySet();
                for (Integer key:set) {
                    for (int i = 0; i < adjacencyGraph.get(key).size(); ++i) {
                        graph.getEdge(Integer.toString(key) + " " +  Integer.toString(adjacencyGraph.get(key).get(i))).setAttribute("ui.style", "fill-color: black; size: 1px;");
                    }
                }

                frame.remove(view);
                frame.add(view);
                frame.repaint();
                frame.revalidate();
                //frame.pack();
                frame.setVisible(true);
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
//				frameBai6.dispose();
                model.setRowCount(0);
                for (int i = 1; i <= max; ++i) {
                    flag[i] = false;
                }
                countPath = 0;
                currentPath.clear();

                // Khởi tạo ban đầu cho anyPath()
                sumCost = 0;
                stack.clear();
                countTableOfAnyPath = 0;
                for (int i = 1; i <= max; ++i) {
                    graph.getNode(String.valueOf(i)).setAttribute("ui.style", "shape:circle; fill-color: yellow; size: 25px;");
                }
                Set<Integer> set = adjacencyGraph.keySet();
                for (Integer key:set) {
                    for (int i = 0; i < adjacencyGraph.get(key).size(); ++i) {
                        graph.getEdge(Integer.toString(key) + " " +  Integer.toString(adjacencyGraph.get(key).get(i))).setAttribute("ui.style", "fill-color: black; size: 1px;");
                    }
                }

                if (signal == 1) {
                    panelSouthInPanelWest.remove(nextButton);
                } else if (signal == 2) {
                    panelSouthInPanelWest.remove(stopButton);
                    panelSouthInPanelEast.removeAll();
                    panelSouthInPanelWest.updateUI();
                    panelSouthInPanelEast.updateUI();
                } else {
                    //do nothing
                }
                frameBai6.setExtendedState(JFrame.MAXIMIZED_BOTH);
                twoSelection();
            }
        });


        dataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame dataFrame = new JFrame();
                dataFrame.setBounds(420,30,600,700);
                dataFrame.setLayout(new BorderLayout());
                dataFrame.setTitle("Data Graph");
                // Label
                JLabel dataLabel = new JLabel();
                dataLabel.setPreferredSize(new Dimension(600, 70));
                dataLabel.setText("DATA GRAPH");
                dataLabel.setBackground(Color.GRAY);
                dataLabel.setOpaque(true);
                dataLabel.setFont(new Font("Calibri",Font.BOLD, 30));
                dataLabel.setHorizontalAlignment((int) JFrame.CENTER_ALIGNMENT);
                // Hoàn thành Label

                // Data Table
                String[] columns = {"Số thứ tự","Đỉnh bắt đầu - Đỉnh kết thúc", "Trọng số"};
                DefaultTableModel dataModel = new DefaultTableModel(columns, 0);
                int count = 0;
                Set<Integer> key = adjacencyGraph.keySet();
                for (Integer iter:key) {
                    for (int i = 0; i < adjacencyGraph.get(iter).size(); ++i) {
                        ++count;
                        int v = adjacencyGraph.get(iter).get(i);
                        String[] dataRow = {
                                Integer.toString(count),
                                "Cạnh " + iter + " - " + v,
                                Integer.toString(weightGraph.get(Integer.toString(iter) + Integer.toString(v)))
                        };
                        dataModel.addRow(dataRow);
                    }
                }
                JTable dataTable = new JTable(dataModel);
                JScrollPane dataScrollPane = new JScrollPane(dataTable);

                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(JLabel.CENTER);
                for(int i = 0; i < 3; ++i) {
                    dataTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                }
                // Hoàn thành Set up table

                // Chức năng tìm kiếm data trong data Table: Label | TextField
                JPanel panel = new JPanel();

                JLabel findLabel = new JLabel();
                findLabel.setText("Find data:");
                findLabel.setFont(new Font("Calibri",Font.BOLD, 20));

                JTextField dataFind = new JTextField();
                dataFind.setPreferredSize(new Dimension(400,20));
                TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(dataTable.getModel());
                String text = dataFind.getText();
                dataFind.getDocument().addDocumentListener(new DocumentListener(){
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        String text = dataFind.getText();

                        if (text.trim().length() == 0) {
                            rowSorter.setRowFilter(null);
                        } else {
                            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                        }
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        String text = dataFind.getText();

                        if (text.trim().length() == 0) {
                            rowSorter.setRowFilter(null);
                        } else {
                            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                        }
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                });
                dataTable.setRowSorter(rowSorter);
                panel.add(findLabel, BorderLayout.WEST);
                panel.add(dataFind, BorderLayout.CENTER);
                // Hoàn thành chức năng find Data


                // Add vào frame
                dataFrame.getContentPane().add(dataLabel, BorderLayout.NORTH);
                dataFrame.getContentPane().add(dataScrollPane, BorderLayout.CENTER);
                dataFrame.add(panel, BorderLayout.SOUTH);
                /*dataFrame.getContentPane().add(findLabel, BorderLayout.SOUTH);
                dataFrame.add(dataFind, BorderLayout.SOUTH);*/
                //dataFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                dataFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                dataFrame.setVisible(true);
            }
        });
    }

    public void twoSelection() {
        frameBai6.repaint();
        frameBai6.revalidate();
        frameBai6.pack();
        frameBai6.setVisible(true);
        frameBai6.setExtendedState(JFrame.MAXIMIZED_BOTH);
        String[] responses = {"Tự động", "Thủ công"};
        answer = JOptionPane.showOptionDialog(null,
                "Bạn muốn thực hiện như thế nào?",
                "Message",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                responses,
                0);
        // Nếu answer = 0: Tự động
        // Nếu answer = 1: Thủ công
        if (answer == 0) {
            JOptionPane.showMessageDialog(null, "Bắt đầu theo hướng dẫn sau đây!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            String nodeStart = null; int count = 0;
            do {
                if(count > 0 && Integer.parseInt(nodeStart) >= max) {
                    JOptionPane.showMessageDialog(null, "Không có đỉnh " + nodeStart + ". Vui lòng nhập lại!!!", "Lỗi!!!", JOptionPane.ERROR_MESSAGE);
                }
                nodeStart = JOptionPane.showInputDialog("Đỉnh bắt đầu là:");
                ++count;
            } while (Integer.parseInt(nodeStart) > max);
            int count2 = 0;
            // Nhập nodeStar - nodeEnd
            String nodeEnd = null;
            do {
                if(count2 > 0 && Integer.parseInt(nodeEnd) > max) {
                    JOptionPane.showMessageDialog(null, "Không có đỉnh " + nodeEnd + ". Vui lòng nhập lại!!!", "Lỗi!!!",JOptionPane.ERROR_MESSAGE);
                }
                if (count2 > 0 && Integer.parseInt(nodeEnd) == Integer.parseInt(nodeStart)){
                    JOptionPane.showMessageDialog(null, "Trùng với đỉnh bắt đầu là: " + nodeEnd + ". Vui lòng nhập lại!!!", "Lỗi!!!",JOptionPane.ERROR_MESSAGE);
                }
                nodeEnd = JOptionPane.showInputDialog("Đỉnh kết thúc là: ");
                ++count2;
            } while (Integer.parseInt(nodeEnd) > max || Integer.parseInt(nodeEnd) == Integer.parseInt(nodeStart));

            DFSBai6(Integer.parseInt(nodeStart), Integer.parseInt(nodeEnd));
            if (countPath > 0) {
                if (countPath == 1) JOptionPane.showMessageDialog(null, "Có duy nhất 1 đường đi thôi nhé!!!", "Thông báo", JOptionPane.PLAIN_MESSAGE);
                new javax.swing.Timer(1500, new ActionListener() {
                    int cnt = 0;
                    int sum = 0;
                    ArrayList<Integer> arrayListTemp = allPathDFSBai6.get(dem);
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (cnt == arrayListTemp.size()-2) {
                            graph.getNode(Integer.toString(arrayListTemp.get(cnt))).setAttribute("ui.style", "shape:circle;fill-color: green;size: 30px;");
                            graph.getNode(Integer.toString(arrayListTemp.get(cnt+1))).setAttribute("ui.style", "shape:circle;fill-color: green;size: 30px;");
                            graph.getEdge(String.valueOf(arrayListTemp.get(cnt)) + " " + String.valueOf(arrayListTemp.get(cnt+1))).setAttribute("ui.style", "fill-color: rgb(102, 0, 255); size: 2px;");
                            String[] data1 = {String.valueOf(cnt+1),
                                    "Cạnh " + String.valueOf(arrayListTemp.get(cnt)) + " - " + String.valueOf(arrayListTemp.get(cnt+1)),
                                    Integer.toString(weightGraph.get(String.valueOf(arrayListTemp.get(cnt)) + String.valueOf(arrayListTemp.get(cnt+1))))};
                            model.addRow(data1);
                            sum = sum + weightGraph.get(String.valueOf(arrayListTemp.get(cnt)) + String.valueOf(arrayListTemp.get(cnt+1)));
                            String[] data = {"------------------------------", "Tổng chi phí là: ", Integer.toString(sum)};
                            model.addRow(data);
                            ((javax.swing.Timer) e.getSource()).stop();
                            return;
                        }
                        graph.getNode(Integer.toString(arrayListTemp.get(cnt))).setAttribute("ui.style", "shape:circle;fill-color: green;size: 30px;");
                        graph.getEdge(String.valueOf(arrayListTemp.get(cnt)) + " " + String.valueOf(arrayListTemp.get(cnt+1))).setAttribute("ui.style", "fill-color: rgb(102, 0, 255); size: 2px;");
                        sum = sum + weightGraph.get(String.valueOf(arrayListTemp.get(cnt)) + String.valueOf(arrayListTemp.get(cnt+1)));
                        String[] data = {String.valueOf(cnt+1),
                                "Cạnh " + String.valueOf(arrayListTemp.get(cnt)) + " - " + String.valueOf(arrayListTemp.get(cnt+1)),
                                Integer.toString(weightGraph.get(String.valueOf(arrayListTemp.get(cnt)) + String.valueOf(arrayListTemp.get(cnt+1))))};
                        model.addRow(data);
                        panelEast.updateUI();
                        ++cnt;
                    }
                }).start();
                //JOptionPane.showMessageDialog(null, "Hết đường đi rồi nhé!!!", "Thông báo", JOptionPane.PLAIN_MESSAGE);

                if (countPath > 1) {
                    signal = 1;
                    nextButton = new JButton("Next path");
                    panelSouthInPanelWest.add(nextButton);
                    panelSouthInPanelWest.updateUI();
                    frameBai6.setExtendedState(JFrame.MAXIMIZED_BOTH);

                    nextButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            ++dem;
                            String[] responses1 = {"Mô phỏng", "In đường đi"};
                            int answer2 = JOptionPane.showOptionDialog(null,
                                    "Bạn muốn thực hiện như thế nào?",
                                    "Message",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    responses1,
                                    0);
                            if (answer2 == 0) {
                                if (dem <= countPath) {
                                    String[] dataX = {"------------------------------", "Đường đi tiếp theo", "------------------------------"};
                                    model.addRow(dataX);
                                    setInitialDrawGraph();

                                    new javax.swing.Timer(1500, new ActionListener() {
                                        int cnt = 0;
                                        int sum = 0;
                                        ArrayList<Integer> arrayListTemp = allPathDFSBai6.get(dem);
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            if (cnt == arrayListTemp.size()-2) {
                                                graph.getNode(Integer.toString(arrayListTemp.get(cnt))).setAttribute("ui.style", "shape:circle;fill-color: green;size: 30px;");
                                                graph.getNode(Integer.toString(arrayListTemp.get(cnt+1))).setAttribute("ui.style", "shape:circle;fill-color: green;size: 30px;");
                                                graph.getEdge(String.valueOf(arrayListTemp.get(cnt)) + " " + String.valueOf(arrayListTemp.get(cnt+1))).setAttribute("ui.style", "fill-color: rgb(102, 0, 255); size: 2px;");
                                                String[] data1 = {String.valueOf(cnt+1),
                                                        "Cạnh " + String.valueOf(arrayListTemp.get(cnt)) + " - " + String.valueOf(arrayListTemp.get(cnt+1)),
                                                        Integer.toString(weightGraph.get(String.valueOf(arrayListTemp.get(cnt)) + String.valueOf(arrayListTemp.get(cnt+1))))};
                                                model.addRow(data1);
                                                sum = sum + weightGraph.get(String.valueOf(arrayListTemp.get(cnt)) + String.valueOf(arrayListTemp.get(cnt+1)));
                                                String[] data = {"------------------------------", "Tổng chi phí là: ", Integer.toString(sum)};
                                                model.addRow(data);
                                                ((javax.swing.Timer) e.getSource()).stop();
                                                return;
                                            }
                                            graph.getNode(Integer.toString(arrayListTemp.get(cnt))).setAttribute("ui.style", "shape:circle;fill-color: green;size: 30px;");
                                            graph.getEdge(String.valueOf(arrayListTemp.get(cnt)) + " " + String.valueOf(arrayListTemp.get(cnt+1))).setAttribute("ui.style", "fill-color: rgb(102, 0, 255); size: 2px;");
                                            sum = sum + weightGraph.get(String.valueOf(arrayListTemp.get(cnt)) + String.valueOf(arrayListTemp.get(cnt+1)));
                                            String[] data = {String.valueOf(cnt+1),
                                                    "Cạnh " + String.valueOf(arrayListTemp.get(cnt)) + " - " + String.valueOf(arrayListTemp.get(cnt+1)),
                                                    Integer.toString(weightGraph.get(String.valueOf(arrayListTemp.get(cnt)) + String.valueOf(arrayListTemp.get(cnt+1))))};
                                            model.addRow(data);
                                            panelEast.updateUI();
                                            ++cnt;
                                        }
                                    }).start();
                                }
                                else {
                                    JOptionPane.showMessageDialog(null, "Hết đường đi rồi nhé!!!", "Thông báo", JOptionPane.PLAIN_MESSAGE);
                                }
                            }
                            else {
                                if (dem <= countPath) {
                                    String[] dataX = {"------------------------------", "Đường đi tiếp theo", "------------------------------"};
                                    model.addRow(dataX);
                                    setInitialDrawGraph();
                                    ArrayList<Integer> arrayListTemp = allPathDFSBai6.get(dem);

                                    int sum = 0;
                                    for (int i = 0; i < arrayListTemp.size()-1; ++i) {
                                        graph.getNode(Integer.toString(arrayListTemp.get(i))).setAttribute("ui.style", "shape:circle;fill-color: green;size: 30px;");
                                        graph.getEdge(String.valueOf(arrayListTemp.get(i)) + " " +  String.valueOf(arrayListTemp.get(i+1))).setAttribute("ui.style", "fill-color: rgb(102, 0, 255); size: 2px;");
                                        sum = sum + weightGraph.get(String.valueOf(arrayListTemp.get(i)) + String.valueOf(arrayListTemp.get(i+1)));
                                        String[] data = {String.valueOf(i+1),
                                                "Cạnh " + String.valueOf(arrayListTemp.get(i)) + " - " + String.valueOf(arrayListTemp.get(i+1)),
                                                Integer.toString(weightGraph.get(String.valueOf(arrayListTemp.get(i)) + String.valueOf(arrayListTemp.get(i+1))))};
                                        model.addRow(data);
                                        panelEast.updateUI();
                                        if (i == arrayListTemp.size() - 2) {
                                            graph.getNode(Integer.toString(arrayListTemp.get(i+1))).setAttribute("ui.style", "shape:circle;fill-color: green;size: 30px;");
                                        }
                                    }
                                    String[] data = {"------------------------------", "Tổng chi phí là: ", Integer.toString(sum)};
                                    model.addRow(data);
                                    JOptionPane.showMessageDialog(null, "Chi phí của đường đi là: " + Integer.toString(sum), "Thông báo", JOptionPane.PLAIN_MESSAGE);
                                }
                                else {
                                    JOptionPane.showMessageDialog(null, "Hết đường đi rồi nhé!!!", "Thông báo", JOptionPane.PLAIN_MESSAGE);
                                }
                            }
                        }
                    });
                }

            }
            else {
                String[] option = {"Menu", "Tiếp tục"};
                int thisOption = JOptionPane.showOptionDialog(null,
                        "Không tồn tại đường đi!!!!",
                        "Thông báo",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        option,
                        0);
                if (thisOption == 0) {
                    //frameBai6.setVisible(false);
                    frameBai6.dispose();
                    //Clear: Set status of graph return initial

                    frame.remove(view);
                    frame.add(view);
                    frame.repaint();
                    frame.revalidate();
                    //frame.pack();
                    frame.setVisible(true);
                }
                else {
                }
            }
        }
        else {  // Trường hợp: ans = 1: Thủ công
            signal = 2;
            panelSouthInPanelWest.add(stopButton);
            panelEast.add(panelScrollContainButton, BorderLayout.SOUTH);
            panelSouthInPanelWest.updateUI();
            frameBai6.setExtendedState(JFrame.MAXIMIZED_BOTH);

            stopButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    System.out.println("Count of listeners: " + ((JButton) e.getSource()).getActionListeners().length);
                    String[] respon = {"Tiếp tục", "Dừng"};
                    int thisOption = JOptionPane.showOptionDialog(null,
                            "Bạn thực sự muốn dừng ?",
                            "Thông báo",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            respon,
                            0);

                    if (thisOption == 1) {
                        while (((JButton) e.getSource()).getActionListeners().length > 0) {
                            stopButton.removeActionListener(this);
                        }
                        panelSouthInPanelEast.removeAll();
                        panelSouthInPanelEast.updateUI();
                        JOptionPane.showMessageDialog(null, "Chi phí của đường đi là: " + Integer.toString(sumCost), "Thông báo", JOptionPane.PLAIN_MESSAGE);
                        String[] data = {"------------------------------", "Tổng chi phí là: ", Integer.toString(sumCost)};
                        model.addRow(data);
                        frameBai6.setExtendedState(JFrame.MAXIMIZED_BOTH);
                        // Tô màu đã đi cho đường đi vừa rồi
                        int nodeTop1 = stack.pop();
                        int nodeTop2 = stack.pop();
                        graph.getNode(Integer.toString(nodeTop1)).setAttribute("ui.style", "shape:circle;fill-color: green;size: 30px;");
                        graph.getEdge(Integer.toString(nodeTop2) + " " + Integer.toString(nodeTop1)).setAttribute("ui.style", "fill-color: rgb(102, 0, 255); size: 2px;");
                        // Xoá stopButton
                        panelSouthInPanelWest.remove(stopButton);
                        panelSouthInPanelWest.updateUI();
                    }
                }
            });

            JOptionPane.showMessageDialog(null, "Bắt đầu bằng cách chọn node", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            anyPath();
        }
    }

    public void DFSBai6(int x, int y) {
        currentPath.add(x);
        if (x == y) {
            ++countPath;
            flag[x] = true;
            ArrayList<Integer> arrTemp = new ArrayList<>(currentPath);
            allPathDFSBai6.put(countPath, arrTemp);
        }
        flag[x] = true;
        if (adjacencyGraph.get(x) == null) { }
        else {
            for (int i = 0; i < adjacencyGraph.get(x).size(); ++i) {
                int v = adjacencyGraph.get(x).get(i);
                if (flag[v] == false) {
                    flag[v] = true;
                    DFSBai6(v,y);
                    currentPath.remove(currentPath.size()-1);
                    flag[v] = false;
                }
            }
        }
    }

    // Thủ công
    public void anyPath() {
        for (int i = 1; i <= max; ++i) {
            buttonBai6[i] = new JButton(String.valueOf(i));
            panelSouthInPanelEast.add(buttonBai6[i]);
        }
        panelEast.updateUI();

        for (xyz = 1; xyz <= max; ++xyz) {
            buttonBai6[xyz].addActionListener(new ActionListener() {
                int node = xyz;
                @Override
                public void actionPerformed(ActionEvent e) {
                    stack.push(node);
                    if (adjacencyGraph.get(node) == null) {  // Chọn vào node Lá
                        if (stack.size() == 1) {  // Chọn node lá ngay từ lần đầu tiên
                            String[] responses = {"Menu", "Lùi lại"};
                            int choose = JOptionPane.showOptionDialog(null,
                                    "Đó là node lá đấy! Không có đường đi đâu. Trở lại nhé!",
                                    "Thông báo",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    responses,
                                    0);
                            if (choose == 0) { // Trở về Menu
                                //frameBai6.setVisible(false);
                                frameBai6.dispose();
                                //Clear: Set status of graph return initial
                                for (int i = 1; i <= max; ++i) {
                                    flag[i] = false;
                                }
                                countPath = 0;
                                currentPath.clear();


                                // Khởi tạo ban đầu cho anyPath()
                                stack.clear();
                                countTableOfAnyPath = 0;
                                for (int i = 1; i <= max; ++i) {
                                    graph.getNode(String.valueOf(i)).setAttribute("ui.style", "shape:circle; fill-color: yellow; size: 25px;");
                                }
                                Set<Integer> set = adjacencyGraph.keySet();
                                for (Integer key:set) {
                                    for (int i = 0; i < adjacencyGraph.get(key).size(); ++i) {
                                        graph.getEdge(Integer.toString(key) + " " +  Integer.toString(adjacencyGraph.get(key).get(i))).setAttribute("ui.style", "fill-color: black; size: 1px;");
                                    }
                                }
                                frame.remove(view);
                                frame.add(view);
                                frame.repaint();
                                frame.revalidate();
                                //frame.pack();
                                frame.setVisible(true);
                            }
                            else { // Lùi lại
                                stack.pop();
                            }
                        }
                        else if (stack.size() == 2) { // Chọn node lá sau khi đi được 2 đỉnh
                            ++countTableOfAnyPath;
                            int nodeTop1 = stack.pop();
                            int nodeTop2 = stack.pop();
                            // Tô màu xanh cây cho nodeTop2
                            graph.getNode(Integer.toString(nodeTop2)).setAttribute("ui.style", "shape:circle;fill-color: green;size: 30px;");
                            // Tô màu tím cho nodeTop1. Đồng thời tô màu tím cho cạnh nodeTop2-nodeTop1
                            graph.getNode(Integer.toString(nodeTop1)).setAttribute("ui.style", "shape:circle;fill-color: violet;size: 30px;");
                            graph.getEdge(Integer.toString(nodeTop2) + " " + Integer.toString(nodeTop1)).setAttribute("ui.style", "fill-color: violet; size: 2px;");
                            // Cộng chi phí đường đi cho nodeTop1-nodeTop2
                            sumCost = sumCost + weightGraph.get(Integer.toString(nodeTop2) + Integer.toString(nodeTop1));
                            // Add vào table thông tin đường
                            String[] data = {Integer.toString(countTableOfAnyPath),
                                    "Cạnh " + nodeTop2 + " - " + nodeTop1,
                                    Integer.toString(weightGraph.get(Integer.toString(nodeTop2) + Integer.toString(nodeTop1)))};
                            model.addRow(data);
                            // Add node trở lại vào đường đi
                            stack.push(nodeTop2);
                            stack.push(nodeTop1);
                            // Đưa thông tin
                            String[] responses = {"Menu", "Dừng ", "Lùi lại"};
                            int choose = JOptionPane.showOptionDialog(null,
                                    "Đó là node lá đấy! Không có đường đi đâu. Trở lại nhé!",
                                    "Thông báo",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    responses,
                                    0);
                            if (choose == 0) { // Menu
                                //frameBai6.setVisible(false);
                                frameBai6.dispose();
                                //Clear: Set status of graph return initial
                                for (int i = 1; i <= max; ++i) {
                                    flag[i] = false;
                                }
                                countPath = 0;
                                currentPath.clear();


                                // Khởi tạo ban đầu cho anyPath()
                                stack.clear();
                                countTableOfAnyPath = 0;
                                for (int i = 1; i <= max; ++i) {
                                    graph.getNode(String.valueOf(i)).setAttribute("ui.style", "shape:circle; fill-color: yellow; size: 25px;");
                                }
                                Set<Integer> set = adjacencyGraph.keySet();
                                for (Integer key:set) {
                                    for (int i = 0; i < adjacencyGraph.get(key).size(); ++i) {
                                        graph.getEdge(Integer.toString(key) + " " +  Integer.toString(adjacencyGraph.get(key).get(i))).setAttribute("ui.style", "fill-color: black; size: 1px;");
                                    }
                                }
                                frame.remove(view);
                                frame.add(view);
                                frame.repaint();
                                frame.revalidate();
                                //frame.pack();
                                frame.setVisible(true);
                            }
                            else if(choose == 1) { // OK: Chỉ để màn hình xem thông tin thôi
                                // Thông báo chi phí
                                JOptionPane.showMessageDialog(null, "Chi phí của đường đi là: " + sumCost, "Thông báo", JOptionPane.PLAIN_MESSAGE);
                                // Add chi phí vào bảng
                                String[] data1 = {"", "Chi phí của đường đi là: " , Integer.toString(sumCost)};
                                model.addRow(data1);
                                panelSouthInPanelEast.removeAll();
                                panelSouthInPanelEast.updateUI();
                                panelSouthInPanelWest.remove(stopButton);
                                panelSouthInPanelWest.updateUI();
                            }
                            else {  // Lùi lại
                                int nodeTopTop1 = stack.pop();
                                int nodeTopTop2 = stack.pop();
                                // Tô màu lại cạnh vừa đi và node vừa đi qua trở lại trạng thái ban đầu: cạnh - đen, bode - vàng
                                graph.getNode(Integer.toString(nodeTopTop1)).setAttribute("ui.style", "shape:circle;fill-color: yellow;size: 30px;");
                                graph.getEdge(Integer.toString(nodeTopTop2) + " " + Integer.toString(nodeTopTop1)).setAttribute("ui.style", "fill-color: black;size: 1px;");
                                // Tô màu nodeTopTop2 lại màu tím
                                graph.getNode(Integer.toString(nodeTopTop2)).setAttribute("ui.style", "shape:circle;fill-color: violet;size: 30px;");
                                // trả lại sumCost
                                sumCost = sumCost - weightGraph.get(Integer.toString(nodeTopTop2) + Integer.toString(nodeTopTop1));
                                // Add vào PathLog câu: Trở lại đỉnh xxx, chỉ phí bao nhiêu
                                String[] data1 = {"------------------------------", "Trở lại đỉnh " + nodeTopTop2 , "------------------------------"};
                                String[] data2 = {"------------------------------", "Lúc đó, chi phí đường đi là: ", Integer.toString(sumCost)};
                                model.addRow(data1);
                                model.addRow(data2);
                                // Add đỉnh vào stack lại
                                stack.push(nodeTopTop2);
                            }
                        }
                        else {
                            ++countTableOfAnyPath;
                            int nodeTop1 = stack.pop();
                            int nodeTop2 = stack.pop();
                            int nodeTop3 = stack.pop();

                            // Tô màu xanh cây cho NodeTop2. Đồng thời tô màu xanh biển cho cạnh nodeTop3-nodeTop2
                            graph.getNode(Integer.toString(nodeTop2)).setAttribute("ui.style", "shape:circle;fill-color: green;size: 30px;");
                            graph.getEdge(Integer.toString(nodeTop3) + " " + Integer.toString(nodeTop2)).setAttribute("ui.style", "fill-color: rgb(102, 0, 255); size: 2px;");
                            // Tô màu tím cho nodeTop1. Đồng thời tô màu tím cho cạnh nodeTop2-NodeTop1
                            graph.getNode(Integer.toString(nodeTop1)).setAttribute("ui.style", "shape:circle;fill-color: violet;size: 30px;");
                            graph.getEdge(Integer.toString(nodeTop2) + " " + Integer.toString(nodeTop1)).setAttribute("ui.style", "fill-color: violet; size: 2px;");
                            // Cộng chi phí đường đi cho nodeTop1-nodeTop2
                            sumCost = sumCost + weightGraph.get(Integer.toString(nodeTop2) + Integer.toString(nodeTop1));
                            // Add vào table thông tin đường
                            String[] data = {Integer.toString(countTableOfAnyPath),
                                    "Cạnh " + nodeTop2 + " - " + nodeTop1,
                                    Integer.toString(weightGraph.get(Integer.toString(nodeTop2) + Integer.toString(nodeTop1)))};
                            model.addRow(data);
                            // Add node trở lại vào đường
                            stack.push(nodeTop3);
                            stack.push(nodeTop2);
                            stack.push(nodeTop1);
                            // Đưa thông tin
                            String[] responses = {"Menu", "Dừng lại", "Lùi lại"};
                            int choose = JOptionPane.showOptionDialog(null,
                                    "Đó là node lá đấy! Không có đường đi đâu. Trở lại nhé!",
                                    "Thông báo",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    responses,
                                    0);
                            if (choose == 0) { // Menu
                                //frameBai6.setVisible(false);
                                frameBai6.dispose();
                                //Clear: Set status of graph return initial
                                for (int i = 1; i <= max; ++i) {
                                    flag[i] = false;
                                }
                                countPath = 0;
                                currentPath.clear();

                                // Khởi tạo ban đầu cho anyPath()
                                stack.clear();
                                countTableOfAnyPath = 0;
                                for (int i = 1; i <= max; ++i) {
                                    graph.getNode(String.valueOf(i)).setAttribute("ui.style", "shape:circle; fill-color: yellow; size: 25px;");
                                }
                                Set<Integer> set = adjacencyGraph.keySet();
                                for (Integer key:set) {
                                    for (int i = 0; i < adjacencyGraph.get(key).size(); ++i) {
                                        graph.getEdge(Integer.toString(key) + " " +  Integer.toString(adjacencyGraph.get(key).get(i))).setAttribute("ui.style", "fill-color: black; size: 1px;");
                                    }
                                }
                                frame.remove(view);
                                frame.add(view);
                                frame.repaint();
                                frame.revalidate();
                                //frame.pack();
                                frame.setVisible(true);
                            }
                            else if(choose == 1) { // OK: Chỉ để màn hình xem thông tin thôi
                                // Thông báo chi phí
                                JOptionPane.showMessageDialog(null, "Chi phí của đường đi là: " + sumCost, "Thông báo", JOptionPane.PLAIN_MESSAGE);
                                // Add chi phí vào bảng
                                String[] data1 = {"", "Chi phí của đường đi là: " , Integer.toString(sumCost)};
                                model.addRow(data1);
                                // Node màu xanh cây. Tô màu cạnh vừa đi bằng màu xanh biển
                                int nodeTopTop1 = stack.pop();
                                int nodeTopTop2 = stack.pop();
                                graph.getNode(Integer.toString(nodeTopTop1)).setAttribute("ui.style", "shape:circle;fill-color: green;size: 30px;");
                                graph.getEdge(Integer.toString(nodeTopTop2) + " " +Integer.toString(nodeTopTop1)).setAttribute("ui.style", "fill-color: rgb(102, 0, 255); size: 2px;");
                                stack.push(nodeTopTop2);
                                stack.push(nodeTopTop1);
                                panelSouthInPanelEast.removeAll();
                                panelSouthInPanelEast.updateUI();
                                panelSouthInPanelWest.remove(stopButton);
                                panelSouthInPanelWest.updateUI();
                            }
                            else {  // Lùi lại
                                int nodeTopTop1 = stack.pop();
                                int nodeTopTop2 = stack.pop();
                                int nodeTopTop3 = stack.pop();
                                // Tô màu lại cạnh vừa đi và node vừa đi qua trở lại trạng thái ban đầu: cạnh - đen, bode - vàng
                                graph.getNode(Integer.toString(nodeTopTop1)).setAttribute("ui.style", "shape:circle;fill-color: yellow;size: 30px;");
                                graph.getEdge(Integer.toString(nodeTopTop2) + " " +Integer.toString(nodeTopTop1)).setAttribute("ui.style", "fill-color: black;size: 1px;");
                                // Tô màu nodeTopTop2 lại màu tím, cạnh nodeTopTop3-nodeTopTop2 màu tím
                                graph.getNode(Integer.toString(nodeTopTop2)).setAttribute("ui.style", "shape:circle;fill-color: violet;size: 30px;");
                                graph.getEdge(Integer.toString(nodeTopTop3) + " " + Integer.toString(nodeTopTop2)).setAttribute("ui.style", "fill-color: violet;size: 2px;");
                                // trả lại sumCost
                                sumCost = sumCost - weightGraph.get(Integer.toString(nodeTopTop2) + Integer.toString(nodeTopTop1));
                                // Add vào PathLog câu: Trở lại đỉnh xxx, chỉ phí bao nhiêu
                                String[] data1 = {"------------------------------", "Trở lại đỉnh " + nodeTopTop2 , "------------------------------"};
                                String[] data2 = {"------------------------------", "Lúc đó, chi phí đường đi là: ", Integer.toString(sumCost)};
                                model.addRow(data1);
                                model.addRow(data2);
                                // Add đỉnh vào stack lại
                                stack.push(nodeTopTop3);
                                stack.push(nodeTopTop2);
                            }
                        }
                    }
                    else {  // Chọn đúng node bình thường, cứ đi
                        if (stack.size() == 1) {  // Node đầu tiên được chọn và không phải node lá
                            int nodeTop1 = stack.pop();
                            // Tô màu tím cho nodeTop1
                            graph.getNode(Integer.toString(nodeTop1)).setAttribute("ui.style", "shape:circle;fill-color: violet;size: 30px;");
                            //Hiện các node kề của nodeTop1 lên panelSouthInPanelEast
                            panelSouthInPanelEast.removeAll();
                            for (int i = 0; i < adjacencyGraph.get(nodeTop1).size(); ++i) {
                                panelSouthInPanelEast.add(buttonBai6[adjacencyGraph.get(nodeTop1).get(i)]);
                            }
                            panelSouthInPanelEast.updateUI();
                            // Add node trở lại vào đường đi
                            stack.push(nodeTop1);
                        }
                        else if (stack.size() == 2) {  // Node thứ 2 được chọn
                            ++countTableOfAnyPath;
                            int nodeTop1 = stack.pop();
                            int nodeTop2 = stack.pop();
                            // Tô màu xanh cây cho nodeTop2
                            graph.getNode(Integer.toString(nodeTop2)).setAttribute("ui.style", "shape:circle;fill-color: green;size: 30px;");
                            // Tô màu tím cho nodeTop1. Đồng thời tô màu tím cho cạnh nodeTop2-nodeTop1
                            graph.getNode(Integer.toString(nodeTop1)).setAttribute("ui.style", "shape:circle;fill-color: violet;size: 30px;");
                            graph.getEdge(Integer.toString(nodeTop2) + " " + Integer.toString(nodeTop1)).setAttribute("ui.style", "fill-color: violet; size: 2px;");
                            // Cộng chi phí đường đi cho nodeTop1-nodeTop2
                            sumCost = sumCost + weightGraph.get(Integer.toString(nodeTop2) + Integer.toString(nodeTop1));
                            // Add vào table thông tin đường
                            String[] data = {Integer.toString(countTableOfAnyPath),
                                    "Cạnh " + nodeTop2 + " - " + nodeTop1,
                                    Integer.toString(weightGraph.get(Integer.toString(nodeTop2) + Integer.toString(nodeTop1)))};
                            model.addRow(data);
                            //Hiện các node kề của nodeTop1 lên panelSouthInPanelEast
                            panelSouthInPanelEast.removeAll();
                            for (int i = 0; i < adjacencyGraph.get(nodeTop1).size(); ++i) {
                                panelSouthInPanelEast.add(buttonBai6[adjacencyGraph.get(nodeTop1).get(i)]);
                            }
                            panelSouthInPanelEast.updateUI();
                            // Add node trở lại vào đường đi
                            stack.push(nodeTop2);
                            stack.push(nodeTop1);
                        }
                        else {  // Node bình thường
                            ++countTableOfAnyPath;
                            int nodeTop1 = stack.pop();
                            int nodeTop2 = stack.pop();
                            int nodeTop3 = stack.pop();

                            // Tô màu xanh cây cho NodeTop2. Đồng thời tô màu xanh biển cho cạnh nodeTop3-nodeTop2
                            graph.getNode(Integer.toString(nodeTop2)).setAttribute("ui.style", "shape:circle;fill-color: green;size: 30px;");
                            graph.getEdge(Integer.toString(nodeTop3) + " " + Integer.toString(nodeTop2)).setAttribute("ui.style", "fill-color: rgb(102, 0, 255); size: 2px;");
                            // Tô màu tím cho nodeTop1. Đồng thời tô màu tím cho cạnh nodeTop2-NodeTop1
                            graph.getNode(Integer.toString(nodeTop1)).setAttribute("ui.style", "shape:circle;fill-color: violet;size: 30px;");
                            graph.getEdge(Integer.toString(nodeTop2) + " " + Integer.toString(nodeTop1)).setAttribute("ui.style", "fill-color: violet; size: 2px;");
                            // Cộng chi phí đường đi cho nodeTop1-nodeTop2
                            sumCost = sumCost + weightGraph.get(Integer.toString(nodeTop2) + Integer.toString(nodeTop1));
                            // Add vào table thông tin đường
                            String[] data = {Integer.toString(countTableOfAnyPath),
                                    "Cạnh " + nodeTop2 + " - " + nodeTop1,
                                    Integer.toString(weightGraph.get(Integer.toString(nodeTop2) + Integer.toString(nodeTop1)))};
                            model.addRow(data);
                            //Hiện các node kề của nodeTop1 lên panelSouthInPanelEast
                            panelSouthInPanelEast.removeAll();
                            for (int i = 0; i < adjacencyGraph.get(nodeTop1).size(); ++i) {
                                panelSouthInPanelEast.add(buttonBai6[adjacencyGraph.get(nodeTop1).get(i)]);
                            }
                            panelSouthInPanelEast.updateUI();
                            // Add node trở lại vào đường
                            stack.push(nodeTop3);
                            stack.push(nodeTop2);
                            stack.push(nodeTop1);

                        }
                    }
                }
            });
        }
    }
}
