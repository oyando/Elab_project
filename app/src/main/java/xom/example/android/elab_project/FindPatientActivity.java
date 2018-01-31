package xom.example.android.elab_project;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import xom.example.android.elab_project.myClass.DatabaseHandler;

public class FindPatientActivity extends AppCompatActivity {
    private DatabaseHandler DB;
    private ListView myListView;
    private TextView notFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_patient);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name);
            getSupportActionBar().setSubtitle("Find Patient");
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        final EditText search = (EditText) findViewById(R.id.searchTxt);
        ImageButton searchBtn = (ImageButton) findViewById(R.id.searchBtn);
        myListView = (ListView) findViewById(R.id.patientList);
        notFound = (TextView) findViewById(R.id.match_notFound);
        LinearLayout myPage = (LinearLayout) findViewById(R.id.list_search);
        TextView empty = (TextView) findViewById(R.id.empty);
        final LinearLayout title = (LinearLayout) findViewById(R.id.title);
        DB = new DatabaseHandler(FindPatientActivity.this);
        DB.getAllPatients(myListView, myPage, empty, title);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String patient_ID = view.getTag().toString();
                Intent intent = new Intent(FindPatientActivity.this, PatientsActivity.class);
                intent.putExtra("patient_ID", patient_ID);
                startActivity(intent);
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(search, title);
            }
        });
    }

    private void validate(EditText search, LinearLayout title) {
        if (search.getText() == null || search.getText().toString().isEmpty()) {

            search.requestFocus();
            search.setHint("Enter data to search...");
            search.setHintTextColor(Color.RED);
        } else {
            DB.search(search.getText().toString(), myListView, notFound, title);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
