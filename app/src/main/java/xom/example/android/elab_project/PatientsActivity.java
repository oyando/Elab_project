package xom.example.android.elab_project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import xom.example.android.elab_project.adapter.TestsAdapter;
import xom.example.android.elab_project.gettersAndSetters.Patients;
import xom.example.android.elab_project.myClass.CustomDialog;
import xom.example.android.elab_project.myClass.DatabaseHandler;
import xom.example.android.elab_project.myClass.StringFormat;

public class PatientsActivity extends AppCompatActivity {
    private DatabaseHandler DB;
    private String myPatientID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients);

        DB = new DatabaseHandler(PatientsActivity.this);
        String patient_ID = getIntent().getStringExtra("patient_ID");
        Patients myPatient = DB.getPatient(patient_ID);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name);
            getSupportActionBar().setSubtitle("Patient " + StringFormat.sentCase(myPatient.getfName()));
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        //Patient details section
        TextView fName = (TextView) findViewById(R.id.fName);
        fName.setText(StringFormat.sentCase(myPatient.getfName()));
        TextView mName = (TextView) findViewById(R.id.mName);
        mName.setText(StringFormat.sentCase(myPatient.getmName()));
        TextView lName = (TextView) findViewById(R.id.lName);
        lName.setText(StringFormat.sentCase(myPatient.getlName()));
        TextView dob = (TextView) findViewById(R.id.dob);
        dob.setText(myPatient.getDob());
        TextView phone = (TextView) findViewById(R.id.phone);
        phone.setText(myPatient.getPhone());
        TextView email = (TextView) findViewById(R.id.email);
        email.setText(myPatient.getEmail());
        TextView visit = (TextView) findViewById(R.id.visit_type);
        visit.setText(myPatient.getVisit_Type());
        //Diagnosis
        TextView diagnosis = (TextView) findViewById(R.id.diagnosis);
        diagnosis.setText(StringFormat.sentCase(myPatient.getDiagnosis()));
        //Insurance Section
        TextView ins_Name = (TextView) findViewById(R.id.insurance_company);
        if (myPatient.getInsurance_company() == null || myPatient.getInsurance_company().isEmpty()) {
            ins_Name.setText("N/A");
        } else {
            ins_Name.setText(StringFormat.sentCase(myPatient.getInsurance_company()));
        }
        TextView ins_No = (TextView) findViewById(R.id.insurance_No);
        if (myPatient.getInsurance_No() == null || myPatient.getInsurance_No().isEmpty()) {
            ins_No.setText("N/A");
        } else {
            ins_No.setText(myPatient.getInsurance_No());
        }
        myPatientID = myPatient.getId();
        //Test Section
        final List<Patients> myTestsList = DB.getMyTests(myPatientID);
        final ListView myTests = (ListView) findViewById(R.id.test_list);
        final TextView no_test = (TextView) findViewById(R.id.no_test);
        no_test.setVisibility(View.GONE);
        if (myTestsList.size() == 0) {
            no_test.setVisibility(View.VISIBLE);
        }

        final TestsAdapter myTestAdapter = new TestsAdapter(PatientsActivity.this, myTestsList);
        myTests.setAdapter(myTestAdapter);

        //further operations
        myTests.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String testId = view.getTag().toString();
                testClicked(testId, myTestsList, position, myTestAdapter, no_test);
            }
        });

    }

    private void testClicked(final String testId, final List<Patients> myTestsList, final int pos, final TestsAdapter myTestAdapter, final TextView no_test) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(PatientsActivity.this);
        dialog.setMessage("Choose action");
        dialog.setCancelable(false);
        dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (DB.deleteTest(testId)) {
                    myTestsList.remove(pos);
                    myTestAdapter.notifyDataSetChanged();
                    no_test.setVisibility(View.GONE);
                    if (myTestsList.size() == 0) {
                        no_test.setVisibility(View.VISIBLE);
                    }
                    Toast.makeText(PatientsActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CustomDialog customDialog = new CustomDialog(PatientsActivity.this, Integer.valueOf(myPatientID), Integer.valueOf(testId));
                customDialog.show();

            }
        });
        dialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 1, 1, "Add test");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case 1:
                CustomDialog Dialog = new CustomDialog(PatientsActivity.this, Integer.valueOf(myPatientID), 0);
                Dialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
