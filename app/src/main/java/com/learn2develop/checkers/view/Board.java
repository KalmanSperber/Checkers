package com.learn2develop.checkers.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


import com.learn2develop.checkers.MainActivity;
import com.learn2develop.checkers.R;

/**
 * Created by User on 11/30/2015.
 */
public class Board extends View {

    private static final String TAG = "checkers";
    int amtOfRows = 8;
    int cellWidth = getWidth() / 8;
    public static int number = 0;
    public static int turn = 0;
    public static final int BLACKS_TURN = 0;
    public static final int REDS_TURN = 1;
    public int[][] board = new int[8][8];
    private final Paint paint = new Paint();
    public static boolean shouldWait = true;
    static int columnFrom;
    static int rowFrom;
    static int boxClickedBeforeValue;
    public static int hackedBlack = 0;
    public static int hackedRed = 0;
    public static final int EMPTY_BOX = 0;
    public static final int BLACK_PIECE = 1;
    public static final int RED_PIECE = 2;
    public static final int BLACK_KING = 10;
    public static final int RED_KING = 20;
    public static int hackedRedKing = 0;
    public static int hackedBlackKing = 0;


    public Board(Context context) {
        super(context);
        Log.d(TAG, "constructor 1");
    }

    public Board(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d(TAG, "constructor 2");
    }

    public Board(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d(TAG, "constructor 3");
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent");

        Log.d(TAG, "onTouch X:" + event.getX() + " Y:" + event.getY());

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                int boardColumn = 0;
                int boardRow = 0;

                boardColumn = (int) (event.getX() / cellWidth);
                boardRow = (int) (event.getY() / cellWidth);


                // redraw the board
                if ((boardColumn % 2 == 0 && boardRow % 2 == 0 || boardColumn % 2 != 0 && boardRow % 2 != 0) && boardRow < 8) {
                    number = 1;

                    if (shouldWait) {
                        if (board[boardColumn][boardRow] != 0 && checkTurn(board[boardColumn][boardRow])) {
                            boxClickedBeforeValue = board[boardColumn][boardRow];
                            System.out.println("boxClickedBeforeValue " + boxClickedBeforeValue);

                            board[boardColumn][boardRow] = board[boardColumn][boardRow] == 0 ? (turn == 0 ? 1 : 2) : 0;

                            columnFrom = boardColumn;
                            rowFrom = boardRow;
                            invalidate();
                        }

                    } else if (validMove(boardColumn, boardRow)) {
                        if (turn == BLACKS_TURN && boardRow == 7) {
                            board[boardColumn][boardRow] = 10;
                            System.out.println(" board[i][j] = 10 " + board[boardColumn][boardRow]);
                        } else if (turn == REDS_TURN && boardRow == 0) {
                            board[boardColumn][boardRow] = 20;

                        } else {
                            board[boardColumn][boardRow] = boxClickedBeforeValue;
                            //board[boardColumn][boardRow] = board[boardColumn][boardRow] == 0 ? (turn == BLACKS_TURN ? 1 : 2) : 0;
                        }
                        invalidate();

                    }


                   // Snackbar.make(this, turn == 0 ? "blacks turn" : "reds turn", Snackbar.LENGTH_LONG)
                   //         .setAction("Action", null).show();


                }

                break;
        }

