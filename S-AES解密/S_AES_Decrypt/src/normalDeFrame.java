import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.Scanner;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class normalDeFrame extends JFrame {

	private JPanel contentPane;
	private static JTextField cipherText;
	private static JTextField mKeyText;
	private static JTextField PlainText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					normalDeFrame frame = new normalDeFrame();
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
	public normalDeFrame() {
		setResizable(false);
		setAlwaysOnTop(true);
		setTitle("S-AES普通解密");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 505, 393);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("请输入：");
		lblNewLabel.setFont(new Font("宋体", Font.BOLD, 25));
		lblNewLabel.setBounds(193, 36, 125, 45);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("密文：");
		lblNewLabel_1.setFont(new Font("宋体", Font.BOLD, 20));
		lblNewLabel_1.setBounds(121, 102, 62, 24);
		contentPane.add(lblNewLabel_1);
		
		cipherText = new JTextField();
		cipherText.setBounds(193, 106, 175, 21);
		contentPane.add(cipherText);
		cipherText.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("密钥：");
		lblNewLabel_1_1.setFont(new Font("宋体", Font.BOLD, 20));
		lblNewLabel_1_1.setBounds(121, 156, 62, 24);
		contentPane.add(lblNewLabel_1_1);
		
		mKeyText = new JTextField();
		mKeyText.setColumns(10);
		mKeyText.setBounds(193, 156, 175, 21);
		contentPane.add(mKeyText);
		
		JButton btnNewButton = new JButton("开始解密");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mKey=mKeyText.getText();
				cipher=cipherText.getText();
				//保证输入正确格式的密文密钥
				if(mKey.length()!=16 ||cipher.length()!=16) {
					JOptionPane.showMessageDialog(null, "请输入正确的密钥密文！");
					//重置文本框
					cipherText.setText("");
					mKeyText.setText("");
					PlainText.setText("");
				}else {
				//获得K1,K2,mKey,cipher
				getKey();
				//进行解密
				decrypt();
				//结果显示
				PlainText.setText(plainText);
				}
			}
		});
		btnNewButton.setFont(new Font("宋体", Font.BOLD, 20));
		btnNewButton.setBounds(183, 211, 135, 33);
		contentPane.add(btnNewButton);
		btnNewButton.setFocusable(false);
		btnNewButton.setContentAreaFilled(false);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("明文：");
		lblNewLabel_1_1_1.setFont(new Font("宋体", Font.BOLD, 20));
		lblNewLabel_1_1_1.setBounds(121, 270, 62, 24);
		contentPane.add(lblNewLabel_1_1_1);
		
		PlainText = new JTextField();
		PlainText.setEditable(false);
		PlainText.setColumns(10);
		PlainText.setBounds(193, 270, 175, 21);
		contentPane.add(PlainText);
	}
	//异或
		public static String XOR(String s1,String s2) {
			StringBuffer sb=new StringBuffer();
			int l=s1.length();
			for(int i=0;i<l;i++) {
				if(s1.charAt(i)==s2.charAt(i)) {
					sb.append("0");
				}else {
					sb.append("1");
				}
			}
			return new String(sb);
		}
		//S盒,逆S盒
		public static int[][] S_BOX= new int[][]{{9,4,10,11},
												 {13,1,8,5},
												 {6,2,0,3},
												 {12,14,15,7}};
		public static int[][] S1_BOX=new int[][]{{10,5,9,11},
												 {1,7,8,15},
												 {6,0,2,3},
												 {12,4,13,14}};
												
		//RCON轮常数
		public static String RCON1=new String("10000000");
		public static String RCON2=new String("00110000");
		//密钥扩展-g函数
		public static String G(String s,int n) {
			String R=s.substring(0, 4);
			String L=s.substring(4, 8);
			//S盒代换
			String L1=L.substring(0, 2);
			String L2=L.substring(2, 4);
			String R1=R.substring(0, 2);
			String R2=R.substring(2,4);
			//parseInt(n,m)其中m代表该n为二进制数
			int ans=S_BOX[Integer.parseInt(L1,2)][Integer.parseInt(L2,2)];
			int ans1=S_BOX[Integer.parseInt(R1,2)][Integer.parseInt(R2,2)];
			//转2进制String
			String L_ans=Integer.toBinaryString(ans);
			//保证4bit
			L_ans=Ensure4Bit(L_ans);
			String R_ans=Integer.toBinaryString(ans1);
			R_ans=Ensure4Bit(R_ans);
			//初步结果
			String m=L_ans+R_ans;
			String s1=new String();
			if(n==1) {
				s1=XOR(m, RCON1);
			}else {
				s1=XOR(m,RCON2);
			}
			return s1;
		}
		//密钥扩展
		public static String W2=new String();
		public static String W3=new String();
		public static String W4=new String();
		public static String W5=new String();
		public static String K1=new String();//第一轮密钥
		public static String K2=new String();//第二轮密钥
		public static String cipher=new String();//获取输入密文
		public static String mKey=new String();//获取输入密钥
		
		public static void getKey() {
			String W0=mKey.substring(0, 8);
			//赋值w2-w5
			String W1=mKey.substring(8);
			W2=XOR(W0,G(W1,1));
			W3=XOR(W2,W1);
			W4=XOR(W2,G(W3,2));
			W5=XOR(W4,W3);
			K1=W2+W3;
			K2=W4+W5;
		}
		//半字节代替求逆
		public static String byteSub(String s) {
			String L=s.substring(0, 2);
			String R=s.substring(2);
			int m=S1_BOX[Integer.parseInt(L,2)][Integer.parseInt(R,2)];
			String ans=Integer.toBinaryString(m);
			//确保4bit
			ans=Ensure4Bit(ans);
			return ans;
		}
		//密文处理
		public static String S00=new String();
		public static String S01=new String();
		public static String S10=new String();
		public static String S11=new String();
		//行移位求逆
		public static void rowShift() {
			String temp=S11;
			S11=S10;
			S10=temp;
		}
		//确保4bit
		public static String Ensure4Bit(String s) {
			if(s.length()<4) {
				StringBuffer sb=new StringBuffer();
				for(int i=0;i<4-s.length();i++) {
					sb.append("0");
				}
				s=sb+s;
			}
			return s;
		}
		//乘法矩阵
		public static int[][] Multi=new int[][] {{0,2,4,6,8,10,12,14,3,1,7,5,11,9,15,13},//2乘[0][]
			                                     {0,9,1,8,2,11,3,10,4,13,5,12,6,15,7,14}};//9乘[1][]
	    //列混淆求逆
        public static void confCol() {
        	//S00=9cS00+2cS10
        	int s1=Multi[1][Integer.parseInt(S00,2)];//9cS00
        	int s2=Multi[0][Integer.parseInt(S10,2)];//2cS10
        	//确保4bit
        	String m1=Ensure4Bit(Integer.toBinaryString(s1));
        	String m2=Ensure4Bit(Integer.toBinaryString(s2));
        	
        	//S01=9cS01+2cS11
        	int s3=Multi[1][Integer.parseInt(S01,2)];//9cS01
        	int s4=Multi[0][Integer.parseInt(S11,2)];//2cS11
        	String m3=Ensure4Bit(Integer.toBinaryString(s3));
        	String m4=Ensure4Bit(Integer.toBinaryString(s4));
        	
        	//S10=2cS00+9cS10
        	int s5=Multi[0][Integer.parseInt(S00,2)];//2cS00
        	//System.out.println("s5为："+s5);
        	int s6=Multi[1][Integer.parseInt(S10,2)];//9cS10
        	//System.out.println("s6为："+s6);
        	String m5=Ensure4Bit(Integer.toBinaryString(s5));
        	String m6=Ensure4Bit(Integer.toBinaryString(s6));
        	
        	//S11=2cS01+9cS11
        	int s7=Multi[0][Integer.parseInt(S01,2)];//2cS01
        	int s8=Multi[1][Integer.parseInt(S11,2)];//9cS11
        	String m7=Ensure4Bit(Integer.toBinaryString(s7));
        	String m8=Ensure4Bit(Integer.toBinaryString(s8));
        	
        	//最后赋值，防止被提前覆盖
        	S00=XOR(m1,m2);
        	S01=XOR(m3,m4);
        	S10=XOR(m5,m6);
        	S11=XOR(m7,m8);
        }
        public static String plainText=new String();
        //解密主流程
        public static void decrypt() {
        	//轮密钥加
        	String s=XOR(cipher,K2);
        	//行移位求逆
        	//[s00,s01]
			//[s10,s11]
        	S00=s.substring(0, 4);
			S10=s.substring(4, 8);
			S01=s.substring(8, 12);
			S11=s.substring(12);
			rowShift();
			//半字节代替求逆
			S00=byteSub(S00);
			S01=byteSub(S01);
			S10=byteSub(S10);
			S11=byteSub(S11);
        	//第二次轮密钥加
			String s1=XOR((S00+S10+S01+S11),K1);
			S00=s1.substring(0, 4);
			S10=s1.substring(4, 8);
			S01=s1.substring(8, 12);
			S11=s1.substring(12);
			//列混淆求逆
			confCol();
			
        	//行移位求逆
			rowShift();
			//半字节代替求逆
			S00=byteSub(S00);
			S01=byteSub(S01);
			S10=byteSub(S10);
			S11=byteSub(S11);
        	//第三次轮密钥加
			plainText=XOR((S00+S10+S01+S11),mKey);
        }
        //测试用例
		//密钥：0010110101010101
		//密文：0011110000111011
		//明文：0110101110100011
		
        //密钥：0011110111010101
        //密文：0110101110100011
        //明文：0101110010111000
        
		//密钥：1010011100111011
		//密文：0000011100111000
		//明文：0110111101101011
        
        
		
}
