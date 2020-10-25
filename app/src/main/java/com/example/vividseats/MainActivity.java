package com.example.vividseats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText etStartDate, etEndDate;
    private DataService dataService;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        etStartDate = (EditText) findViewById(R.id.etStartDate);
        etEndDate = (EditText) findViewById(R.id.etEndDate);
        Button btnApply = (Button) findViewById(R.id.btnApply);
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startDate = etStartDate.getText().toString();
                String endDate = etEndDate.getText().toString();
                refreshItemList(startDate, endDate);
            }
        });
        dataService = new DataService(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshItemList("", "");
    }

    private void refreshItemList(String startDate, String endDate) {
        new GetItemsAsyncTask(startDate, endDate, dataService).execute();
    }

    private class GetItemsAsyncTask extends AsyncTask<Void, Void, List<Item>> {
        private String startDate, endDate;
        private DataService dataService;

        public GetItemsAsyncTask(String startDate, String endDate, DataService dataService) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.dataService = dataService;
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            Toast.makeText(MainActivity.this, "Loading", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected List<Item> doInBackground(Void... params) {
            Clock clk = new Clock();
            clk.start();
            List<Item> list = dataService.getItems(startDate, endDate);
            clk.stop();
            Log.e("Latency", Long.toString(clk.getCurrentInterval()));
            return list;
        }

        @Override
        protected void onPostExecute(List<Item> items) {
            RecyclerView.Adapter adapter = new ItemAdapter(MainActivity.this, items);
            recyclerView.setAdapter(adapter);

            progressBar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, "Loaded", Toast.LENGTH_SHORT).show();
        }
    }
}