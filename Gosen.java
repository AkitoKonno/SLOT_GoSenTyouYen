package gosen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Gosen {

    public static void main(String[] args) {
        Gosen gs = new Gosen();
        gs.start();
    }

    JFrame mainwindow;
    BufferStrategy st;
    long lasttime = System.currentTimeMillis();
    GraInfo ginfo = new GraInfo();
    MyGameDisplay display = new MyGameDisplay();

    //コンストラクタ
    Gosen() {
        this.mainwindow = new JFrame("5000兆円スロット");
        this.mainwindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainwindow.setSize(864, 600);
        this.mainwindow.setLocationRelativeTo(null);
        this.mainwindow.setResizable(false);
        this.mainwindow.setVisible(true);
        //バッファ関連
        this.mainwindow.setIgnoreRepaint(true);
        this.mainwindow.createBufferStrategy(2);
        this.st = this.mainwindow.getBufferStrategy();
        //キー関連
        this.mainwindow.addKeyListener(new MyKeyAdapter());
        //画像読み込み
        try{
            this.display.loadImage();
        }catch(IOException e){
            JOptionPane.showMessageDialog(this.mainwindow,"読み込みエラー");
        }
    }

    private void start() {
        Timer t = new Timer();
        t.schedule(new RenderTask(), 0, 16);
    }

    void render() {
        //時間計測
        long time = System.currentTimeMillis();

        //1フレーム分の秒数を求める
        this.ginfo.frametime = (time - this.lasttime) * 0.001;
        this.lasttime = time;
        this.ginfo.currenttime = time;

        Graphics2D g = (Graphics2D) this.st.getDrawGraphics();
        g.setBackground(Color.black);
        g.clearRect(0, 0, this.mainwindow.getWidth(), this.mainwindow.getHeight());
        ginfo.g = g;
        
        this.display.getCurrentDisplay().show(ginfo);
        
        g.dispose();
        this.st.show();
    }

    class RenderTask extends TimerTask {

        @Override
        public void run() {
            Gosen.this.render();
        }

    }

    class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            this.setValue(e.getKeyCode(), true);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            this.setValue(e.getKeyCode(), false);
        }

        private void setValue(int keycode, boolean b) {
            boolean[] keystate = Gosen.this.ginfo.keystate;
            switch (keycode) {
                case KeyEvent.VK_LEFT:
                    keystate[KEY_STATE.LEFT] = b;
                    break;
                case KeyEvent.VK_RIGHT:
                    keystate[KEY_STATE.RIGHT] = b;
                    break;
                case KeyEvent.VK_UP:
                    keystate[KEY_STATE.UP] = b;
                    break;
                case KeyEvent.VK_DOWN:
                    keystate[KEY_STATE.DOWN] = b;
                    break;
                case KeyEvent.VK_Z:
                    keystate[KEY_STATE.Z] = b;
                    break;
                case KeyEvent.VK_X:
                    keystate[KEY_STATE.X] = b;
                    break;
                case KeyEvent.VK_C:
                    keystate[KEY_STATE.C] = b;
                    break;
                case KeyEvent.VK_SPACE:
                    keystate[KEY_STATE.SPACE] = b;
                    break;
            }
        }
    }
}
