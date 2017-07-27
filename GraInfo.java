package gosen;

import java.awt.Graphics2D;

public class GraInfo {

    public Graphics2D g;

    //前のフレームからの秒数(調整用)
    public double frametime;
    //現在のミリ秒数
    public long currenttime;
    //キーステート
    public boolean[] keystate;
    
    public GraInfo(){
        this.keystate = new boolean[8];
        for(int i = 0;i<8;i++){
            this.keystate[i] = false;
        }
    }
}
