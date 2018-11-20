package fall2018.csc207_project.myapplication;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;


public class Tile extends FrameLayout {

    private int num;
    private TextView label;
    private boolean mergedState;

    public Tile(Context context) {
        super(context);
        label = new TextView(getContext());
        label.setTextSize(32);
        label.setBackgroundColor(getResources().getColor(R.color.tileHolder));
        label.setGravity(Gravity.CENTER);

        LayoutParams params = new LayoutParams(-1,-1);
        params.setMargins(10,10,0,0);
        addView(label, params);

        setNum(0);
    }


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        if(num<=0)
            label.setText("");
        else
            label.setText(num+"");
    }


    @Override
    public boolean equals(Object o) {
        if(o instanceof Tile)
            return this.getNum() == ((Tile)o).getNum();
        else
            return super.equals(o);
    }

    public int merge(Tile tile) {
        if(this.equals(tile)) {
            this.mergedState = true;
            this.setNum(num *=2);
            return num;
        }
        return -1;
    }

    public void setMergedState(boolean merged) {
        this.mergedState = merged;
    }

    public boolean getMergedState() {
        return mergedState;
    }
}

