package gosen;

import java.io.IOException;

public abstract class GameDisplay {

    protected static GameDisplay current = null;
    protected long starttime;

    //開始時間設定
    public void setStartTime(long st) {
        this.starttime = st;
    }

    //現在のディスプレイを返す
    public GameDisplay getCurrentDisplay() {
        return GameDisplay.current;
    }

    //このディスプレイを表示
    public abstract void show(GraInfo ginfo);

    //画像などを読み込む
    public abstract void loadImage() throws IOException;
}
