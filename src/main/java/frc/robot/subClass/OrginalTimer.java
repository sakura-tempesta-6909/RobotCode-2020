package frc.robot.subClass;

public class OriginalTimer {

    // 秒単位
    private double start, limtTime;

    // カウントされてるか
    private boolean is_Start;

    // 時間を超えた/超えてないときの処理
    private Runnable over, notOver;

    public OriginalTimer(double limitTime, Runnable notOver, Runnable over) {
        this.limtTime = limitTime;
        this.notOver = notOver;
        this.over = over;
        reset();

    }

    public void reset() {
        start = System.currentTimeMillis()/1000;
        is_Start = false;

    }

    public void run() {

        if (!is_Start) {
            start = System.currentTimeMillis()/1000;
            is_Start = true;
        }

        double nowTime = System.currentTimeMillis()/1000;
        if (nowTime - start < limtTime) {
            notOver.run();
        } else {
            over.run();
        }

    }

}