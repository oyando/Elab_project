package xom.example.android.elab_project;

import android.app.DatePickerDialog;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import xom.example.android.elab_project.gettersAndSetters.Patients;
import xom.example.android.elab_project.myClass.Mystrings;

public class AddPatientActivity extends AppCompatActivity {
    String insurance_company_name = "";
    String mySpecimen = "";
    String myTest_type = "";
    String visit = "";
    DatabaseHandler DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_form);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name);
            getSupportActionBar().setSubtitle("Add Patient");
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        //views initialisation
        final TextInputEditText fName = (TextInputEditText)findViewById(R.id.fName);
        final TextInputEditText mName = (TextInputEditText)findViewById(R.id.mName);
        final TextInputEditText lName = (TextInputEditText)findViewById(R.id.lName);
        final TextInputEditText email = (TextInputEditText)findViewById(R.id.email);
        final TextInputEditText phone = (TextInputEditText)findViewById(R.id.phone);
        final TextInputEditText dob =(TextInputEditText)findViewById(R.id.dob);
        final Switch insurance_switch = (Switch)findViewById(R.id.insurance_switch);
        final LinearLayout insurance_section = (LinearLayout)findViewById(R.id.insurance_section);
        final Spinner insurance_company = (Spinner)findViewById(R.id.insurance_company);
        final TextInputEditText insurance_No = (TextInputEditText)findViewById(R.id.insurance_No);
        final EditText diagnosis = (EditText)findViewById(R.id.diagnosis);
        final Switch test_switch = (Switch)findViewById(R.id.test_switch);
        final LinearLayout test_section = (LinearLayout)findViewById(R.id.test_section);
        final Spinner specimen = (Spinner) findViewById(R.id.specimen);
        final Spinner test_type = (Spinner)findViewById(R.id.test_type);
        Button prev_test = (Button)findViewById(R.id.prev_testBtn);
        Button next_test = (Button)findViewById(R.id.next_testBtn);
        final Spinner visit_type = (Spinner)findViewById(R.id.visit_type);

        DB = new DatabaseHandler(AddPatientActivity.this);
        ArrayAdapter<String> myVisitTypeAdapter = new ArrayAdapter<String>(AddPatientActivity.this,R.layout.list_spinner,Mystrings.visit_type);
        myVisitTypeAdapter.setDropDownViewResource(R.layout.list_spinner);
        visit_type.setAdapter(myVisitTypeAdapter);


        ArrayAdapter<String> mySpecimenAdapter = new ArrayAdapter<String>(AddPatientActivity.this,R.layout.list_spinner,Mystrings.specimens);
        mySpecimenAdapter.setDropDownViewResource(R.layout.list_spinner);
        specimen.setAdapter(mySpecimenAdapter);

        ArrayAdapter<String> insuranceCompanyAdapter = new ArrayAdapter<String>(AddPatientActivity.this,R.layout.list_spinner,Mystrings.insuranceCompany);
        insuranceCompanyAdapter.setDropDownViewResource(R.layout.list_spinner);
        insurance_company.setAdapter(insuranceCompanyAdapter);

        ArrayAdapter<String> myTestAdapter = new ArrayAdapter<String>(AddPatientActivity.this,R.layout.list_spinner,Mystrings.testTypes);
        myTestAdapter.setDropDownViewResource(R.layout.list_spinner);
        test_type.setAdapter(myTestAdapter);

         /*Date picking dialog*//**/
       final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                setDate(dob,myCalendar);
            }
        };

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddPatientActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


       //insurance section
        insurance_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                   insurance_section.setVisibility(View.VISIBLE);
                }
                else {
                   insurance_section.setVisibility(View.GONE);
                }
            }
        });

        //Test section
        test_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                   test_section.setVisibility(View.VISIBLE);
                }else{
                   test_section.setVisibility(View.GONE);
                }
            }
        });
        //getting visit type
        visit_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                visit = Mystrings.visit_type[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //getting insurance company name
        insurance_company.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                insurance_company_name = Mystrings.insuranceCompany[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                insurance_company_name = "";

            }
        });
        //select specimen
        specimen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mySpecimen = Mystrings.specimens[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mySpecimen = "";
            }
        });
        //select test type
        test_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                myTest_type = Mystrings.testTypes[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
               myTest_type = "";
            }
        });

        Button submit = (Button)findViewById(R.id.SubmitBtn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if( validate(fName,mName,lName,phone,email,dob,insurance_switch,insurance_company_name,insurance_No,diagnosis,test_switch,mySpecimen,myTest_type,visit))
                  submit(fName,mName,lName,email,phone,dob,insurance_switch,insurance_company_name,insurance_No,diagnosis,test_switch,mySpecimen,myTest_type,visit);
            }
        });
    }

    private void setDate(TextInputEditText dob,Calendar myCalendar) {
        String myFormat = "EEE dd-MMM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        dob.setError(null);
        dob.setText(sdf.format(myCalendar.getTime()));
    }

    /**
     *
      * @param fName patient first name
     * @param mName patient middle name
     * @param lName patient last name
     * @param phone patient phone
     * @param dob patient date of birth
     * @param insurance_switch switch to open insurance section
     * @param insurance_company insurance name
     * @param insurance_no member no
     * @param diagnosis patient diagnosis
     * @param test_switch switch to open test section
     * @param specimen patient specimen
     * @param test_type test to be performed
     * @return boolean
     */
    private boolean validate(TextInputEditText fName, TextInputEditText mName, TextInputEditText lName, TextInputEditText phone,TextInputEditText email, TextInputEditText dob, Switch insurance_switch, String insurance_company, TextInputEditText insurance_no, EditText diagnosis, Switch test_switch, String specimen, String test_type,String visit_type) {
        Pattern mobile = Pattern.compile("(?:254|\\+254|0){1}[ ]?[7]{1}([0-9]{1}[0-9]{1})[ ]?[0-9]{3}[ ]?[0-9]{3}\\z");

        Pattern emai = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcherMobile = mobile.matcher(phone.getText().toString());
        Matcher matcherEmail = emai.matcher(email.getText().toString());
        if(fName.getText()==null || fName.getText().toString().isEmpty()){
            fName.requestFocus();
            fName.setError("field is required");
            return false;
        }else if(mName.getText()==null || mName.getText().toString().isEmpty()){
            mName.requestFocus();
            mName.setError("field is required");
            return false;
        }else if(lName.getText()==null || lName.getText().toString().isEmpty()){
            lName.requestFocus();
            lName.setError("field is required");
            return false;
        }else if(!email.getText().toString().isEmpty() && !matcherEmail.matches()){
            email.requestFocus();
            email.setError("Invalid Email!");
            return false;
        }else if(phone.getText()==null || phone.getText().toString().isEmpty()){
            phone.requestFocus();
            phone.setError("field is required");
            return false;
        }else if(!phone.getText().toString().isEmpty() && !matcherMobile.matches()){
            phone.requestFocus();
            phone.setError("Invalid Phone Number!");
            return false;
        }else if(dob.getText()==null || dob.getText().toString().isEmpty()){
            dob.requestFocus();
            dob.setError("field is required");
            return false;
        }else if(visit_type.isEmpty()){
            Toast.makeText(AddPatientActivity.this,"Select Visit Type!",Toast.LENGTH_SHORT).show();
            return false;

        }else if(insurance_switch.isChecked()){
           if(insurance_company.isEmpty()){
               Toast.makeText(AddPatientActivity.this,"Select Company!",Toast.LENGTH_SHORT).show();
               return false;
           }else if(insurance_no.getText()==null || insurance_no.getText().toString().isEmpty()) {
               insurance_no.requestFocus();
               insurance_no.setError("field is required");
               return false;
           }
        }else if(diagnosis.getText()==null||diagnosis.getText().toString().isEmpty()){
            diagnosis.requestFocus();
            diagnosis.setError("Diagnosis can't be empty");
        }else if(test_switch.isChecked()){
            if(specimen.isEmpty()){
                Toast.makeText(AddPatientActivity.this,"Select Specimen!",Toast.LENGTH_SHORT).show();
                return false;
            }else if(test_type.isEmpty()){
                Toast.makeText(AddPatientActivity.this,"Select Test!",Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private void submit(TextInputEditText fName, TextInputEditText mName, TextInputEditText lName, TextInputEditText email, TextInputEditText phone, TextInputEditText dob, Switch insurance_switch, String  company_name, TextInputEditText insurance_no, EditText diagnosis, Switch test_switch, String specimen, String test_type,String visit) {
        Patients details = new Patients();
        details.setfName(fName.getText().toString());
        details.setmName(mName.getText().toString());
        details.setlName(lName.getText().toString());
        details.setEmail(email.getText().toString());
        details.setPhone(phone.getText().toString());
        details.setDob(dob.getText().toString());
        details.setVisit_Type(visit);
        if(insurance_switch.isChecked()){
            details.setInsurance_switch(true);
        }else{
            details.setInsurance_switch(false);
        }
        details.setInsurance_company(company_name);
        details.setInsurance_No(insurance_no.getText().toString());
        details.setDiagnosis(diagnosis.getText().toString());
        if(test_switch.isChecked()){
          details.setTest_switch(true);
        }else{
           details.setTest_switch(false);
        }
        details.setSpecimen(specimen);
        details.setTest_type(test_type);

      DB.addPatient(details);
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