        return super.onTouchEvent(event);

    }


    private boolean checkTurn(int boxClicked) {

        return ((turn == BLACKS_TURN && (boxClicked == BLACK_PIECE || boxClicked == BLACK_KING)) || (turn == REDS_TURN && (boxClicked == RED_PIECE || boxClicked == RED_KING)));
    }

    private boolean validMove(int columnTo, int rowTo) {
        System.out.println("i is " + columnTo + " j is " + rowTo);
        if (columnTo == columnFrom && rowTo == rowFrom) {
            return true;
        }
        if (turn == BLACKS_TURN && boxClickedBeforeValue == 10) {
            if (rowTo == rowFrom + 1 || rowTo == rowFrom - 1) {
                if ((columnTo == columnFrom + 1 && board[columnTo][rowTo] == 0) || (columnTo == columnFrom - 1 && board[columnTo][rowTo] == 0)) {
                    //
                    return true;

                }
            }
            if (rowTo == rowFrom - 2) {
                if (columnTo == columnFrom + 2 && board[columnTo][rowTo] == EMPTY_BOX && (board[columnFrom + 1][rowFrom - 1] == RED_PIECE
                        || board[columnFrom + 1][rowFrom - 1] == RED_KING)) {
                    if (board[columnFrom + 1][rowFrom - 1] == RED_KING) {
                        hackedRedKing++;
                    }

                    board[columnFrom + 1][rowFrom - 1] = EMPTY_BOX;
                    hackedRed++;
                    return true;
                }
                if (columnTo == columnFrom - 2 && board[columnTo][rowTo] == 0 && (board[columnFrom - 1][rowFrom - 1] == RED_PIECE)
                        || (board[columnFrom - 1][rowFrom - 1] == RED_KING)) {
                    if (board[columnFrom - 1][rowFrom - 1] == RED_KING) {
                        hackedRedKing++;
                    }
                    board[columnFrom - 1][rowFrom - 1] = EMPTY_BOX;
                    hackedRed++;
                    return true;
                }
            }
        }
        if (turn == REDS_TURN && boxClickedBeforeValue == RED_KING) {
            if (rowTo == rowFrom - 1 || rowTo == rowFrom + 1) {
                return ((columnTo == columnFrom + 1 && board[columnTo][rowTo] == EMPTY_BOX) || (columnTo == columnFrom - 1 && board[columnTo][rowTo] == EMPTY_BOX));
            }
            if (rowTo == rowFrom + 2 && columnTo == columnFrom + 2) {
                if (board[columnTo][rowTo] == 0 && (board[columnFrom + 1][rowFrom + 1] == BLACK_PIECE)
                        || (board[columnFrom + 1][rowFrom + 1] == BLACK_KING)) {
                    if (board[columnFrom + 1][rowFrom + 1] == BLACK_KING) {
                        hackedBlackKing++;
                    }

                    board[columnFrom + 1][rowFrom + 1] = EMPTY_BOX;
                    hackedBlack++;
                    return true;
                }
            }
            if (rowTo == rowFrom + 2) {
                if (columnTo == columnFrom - 2 && board[columnTo][rowTo] == 0 && (board[columnFrom - 1][rowFrom + 1] == BLACK_PIECE)
                        || (board[columnFrom - 1][rowFrom + 1] == BLACK_KING)) {
                    if (board[columnFrom - 1][rowFrom + 1] == BLACK_KING) {
                        hackedBlackKing++;
                    }
                    board[columnFrom - 1][rowFrom + 1] = EMPTY_BOX;
                    hackedBlack++;
                    return true;
                }
            }
            if (columnTo == columnFrom + 2) {
                if (board[columnTo][rowTo] == 0 && (board[columnFrom + 1][rowFrom - 1] == BLACK_PIECE)
                        || (board[columnFrom + 1][rowFrom - 1] == BLACK_KING)) {
                    if (board[columnFrom + 1][rowFrom - 1] == BLACK_KING) {
                        hackedBlackKing++;
                    }
                    board[columnFrom + 1][rowFrom - 1] = EMPTY_BOX;
                    hackedBlack++;
                    return true;
                }
            }
            if (columnTo == columnFrom - 2 && board[columnTo][rowTo] == 0 && (board[columnFrom - 1][rowFrom - 1] == BLACK_PIECE)
                    || (board[columnFrom - 1][rowFrom - 1] == BLACK_KING)) {
                if (board[columnFrom - 1][rowFrom - 1] == BLACK_KING) {
                    hackedBlackKing++;
                }
                board[columnFrom - 1][rowFrom - 1] = EMPTY_BOX;
                hackedBlack++;
                return true;
            }
        } else if (turn == BLACKS_TURN && rowTo == rowFrom + 1) {
            if ((columnTo == columnFrom + 1 && board[columnTo][rowTo] == EMPTY_BOX) || (columnTo == columnFrom - 1 && board[columnTo][rowTo] == EMPTY_BOX)) {
//
                return true;

            }
        } else if (turn == REDS_TURN && rowTo == rowFrom - 1) {
            return ((columnTo == columnFrom + 1 && board[columnTo][rowTo] == EMPTY_BOX) || (columnTo == columnFrom - 1 && board[columnTo][rowTo] == EMPTY_BOX));
        } else if (turn == BLACKS_TURN && rowTo == rowFrom + 2) {
            if (columnTo == columnFrom + 2 && board[columnTo][rowTo] == EMPTY_BOX && (board[columnFrom + 1][rowFrom + 1] == RED_PIECE
                    || board[columnFrom + 1][rowFrom + 1] == RED_KING)) {
                if (board[columnFrom + 1][rowFrom + 1] == RED_KING) {
                    hackedRedKing++;
                }
                board[columnFrom + 1][rowFrom + 1] = EMPTY_BOX;
                hackedRed++;
                return true;
            }
            if (columnTo == columnFrom - 2 && board[columnTo][rowTo] == EMPTY_BOX && (board[columnFrom - 1][rowFrom + 1] == RED_PIECE)
                    || (board[columnFrom - 1][rowFrom + 1] == RED_KING)) {
                if (board[columnFrom - 1][rowFrom + 1] == RED_KING) {
                    hackedRedKing++;
                }
                board[columnFrom - 1][rowFrom + 1] = EMPTY_BOX;
                hackedRed++;
                return true;
            }
        } else if (turn == REDS_TURN && rowTo == rowFrom - 2) {
            if (columnTo == columnFrom + 2) {
                if (board[columnTo][rowTo] == 0 && (board[columnFrom + 1][rowFrom - 1] == BLACK_PIECE)
                        || (board[columnFrom + 1][rowFrom - 1] == BLACK_KING)) {
                    if (board[columnFrom + 1][rowFrom - 1] == BLACK_KING) {
                        hackedBlackKing++;
                    }
                    board[columnFrom + 1][rowFrom - 1] = EMPTY_BOX;
                    hackedBlack++;
                    return true;
                }
            }
            if (columnTo == columnFrom - 2 && board[columnTo][rowTo] == 0 && (board[columnFrom - 1][rowFrom - 1] == BLACK_PIECE)
                    || (board[columnFrom - 1][rowFrom - 1] == BLACK_KING)) {
                if (board[columnFrom - 1][rowFrom - 1] == BLACK_KING) {
                    hackedBlackKing++;
                }
                board[columnFrom - 1][rowFrom - 1] = EMPTY_BOX;
                hackedBlack++;
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw");
        amtOfRows = 8;
        cellWidth = getWidth() / 8;
        if (number == 0) {                   //if new game
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (i % 2 == 0 && j % 2 == 0 || i % 2 != 0 && j % 2 != 0) {
                        if (j < 3) {         //black pieces
                            board[i][j] = 1;
                        } else if (j > 4) {  //red pieces
                            board[i][j] = 2;
                        }
                    }
                }
            }
        }
        drawSquares(canvas);
        drawPieces(canvas);
        drawHackedPieces(canvas);


    }

    private void drawHackedPieces(Canvas canvas) {
        int controlRedLoop = 0;
        int controlKing = 0;
        for (int j = 8; controlRedLoop < hackedRed && j < 11; j++) {
            for (int i = 0; controlRedLoop < hackedRed && i < 4; i++) {
                controlRedLoop++;
                controlKing++;
                paint.setColor(Color.argb(255, 255, 255, 0)); //yellow frame
                canvas.drawCircle(i * cellWidth + cellWidth / 2, j * cellWidth + cellWidth / 2, cellWidth / 3 + 2, paint);
                paint.setColor(Color.argb(255, 255, 0, 111));
                canvas.drawCircle(i * cellWidth + cellWidth / 2, j * cellWidth + cellWidth / 2, cellWidth / 3, paint);
                if (hackedRedKing >= controlKing) {
                    paint.setColor(Color.argb(255, 255, 255, 0));
                    canvas.drawRect(
                            i * cellWidth + 35, j * cellWidth + 20, (i * cellWidth) + cellWidth - 35, (j * cellWidth - 20) + cellWidth, paint);
                    canvas.drawRect(
                            i * cellWidth + 20, j * cellWidth + 35, (i * cellWidth) + cellWidth - 20, (j * cellWidth - 35) + cellWidth, paint);
                }

            }
        }
        int controlBlackLoop = 0;
        int controlBlackKing = 0;

        for (int j = 8; controlBlackLoop < hackedBlack && j < 11; j++) {
            for (int i = 7; controlBlackLoop < hackedBlack && i > 3; i--) {
                controlBlackLoop++;
                controlBlackKing++;
                paint.setColor(Color.argb(255, 255, 255, 0)); //yellow frame
                canvas.drawCircle(i * cellWidth + cellWidth / 2, j * cellWidth + cellWidth / 2, cellWidth / 3 + 2, paint);
                paint.setColor(Color.BLACK);
                canvas.drawCircle(i * cellWidth + cellWidth / 2, j * cellWidth + cellWidth / 2, cellWidth / 3, paint);
                if (hackedBlackKing >= controlBlackKing) {
                    paint.setColor(Color.argb(255, 255, 255, 0));
                    canvas.drawRect(
                            i * cellWidth + 35, j * cellWidth + 20, (i * cellWidth) + cellWidth - 35, (j * cellWidth - 20) + cellWidth, paint);
                    canvas.drawRect(
                            i * cellWidth + 20, j * cellWidth + 35, (i * cellWidth) + cellWidth - 20, (j * cellWidth - 35) + cellWidth, paint);
                }
            }
        }
    }

    private void drawPieces(Canvas canvas) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i % 2 == 0 && j % 2 == 0 || i % 2 != 0 && j % 2 != 0) {
                    if (board[i][j] == 1 || board[i][j] == 10) {
                        paint.setColor(Color.argb(255, 255, 255, 0)); //yellow frame
                        canvas.drawCircle(i * cellWidth + cellWidth / 2, j * cellWidth + cellWidth / 2, cellWidth / 3 + 2, paint);
                        paint.setColor(Color.BLACK);
                        canvas.drawCircle(i * cellWidth + cellWidth / 2, j * cellWidth + cellWidth / 2, cellWidth / 3, paint);


                    } else if (board[i][j] == 2 || board[i][j] == RED_KING) {
                        paint.setColor(Color.argb(255, 255, 255, 0)); //yellow frame
                        canvas.drawCircle(i * cellWidth + cellWidth / 2, j * cellWidth + cellWidth / 2, cellWidth / 3 + 2, paint);
                        paint.setColor(Color.argb(255, 255, 0, 111));
                        canvas.drawCircle(i * cellWidth + cellWidth / 2, j * cellWidth + cellWidth / 2, cellWidth / 3, paint);
                    }
                    if (board[i][j] == BLACK_KING || board[i][j] == RED_KING) {
                        paint.setColor(Color.argb(255, 255, 255, 0));
                        canvas.drawRect(
                                i * cellWidth + 35, j * cellWidth + 20, (i * cellWidth) + cellWidth - 35, (j * cellWidth - 20) + cellWidth, paint);
                        canvas.drawRect(
                                i * cellWidth + 20, j * cellWidth + 35, (i * cellWidth) + cellWidth - 20, (j * cellWidth - 35) + cellWidth, paint);

                    }
                }
            }
        }


        if (shouldWait && number != 0) {
            if (board[columnFrom][rowFrom] == 0) {
                paint.setColor(Color.argb(255, 255, 255, 0)); //yellow frame
                canvas.drawCircle(columnFrom * cellWidth + cellWidth / 2, rowFrom * cellWidth + cellWidth / 2, cellWidth / 3 + 10, paint);
                paint.setColor(turn == 0 ? Color.BLACK : Color.RED);
                canvas.drawCircle(columnFrom * cellWidth + cellWidth / 2, rowFrom * cellWidth + cellWidth / 2, cellWidth / 3, paint);
                if (boxClickedBeforeValue == BLACK_KING || boxClickedBeforeValue == RED_KING) {
                    paint.setColor(Color.argb(255, 255, 255, 0)); //yellow frame
                    canvas.drawRect(
                            columnFrom * cellWidth + 35, rowFrom * cellWidth + 20, (columnFrom * cellWidth) + cellWidth - 35, (rowFrom * cellWidth - 20) + cellWidth, paint);
                    canvas.drawRect(
                            columnFrom * cellWidth + 20, rowFrom * cellWidth + 35, (columnFrom * cellWidth) + cellWidth - 20, (rowFrom * cellWidth - 35) + cellWidth, paint);
                }

                shouldWait = false;

            }
        } else {
            shouldWait = true;
            turn = turn == 0 ? 1 : 0;

        }
    }

    @Override
    public void onMeasure(int widthSpec, int heightSpec) {
        Log.d(TAG, "onMeasure");
        super.onMeasure(widthSpec, heightSpec);
        int size = Math.min(getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension(size, getMeasuredHeight() - size / 11);
    }

    private void drawSquares(Canvas canvas) {
        Log.d(TAG, "drawSquares");
        canvas.drawColor(Color.TRANSPARENT);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Log.d(TAG, i + "  " + j);


                paint.setColor(i % 2 == 0 && j % 2 == 0 || i % 2 != 0 && j % 2 != 0 ? Color.DKGRAY : Color.RED);
                canvas.drawRect(
                        i * cellWidth, j * cellWidth, (i * cellWidth) + cellWidth, (j * cellWidth) + cellWidth, paint);


            }
        }


    }


}
   /* private boolean isValidMove(int columnTo, int rowTo) {
        if (board[columnTo][rowTo] == 0) {
            if (turn == BLACKS_TURN){
                if (rowTo == rowFrom + 1 && (columnTo == columnFrom + 1 || columnTo == columnFrom - 1 )){
                    return true;
                } else if (rowTo == rowFrom + 2){
                    if (columnTo == columnFrom + 2 && (board[columnTo - 1][rowTo - 1] == RED_PIECE)
                            || (board[columnTo - 1][rowTo - 1] == RED_KING)){
                        board[columnTo - 1][rowTo - 1] = 0;
                    } else if (columnTo == columnFrom - 2 && (board[columnTo + 1][rowTo - 1] == RED_PIECE)
                            || (board[columnTo + 1][rowTo - 1] == RED_KING)){
                        board[columnTo + 1][rowTo - 1] = 0;
                        hackedRed++;
                        return true;
                    }
                } else if (boxClickedBeforeValue == BLACK_KING){
                    if (rowTo == rowFrom - 1 && (columnTo == columnFrom + 1 || columnTo == columnFrom - 1 )){
                        return true;
                    }
                }
            } else if (turn == REDS_TURN){
                if (rowTo == rowFrom - 1 && (columnTo == columnFrom + 1 || columnTo == columnFrom - 1 )){
                    return true;
                } else if (rowTo == rowFrom - 2){
                    if (columnTo == columnFrom + 2 && (board[columnTo - 1][rowTo + 1] == BLACK_PIECE)
                            || (board[columnTo - 1][rowTo + 1] == BLACK_KING)){
                        board[columnTo - 1][rowTo + 1] = 0;
                    } else if (columnTo == columnFrom - 2 && (board[columnTo + 1][rowTo + 1] == BLACK_PIECE)
                            || (board[columnTo + 1][rowTo + 1] == BLACK_KING)){
                        board[columnTo + 1][rowTo + 1] = 0;
                        hackedBlack++;
                        return true;
                    }
                } else if (boxClickedBeforeValue == RED_KING){
                    if (rowTo == rowFrom - 1 && (columnTo == columnFrom + 1 || columnTo == columnFrom - 1 )){
                        return true;
                    }
                }
            }
        }
        return false;
    }*/
