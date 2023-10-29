package zsy;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class main extends JFrame {
    private JPanel contentPane;
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    main frame = new main();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public main(){
        setResizable(false);
        setTitle("S-AES加密");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 350);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        JPanel panel = new JPanel();
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel main = new JLabel("S-DES加密系统");
        main.setFont(new Font("宋体", Font.BOLD,25));
        main.setBounds(130, 60, 210, 40);
        panel.add(main);

        JButton normal = new JButton("普通加密");
        normal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dispose();
                new ase().setVisible(true);
            }
        });
        normal.setFont(new Font("宋体", Font.PLAIN, 18));
        normal.setBounds(60, 170, 150, 38);
        panel.add(normal);

        JButton doubl = new JButton("双重加密");
        doubl.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dispose();
                new doub().setVisible(true);
            }
        });
        doubl.setFont(new Font("宋体", Font.PLAIN, 18));
        doubl.setBounds(240, 170, 150, 38);
        panel.add(doubl);

        JButton tri = new JButton("三重加密");
        tri.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dispose();
                new trib().setVisible(true);
            }
        });
        tri.setFont(new Font("宋体", Font.PLAIN, 18));
        tri.setBounds(240, 220, 150, 38);
        panel.add(tri);

        JButton csc = new JButton("CSC模式加密");
        csc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dispose();
                new CBC().setVisible(true);
            }
        });
        csc.setFont(new Font("宋体", Font.PLAIN, 18));
        csc.setBounds(60, 220, 150, 38);
        panel.add(csc);
    }
}
