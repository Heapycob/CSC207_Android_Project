package fall2018.csc207_project.myapplication;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

public class GameView extends GridLayout {

    private final int THRESHOLD = 5;
    private int complexity = 4;
    private final int DEFAULTWIDTH = 245;
    private Tile[][] tileTable;
    private List<Point> emptyPoints = new ArrayList<>();
    private boolean checkingAvailableMoves;
    private int highest;
    private int score;

    public GameView(Context context) {
        super(context);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGameView();
    }

    private void initGameView() {
        tileTable = new Tile[complexity][complexity];
        score = 0;
        setColumnCount(complexity);
        setBackgroundColor(getResources().getColor(R.color.tileHolderBackground));
        addTiles();
        startGame();

        setOnTouchListener(new View.OnTouchListener(){
            private float startX,startY,offsetX,offsetY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX() - startX;
                        offsetY = event.getY() - startY;
                        getTouchDirection(offsetX,offsetY);
                        break;
                }

                return true;
            }
        });
    }

    private void getTouchDirection(float offsetX, float offsetY) {

        if(Math.abs(offsetX)>Math.abs(offsetY)) {
            if(offsetX<-THRESHOLD)
                touchLeft();
            else if(offsetX> THRESHOLD)
                touchRight();
        }
        else {
            if (offsetY<-THRESHOLD)
                touchUp();
            else if (offsetY> THRESHOLD)
                touchDown();
        }
    }

    private void addTiles() {
        Tile tile;
        for(int y=0;y<complexity;y++) {
            for(int x=0;x<complexity;x++) {
                tile = new Tile(getContext());
                tile.setNum(0);
                addView(tile, DEFAULTWIDTH, DEFAULTWIDTH);

                tileTable[x][y] = tile;
            }
        }
    }

    private void addRandomTile() {

        emptyPoints.clear();

        for (int y=0;y<complexity;y++) {
            for (int x=0;x<complexity;x++) {
                if(tileTable[x][y].getNum() <= 0)
                    emptyPoints.add(new Point(x,y));
            }
        }

        Point p = emptyPoints.remove((int)(Math.random()*emptyPoints.size()));
        tileTable[p.x][p.y].setNum(Math.random()>0.1?2:4);

    }

    private void addTile(int num, int xIndex, int yIndex) {
       tileTable[xIndex][yIndex].setNum(num);
    }

    private void clearTileTable() {
        for(int y=0;y<complexity;y++) {
            for(int x=0;x<complexity;x++) {
                tileTable[x][y].setNum(0);
            }
        }
    }

    private void startGame() {
        clearTileTable();
        highest = 0;
        score = 0;

        addRandomTile();
        addRandomTile();
//
//        addRandomTile();
//        addRandomTile();
//        addRandomTile();
//        addRandomTile();
//        addRandomTile();
//        addRandomTile();
//        addRandomTile();
//        addRandomTile();

//        addTile(8,0,2);
//        addTile(2,0,3);
//        addTile(4,1,3);
//        addTile(2,2,3);

//        addTile(8,0,2);
//        addTile(2,0,3);
//        addTile(4,1,3);
//        addTile(2,2,3);


//        addTile(2,0,0);
//        addTile(2,0,1);
//        addTile(4,0,2);
//        addTile(8,0,3);
//        addTile(2,3,3);

    }

    boolean movesAvailable() {
        checkingAvailableMoves = true;
        boolean hasMoves = touchUp() || touchDown() || touchLeft() || touchRight();
        checkingAvailableMoves = false;
        return hasMoves;
    }

    private void clearMerged() {
        for(int y=0;y<complexity;y++) {
            for(int x=0;x<complexity;x++) {
                tileTable[x][y].setMergedState(false);
            }
        }
    }

    private boolean doMove(int countDownFrom, int yDirection, int xDirection) {
        boolean moved = false;
        int target = 2048;

        for (int i = 0; i < complexity * complexity; i++) {
            int j = Math.abs(countDownFrom - i);

            int r = j / complexity;
            int c = j % complexity;

            if (tileTable[r][c].getNum() == 0)
                continue;

            int nextR = r + xDirection;
            int nextC = c + yDirection;

            while (nextR >= 0 && nextR < complexity && nextC >= 0 && nextC < complexity) {

                Tile next = tileTable[nextR][nextC];
                Tile curr = tileTable[r][c];

                if (next.getNum() == 0) {

                    if (checkingAvailableMoves)
                        return true;

                    next.setNum(curr.getNum());
                    curr.setNum(0);
                    r = nextR;
                    c = nextC;
                    nextR += xDirection;
                    nextC += yDirection;
                    moved = true;

                } else if (next.equals(curr) && !next.getMergedState()) {

                    if (checkingAvailableMoves)
                        return true;

                    int value = next.merge(curr);
                    if (value > highest)
                        highest = value;
                    score += value;
                    tileTable[r][c].setNum(0);
                    moved = true;
                    break;

                } else
                    break;
            }
        }


        if (moved) {
            if (highest < target) {
                clearMerged();
                addRandomTile();
                if (!movesAvailable()) {
                    System.out.println("Game Over!");
                }
            } else if (highest == target)
                System.out.println("You Won!");

        }

        System.out.println("Your Score: -----------------------------> "+score+"!!!!!!!!!!!!!!!!!!!");

        return moved;
    }


    private boolean touchUp() {
        return doMove(0,-1,0);
    }

    private boolean touchDown() {
        return doMove(complexity * complexity - 1,1,0);
    }

    private boolean touchLeft() {
        return doMove(0,0,-1);
    }

    private boolean touchRight() {
        return doMove(complexity * complexity - 1, 0, 1);
    }


}
