package com.itheima.ui;

import javax.swing.*;

public class RegisterJFrame extends JFrame {
    //跟注册相关的代码就写在这个类中

    public RegisterJFrame(){
        //设置画面宽高
        this.setSize(488,500);
        this.setVisible(true);

        //设置界面的标题
        this.setTitle("拼图 注册");
        //设置界面置顶(即界面一直在idea上面)
        this.setAlwaysOnTop(true);
        //设置界面居中
        this.setLocationRelativeTo(null);
        //设置关闭模式
        this.setDefaultCloseOperation(3);
    }
}
