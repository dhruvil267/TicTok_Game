package com.example.tictokgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int grid_size;
    TableLayout gameboard;
    TextView txtTurn;
    char [][] myBoard;
    char turn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        grid_size = Integer.parseInt("3");
        myBoard = new char[grid_size][grid_size];
        gameboard= (TableLayout) findViewById(R.id.mainBoard);
        txtTurn=(TextView) findViewById(R.id.turn);

        resetBoard();
        txtTurn.setText("Turn: "+turn);
        for(int i =0;i<gameboard.getChildCount();i++){
            TableRow row = (TableRow) gameboard.getChildAt(i);
            for(int j =0;j<row.getVirtualChildCount();j++){
                TextView tv = (TextView) row.getVirtualChildAt(j);
                tv.setText(" ");
                tv.setOnClickListener(Move(i,j,tv));
            }
        }

        Button reset_btn=(Button) findViewById(R.id.reset);
        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent current= getIntent();
                finish();
                startActivity(current);
            }
        });

    }

    protected void resetBoard() {
        turn='X';
        for(int i =0;i<grid_size;i++){
            for(int j=0;j<grid_size;j++){
                myBoard[i][j]=' ';
            }
        }


    }

    protected int gameStatus(){

        //0 Continue
        //1 X Wins
        //2 O Wins
        //-1 Draw


        int rowX = 0, colX = 0, rowO=0, col=0;

        for(int i=0;i<grid_size;i++){
            if(check_Row_Equality(i,'X')){
                return 1;
            }
            if(check_Column_Equality(i,'X')){
                return 1;
            }
            if(check_Diagonal('X')){
                return 1;
            }
            if(check_Row_Equality(i,'O')){
                return 2;
            }
            if(check_Column_Equality(i,'O')){
                return 2;
            }
            if(check_Diagonal('O')){
                return 2;
            }
        }
        boolean boardFull=true;
        for(int i=0;i<grid_size;i++){
            for(int j=0;j<grid_size;j++){
                if(myBoard[i][j]==' '){
                    boardFull=false;
                }
            }
        }
        if(boardFull)
            return  -1;
        else
            return 0;






    }

    protected boolean check_Diagonal( char player){
        int count_Equal1=0,count_Equal2=0;
        for(int i = 0; i< grid_size; i++){
            if(myBoard[i][i]==player)
                count_Equal1++;
        }
        for(int i = 0; i< grid_size; i++){
            if(myBoard[i][grid_size-1-i]==player)
                count_Equal2++;
        }

        if(count_Equal1== grid_size || count_Equal2==grid_size)
            return true;
        else
            return false;
    }
    protected boolean check_Row_Equality(int r, char player){
        int count_Equal=0;
        for(int i = 0; i< grid_size; i++){
            if(myBoard[r][i]==player)
                count_Equal++;
        }

        if(count_Equal== grid_size)
            return true;
        else
            return false;
    }


    protected boolean check_Column_Equality(int c, char player){
        int count_Equal=0;
        for(int i = 0; i< grid_size; i++){
            if(myBoard[i][c]==player)
                count_Equal++;
        }

        if(count_Equal== grid_size)
            return true;
        else
            return false;
    }
    protected boolean Cell_Set(int r,int c){
        return !(myBoard[r][c]==' ');
    }

    protected void stopMatch(){
        for (int i=0;i<gameboard.getChildCount();i++){
            TableRow row= (TableRow) gameboard.getChildAt(i);
            for (int j=0;j<gameboard.getChildCount();j++){
                TextView tv=(TextView) row.getChildAt(j);
                tv.setOnClickListener(null);
            }

        }
    }


    View.OnClickListener Move(final int r,final int c, final TextView tv){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Cell_Set(r,c)){
                    myBoard[r][c]=turn;
                    if (turn == 'X') {
                        tv.setText(R.string.X);
                        turn='O';
                    }else if(turn=='O'){
                        tv.setText(R.string.O);
                        turn='X';
                    }

                    if(gameStatus()==0){
                        txtTurn.setText("Turn: Player "+turn);
                    }else if(gameStatus()==-1){
                        txtTurn.setText("This is a Draw Match");
                        stopMatch();
                    }else{
                        txtTurn.setText(turn+" Losses!");
                        stopMatch();
                    }
                }else{
                    txtTurn.setText(txtTurn.getText()+" Choose an Empty Call");
                }
            }
        };
    };
    @Override
    public  boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_board,menu);
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if(id==R.id.app_bar_switch){
            return true;
        }
        return  super.onOptionsItemSelected(item);
    }


}