package zsy;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ase extends JFrame{
    private JPanel contentPane;
    JTextField mainKey;
    JTextField bigText;
    JTextField key1;
    JTextField key2;
    JTextField smallText;
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
    public ase() {
        setResizable(false);
        setTitle("S-AES加密");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 400);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        JPanel panel = new JPanel();
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel main = new JLabel("请输入密钥(16 bits)：");
        main.setFont(new Font("宋体", Font.PLAIN, 16));
        main.setBounds(45, 0, 208, 29);
        panel.add(main);

        mainKey = new JTextField();
//        mainKey.setEditable(false);
        mainKey.setColumns(10);
        mainKey.setBounds(45, 32, 240, 43);
        panel.add(mainKey);

        JLabel text = new JLabel("请输入待加密明文：");
        text.setFont(new Font("宋体", Font.PLAIN, 16));
        text.setBounds(45, 80, 208, 30);
        panel.add(text);

        bigText = new JTextField();
//        smallText.setEditable(false);
        bigText.setColumns(10);
        bigText.setBounds(45, 113, 240, 43);
        panel.add(bigText);

        JLabel k1 = new JLabel("扩展密钥1：");
        k1.setFont(new Font("宋体", Font.PLAIN, 16));
        k1.setBounds(60, 170, 100, 30);
        panel.add(k1);

        key1 = new JTextField();
        key1.setEditable(false);
        key1.setColumns(10);
        key1.setBounds(165, 165, 240, 41);
        panel.add(key1);

        JLabel k2 = new JLabel("扩展密钥2：");
        k2.setFont(new Font("宋体", Font.PLAIN, 16));
        k2.setBounds(60, 215, 100, 30);
        panel.add(k2);

        key2 = new JTextField();
        key2.setEditable(false);
        key2.setColumns(10);
        key2.setBounds(165, 210, 240, 41);
        panel.add(key2);

        JLabel text1 = new JLabel("加密后密文：");
        text1.setFont(new Font("宋体", Font.PLAIN, 16));
        text1.setBounds(60, 260, 100, 30);
        panel.add(text1);

        smallText = new JTextField();
        smallText.setEditable(false);
        smallText.setColumns(10);
        smallText.setBounds(165, 255, 240, 41);
        panel.add(smallText);

        JButton kb = new JButton("密钥扩展");
        kb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String mKey = mainKey.getText();
                String s0 = mKey.substring(0,8);
                String s11 = mKey.substring(8,16);
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
                key1.setText(w1);
                key2.setText(w2);
            }
        });
        kb.setFont(new Font("宋体", Font.PLAIN, 16));
        kb.setBounds(290, 34, 130, 38);
        panel.add(kb);

        JButton decode = new JButton("加密");
        decode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String st = bigText.getText();
                String mKey = mainKey.getText();
                //length<8 ascii转二进制
                if(st.length()<8) {
                    st=decimalToBinary(st);
                }
                String st1 = XOR(st, mKey);
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
                String smText = XOR(st5,w2);
                smallText.setText(smText);
            }
        });
        decode.setFont(new Font("宋体", Font.PLAIN, 16));
        decode.setBounds(290, 115, 100, 37);
        panel.add(decode);

        JButton back = new JButton("返回");
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dispose();
                new main().setVisible(true);
            }
        });
        back.setFont(new Font("宋体", Font.PLAIN, 18));
        back.setBounds(170, 310, 130, 38);
        panel.add(back);
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ase frame = new ase();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
