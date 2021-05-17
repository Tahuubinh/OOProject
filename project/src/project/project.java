package project;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.stream.file.FileSinkImages.LayoutPolicy;
import org.graphstream.stream.file.FileSinkImages.OutputPolicy;
import org.graphstream.stream.file.FileSinkImages.OutputType;
import org.graphstream.stream.file.images.Resolutions;
import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.Viewer.CloseFramePolicy;
import org.graphstream.ui.view.camera.Camera;





public class project {
	private static OnMyWayabc omw;
	private static OnMyWay omw4;
	private static OnMyWay2 omw5;
	private static DFS g;
	
	private static Graph graph;
	private static String path = "";
	private static String[] v;
	private static int[][] allIntArr;
	private static String[] arrOfStr;
	private static int max = 0; // file index of the last vertex
	private static int i1, i2, i3 = 0, x = 0, y = 0, a = 0, y1 = 0;
	private static Integer[] c;
	private static int size;
	private static JFrame welcomeFrame;
	private static JFrame frame = new JFrame();
	private static JPanel buttonJPanel;
	private static SwingViewer viewer;
	private static JPanel view;
	private static ArrayList<Integer> vertex = new ArrayList<Integer>();
	private static LinkedList<Integer> aIntegers = new LinkedList<Integer>();
	private static ArrayList<String> hasNext=new ArrayList<>();
	private static HashMap<String,String[]> adjEdge=new HashMap<>();
	
	public static void main(String args[]) throws IOException {
		welcome();
		///welcome: hiện thị ra tên các thành viên trong nhóm và chọn file txt để chạy đồ thị
	}
	
