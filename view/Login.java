package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame implements ActionListener{
	
	private JTextField textUn;
	private JTextField textPass;
	private JButton btnDN;
	private MainUI mainUI;
	
	public Login() {
		super("Cafe login");
		
		JPanel pC = new JPanel();
		JPanel pW = new JPanel();
		
		Box logoGroup = Box.createVerticalBox();
		ImageIcon imageIcon = new ImageIcon("src\\assets\\cafeLogo.png");
		ImageIcon scaledIcon = scaleImageIcon(imageIcon, 200, 200);
		JLabel imageLabel = new JLabel(scaledIcon);
		
		logoGroup.add(imageLabel);
		JLabel logoTitle = new JLabel("  Cafe system login");
		logoTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD | Font.ITALIC, 20));
		logoGroup.add(logoTitle);
		pW.add(logoGroup);
		add(pW,BorderLayout.NORTH);
		
		
		
		Box box = Box.createVerticalBox();
		Box boxUn = Box.createHorizontalBox();
		boxUn.add(new Label("username: "));
		boxUn.add(textUn=new JTextField(20));
		Box boxPass = Box.createHorizontalBox();
		boxPass.add(new Label("password: "));
		boxPass.add(textPass = new JPasswordField(20));
		box.add(boxUn);
		box.add(Box.createVerticalStrut(15));
		box.add(boxPass);
		box.add(Box.createVerticalStrut(15));
		Box boxDN = Box.createHorizontalBox();
		boxDN.add(btnDN = new JButton("Log in"));
		box.add(boxDN);
		pC.add(box);
		add(pC,BorderLayout.CENTER);
		
		btnDN.addActionListener(this);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setSize(400,400);
		
	}
    private static ImageIcon scaleImageIcon(ImageIcon icon, int width, int height) {
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
	public static void main(String[] args) {
		new Login();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		mainUI = new MainUI();
		this.setVisible(false);
	}
}
