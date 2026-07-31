package com.itheima.ui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GameJFrame extends JFrame implements KeyListener, ActionListener {
    //规定GameJFrame这个界面表示的就是游戏主界面
    //以后跟游戏相关的所有逻辑都写在这个类里

    // 按照 4 个一组的方式添加到二维数组当中
    //目的:管理数据
    //加载图片的时候,会根据二维数组中的数据进行加载
    int[][] data = new int[4][4];

    //记录空白方块在二维数组中的位置
    int x = 0;
    int y = 0;

    int win[][] = {
            {1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}
    };

    //定义变量,用来统计步数
    int step = 0;

    Random r = new Random();

    //定义一个记录当前展示图片的路径
    String path = "puzzlegame\\image\\animal\\animal3\\";

    //创建选项下的条目对象
    JMenuItem replayItem = new JMenuItem("重新游戏");
    JMenuItem reLoginItem = new JMenuItem("重新登录");
    JMenuItem closeItem = new JMenuItem("关闭游戏");
    JMenuItem accountItem = new JMenuItem("公众号");
    //创建更换图片下的条目对象
    JMenuItem girl = new JMenuItem("美女");
    JMenuItem animal = new JMenuItem("动物");
    JMenuItem sport = new JMenuItem("运动");

    public GameJFrame() {
        //初始化界面
        initJFrame();
        //初始化菜单
        initJMenuBar();

        //初始化数据(打乱图片)
        initData();

        //初始化图片(根据打乱之后的结果去加载图片)
        initImage();

        //让界面显示出来,建议写在最后
        this.setVisible(true);
    }

    //初始化数据(打乱图片)
    private void initData() {
        //定义一个一维数组
        int[] tempArr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        //打乱
        //遍历数组,得到每一个对象,拿着每一个元素跟随机索引上的数据进行交换.
        for (int i = 0; i < tempArr.length; i++) {
            int index = r.nextInt(tempArr.length);//[0,length)
            //将遍历得到的每一个数据,跟随机索引上的数据进行交换
            int temp = tempArr[i];
            tempArr[i] = tempArr[index];
            tempArr[index] = temp;
        }
        //给二维数组添加数据
        //解法一:遍历一维数组每个元素,把每个元素依次添加到二维数组当中
        for (int i = 0; i < tempArr.length; i++) {
            if (tempArr[i] == 0) {
                x = i / 4;
                y = i % 4;
            }
            data[i / 4][i % 4] = tempArr[i];

        }
    }


    //初始化图片
    //添加图片的时候,需要按照二维数组中管理的数据添加图片
    private void initImage() {
        //清空之前的所有图片
        this.getContentPane().removeAll();

        if (victory()) {
            //显示胜利的图标
            JLabel winJLabel = new JLabel(new ImageIcon("puzzlegame\\image\\win.png"));
            winJLabel.setBounds(203, 283, 197, 73);
            this.getContentPane().add(winJLabel);
        }

        JLabel stepCount = new JLabel("步数" + step);
        stepCount.setBounds(50, 50, 100, 20);
        this.getContentPane().add(stepCount);

        //相对路径:把绝对路径从盘符到项目名称之前全部删掉
        //细节:先加载的图片在上,后加载的图片在下
        for (int i = 0; i < 4; i++) {
            //外循环--表示第i行
            for (int j = 0; j < 4; j++) {
                //内循环--表示第j列
                //获取当前要加载图片的序号
                int num = data[i][j];
                //创建一个图片ImageIcon的对象
                //ImageIcon icon1 =new ImageIcon("C:\\Users\\linlin\\Downloads\\java代码练习\\String-and-Set\\puzzlegame\\image\\animal\\animal3\\1.jpg")
                //创建一个JLabel对象(管理容器)
                JLabel jLabel = new JLabel(new ImageIcon(path + num + ".jpg"));             //指定图片位置
                jLabel.setBounds(105 * j + 83, 105 * i + 134, 105, 105);
                //给图片添加边框
                //0:表示让图片凸起来
                //1:让图片凹进去
                jLabel.setBorder(new BevelBorder(1));
                //把管理容器添加到界面中
                //this.add(jLabel)也可以是因为:因为 JFrame 的 add 方法内部帮你转发了。
                // 但设置布局时，强烈建议用 this.getContentPane().setLayout(null);，
                // 否则可能会出现组件位置不生效的诡异 bug。

                //getContentPane():
                /*JFrame 的结构比较复杂，它内部其实是分层的（从里到外）：
                RootPane（根面板）
                LayeredPane（分层面板，包含菜单栏等）
                ContentPane（内容面板）—— 这里才是你放游戏图片、按钮的地方
                GlassPane（玻璃面板，通常用于拦截鼠标事件）
                如果你直接往 JFrame 上放东西，Java 不知道你想放哪一层，所以就规定：“所有普通组件统一放到 ContentPane 上”。*/
                this.getContentPane().add(jLabel);
            }
        }
        //添加背景图片
        JLabel background = new JLabel(new ImageIcon("puzzlegame\\image\\background.png"));
        background.setBounds(40, 40, 508, 560);
        //把背景图片添加到界面中
        this.getContentPane().add(background);
        //刷新图片
        this.getContentPane().repaint();
    }


    private void initJMenuBar() {
        //先创建 JMenuBar（菜单栏）
        //再创建 JMenu（一级菜单，如 “功能”）
        //再创建 JMenuItem（菜单项，如 “重新游戏”）
        //把 JMenuItem 添加到 JMenu 里面
        //把 JMenu 添加到 JMenuBar 里面
        //最后再把 JMenuBar 添加到整个 Frame 窗口界面中

        //创建整个菜单对象
        JMenuBar jMenuBar = new JMenuBar();

        //创建菜单上的两个选项的对象 (功能 关于我们)
        JMenu functionJMenu = new JMenu("功能");
        JMenu aboutJMenu = new JMenu("关于我们");
        JMenu changeImage = new JMenu("更换图片");


        //将每个选项下面的条目添加到选项当中
        functionJMenu.add(replayItem);
        functionJMenu.add(reLoginItem);
        functionJMenu.add(closeItem);
        functionJMenu.add(changeImage);
        changeImage.add(girl);
        changeImage.add(animal);
        changeImage.add(sport);

        aboutJMenu.add(accountItem);
        //给条目绑定事件
        //给这个组件添加动作监听,当事件被触发之后,执行本类里面对应的代码
        replayItem.addActionListener(this);
        reLoginItem.addActionListener(this);
        closeItem.addActionListener(this);
        accountItem.addActionListener(this);
        girl.addActionListener(this);
        animal.addActionListener(this);
        sport.addActionListener(this);
        //将菜单的两个选项添加到菜单中
        jMenuBar.add(functionJMenu);
        jMenuBar.add(aboutJMenu);

        //给整个界面设置菜单
        this.setJMenuBar(jMenuBar);
    }


    private void initJFrame() {
        //设置画面宽高
        this.setSize(603, 680);
        //设置界面的标题
        this.setTitle("拼图单机版 v1.0");
        //设置界面置顶(即界面一直在idea上面)
        this.setAlwaysOnTop(true);
        //设置界面居中
        this.setLocationRelativeTo(null);
        //设置关闭模式
        //选中方法名,ctrl+b可以查看该方法的源码
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //取消默认的居中放置,左右取消了才会按照XY轴的形式添加组件
        this.getContentPane().setLayout(null);
        //给整个界面添加键盘监听事件
        //括号里的this:为什么能这么写：因为 addKeyListener() 方法要求传入一个 KeyListener 类型的参数，
        // 而 GameJFrame 类实现了 KeyListener 接口，所以 this（当前对象）就符合这个要求。
        this.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //按下不松时调用此方法
    @Override
    public void keyPressed(KeyEvent e) {
        if (victory()) {
            return;
        }
        int code = e.getKeyCode();
        if (code == 65) {
//把界面中所有图片删除
            this.getContentPane().removeAll();
            //加载第一张完整图片
            JLabel all = new JLabel(new ImageIcon(path + "all.jpg"));
            all.setBounds(83, 134, 420, 420);
            this.getContentPane().add(all);
            //加载背景图片
            JLabel background = new JLabel(new ImageIcon("puzzlegame\\image\\background.png"));
            background.setBounds(40, 40, 508, 560);
            //把背景图片添加到界面中
            this.getContentPane().add(background);
            //刷新图片
            this.getContentPane().repaint();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        //判断游戏是否胜利.如果胜利,此方法需直接结束,不能再执行下面的移动代码
        //reture:返回值,结束方法
        if (victory()) {
            return;
        }

        //对上下左右进行判断
        //左:37 上:38 右:39 下:40
        //A(65):重置图片位置
        //W(87):作弊码
        int code = e.getKeyCode();
        if (code == 38) {
            if (x == 3) {
                return;//结束方法
            }
            data[x][y] = data[x + 1][y];
            data[x + 1][y] = 0;
            x++;
            //调用方法按照最新的数字加载图片
            initImage();
        } else if (code == 40) {
            if (x == 0) {
                return;//结束方法
            }
            data[x][y] = data[x - 1][y];
            data[x - 1][y] = 0;
            x--;
            //每移动一次,计数器就自增一次
            step++;
            initImage();
        } else if (code == 37) {
            if (y == 3) {
                return;//结束方法
            }
            data[x][y] = data[x][y + 1];
            data[x][y + 1] = 0;
            y++;
            //每移动一次,计数器就自增一次
            step++;
            initImage();
        } else if (code == 39) {
            if (y == 0) {
                return;//结束方法
            }
            data[x][y] = data[x][y - 1];
            data[x][y - 1] = 0;
            y--;
            //每移动一次,计数器就自增一次
            step++;
            initImage();
        } else if (code == 65) {
            initImage();
        } else if (code == 87) {
            data = new int[][]{
                    {1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}
            };
            initImage();
        }
    }


    //判断data数据中的数据是否跟win数组中相同
    //如果全部相同,则返回true,否则返回false
    public boolean victory() {
        for (int i = 0; i < data.length; i++) {
            //i:依次表示二维数组里面的索引
            //data[i]依次表示每一个一维数组
            for (int j = 0; j < data.length; j++) {
                if (data[i][j] != win[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //获取当前被点击的条目对象
        Object obj = e.getSource();
        //判断
        if (obj == replayItem) {
            //让计数器清零
            step = 0;
            //再次打乱二维数组中的数据
            initData();
            //重新加载图片
            initImage();

        } else if (obj == reLoginItem) {
            //关闭当前界面
            this.setVisible(false);
            //打开登录界面
            new LoginJFrame();
        } else if (obj == closeItem) {
//直接关闭虚拟机即可
            System.exit(0);
        } else if (obj == accountItem) {
            //新建一个弹框对象
            JDialog jDialog = new JDialog();
            //创建一个管理图片的容器JLabel
            JLabel jLabel = new JLabel(new ImageIcon("puzzlegame\\image\\about.png"));
            //设置位置和宽高
            jLabel.setBounds(0, 0, 258, 258);
            //把图片添加到弹框当中
            jDialog.getContentPane().add(jLabel);
            //给弹框设置容器大小
            jDialog.setSize(344, 344);
            //让弹框置顶
            jDialog.setAlwaysOnTop(true);
            //让弹框居中
            jDialog.setLocationRelativeTo(null);
            //弹框不关闭就不能操作下面的界面
            jDialog.setModal(true);
            //让弹框显示出来
            jDialog.setVisible(true);
        } else if (obj == girl) {
            //随机选择图片
            int newImage = r.nextInt(12) + 1;
            path = "puzzlegame\\image\\girl\\girl" + newImage + "\\";
            //让计数器清零
            step = 0;
            //再次打乱二维数组中的数据
            initData();
            //重新加载图片
            initImage();
        } else if (obj == animal) {
            int newImage = r.nextInt(7) + 1;
            path = "puzzlegame\\image\\animal\\animal" + newImage + "\\";
            step = 0;
            initData();
            initImage();
        } else if (obj == sport) {
            int newImage = r.nextInt(9) + 1;
            path = "puzzlegame\\image\\sport\\sport" + newImage + "\\";
            step = 0;
            initData();
            initImage();
        }
    }
}