	public static void welcome() throws IOException {
		welcomeFrame = new JFrame();
		BufferedImage myPicture = ImageIO.read(new File("MainProject\\project.jpg")); // ảnh logo đại học bách khoa
		JLabel picLabel = new JLabel(new ImageIcon(myPicture));
		
		JPanel nameMember = new JPanel(); // nameMember panel chứa các label tên và mssv của các member
		JPanel dirPanel = new JPanel(); // dirPanel chứa button directory, finish và điền đường path của file txt
		nameMember.add(picLabel);
		JLabel[] mb = new JLabel[7];
		JLabel[] mssv = new JLabel[7];
		mb[1] = new JLabel("Hồ Anh");
		mssv[1] = new JLabel("20190037");
		mb[2] = new JLabel("Tạ Hữu Bình");
		mssv[2] = new JLabel("20190094");
		mb[3] = new JLabel("Nguyễn Hải Dương");
		mssv[3] = new JLabel("20190044");
		mb[4] = new JLabel("Trịnh Tùng Dương");
		mssv[4] = new JLabel("20190045");
		mb[5] = new JLabel("Trần Trọng Hiệp");
		mssv[5] = new JLabel("20190051");
		mb[6] = new JLabel("Lê Huy Hoàng");
		mssv[6] = new JLabel("20190053");
		nameMember.setLayout(new GridLayout(6, 2)); // tạo lớp layout 6 hàng 2 cột (kiểu dạng bảng 6x2)
        // các label được add vào sẽ theo thứ tự add vào các cột rồi đến các hàng, cái nào đc add trước thì thêm vào trước
        
		for(int i = 1; i <= 6; ++i) {
			mb[i].setFont(new Font("Helvetica", Font.PLAIN, 20)); // kiểu chữ Helvetica, cỡ chữ 20
			mssv[i].setFont(new Font("Helvetica", Font.PLAIN, 20));
			nameMember.add(mb[i]);    // mb[1] add vào trước sẽ ở ô (1,1), sau đó add mssv[1] sẽ ở ô (1,2), tiếp đó add mb[2] sẽ ở ô (2,1) và cứ như thế ta sẽ có được cái in mong muốn ra frame..... 
			nameMember.add(mssv[i]);
		}
		
		
		JLabel dirLabel = new JLabel("Enter path ");
		JTextField dirText = new JTextField(50); // độ dài của phần được nhập là 50 ký tự
		JButton finishButton = new JButton("Finish"); // hoàn tất việc điền đường path và xử lý file txt đó
		JButton directoryButton = new JButton("Directory"); // chọn file txt thỏa mãn trong máy
		dirPanel.add(dirLabel);
		dirPanel.add(dirText);
		dirPanel.add(finishButton);
		dirPanel.add(directoryButton);
		
		//Take the current dir
		
		String curentDir = System.getProperty("user.dir");
//		System.out.println(curentDir);
		JFileChooser fileDialog = new JFileChooser(curentDir + "\\MainProject\\DataGraph"); //xử lý việc chọn directory
		
		directoryButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int returnval = fileDialog.showOpenDialog(welcomeFrame);
				if(returnval == JFileChooser.APPROVE_OPTION) {
					File file = fileDialog.getSelectedFile();
					String p = file.getName();
					if(!p.endsWith("txt")) { // nếu không là file txt lập tức thông báo lỗi
						JOptionPane.showMessageDialog(null, "File error", "ERROR", JOptionPane.ERROR_MESSAGE);
					}
					else {
						path = file.getPath();
						try {
							prepare();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						if(graph.getEdgeCount() == 0) { // nếu file txt đó không phải file chứ dữ liệu đồ thị lập tức thông báo lỗi
							JOptionPane.showMessageDialog(null, "File error", "ERROR", JOptionPane.ERROR_MESSAGE);
						}
						else {
						welcomeFrame.setVisible(false);
						frame.setVisible(true);
						console();
						}
					}
				}
			}
		});
		finishButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				path =  dirText.getText();
				
				try {
					prepare();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(graph.getEdgeCount() == 0) {
					JOptionPane.showMessageDialog(null, "File error", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				else {
				welcomeFrame.setVisible(false);

				console();
				}
			}
		});
		welcomeFrame.setTitle("Project Java");
		welcomeFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		welcomeFrame.getContentPane().add(picLabel, BorderLayout.WEST);
		welcomeFrame.getContentPane().add(nameMember, BorderLayout.CENTER);
		welcomeFrame.getContentPane().add(dirPanel, BorderLayout.SOUTH);
		welcomeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		welcomeFrame.setVisible(true);
	}
	
	/// console được gọi khi chọn xong file txt và xử lý xong phần prepare()
	public static void console() {
		
        JButton showButton = new JButton("Bài 1"); // xử lý bài 1
        JButton AllPAthButton = new JButton("Bài 2"); // xử lý bài 2
        JButton QuestionsPathButton = new JButton("Bài 3");  // xử lý bài 3
        JButton bai4 = new JButton("Bài 4* (Hamilton)");
        JButton bai5 = new JButton("Bài 5* (Euler)");
        JButton homeButton = new JButton("Home"); // quay trở về welcomeframe
        
        
        buttonJPanel = new JPanel();
        buttonJPanel.add(homeButton);
        buttonJPanel.add(showButton);
        buttonJPanel.add(AllPAthButton);
        buttonJPanel.add(QuestionsPathButton);
        buttonJPanel.add(bai4);
        buttonJPanel.add(bai5);
        buttonJPanel.setBackground(Color.orange);
        

        setLabel(frame);
        
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.getContentPane().add(buttonJPanel, BorderLayout.SOUTH);
        frame.setTitle("Project OOPT");
        frame.setForeground(Color.YELLOW);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getView(frame);
        frame.getContentPane().add(view);
        homeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.setVisible(false);
				frame = new JFrame();
				max = 0;
				welcomeFrame.setVisible(true);
			}
		});
        showButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				try {
					g.graphString("Bai1"); // thực hiện bài 1
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
        
		
        AllPAthButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				g.runDFS(1, max, "Bai2"); // in tất cả các đường đi từ đỉnh đầu đên đỉnh cuối
				AllPathButton(); // mở rộng ra, ta có thể chọn 2 đỉnh bất ký và in ra tất cả đường đi giữa 2 đỉnh đó
				
			}
		});
        
        QuestionsPathButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				QuestionsPath(); // phần mô phỏng bài 3
							}
		});
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setVisible(true);
        
        bai4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				QuestionsPath4(); // phần mô phỏng bài 3
							}
		});
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setVisible(true);
        
        bai5.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				QuestionsPath5(); // phần mô phỏng bài 3
							}
		});
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setVisible(true);
	}
	
	protected static void QuestionsPath4() {
		// TODO Auto-generated method stub
		JFrame AllPathFrame = new JFrame();
		JPanel vPanel = new JPanel();
		JButton clearButton = new JButton("Clear");
		JButton backButton = new JButton("Back");
		JButton btnNewButton = new JButton("Menu");
		btnNewButton.setBounds(10, 10, 208, 29);
		btnNewButton.setBackground(Color.CYAN);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							omw4.clear();
							console();
							AllPathFrame.dispose();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		vPanel.add(btnNewButton);
		vPanel.add(clearButton);
		vPanel.add(backButton);
		JButton[] vButtons = new JButton[max];
		for(int i = 0; i < max; ++i) {
			vButtons[i] = new JButton(Integer.toString(i+1));
			vPanel.add(vButtons[i]);
		}
		
		vPanel.setForeground(Color.GREEN);
		
		AllPathFrame.getContentPane().add(vPanel, BorderLayout.SOUTH);
		AllPathFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		AllPathFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		AllPathFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				AllPathFrame.dispose();
				omw4.clear();
			}
		});
		setLabel(AllPathFrame);
		
		getView(AllPathFrame);
		
		view = omw4.getViewer();
		AllPathFrame.add(view);
		

		for(int i = 0; i < max; ++i) {
			vButtons[i].addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					for(int i = 1; i <= max; ++i) {
						if(e.getActionCommand().equals(Integer.toString(i))) {
							try {
								i3 = i;
								omw4.addOption(1, i3);
								for(int j = 0; j < max; ++j) {
									
									vPanel.add(vButtons[j]);
								}
								vertex = omw4.getVertex();
								for(int j = 0; j < max; ++j) {
									if(!vertex.contains(j+1)) {
										vPanel.remove(vButtons[j]);
									}
								}
								
								AllPathFrame.getContentPane().add(vPanel, BorderLayout.SOUTH);
								AllPathFrame.getContentPane().remove(view);
								view = omw4.getViewer();
								AllPathFrame.add(view);
								AllPathFrame.pack();
								AllPathFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
								AllPathFrame.setVisible(true);
								frame.dispose();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				}
			});
			
		}
		
		backButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					omw4.addOption(0, i3);
					for(int j = 0; j < max; ++j) {
						if(vertex.contains(j+1)) {
							vPanel.remove(vButtons[j]);
						}
							vPanel.add(vButtons[j]);

					}
					vertex = omw4.getVertex();
					for(int j = 0; j < max; ++j) {
						if(!vertex.contains(j+1)) {
							vPanel.remove(vButtons[j]);
						}
					}
					AllPathFrame.getContentPane().add(vPanel, BorderLayout.SOUTH);
					AllPathFrame.getContentPane().remove(view);
					view = omw4.getViewer();
					AllPathFrame.add(view);
					AllPathFrame.pack();
					AllPathFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
					AllPathFrame.setVisible(true);
					frame.dispose();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		clearButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				omw4.clear();
				for(int j = 0; j < max; ++j) {
					if(vertex.contains(j+1)) {
						vPanel.remove(vButtons[j]);
					}
					vPanel.add(vButtons[j]);
				}
				AllPathFrame.getContentPane().add(vPanel, BorderLayout.SOUTH);
				AllPathFrame.getContentPane().remove(view);
				view = omw4.getViewer();
				AllPathFrame.add(view);
				AllPathFrame.pack();
				AllPathFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
				AllPathFrame.setVisible(true);
				frame.dispose();
			}
		});
		
		
		AllPathFrame.pack();
		AllPathFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		AllPathFrame.setVisible(true);
		frame.dispose();
	}
	
	protected static void QuestionsPath5() {
		// TODO Auto-generated method stub
		JFrame AllPathFrame = new JFrame();
		JPanel vPanel = new JPanel();
		JButton clearButton = new JButton("Clear");
		JButton backButton = new JButton("Back");
		JButton btnNewButton = new JButton("Menu");
		btnNewButton.setBounds(10, 10, 208, 29);
		btnNewButton.setBackground(Color.CYAN);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							omw5.clear();
							console();
							AllPathFrame.dispose();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		vPanel.add(btnNewButton);
		vPanel.add(clearButton);
		vPanel.add(backButton);
		JButton[] vButtons = new JButton[max];
		for(int i = 0; i < max; ++i) {
			vButtons[i] = new JButton(Integer.toString(i+1));
			vPanel.add(vButtons[i]);
		}
		
		vPanel.setForeground(Color.GREEN);
		
		AllPathFrame.getContentPane().add(vPanel, BorderLayout.SOUTH);
		AllPathFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		AllPathFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		AllPathFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				AllPathFrame.dispose();
				omw5.clear();
			}
		});
		setLabel(AllPathFrame);
		
		getView(AllPathFrame);
		
		view = omw5.getViewer();
		AllPathFrame.add(view);

		for(int i = 0; i < max; ++i) {
			vButtons[i].addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					for(int i = 1; i <= max; ++i) {
						if(e.getActionCommand().equals(Integer.toString(i))) {
							try {
								i3 = i;
								omw5.addOption(1, i3);
								for(int j = 0; j < max; ++j) {
									
									vPanel.add(vButtons[j]);
								}
								/*vertex = omw5.getVertex();
								for(int j = 0; j < max; ++j) {
									if(!vertex.contains(j+1)) {
										vPanel.remove(vButtons[j]);
									}
								}*/
								if (!omw5.getSignal()) {
									
								}
								
								aIntegers = omw5.getPlaceAdj();
								for(int j = 0; j < max; ++j) {
									if(!aIntegers.contains(j+1)) {
										vPanel.remove(vButtons[j]);
									}
								}
								AllPathFrame.getContentPane().add(vPanel, BorderLayout.SOUTH);
								AllPathFrame.getContentPane().remove(view);
								view = omw5.getViewer();
								AllPathFrame.add(view);
								AllPathFrame.pack();
								AllPathFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
								AllPathFrame.setVisible(true);
								frame.dispose();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				}
			});
			
		}
		
		backButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					omw5.addOption(0, i3);
					for(int j = 0; j < max; ++j) {
						if(vertex.contains(j+1)) {
							vPanel.remove(vButtons[j]);
						}
							vPanel.add(vButtons[j]);

					}
					vertex = omw5.getVertex();
					for(int j = 0; j < max; ++j) {
						if(!vertex.contains(j+1)) {
							vPanel.remove(vButtons[j]);
						}
					}
					AllPathFrame.getContentPane().add(vPanel, BorderLayout.SOUTH);
					AllPathFrame.getContentPane().remove(view);
					view = omw5.getViewer();
					AllPathFrame.add(view);
					AllPathFrame.pack();
					AllPathFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
					AllPathFrame.setVisible(true);
					frame.dispose();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		clearButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				omw5.clear();
				for(int j = 0; j < max; ++j) {
					if(vertex.contains(j+1)) {
						vPanel.remove(vButtons[j]);
					}
					vPanel.add(vButtons[j]);
				}
				AllPathFrame.getContentPane().add(vPanel, BorderLayout.SOUTH);
				AllPathFrame.getContentPane().remove(view);
				view = omw5.getViewer();
				AllPathFrame.add(view);
				AllPathFrame.pack();
				AllPathFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
				AllPathFrame.setVisible(true);
				frame.dispose();
			}
		});
		
		
		AllPathFrame.pack();
		AllPathFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		AllPathFrame.setVisible(true);
		frame.dispose();
	}
	
	/// bài 3
	protected static void QuestionsPath() {
		// TODO Auto-generated method stub
		JFrame AllPathFrame = new JFrame("Bai3"); // tạo 1 frame mới 
		AllPathFrame.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		AllPathFrame.getContentPane().setLayout(new  GridBagLayout());
		GridBagConstraints c = new GridBagConstraints(); // gridbag of button
		GridBagConstraints gc = new GridBagConstraints();// gridbag of graph
		GridBagConstraints sc = new GridBagConstraints();// gridbag of scroll
		
		
		//Scroll when overload
		JScrollPane showPathScroll = new JScrollPane();
		JTextArea pathTxt = new JTextArea();
		showPathScroll.setViewportView(pathTxt);
		
		pathTxt.setText("Edge has passed:\n");
		JPanel vPanel = new JPanel();
		
		JScrollPane vPanelScoll = new JScrollPane(vPanel);
		vPanelScoll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		JButton clearButton = new JButton("Clear"); // khôi phục lại đồ thị ban đầu
		JButton btnNewButton = new JButton("Menu"); // quay lại frame chọn bài
		JButton stopButton = new JButton("Stop"); // stop simulation graph
		btnNewButton.setBounds(10, 10, 208, 29);
		btnNewButton.setBackground(Color.CYAN);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							omw.clear();
							pathTxt.setText("Edge has passed:\n");
							y1 = 0;
							frame.setVisible(true);
							AllPathFrame.dispose();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		
		
		vPanel.add(clearButton);
		vPanel.add(stopButton);
		JButton[] vButtons = new JButton[max]; // tạo các button với vButtons[i] là đỉnh thứ i
		for(int i = 0; i < max; ++i) {
			vButtons[i] = new JButton(Integer.toString(i+1));
			vPanel.add(vButtons[i]);
		}
		vPanel.setForeground(Color.GREEN);
		
       
        gc.fill = GridBagConstraints.BOTH; // mở rộng panel cho khít với khoảng trống với cả chiều rộng và chiều cao
        gc.weightx = 0.5; // khoảng cách tương đối giữa các đối tượng
		gc.gridx = 1; // tọa độ (x, y) = 1, 1
		gc.gridy = 1;
		gc.ipady = 750; // mở rộng theo chiều dọc cả trên và dưới 
        gc.anchor = GridBagConstraints.EAST; // vị trí tương đối của panel trong tọa độ đó
        
        sc.fill = GridBagConstraints.BOTH;
        sc.weightx = 0.5;
        sc.gridx = 0;
        sc.gridy = 1;
        sc.ipady = 750;
        sc.anchor = GridBagConstraints.WEST;
        
        
        c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 2;
		c.ipady = 40;
		
		AllPathFrame.getContentPane().add(btnNewButton, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 2;
		c.gridx = 1;
		c.anchor = GridBagConstraints.PAGE_END;
		AllPathFrame.getContentPane().add(showPathScroll, sc);

		AllPathFrame.getContentPane().add(vPanelScoll, c);
		AllPathFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		AllPathFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		AllPathFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) { // khi frame đóng, khôi phục lại đồ thị như ban đầu
				AllPathFrame.dispose();
				frame.setVisible(true);
				pathTxt.setText("Edge has passed:\n");
				omw.clear();
			}
		});

		
		getView(AllPathFrame);
		AllPathFrame.getContentPane().add(view, gc);

		for(int i = 0; i < max; ++i) {
			vButtons[i].addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					for(int i = 1; i <= max; ++i) {
						if(e.getActionCommand().equals(Integer.toString(i))) {// xem đỉnh nào được nhấn hiện tại
							try {
								i3 = i;
								omw.addOption(1, i3); // đi tới đỉnh đó
								for(int j = 0; j < max; ++j) { // khôi phục lại các buton, để khi xóa các button ta sẽ có các button được xếp sếp theo thứ tự tăng dần
									
									vPanel.add(vButtons[j]);
								}
								/*vertex = omw.getVertex();
								for(int j = 0; j < max; ++j) {
									if(!vertex.contains(j+1)) {
										vPanel.remove(vButtons[j]);
									}
								}*/
								
								
								aIntegers = omw.getPlaceAdj();
								for(int j = 0; j < max; ++j) {
									if(!aIntegers.contains(j+1)) { // những đỉnh nào mà không kề với đỉnh hiện tại sẽ xóa các button của các đỉnh đó đi
										vPanel.remove(vButtons[j]);
									}
								}
								vPanel.repaint();
								String a = omw.getLabel();
								pathTxt.setText(pathTxt.getText() + a);
								AllPathFrame.getContentPane().add(vPanelScoll, c);
								AllPathFrame.getContentPane().remove(view);
								view = omw.getViewer();
								
								AllPathFrame.getContentPane().add(view, gc);
								AllPathFrame.pack();
								AllPathFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
								AllPathFrame.setVisible(true);
								frame.dispose();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				}
			});
			
		}
		
	
		clearButton.addActionListener(new ActionListener() { // khôi phục lại đồ thị
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				omw.clear();
				y1 = 0;
				for(int j = 0; j < max; ++j) {
					if(vertex.contains(j+1)) {
						vPanel.remove(vButtons[j]);
					}
					vPanel.add(vButtons[j]);
				}
				pathTxt.setText("Edge has passed:\n");
				vPanel.repaint();
				AllPathFrame.getContentPane().add(vPanelScoll, c);
				AllPathFrame.getContentPane().remove(view);
				view = omw.getViewer();
				AllPathFrame.add(view, gc);
				AllPathFrame.pack();
				AllPathFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
				AllPathFrame.setVisible(true);
				frame.dispose();
			}
		});
		
		stopButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				aIntegers = omw.getPlaceAdj();
				for(int j = 0; j < max; ++j) {
					if(aIntegers.contains(j+1)) { // những đỉnh nào mà không kề với đỉnh hiện tại sẽ xóa các button của các đỉnh đó đi
						vPanel.remove(vButtons[j]);
					}
				}
				vPanel.repaint();
				AllPathFrame.getContentPane().add(vPanelScoll, c);
				AllPathFrame.getContentPane().remove(view);
				view = omw.getViewer();
				AllPathFrame.add(view, gc);
				AllPathFrame.pack();
				AllPathFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
				AllPathFrame.setVisible(true);
				frame.dispose();
			}
		});
		AllPathFrame.pack();
		AllPathFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		AllPathFrame.setVisible(true);
		frame.dispose();
	}
	
	
	public static void AllPathButton() { // ài 2
		// TODO Auto-generated method stub
		 c = new Integer[max + 1];
		JFrame AllPathFrame = new JFrame();
		JPanel vPanel = new JPanel();
		JButton[] vButtons = new JButton[max];
		JButton btnNewButton = new JButton("Menu");
		btnNewButton.setBounds(10, 10, 208, 29);
		btnNewButton.setBackground(Color.CYAN);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							frame.setVisible(true);
							AllPathFrame.dispose();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
		vPanel.add(btnNewButton);
		for(int i = 0; i < max; ++i) {
			vButtons[i] = new JButton(Integer.toString(i+1));
			vPanel.add(vButtons[i]);
		}
		
		vPanel.setForeground(Color.GREEN);
		
		AllPathFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		AllPathFrame.getContentPane().add(vPanel, BorderLayout.SOUTH);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		AllPathFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		AllPathFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				AllPathFrame.dispose();
			}
		});
		setLabel(AllPathFrame);
		AllPathFrame.pack();
		AllPathFrame.setVisible(true);
		frame.dispose();
		getView(AllPathFrame);
		AllPathFrame.getContentPane().add(view);
		c[0] = 0;
		for(int i = 0; i < max; ++i) {
			c[i + 1] = 0;
			vButtons[i].addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					for(int i = 1; i <= max; ++i) {
						if(e.getActionCommand().equals(Integer.toString(i))) {
							// đây là thuật toán để khi nhấn 2 đỉnh khác nhau liên tiếp sẽ in tất cả các đường đi giữa 2 đỉnh đó
							//nếu nhấn 1 đỉnh nhiều lần liên tiếp sẽ chỉ tính 1 lần
							
							if(a == 0) {
									c[i] = 1;
									i1 = i;
									y = 0;
									a++;
									a = a%2;
								
							}
							else {
								if(c[i] == 0) {
									c[i] = 1;
									i2 = i;
									x = 0;
									a++;
									a = a%2;
								}
							}
						}
						
					}
					if(i2 != 0) {
					for(int i = 1; i <= max; ++i) {
						for(int j = 1; j <= max; ++j) {
							if(c[i1] == 1 && c[i2] == 1) {
								g.runDFS(i1 , i2, "path between vertex " + i1 + " and vertex " +i2 );
								c[i1] = 0;
								c[i2] = 0;
								i1 = 0;
								i2 = 0;
							}
						}
					}
					}
				}
			});
		}
	}
	
	
	public static void prepare() throws IOException { // đọc file, xử lý để in ra đồ thị từ file đó
		System.setProperty("org.graphstream.ui", "swing");
		graph = new SingleGraph("Project");
		graph.setStrict(false);
		graph.setAutoCreate( true );
		
		 // Creates an array of character
        char[] array = new char[100];
        // Read file into an arraylist
    	ArrayList<String> listOfLines = new ArrayList<>(); 
    	BufferedReader bufReader;
        try {
            //doc file text theo dong
            bufReader = new BufferedReader(new FileReader(
                    path));
            String line = bufReader.readLine();
            //voi moi dong:
            //add gia tri dau tien vao danh sach cac dinh co it nhat 1 dinh ke hasNext              (1)
            //put vao adjmap (hashmap) ten dinh va danh sach cac dinh ke voi no.                    (2)
            //them canh vao graph tu dong do                                                        (3)
            //tim dinh dich   
            
            while (line != null) {
        		listOfLines.add(line);
        		line = bufReader.readLine();
        	} 
            bufReader.close();
        } catch (IOException e) {
        }
    
        size = listOfLines.size(); // số dòng trong file
        
        
        allIntArr = new int[size][];
        
        //Read to list of integers for each line
        for (int i = 0; i < size; i++) {
        	arrOfStr = listOfLines.get(i).split(" "); // mỗi dòng sẽ tách ra thành các phần tử vào lưu vào mảng tương ứng
        	int arrOfStrlength = arrOfStr.length;
        	allIntArr[i] = new int[arrOfStrlength];
        	
        	for (int j = 0; j < arrOfStrlength; j++) {
        		allIntArr[i][j] = Integer.parseInt(arrOfStr[j]);
        		
        	}
        	for (int j = 1; j < arrOfStrlength; j++) {
        			graph.addEdge(arrOfStr[0] + arrOfStr[j], arrOfStr[0], arrOfStr[j], true); // true: đồ thị có hướng
        			
        	        		
        	}
        	for (int j = 0; j < arrOfStrlength; j++) { // tìm đỉnh có số hiệu lớn nhất
        		if (max < allIntArr[i][j])
            		max = allIntArr[i][j];
        	}
        	//find max
        	
        	
        }
        g = new DFS(max); // add các cạnh vào DFS để chạy thuật toán đó
        for (int i = 0; i < size; i++) {
        	for (int j = 1; j < allIntArr[i].length; j++) {
        		g.addEdge(allIntArr[i][0], allIntArr[i][j]);
        	}
        }
        
        //Max is the numbers of node of graph
        /////////////////////////////////////
        /////////////////////////////////////
        //g: save data of graph
        omw = new OnMyWayabc(max); //thêm các cạnh vào để chạy thuật toán bài 3
        for (int i = 0; i < size; i++) {
        	for (int j = 1; j < allIntArr[i].length; j++) {
        		omw.addEdge(allIntArr[i][0], allIntArr[i][j]);
        	}
        }
        omw.runner();
        
        omw4 = new OnMyWay(max);
        for (int i = 0; i < size; i++) {
        	for (int j = 1; j < allIntArr[i].length; j++) {
        		omw4.addEdge(allIntArr[i][0], allIntArr[i][j]);
        	}
        }
        omw4.runner();
        
        omw5 = new OnMyWay2(max);
        for (int i = 0; i < size; i++) {
        	for (int j = 1; j < allIntArr[i].length; j++) {
        		omw5.addEdge(allIntArr[i][0], allIntArr[i][j]);
        	}
        }
        omw5.runner();
        
        Node[] e = new Node[max];
        for(int i = 0; i < max; ++i) {

        	graph.addNode(Integer.toString(i+1));
        	e[i] = graph.getNode(Integer.toString(i+1));
        	e[i].setAttribute("ui.style", "shape:circle;fill-color: yellow;size: 30px;");
    		e[i].setAttribute("ui.label", Integer.toString(i+1)); 
        }
        
	}
	// đây là đặt nhãn dán cho 1 frame bất kỳ ở phía trên cùng của frame
		public static void setLabel(JFrame frame) {
			JLabel showGraphLabel = new JLabel("PROJECT JAVA");
	        showGraphLabel.setFont(new Font("Helvetica", Font.PLAIN, 30));
	        showGraphLabel.setForeground(Color.RED);
	        
	        showGraphLabel.setHorizontalAlignment(SwingConstants.CENTER);
	      
	        frame.add(showGraphLabel, BorderLayout.NORTH);
		}
		
	// chèn đồ thị vào frame
	public static void getView(JFrame frame) {
    	viewer = new SwingViewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewer.enableAutoLayout();
        view = (JPanel) viewer.addDefaultView(false);
        view.setSize(new Dimension(500, 750));
        
    }
	
	

}
