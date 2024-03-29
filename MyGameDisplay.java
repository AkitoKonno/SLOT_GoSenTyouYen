package gosen;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class MyGameDisplay extends GameDisplay {

    GameDisplay title, main, over, clear;
    private Font mfont = new Font("Sanserif", Font.BOLD, 50);
    World stage = new MyWorld();
    boolean isOver = false;

    public MyGameDisplay() {
        //コンストラクタ、初期画面はタイトル画面に
        this.title = new MyGameTitle();
        this.main = new MyGameMain();
        this.over = new MyGameOver();
        this.clear = new MyGameClear();
        GameDisplay.current = this.title;
    }

    @Override
    public void show(GraInfo ginfo) {
    }

    @Override
    public void loadImage() throws IOException {
        //各シーンの画像読み込み
        this.title.loadImage();
        this.clear.loadImage();
        this.main.loadImage();
        this.over.loadImage();
    }

    //タイトル画面
    class MyGameTitle extends GameDisplay {
        private BufferedImage img_title;
        
        @Override
        public void show(GraInfo ginfo) {
            ginfo.g.drawImage(this.img_title, 120, 100, null);

            ginfo.g.setColor(Color.CYAN);
            ginfo.g.setFont(MyGameDisplay.this.mfont);
            String str = "PUSH SPACE";
            FontMetrics fm = ginfo.g.getFontMetrics();
            int strw = fm.stringWidth(str) / 2;
            ginfo.g.drawString(str, 450 - strw, 450);

            if (ginfo.keystate[KEY_STATE.SPACE] == true) {
                GameDisplay.current = MyGameDisplay.this.main;
                MyGameDisplay.this.stage.init(ginfo);
            }
        }

        @Override
        public void loadImage() throws IOException {
            this.img_title = ImageIO.read(new File("media/title.png"));
        }
    }

    //メイン画面
    class MyGameMain extends GameDisplay {
        @Override
        public void show(GraInfo ginfo) {
            isOver = MyGameDisplay.this.stage.draw(ginfo);
            System.out.println(isOver);
            if (isOver) {
                //金が0より小さくなったらゲームオーバ画面へ
                isOver = false;
                GameDisplay.current = MyGameDisplay.this.over;
            }
        }
        @Override
        public void loadImage() throws IOException {
            MyGameDisplay.this.stage.loadMedia();
        }
    }

    //ゲームオーバー
    class MyGameOver extends GameDisplay {

        @Override
        public void show(GraInfo ginfo) {
            ginfo.g.setColor(Color.RED);
            ginfo.g.setFont(MyGameDisplay.this.mfont);
            String str = "破産しました";
            FontMetrics fm = ginfo.g.getFontMetrics();
            int strw = fm.stringWidth(str) / 2;
            ginfo.g.drawString(str, 400 - strw, 300);

            if (ginfo.keystate[KEY_STATE.SPACE] == true) {
                GameDisplay.current = MyGameDisplay.this.title;
            }
        }

        @Override
        public void loadImage() {
        }
    }

    //ゲームクリア
    class MyGameClear extends GameDisplay {
        @Override
        public void show(GraInfo ginfo) {
            ginfo.g.setColor(Color.CYAN);
            ginfo.g.setFont(MyGameDisplay.this.mfont);
            String str = "ゲームクリア";
            FontMetrics fm = ginfo.g.getFontMetrics();
            int strw = fm.stringWidth(str) / 2;
            ginfo.g.drawString(str, 400 - strw, 200);

            if (ginfo.currenttime - this.starttime > 5000) {
                GameDisplay.current = MyGameDisplay.this.title;
            }
        }

        @Override
        public void loadImage() {
        }
    }
}
