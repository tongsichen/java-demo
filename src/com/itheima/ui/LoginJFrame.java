package com.itheima.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class LoginJFrame extends JFrame implements MouseListener {
    //创建一个集合存储正确的用户名和密码
    static ArrayList<User> list = new ArrayList<>();

    static {
        list.add(new User("zhangsan", "123"));
        list.add(new User("lisi", "1234"));
    }

    //登录界面
    public LoginJFrame() {
        //空参构造,在创建界面的时候进行初始化
        initFrame();

        //在这个界面中添加内容
        initView();

        //让当前界面显示出来
        this.setVisible(true);

    }

    //添加注册按钮
    JButton register = new JButton();
    //添加登录按钮
    JButton login = new JButton();
    //添加用户输入框
    JTextField username = new JTextField();
    //添加密码输入框
    JTextField password = new JTextField();
    //验证码的输入框
    JTextField code = new JTextField();
    //正确的验证码
    JLabel rightCode = new JLabel();

    public void initFrame() {
        //设置宽高
        this.setSize(488, 430);
        //设置界面的标题
        this.setTitle("拼图 登录");
        //设置界面置顶(即界面一直在idea上面)
        this.setAlwaysOnTop(true);
        //设置界面居中
        this.setLocationRelativeTo(null);
//设置关闭模式
        this.setDefaultCloseOperation(3);
        this.setLayout(null);
    }

    public void initView() {
        //添加用户名文字
        JLabel usernameText = new JLabel(new ImageIcon("puzzlegame\\image\\login\\用户名.png"));
        usernameText.setBounds(116, 135, 47, 17);
        this.getContentPane().add(usernameText);

        username.setBounds(195, 134, 200, 30);
        this.getContentPane().add(username);
        //添加密码文字
        JLabel passwordText = new JLabel(new ImageIcon("puzzlegame\\image\\login\\密码.png"));
        passwordText.setBounds(130, 195, 32, 16);
        this.getContentPane().add(passwordText);

        password.setBounds(195, 195, 200, 30);
        this.getContentPane().add(password);
        //验证码提示
        JLabel codeText = new JLabel(new ImageIcon("puzzlegame\\image\\login\\验证码.png"));
        codeText.setBounds(133, 256, 50, 30);
        this.getContentPane().add(codeText);
//code是验证码的输入框,rightcode才是验证码
        code.setBounds(195, 256, 100, 30);
        this.getContentPane().add(code);

        String codeStr = CodeUtil.getCode();//静态方法调用:类名.方法名

        //设置内容
        rightCode.setText(codeStr);
        //绑定鼠标事件
        rightCode.addMouseListener(this);
        //位置和宽高
        rightCode.setBounds(300, 256, 50, 30);
        //添加到界面
        this.getContentPane().add(rightCode);


        login.setBounds(123, 310, 128, 47);
        //setIcon()功能:为某个可视化组件（如按钮、标签、对话框等）设置一个图标（小图片/图像）。
        login.setIcon(new ImageIcon("puzzlegame\\image\\login\\登录按钮.png"));
        //去掉按钮的默认边框
        login.setBorderPainted(false);
        //去除按钮的默认背景
        login.setContentAreaFilled(false);
        this.getContentPane().add(login);
        //添加MouseListener监听
        login.addMouseListener(this);


        register.setBounds(256, 310, 128, 47);
        //setIcon()功能:为某个可视化组件（如按钮、标签、对话框等）设置一个图标（小图片/图像）。
        register.setIcon(new ImageIcon("puzzlegame\\image\\login\\注册按钮.png"));
        //去掉按钮的默认边框
        register.setBorderPainted(false);
        //去除按钮的默认背景
        register.setContentAreaFilled(false);
        this.getContentPane().add(register);
        //添加MouseListener监听
        register.addMouseListener(this);
        //添加背景图片
        JLabel background = new JLabel(new ImageIcon("puzzlegame\\image\\login\\background.png"));
        background.setBounds(0, 0, 470, 390);
        this.getContentPane().add(background);

    }

    //要展示用户名或密码错误
    public void showjDialog(String content) {
        //创建一个弹框对象
        JDialog jDialog = new JDialog();
        //给弹框设置大小
        jDialog.setSize(200, 150);
        //让弹框置顶
        jDialog.setAlwaysOnTop(true);
        //让弹框居中
        jDialog.setLocationRelativeTo(null);
        //弹框不关闭的情况下无法操作下面的界面
        jDialog.setModal(true);
        //创建JLabel对象管理文字并添加到弹框当中
        JLabel warning = new JLabel(content);
        warning.setBounds(0, 0, 200, 150);
        jDialog.getContentPane().add(warning);
        //让弹框展示出来
        jDialog.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == login) {
            //获取两个文本框输入框中的内容
            String usernameInput = username.getText();
            String passwordInput = password.getText();
            //获取用户输入的验证码
            String codeInput = this.code.getText();
            //创建一个User对象
            User userInfo = new User(usernameInput, passwordInput);
            System.out.println("用户输入的用户名为" + usernameInput);
            System.out.println("用户输入的密码为" + passwordInput);
            if (codeInput.length() == 0) {
                showjDialog("验证码不能为空");
            } else if (usernameInput.length() == 0 || passwordInput.length() == 0) {
//校验用户名和密码是否为空
                System.out.println("用户名或者密码为空");
                showjDialog("用户或者密码为空");
            } else if (!codeInput.equalsIgnoreCase(rightCode.getText())) {
                //equalsIgnoreCase()和equals()的区别:
                //equals()：区分大小写。必须两个字符串的字符完全一致（包括大小写）才返回 true。
                //equalsIgnoreCase()：不区分大小写。比较时会忽略字母的大小写（'A' 与 'a' 视为相同），只要字符顺序一致即返回 true。
                showjDialog("输入验证码错误");
            } else if (contains(userInfo)) {
                //为什么不能用list.contains(userInfo)
                //ArrayList.contains(Object o) 的内部实现是遍历集合，对每个元素调用 o.equals(element)。
                //在 Java 中，所有类默认继承 Object 类的 equals() 方法，
                // 而 Object.equals() 比较的是两个对象的内存地址（引用），而不是内容。
                System.out.println("用户和密码正确可以开始玩游戏了");
                //关闭当前登录界面
                this.setVisible(false);
                //打开游戏主界面
                //并把当前登录的用户传递给游戏界面
                new GameJFrame();
            } else {
                System.out.println("用户名或密码错误");
                showjDialog("用户名或密码错误");
            }
        } else if (e.getSource() == register) {
            System.out.println("点击了注册按钮");
        } else if (e.getSource() == rightCode) {
            System.out.println("更换验证码");
            String code = CodeUtil.getCode();
            rightCode.setText(code);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Object obj = e.getSource();
        if (obj == login) {
            login.setIcon(new ImageIcon("puzzlegame\\image\\login\\登录按下.png"));
        } else if (obj == register) {
            register.setIcon(new ImageIcon("puzzlegame\\image\\login\\注册按下.png"));
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Object obj = e.getSource();
        if (obj == login) {
            login.setIcon(new ImageIcon("puzzlegame\\image\\login\\登录按钮.png"));
        } else if (obj == register) {
            register.setIcon(new ImageIcon("puzzlegame\\image\\login\\注册按钮.png"));
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    //判断用户在集合中是否存在
    public boolean contains(User userInput) {
        for (int i = 0; i < list.size(); i++) {
            User rightUser = list.get(i);
            if (userInput.getName().equals(rightUser.getName()) && userInput.getPassword().equals(rightUser.getPassword())) {
                //有相同的代表存在，返回true，后面的不需要再比了
                return true;
            }
        }
//循环结束之后还没有找到就表示不存在
        return false;
    }
}
