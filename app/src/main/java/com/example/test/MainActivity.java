package com.example.test;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.test.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView t;
    Button button3;

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button buttonGO = this.findViewById(R.id.button);
        EditText editText = this.findViewById(R.id.editText);
        TextView textView = this.findViewById(R.id.textView);
        buttonGO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String scores = editText.getText().toString();
                String[] array = scores.split("/,/");
                MyTest test = new MyTest();
                for(int i=0;i<array.length;i++){
                    test.Try(Integer.parseInt(array[i]));
                }
                textView.setText("Total score is:" + test.score);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public static class MyTest {

        int score[] = new int[21];
        int currentScoreIndex = 0;

        public void Try(int i) {
            score[currentScoreIndex++] = i;
        }

        public int result(){
            int totalScore = 0;
            int currentFrameScoreIdx=0;
            for(int currentFrame=0;currentFrame<10;currentFrame++){
                totalScore+=score[currentFrameScoreIdx];
                if (isAStrike(currentFrameScoreIdx)) {
                    totalScore += score[currentFrameScoreIdx + 1];
                    totalScore += score[currentFrameScoreIdx + 2];
                }
                else if(isAZero(currentFrameScoreIdx)){
                    totalScore -= 1;
                }
                else if(isASpare(currentFrameScoreIdx)){
                    totalScore += score[currentFrameScoreIdx + 1];
                    totalScore += score[currentFrameScoreIdx + 2];
                    currentFrameScoreIdx++;
                }else{
                    totalScore += score[currentFrameScoreIdx + 1];
                    currentFrameScoreIdx++;
                }
                currentFrameScoreIdx++;
            }
            return totalScore;
        }

        private boolean isAZero(int scoreIdx) {
            return 0 == score[scoreIdx] && 0 == score[scoreIdx + 1];
        }

        private boolean isASpare(int scoreIdx) {
            return 10 == score[scoreIdx] + score[scoreIdx + 1];
        }

        private boolean isAStrike(int scoreIdx) {
            return 10 == score[scoreIdx];
        }

    }
}