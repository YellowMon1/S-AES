package zsy;

import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.Random;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CBC extends JFrame{
    private JPanel contentPane;
    JTextField mainKey;
    JTextField bigText;
    JTextField smallText;
    public static String w1;
    public static String w2;
    public static String key;
    public static String smText;
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
    public static String XOR(String s1,String s2) {
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<s1.length();i++) {
            if(s1.charAt(i)==s2.charAt(i)) {
                sb.append("0");
            }
            else {
                sb.append("1");
            }
        }
        String s=new String(sb);
        return s;
    }
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
        }
        else
            a = s;
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
    public static void enkey(String s){
        String t1 = s.substring(0,8);
        String t2 = s.substring(8,16);
        String s12 = leturn(t2);
        String s13 = S_Selected(s12);
        String s1 = XOR(s13, rcon1);
        //扩展密钥1
        String w11 = XOR(t1, s1);
        String w12 = XOR(w11, t2);
        w1 = w11+w12;
        //扩展密钥2
        String s21 = leturn(w12);
        String s22 = S_Selected(s21);
        String s23 = XOR(s22, rcon2);
        String w21 = XOR(s23, w11);
        String w22 = XOR(w21, w12);
        w2 = w21+w22;
    }
    public CBC() {
        setResizable(false);
        setTitle("S-AES三重加密");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 340);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        JPanel panel = new JPanel();
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel main = new JLabel("初始化向量：");
        main.setFont(new Font("宋体", Font.PLAIN, 16));
        main.setBounds(30, 0, 208, 29);
        panel.add(main);

        mainKey = new JTextField();
        mainKey.setEditable(false);
        mainKey.setColumns(10);
        mainKey.setBounds(30, 32, 260, 43);
        panel.add(mainKey);

        JLabel text = new JLabel("请输入待加密明文(n bits)：");
        text.setFont(new Font("宋体", Font.PLAIN, 16));
        text.setBounds(30, 80, 208, 30);
        panel.add(text);

        bigText = new JTextField();
//        smallText.setEditable(false);
        bigText.setColumns(10);
        bigText.setBounds(30, 113, 260, 43);
        panel.add(bigText);

        JLabel text1 = new JLabel("加密后密文：");
        text1.setFont(new Font("宋体", Font.PLAIN, 16));
        text1.setBounds(40, 180, 100, 30);
        panel.add(text1);

        smallText = new JTextField();
        smallText.setEditable(false);
        smallText.setColumns(10);
        smallText.setBounds(145, 175, 240, 41);
        panel.add(smallText);

        JButton kb = new JButton("获取");
        kb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String a = new String("01");
                key = new String("");
                Random random = new Random();
                for(int i=0;i<16;i++){
                    int j = random.nextInt(2);
                    key = key+a.charAt(j);
                }
                System.out.println(key);
                mainKey.setText(key);
            }
        });
        kb.setFont(new Font("宋体", Font.PLAIN, 16));
        kb.setBounds(305, 34, 100, 38);
        panel.add(kb);

        JButton decode = new JButton("加密");
        decode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String st = bigText.getText();
                smText = new String("");
                //length<8 ascii转二进制
                if(st.length()<8) {
                    st=decimalToBinary(st);
                }
                enkey(key);
                int len = st.length();
                int round = len/16;
                String midText = new String("");
                String s0;
                for(int k=0;k<round;k++){
                    int i = 16*k;
                    int j = 16*(k+1);
                    String s = st.substring(i,j);
                    if(k==0){
                        s0 = XOR(s,key);
                    }
                    else {
                        s0 = XOR(s, midText);
                    }
                    String b = s0.substring(0,8);
                    String e = s0.substring(8,16);
                    String b1 = S_Selected(b);
                    String e1 = S_Selected(e);
                    //行移位
                    String s1 = b1.substring(0,4)+e1.substring(4,8)+e1.substring(0,4)+b1.substring(4,8);
                    //列混淆
                    String s2 = colmix(s1);
                    String s3 = XOR(s2,w1);
                    String b0 = s3.substring(0,8);
                    String e0 = s3.substring(8,16);
                    String b2 = S_Selected(b0);
                    String e2 = S_Selected(e0);
                    String s4 = b2.substring(0,4)+e2.substring(4,8)+e2.substring(0,4)+b2.substring(4,8);
                    midText = XOR(s4,w2);
                    smText = smText+midText;
                }
                System.out.println(smText);
                smallText.setText(smText);
            }
        });
        decode.setFont(new Font("宋体", Font.PLAIN, 16));
        decode.setBounds(305,115, 100, 37);
        panel.add(decode);

        JButton back = new JButton("返回");
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dispose();
                new main().setVisible(true);
            }
        });
        back.setFont(new Font("宋体", Font.PLAIN, 18));
        back.setBounds(150, 240, 130, 38);
        panel.add(back);
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CBC frame = new CBC();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
