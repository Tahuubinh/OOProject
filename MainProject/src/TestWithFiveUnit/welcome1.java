package TestWithFiveUnit;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Event;
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
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
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
import org.graphstream.ui.graphicGraph.GraphicSprite;
import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.swing_viewer.ViewPanel;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.Viewer.CloseFramePolicy;
import org.graphstream.ui.view.camera.Camera;


public class welcome1 {
	
	public welcome1() {
	
		JFrame welcomeFrame = new JFrame();
		BufferedImage myPicture;
		try {
			myPicture = ImageIO.read(new File("project.jpg"));
		
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
		}} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // ảnh logo đại học bách khoa
		
		
		/*JLabel dirLabel = new JLabel("Enter path ");
		JTextField dirText = new JTextField(50); // độ dài của phần được nhập là 50 ký tự
		JButton finishButton = new JButton("Finish"); // hoàn tất việc điền đường path và xử lý file txt đó
		JButton directoryButton = new JButton("Directory"); // chọn file txt thỏa mãn trong máy
		dirPanel.add(dirLabel);
		dirPanel.add(dirText);
		dirPanel.add(finishButton);
		dirPanel.add(directoryButton);
		
		//Take the current dir
		
		String curentDir = System.getProperty("user.dir");
		JFileChooser fileDialog = new JFileChooser(curentDir + "\\DataGraph"); //xử lý việc chọn directory
		
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
		welcomeFrame.setVisible(true);*/
	}
}