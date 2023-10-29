import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class doubleDeFrame extends JFrame {

	private JPanel contentPane;
	private static JTextField plainText1;
	private static JTextField cipherText1;
	private static JTextField mkeyText;
	private static JTextField plainText2;
	private static JTextField cipherText2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					doubleDeFrame frame = new doubleDeFrame();
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
	public doubleDeFrame() {
		setResizable(false);
		setAlwaysOnTop(true);
		setTitle("中间相遇攻击");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 739, 425);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("请输入两组明密文对：");
		lblNewLabel.setFont(new Font("宋体", Font.BOLD, 25));
		lblNewLabel.setBounds(247, 37, 263, 45);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("明文：");
		lblNewLabel_1.setFont(new Font("宋体", Font.BOLD, 20));
		lblNewLabel_1.setBounds(99, 102, 62, 24);
		contentPane.add(lblNewLabel_1);
		
		plainText1 = new JTextField();
		plainText1.setBounds(173, 106, 175, 21);
		contentPane.add(plainText1);
		plainText1.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("密文：");
		lblNewLabel_1_1.setFont(new Font("宋体", Font.BOLD, 20));
		lblNewLabel_1_1.setBounds(99, 152, 62, 24);
		contentPane.add(lblNewLabel_1_1);
		
		cipherText1 = new JTextField();
		cipherText1.setColumns(10);
		cipherText1.setBounds(173, 156, 175, 21);
		contentPane.add(cipherText1);
		
		JButton btnNewButton = new JButton("开始解密");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doubleDe();
			}
		});
		
		btnNewButton.setFont(new Font("宋体", Font.BOLD, 20));
		btnNewButton.setBounds(303, 225, 135, 33);
		contentPane.add(btnNewButton);
		btnNewButton.setFocusable(false);
		btnNewButton.setContentAreaFilled(false);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("密钥：");
		lblNewLabel_1_1_1.setFont(new Font("宋体", Font.BOLD, 20));
		lblNewLabel_1_1_1.setBounds(219, 303, 62, 24);
		contentPane.add(lblNewLabel_1_1_1);
		
		mkeyText = new JTextField();
		mkeyText.setEditable(false);
		mkeyText.setColumns(10);
		mkeyText.setBounds(291, 307, 254, 21);
		contentPane.add(mkeyText);
		
		JLabel lblNewLabel_1_2 = new JLabel("明文：");
		lblNewLabel_1_2.setFont(new Font("宋体", Font.BOLD, 20));
		lblNewLabel_1_2.setBounds(425, 102, 62, 24);
		contentPane.add(lblNewLabel_1_2);
		
		plainText2 = new JTextField();
		plainText2.setColumns(10);
		plainText2.setBounds(498, 106, 175, 21);
		contentPane.add(plainText2);
		
		cipherText2 = new JTextField();
		cipherText2.setColumns(10);
		cipherText2.setBounds(498, 156, 175, 21);
		contentPane.add(cipherText2);
		
		JLabel lblNewLabel_1_1_2 = new JLabel("密文：");
		lblNewLabel_1_1_2.setFont(new Font("宋体", Font.BOLD, 20));
		lblNewLabel_1_1_2.setBounds(425, 152, 73, 24);
		contentPane.add(lblNewLabel_1_1_2);
	}
	//两组明密文对——第一对用于加解密，第二对用于检验
	public static String CIPHER1=new String();
	public static String CIPHER2=new String();
	public static String PLAIN1=new String();
	public static String PLAIN2=new String();
	
	
	//-----------------------加密----------------------------
	public static String w1;
    public static String w2;
    //S-盒
    public static String[][] s_box = new String[][]{
            {"9", "4", "10", "11"},
            {"13", "1", "8", "5"},
            {"6", "2", "0", "3"},
            {"12", "14", "15", "7"}};
    //列混淆的固定矩阵
    public static int mix[][] = {
        {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15},
        {0, 4, 8, 12, 3, 7, 11, 15, 6, 2, 14, 10, 5, 1, 13, 9}};
    //* 定义轮常数
    public static String rcon1 = new String("10000000");
    public static String rcon2 = new String("00110000");
    public static String S_Selected(String s) {
        //横坐标
        String hori = s.substring(0,2);
        String hori1 = s.substring(4,6);
        //纵坐标
        String vert = s.substring(2,4);
        String vert1 = s.substring(6,8);
        String t1=s_box[Integer.parseInt(hori,2)][Integer.parseInt(vert,2)];
        String t2=s_box[Integer.parseInt(hori1,2)][Integer.parseInt(vert1,2)];
        String ans1=decimalToBinary(t1);
        String ans2=decimalToBinary(t2);
        String ans=ans1+ans2;
        return ans;
    }
    public static String decimalToBinary(String num) {
        String decimal = Integer.toBinaryString(Integer.parseInt(num));
        //保证4位bit
        if(decimal.length()<4) {
            if(decimal.length()==3)
                decimal = new String("0")+decimal;
            else if(decimal.length()==2)
                decimal = new String("00")+decimal;
            else if(decimal.length()==1)
                decimal = new String("000")+decimal;
        }
        return decimal;
    }
    public static String four(String s){
        String a = new String("");
        //保证4位bit
        if(s.length()<4) {
            if(s.length()==3)
                a = new String("0")+s;
            else if(s.length()==2)
                a = new String("00")+s;
            else if(s.length()==1)
                a = new String("000")+s;
            
        }else {
            a = s;
        }
        return a;
    }
    public static String leturn(String s){
        String a = s.substring(0,4);
        String b = s.substring(4,8);
        String temp = b+a;
        return temp;
    }
    public static int change(String s){
        int sum = 0;
        for(int i = 0;i < s.length();i++){
            char ch = s.charAt(i);
            if(ch > '2' || ch < '0')
                throw new NumberFormatException(String.valueOf(i));
            sum = sum * 2 + (s.charAt(i) - '0');
        }
        return sum;
    }
    
    public static String en_mKey=new String();
    //st为待加密明文
    public static String st=new String();
    
    //获取加密主密钥
    public static void getKEY() {
        String s0 = en_mKey.substring(0,8);
        String s11 = en_mKey.substring(8,16);
        String s12 = leturn(s11);
        String s13 = S_Selected(s12);
        String s1 = XOR(s13, rcon1);
        //扩展密钥1
        String w11 = XOR(s0, s1);
        String w12 = XOR(w11, s11);
        w1 = w11+w12;
        //扩展密钥2
        String s21 = leturn(w12);
        String s22 = S_Selected(s21);
        String s23 = XOR(s22, rcon2);
        String w21 = XOR(s23, w11);
        String w22 = XOR(w21, w12);
        w2 = w21+w22;
    }
    public static String colmix(String s){
        String s00 = s.substring(0,4);
        String s10 = s.substring(4,8);
        String s01 = s.substring(8,12);
        String s11 = s.substring(12,16);
        int a001 = mix[0][change(s00)];
        int a002 = mix[1][change(s10)];
        int a011 = mix[0][change(s01)];
        int a012 = mix[1][change(s11)];
        int a101 = mix[1][change(s00)];
        int a102 = mix[0][change(s10)];
        int a111 = mix[1][change(s01)];
        int a112 = mix[0][change(s11)];
        String a00 = XOR(four(Integer.toBinaryString(a001)), four(Integer.toBinaryString(a002)));
        String a01 = XOR(four(Integer.toBinaryString(a011)), four(Integer.toBinaryString(a012)));
        
        String a10 = XOR(four(Integer.toBinaryString(a101)), four(Integer.toBinaryString(a102)));
        
        String a11 = XOR(four(Integer.toBinaryString(a111)), four(Integer.toBinaryString(a112)));
        String a = a00+a10+a01+a11;
        return a;
    }
    //加密
    public static void encrypt() {
        //length<8 ascii转二进制
        if(st.length()<8) {
            st=decimalToBinary(st);
        }
        String st1 = XOR(st, en_mKey);
        String up1 = st1.substring(0,8);
        String down1 = st1.substring(8,16);
        String up11 = S_Selected(up1);
        String down11 = S_Selected(down1);
        //行移位
        String st2 = up11.substring(0,4)+down11.substring(4,8)+down11.substring(0,4)+up11.substring(4,8);
        String st3 = colmix(st2);
        String st4 = XOR(st3, w1);
        String up2 = st4.substring(0,8);
        String down2 = st4.substring(8,16);
        String up21 = S_Selected(up2);
        String down21 = S_Selected(down2);
        //行移位
        String st5 = up21.substring(0,4)+down21.substring(4,8)+down21.substring(0,4)+up21.substring(4,8);
        code = XOR(st5,w2);
    }
	//-----------------------解密----------------------------
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
			//cipher为待解密密文
			public static String cipher=new String();
			//mKey为解密主密钥
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
	        public static String plain=new String();
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
				plain=XOR((S00+S10+S01+S11),mKey);
	        }
	        //保证密钥16bit
	        public static String ensure16bit(String s) {
	        	StringBuffer sb=new StringBuffer();
	        	for(int i=0;i<16-s.length();i++) {
	        		sb.append("0");
	        	}
	        	return sb+s;		
	        }
	        //遍历主密钥
	        public static String getMkey(int i) {
	        	String s=Integer.toBinaryString(i);
	        	String key=ensure16bit(s);
	        	return key;
	        }
	      //加密map,密文
	        public static Map<String,String> map1=new HashMap<>();
	        //code为加密后结果
	        public static String code=new String();
	        //解密map
	        public static Map<String, String> map2 = new HashMap<>();
	        //存储密钥
	        public static String MKEY=new String();
	        public static ArrayList<String> arrayList=new ArrayList<String>();
	        
	        //中间相遇攻击
	        public static void doubleDe() {
	        	CIPHER1=cipherText1.getText();
				CIPHER2=cipherText2.getText();
				PLAIN1=plainText1.getText();
				PLAIN2=plainText2.getText();
				if(CIPHER1.length()!=16||CIPHER2.length()!=16||PLAIN1.length()!=16||PLAIN2.length()!=16) {
					JOptionPane.showMessageDialog(null, "请输入正确格式的两组明密文对！");
				}else {
					//赋值PLAIN明文-加密
					st=PLAIN1;
					//CIPHER密文-解密					
					cipher=CIPHER1;
					ArrayList<String> arrayList=new ArrayList<>();
					for(int i=0;i<65535;i++) {
						arrayList.add(getMkey(i));
					}
					Iterator<String> iterator=arrayList.iterator();
					while(iterator.hasNext()) {
						String K=iterator.next();
						//解密--K2
						mKey=K;
						getKey();
						decrypt();
						//存储<plain, mKey>键值对，这里的plain是所解密的中间密文
						map2.put(plain, mKey);
						//加密--K1
						en_mKey=K;
						getKEY();
						encrypt();
						//存储键值对
						map1.put(code, en_mKey);					
					}
					//进行匹配--明文plain作key，获取两个map中对应value-mKey值
					for(String key1 : map1.keySet()) {
					    String en_key = map1.get(key1);
					    //判断map2是否存在相同的明文plain的key
					    if(map2.containsKey(key1)) {
					    String de_key = map2.get(key1);					    
					    //第二对明密文对进行验证
					    mKey=de_key;
					    cipher=CIPHER2;
					    getKey();
					    decrypt();
					    String temp=plain;
					    //第二轮
					    mKey=en_key;
					    cipher=temp;
					    getKey();
					    decrypt();			
					    String last=plain;
					    //如果第二对明密文对也符合该mKey,则输出该密钥	    
					    if(last==PLAIN2){
					    	MKEY=en_key+de_key;
					    	mkeyText.setText(MKEY);
					     	}
					    }
					}
					if(mkeyText.getText().length()==0) {
						JOptionPane.showMessageDialog(null, "无解");
					}
				}
				
	        	//测试案例
				//明文：1101111101111110
				//密文：0011110000111011
				//密钥K2+K1:00101101010101110010110101010101
				
				//明文：1101100110011110
				//密文：0011110100111011
				//密钥K2+K1:00101101010101110010110101010101
				
				
				
		        //第一轮：K1：0010110101010101
		      	//C1：0011110000111011
		      	//P1：0110101110100011
				
		        //第二轮：C2=P1：0110101110100011
		        //K2：0010110101010111
		        //P2：1101111101111110
				
				//双重、三重解密举例
		        //K:k2+k1 0010110101010111 0010110101010101
		        
		        //K1：0010110101010101
		      	//C1：0011110100111011
		      	//P1：0110011100000011
				
		        //第二轮：C2=P1：0110011100000011
		        //K2：0010110101010111
		        //P2：1101100110011110
				
	        }
}
