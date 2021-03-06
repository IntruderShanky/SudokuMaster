package com.intrusoft.sudokumaster;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Random;

public class PlayActivity extends AppCompatActivity {

    LinearLayout mainLayout, keyboadrLayout, controll;
    Display display;
    Button field[][] = new Button[9][9];
    int focusI, focusJ;
    Button keyBoard[] = new Button[9];
    Toolbar bar;
    int pos_i[] = new int[33];
    int pos_j[] = new int[33];
    int temp[][] = new int[9][9];
    int final_sudoku[][] = new int[9][9];
    Sudoku sudoku = new Sudoku();
    CreateSudoku createSudoku = new CreateSudoku();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.move_left,R.anim.move_left);
        setContentView(R.layout.activity_play);
        bar = (Toolbar) findViewById(R.id.bar);
        controll = (LinearLayout) findViewById(R.id.controll);
        int cont_width = (int) getResources().getDimension(R.dimen.button_height1);
        setSupportActionBar(bar);
        mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
        keyboadrLayout = (LinearLayout) findViewById(R.id.keyboard);
        display = ((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = ((display.getHeight() / 5) * 3) / 10;
        LinearLayout vl = new LinearLayout(this);
        vl.setOrientation(LinearLayout.VERTICAL);
        vl.setGravity(Gravity.CENTER);
        LinearLayout hl[] = new LinearLayout[9];
        for (int i = 0; i < 9; i++) {
            hl[i] = new LinearLayout(this);
            hl[i].setOrientation(LinearLayout.HORIZONTAL);
            hl[i].setGravity(Gravity.CENTER);
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0) {
                    View horView = new View(this);
                    horView.setBackgroundColor(Color.parseColor("#ffffff"));
                    ViewGroup.LayoutParams hv = new ViewGroup.LayoutParams(3, width);
                    hl[i].addView(horView, hv);
                }
                field[i][j] = new Button(this);
                field[i][j].setBackgroundResource(R.drawable.field_background);
                field[i][j].setTextSize(TypedValue.COMPLEX_UNIT_SP, width / 6);
                field[i][j].setTextColor(Color.parseColor("#ffffff"));
                final int finalI = i;
                final int finalJ = j;
                field[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setInFocus(finalI, finalJ);
                    }
                });
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(width, width);
                hl[i].addView(field[i][j], params);
            }
            if (i % 3 == 0) {
                View horView = new View(this);
                horView.setBackgroundColor(Color.parseColor("#ffffff"));
                ViewGroup.LayoutParams hv = new ViewGroup.LayoutParams(width * 9, 3);
                vl.addView(horView, hv);
            }
            vl.addView(hl[i]);
        }
        mainLayout.addView(vl);
        int margin = (int) getResources().getDimension(R.dimen.margin);
        int button_width = (display.getWidth() - margin) / 4;
        int button_height = (((display.getHeight() / 5) * 2) - cont_width) / 4;
        System.out.println(cont_width);
        LinearLayout keyVer = new LinearLayout(this);
        keyVer.setOrientation(LinearLayout.VERTICAL);
        keyVer.setGravity(Gravity.CENTER_HORIZONTAL);
        LinearLayout keyHor[] = new LinearLayout[3];
        for (int i = 0, k = 0; i < 3; i++) {
            keyHor[i] = new LinearLayout(this);
            keyHor[i].setOrientation(LinearLayout.HORIZONTAL);
            keyHor[i].setGravity(Gravity.CENTER_HORIZONTAL);
            for (int j = 0; j < 3; j++) {
                keyBoard[k] = new Button(this);

                keyBoard[k].setBackgroundResource(R.drawable.button_selector);
                keyBoard[k].setText("" + (k + 1));
                keyBoard[k].setTextSize(TypedValue.COMPLEX_UNIT_SP, button_height / 8);
                keyBoard[k].setTextColor(Color.parseColor("#ffffff"));
                final int finalK = k;
                keyBoard[k].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setValue(finalK + 1);
                    }
                });
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(button_width, button_height);
                keyHor[i].addView(keyBoard[k], params);
                k++;
            }
            keyVer.addView(keyHor[i]);
        }
        keyboadrLayout.addView(keyVer);
        sudoku.Array = createSudoku.getSudoku();
        if (sudoku.fill()) {
            final_sudoku = sudoku.Array;
            setZero();
            fill_game();
        }
    }

    private void fill_game() {
        getRandomPositions();
//        for (int i = 0; i < 9; i++)
//            for (int j = 0; j < 9; j++) {
//                field[i][j].setVisibility(View.INVISIBLE);
//            }
        for (int j = 0; j < 33; j++) {
            field[pos_i[j]][pos_j[j]].setVisibility(View.VISIBLE);
            field[pos_i[j]][pos_j[j]].setEnabled(false);
            field[pos_i[j]][pos_j[j]].setBackgroundResource(R.drawable.field_disabled_background);
            temp[pos_i[j]][pos_j[j]] = final_sudoku[pos_i[j]][pos_j[j]];
            fillValue();
        }
    }

    private void setZero() {
        for (int p = 0; p < 9; p++) {
            for (int q = 0; q < 9; q++) {
                temp[p][q] = 0;
                field[p][q].setBackgroundResource(R.drawable.field_background);
            }
        }
    }

    private void setValue(int finalK) {
        field[focusI][focusJ].setText("" + finalK);
        temp[focusI][focusJ] = finalK;
        for (int p = 0; p < 9; p++) {
            for (int q = 0; q < 9; q++) {
                if (temp[p][q] != 0) {
                    if (!sudoku.legal(temp, p, q)) {
                        field[p][q].setBackgroundResource(R.drawable.field_illegal_background);
                    } else {
                        field[p][q].setBackgroundResource(R.drawable.field_background);
                       // field[focusI][focusJ].setBackgroundResource(R.drawable.field_selected_background);
                    }
                    for (int l = 0; l < 33; l++)
                        field[pos_i[l]][pos_j[l]].setBackgroundResource(R.drawable.field_disabled_background);
                }
            }
        }
    }

    public void setInFocus(int i, int j) {
        focusI = i;
        focusJ = j;
        for (int p = 0; p < 9; p++) {
            for (int q = 0; q < 9; q++) {
                field[p][q].setBackgroundResource(R.drawable.field_background);
                if (temp[p][q] != 0) {
                    if (!sudoku.legal(temp, p, q)) {
                        field[p][q].setBackgroundResource(R.drawable.field_illegal_background);
                    }
                }
            }
        }
        field[focusI][focusJ].setBackgroundResource(R.drawable.field_selected_background);
        for (int l = 0; l < 33; l++)
            field[pos_i[l]][pos_j[l]].setBackgroundResource(R.drawable.field_disabled_background);
    }

    public void check(View v) {
        if (check()) {

        } else {
            Toast.makeText(this, "Illegal sudoku", Toast.LENGTH_SHORT).show();
        }

    }

    public void clear(View v) {
        temp[focusI][focusJ] = 0;
        fillValue();
    }

    private void fillValue() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (temp[i][j] == 0) {
                    field[i][j].setText("");
                } else {
                    field[i][j].setText("" + temp[i][j]);
                }
            }
        }
    }

    public Boolean check() {
        for (int p = 0; p < 9; p++) {
            for (int q = 0; q < 9; q++) {
                if (temp[p][q] != final_sudoku[p][q]) {
                    return false;
                }
            }
        }
        return true;
    }

    public void getRandomPositions() {
        Random r = new Random();
        for (int i = 0; i < 33; i++) {
            pos_i[i] = r.nextInt(9);
            pos_j[i] = r.nextInt(9);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.next: {
                PlayActivity.this.finish();
                Intent i = new Intent(PlayActivity.this, PlayActivity.class);
                startActivity(i);
                return true;
            }
            case R.id.solver:{
                PlayActivity.this.finish();
                Intent i = new Intent(PlayActivity.this, SolveActivity.class);
                startActivity(i);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
