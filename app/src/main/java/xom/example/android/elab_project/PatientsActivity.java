package xom.example.android.elab_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import xom.example.android.elab_project.gettersAndSetters.Patients;

public class PatientsActivity extends AppCompatActivity {
    private DatabaseHandler DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients);

        DB = new DatabaseHandler(PatientsActivity.this);
        String patient_ID = getIntent().getStringExtra("patient_ID");
        Patients myPatient = DB.getPatient(patient_ID);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name);
            getSupportActionBar().setSubtitle("Patient "+myPatient.getfName());
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        TextView fName = (TextView)findViewById(R.id.fName);
        fName.setText(myPatient.getfName());
        TextView mName = (TextView)findViewById(R.id.mName);
        mName.setText(myPatient.getmName());
        TextView lName = (TextView)findViewById(R.id.lName);
        lName.setText(myPatient.getlName());
        TextView dob =(TextView)findViewById(R.id.dob);
        dob.setText(myPatient.getDob());
        TextView phone = (TextView)findViewById(R.id.phone);
        phone.setText(myPatient.getPhone());
        TextView email = (TextView)findViewById(R.id.email);
        email.setText(myPatient.getEmail());
        TextView visit = (TextView)findViewById(R.id.visit_type);
        visit.setText(myPatient.getVisit_Type());
        TextView ins_Name = (TextView)findViewById(R.id.insurance_company);
        if(myPatient.getInsurance_company()==null ||  myPatient.getInsurance_company().isEmpty()){
            ins_Name.setText("N/A");
        }else{
            ins_Name.setText(myPatient.getInsurance_company());
        }
        TextView ins_No = (TextView)findViewById(R.id.insurance_No);
        if(myPatient.getInsurance_No()==null  ||  myPatient.getInsurance_No().isEmpty() ){
            ins_No.setText("N/A");
        }else{
            ins_No.setText(myPatient.getInsurance_No());
        }




    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
