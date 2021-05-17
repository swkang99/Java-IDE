package myjava;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.awt.event.ActionEvent;
import javax.swing.Box;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Eclipse_Compiler extends JFrame {

	private JPanel contentPane;
	private JTextArea inputArea;
	private JTextArea outputArea;
	
	private String inputData = "";				// 입력된 글자를 저장하는 임시 문자열 데이터
	private StringBuffer savingData;   
	private JTextArea lineNumber = new JTextArea();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Eclipse_Compiler frame = new Eclipse_Compiler();
					frame.setSize(800, 600);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Eclipse_Compiler() {
		setTitle("Simple Java IDE ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 800);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("New");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inputArea.setText("");
				outputArea.setText("");
				setTitle("Simple Java IDE");
				lineNumber.setText("");
				setLineNumber();
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Open");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame f = new JFrame();
				File openFile = FileUtil.showOpenFileChooser(f);
				
				if (openFile != null) {
					String fileName = openFile.getName();
					String temp = fileName.substring(fileName.length() - 4, fileName.length());
					if (!temp.equals("java")) {
						outputArea.setText(".java 파일이 아닙니다.");
					} 
					else {
						setTitle(openFile.toString());
						temp = FileUtil.read(openFile.getAbsolutePath()).toString();
						inputArea.setText(temp);
						outputArea.setText("");
						lineNumber.setText(null);
						setLineNumber();
					}
				}
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Save");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame f = new JFrame();
				String fileName = getClassName() + ".java";
				File saveFile = FileUtil.showSaveFileChooser(f);
				
				if (saveFile != null) {
					inputData = inputArea.getText();
					savingData = new StringBuffer(inputData);
					FileUtil.save(savingData, fileName);
					setTitle(saveFile.toString());
				}
			}
		});
		mnNewMenu.add(mntmNewMenuItem_2);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Exit");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_3);
		
		JMenu mnNewMenu_1 = new JMenu("Edit");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Copy");
		mntmNewMenuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inputArea.copy();
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_4);
		
		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Paste");
		mntmNewMenuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inputArea.paste();
				setLineNumber();
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_5);
		
		JMenuItem mntmNewMenuItem_6 = new JMenuItem("Cut");
		mntmNewMenuItem_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inputArea.cut();
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_6);
		
		JMenu mnNewMenu_2 = new JMenu("Compile");
		menuBar.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem_7 = new JMenuItem("Compile");
		mntmNewMenuItem_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inputData = inputArea.getText();
				savingData = new StringBuffer(inputData);
				String compFile = ".\\bin\\myjava\\" + getClassName() + ".java";
				FileUtil.save(savingData, compFile);
				
				String cmd = new String("javac " +  compFile);
				
				try {
					Process pc = Runtime.getRuntime().exec(cmd);
					outputArea.setText("컴파일 완료");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					outputArea.setText("컴파일 에러");
				}
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_7);
		
		JMenuItem mntmNewMenuItem_8 = new JMenuItem("run");
		mntmNewMenuItem_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String runFile = ".\\bin\\myjava\\" + getClassName() + ".java";
				String cmd = new String("java " +  runFile);
				
				try {
					Process pc = Runtime.getRuntime().exec(cmd);
					BufferedReader stdOut = new BufferedReader( new InputStreamReader(pc.getInputStream()) );
					String str;
					while( (str = stdOut.readLine()) != null ) {
						outputArea.setText(str);
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					outputArea.setText("실행 에러");
				}
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_8);
		
		JMenu mnNewMenu_3 = new JMenu("Option");
		menuBar.add(mnNewMenu_3);
		
		JMenuItem mntmNewMenuItem_10 = new JMenuItem("Show Developer");
		mntmNewMenuItem_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame J = new JFrame();
				J.setTitle("개발자들");
				J.setSize(300, 150);
				J.setLocationRelativeTo(null);
				J.setVisible(true);
				
				JLabel text = new JLabel("목포대학교 강성우 , 박희원");
				text.setHorizontalAlignment(JLabel.CENTER);
				text.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
				J.getContentPane().add(text);
			}
		});
		mnNewMenu_3.add(mntmNewMenuItem_10);
		
		JMenuItem mntmNewMenuItem_9 = new JMenuItem("Show Line Number");
		mntmNewMenuItem_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (lineNumber.isVisible())
					lineNumber.setVisible(false);
				else
					lineNumber.setVisible(true);
			}
		});
		mnNewMenu_3.add(mntmNewMenuItem_9);
		
		JMenuItem mntmNewMenuItem_11 = new JMenuItem("Show Source Code");
		mntmNewMenuItem_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String fileName = "./src/myjava/Eclipse_Compiler.java";
				StringBuffer source = FileUtil.read(fileName);
				
				JFrame J = new JFrame();
				JTextArea text = new JTextArea();
				JScrollPane scrollPane_3 = new JScrollPane();
				
				text.setEditable(false);
				text.setFont(new Font("Dialog", Font.PLAIN, 20));  // 폰트 Dialog, PlAIN, 20
				text.setText(source.toString());
				
				scrollPane_3.setViewportView(text);
				
				J.setTitle(fileName);
				J.setSize(500, 500);
				J.setLocationRelativeTo(null);
				J.setVisible(true);
				J.getContentPane().add(scrollPane_3);
			}
		});
		mnNewMenu_3.add(mntmNewMenuItem_11);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.8);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		inputArea = new JTextArea();
		inputArea.setFocusable(true);
		inputArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == '\n' || e.getKeyChar() == '\b') {
					setLineNumber();
				}
			}
			public void keyPressed(KeyEvent e) {
				if ((e.getKeyCode() == KeyEvent.VK_V) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
					setLineNumber();
                }
			}
		});
		inputArea.setTabSize(4);
		scrollPane.setViewportView(inputArea);
		inputArea.setFont(new Font("Dialog", Font.PLAIN, 20));  // 폰트 Dialog, PlAIN, 20
		
		Box verticalBox = Box.createVerticalBox();
		scrollPane.setRowHeaderView(verticalBox);
		
		lineNumber = new JTextArea();
		lineNumber.setEditable(false);
		lineNumber.setTabSize(4);
		lineNumber.setColumns(3);
		lineNumber.setFont(new Font("Dialog", Font.PLAIN, 20));  // 폰트 Dialog, PlAIN, 20
		verticalBox.add(lineNumber);
		setLineNumber();
		
		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane.setRightComponent(scrollPane_1);
		
		outputArea = new JTextArea();
		outputArea.setTabSize(4);
		outputArea.setEditable(false);
		scrollPane_1.setViewportView(outputArea);
	}
	
	// 현재 입력된 코드의 클래스 이름을 가져옴
	public String getClassName() {
		String className = inputArea.getText();
		String[] strArray = className.split(" ");
		for (int i = 0; i < strArray.length; i++)
		{
			if (strArray[i].equals("class")) {
				className = strArray[i + 1];
				break;
			}
		}
		return className;
	}
	
	// 줄 번호를 표시함
	public void setLineNumber() {
		lineNumber.setText("");
		for (int i = 1; i <= inputArea.getLineCount(); i++) {
			lineNumber.append(Integer.toString(i));
			lineNumber.append(FileUtil.enter);
		}
	}
}
