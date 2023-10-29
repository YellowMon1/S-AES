import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class mainFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainFrame frame = new mainFrame();
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
	public mainFrame() {
		setResizable(false);
		setTitle("S-AES解密");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 505, 393);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("请选择解密方式：");
		lblNewLabel.setFont(new Font("宋体", Font.BOLD, 20));
		lblNewLabel.setBounds(90, 188, 165, 48);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("S-AES解密");
		lblNewLabel_1.setFont(new Font("宋体", Font.BOLD, 25));
		lblNewLabel_1.setBounds(176, 63, 133, 62);
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("普通解密");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				normalDeFrame ndf=new normalDeFrame();
				ndf.setVisible(true);
				ndf.setLocationRelativeTo(null);
			}
		});
		btnNewButton.setFont(new Font("宋体", Font.BOLD, 20));
		btnNewButton.setBounds(265, 201, 165, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("双重解密");
		btnNewButton_1.setFont(new Font("宋体", Font.BOLD, 20));
		btnNewButton_1.setBounds(265, 234, 165, 23);
		contentPane.add(btnNewButton_1);
		
		btnNewButton.setFocusable(false);
		btnNewButton.setContentAreaFilled(false);
		btnNewButton_1.setFocusable(false);
		btnNewButton_1.setContentAreaFilled(false);
		
		JButton btnNewButton_1_1 = new JButton("三重解密");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				tripleDeFrame tdf=new tripleDeFrame();
				tdf.setVisible(true);
				tdf.setLocationRelativeTo(null);
			}
		});
		btnNewButton_1_1.setFont(new Font("宋体", Font.BOLD, 20));
		btnNewButton_1_1.setFocusable(false);
		btnNewButton_1_1.setContentAreaFilled(false);
		btnNewButton_1_1.setBounds(265, 267, 165, 23);
		contentPane.add(btnNewButton_1_1);
		
		JButton btnNewButton_1_1_1 = new JButton("CBC解密");
		btnNewButton_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				CBC_de tdf=new CBC_de();
				tdf.setVisible(true);
				tdf.setLocationRelativeTo(null);
			}
		});
		btnNewButton_1_1_1.setFont(new Font("宋体", Font.BOLD, 20));
		btnNewButton_1_1_1.setFocusable(false);
		btnNewButton_1_1_1.setContentAreaFilled(false);
		btnNewButton_1_1_1.setBounds(265, 304, 165, 23);
		contentPane.add(btnNewButton_1_1_1);
		
		this.setLocationRelativeTo(null);
	}
}
