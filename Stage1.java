package gosen;

import java.applet.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class Stage1 extends Stage {

    private BufferedImage img_chara, img_back, img_shot;
    private BufferedImage img_yen;
    private BufferedImage img_num1[] = new BufferedImage[10];
    private BufferedImage img_num2[] = new BufferedImage[3];
    private BufferedImage img_num3[] = new BufferedImage[4];

    //サウンド
    AudioClip audio;

    private double score = 1000.0;
    String scoreStr = "SCORE : 00x0000x0000x0000x0000";
    //1~4...一の位から千の位 5...桁(億とか兆) 6...欲しいかどうか 
    private int phase;
    private boolean isStart = false;

    Random rnd = new Random();

    private Font mfont = new Font("Sanserif", Font.BOLD, 50);

    int starttime = 0;

    //bool型は押されたかどうかを示す。
    private boolean isOne, isTen, isHnd, isThu;
    private int one, ten, hnd, thu;

    private boolean isKeta, isKetsu;
    private int keta, ketsu;

    @Override
    public void loadMedia() throws IOException {
        //this.img_chara = ImageIO.read(new File("media/0.png"));
        for (int i = 0; i < img_num1.length; i++) {
            this.img_num1[i] = ImageIO.read(new File("media/" + i + ".png"));
        }
        for (int i = 0; i < img_num2.length; i++) {
            this.img_num2[i] = ImageIO.read(new File("media/2" + i + ".png"));
        }
        for (int i = 0; i < img_num3.length; i++) {
            this.img_num3[i] = ImageIO.read(new File("media/3" + i + ".png"));
        }
        this.img_yen = ImageIO.read(new File("media/yen.png"));
        this.img_back = ImageIO.read(new File("media/main.png"));
        audio = Applet.newAudioClip(getClass().getResource("bomb.wav"));
    }

    @Override
    public void draw(GraInfo ginfo) {
        ginfo.g.drawImage(this.img_back, 0, 30, null);

        //スコアの表示
        ginfo.g.setColor(Color.CYAN);
        ginfo.g.setFont(mfont);
        scoreStr = "SCORE :" + score;
        FontMetrics fm = ginfo.g.getFontMetrics();
        ginfo.g.drawString(scoreStr, 10, 590);

        if (!isThu && isStart) {
            ginfo.g.drawImage(this.img_num1[rnd.nextInt(10)], 170, 250, null);
        } else {
            ginfo.g.drawImage(this.img_num1[thu], 170, 250, null);
        }
        if (!isHnd && isStart) {
            ginfo.g.drawImage(this.img_num1[rnd.nextInt(10)], 250, 250, null);
        } else {
            ginfo.g.drawImage(this.img_num1[hnd], 250, 250, null);
        }
        if (!isTen && isStart) {
            ginfo.g.drawImage(this.img_num1[rnd.nextInt(10)], 330, 250, null);
        } else {
            ginfo.g.drawImage(this.img_num1[ten], 330, 250, null);
        }
        if (!isOne && isStart) {
            ginfo.g.drawImage(this.img_num1[rnd.nextInt(10)], 410, 250, null);
        } else {
            ginfo.g.drawImage(this.img_num1[one], 410, 250, null);
        }
        if (!isKeta && isStart) {
            ginfo.g.drawImage(this.img_num3[rnd.nextInt(4)], 490, 250, null);
        } else {
            ginfo.g.drawImage(this.img_num3[keta], 490, 250, null);
        }
        if (!isKetsu && isStart) {
            ginfo.g.drawImage(this.img_num2[rnd.nextInt(3)], 260, 250, null);
        } else {
            ginfo.g.drawImage(this.img_num2[ketsu], 260, 250, null);
        }
        //ginfo.g.drawImage(this.img_num1[rnd.nextInt(10)], 70, 250, null);
        //this.enemy.draw(ginfo, this);
        if (ginfo.keystate[KEY_STATE.Z]) {
            switch (phase) {
                case 0:
                    if (score > 0) {
                        score -= 100;
                        isStart = true;
                    }
                    break;
                case 1:
                    isOne = true;
                    one = rnd.nextInt(10);
                    break;
                case 2:
                    isTen = true;
                    ten = rnd.nextInt(10);
                    break;
                case 3:
                    isHnd = true;
                    hnd = rnd.nextInt(10);
                    break;
                case 4:
                    isThu = true;
                    thu = rnd.nextInt(10);
                    break;
                case 5:
                    isKeta = true;
                    keta = rnd.nextInt(4);
                    break;
                case 6:
                    isKetsu = true;
                    ketsu = rnd.nextInt(3);
                    break;
            }
            audio.play();
            ginfo.keystate[KEY_STATE.Z] = false;
            phase++;
            if (phase > 5) {
                Result();
                phase = 0;
                isOne = false;
                isTen = false;
                isHnd = false;
                isThu = false;
                isKeta = false;
                isKetsu = false;
                isStart = false;
            }
        }
    }

    @Override
    public void init(GraInfo ginfo) {
        //this.player.position.x = 400;
        //this.player.position.y = 520;
    }

    private void Result() {
        //結果処理
        float nowR = 0;
        nowR += one;
        nowR += ten * 10;
        nowR += hnd * 100;
        nowR += thu * 1000;
        if (keta == 0) {
            //千
            nowR *= 0.00001;
        }
        if (keta == 1) {
            //万
            nowR *= 0.0001;
        }
        if (keta == 2) {
            //億
            nowR *= 1;
        }
        if (keta == 3) {
            //兆
            nowR *= 1000;
        }

        if (ketsu == 0) {
            //欲しい！(加算)
            score += nowR;
        }
        if (ketsu == 1) {
            //要らん！(何も無し)
        }
        if (ketsu == 2) {
            //あげる！(減算)
            score -= nowR;
        }
    }
}
