

import javax.swing.*;

public class JFrameTest extends JFrame{
	public JFrameTest() {
		super("½ºÀ® JFrame Å×½ºÆ®");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(300, 200);
		setVisible(true);
	}
	
	public static void main(String args[]) {
		JFrameTest jf = new JFrameTest();
	}
}
