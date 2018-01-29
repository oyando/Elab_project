package xom.example.android.elab_project;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import xom.example.android.elab_project.adapter.PatientsAdapter;
import xom.example.android.elab_project.gettersAndSetters.Patients;

import static android.content.ContentValues.TAG;

/**
 * Created by Mawinda on 26-Jan-18.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "E-lab_SQLite";
    //fields
    private static final String KEY_ID = "id";
    private static final String KEY_FNAME = "fName";
    private static final String KEY_LNAME = "lName";
    private static final String KEY_MNAME = "mName";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_DOB = "dob";
    private static final String KEY_DIAGNOSIS = "diagnosis";
    private static final String KEY_VISIT = "visitType";
    private static final String KEY_DATE = "date";
    private static final String KEY_INS_NAME = "insName";
    private static final String KEY_INS_NO = "insNO";
    private static final String KEY_SPECIMEN = "specimen";
    private static final String KEY_LAB = "lab";
    private static final String KEY_TEST_TYP = "testType";
    private static final String KEY_PATIENT_ID = "patient_ID";

    //tables
    private final String TABLE_PATIENTS = "patients_tb";
    private final String TABLE_TESTS ="tests_tb";

    //tb patients
    private String CREATE_TABLE_PATIENTS = "CREATE TABLE " + TABLE_PATIENTS + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_FNAME + " TEXT,"
            + KEY_LNAME + " TEXT,"
            + KEY_MNAME + " TEXT,"
            + KEY_EMAIL + " TEXT DEFAULT NULL,"
            + KEY_PHONE + " TEXT,"
            + KEY_DOB + " TEXT,"
            + KEY_DIAGNOSIS + " TEXT,"
            + KEY_VISIT + " TEXT,"
            + KEY_DATE + " TEXT,"
            + KEY_INS_NAME + " TEXT DEFAULT NULL,"
            + KEY_INS_NO + " TEXT DEFAULT NULL);";

    private String CREATE_TABLE_TESTS ="CREATE TABLE "+ TABLE_TESTS + "("
            +KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_SPECIMEN + " TEXT,"
            + KEY_LAB + " TEXT,"
            + KEY_TEST_TYP + " TEXT,"
            + KEY_PATIENT_ID + " LONG )";


    private Context context;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PATIENTS);
        db.execSQL(CREATE_TABLE_TESTS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATIENTS);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TESTS);

        // Create tables again
        onCreate(db);
    }

    public void addPatient(Patients patient){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FNAME,patient.getfName());
        values.put(KEY_LNAME,patient.getlName());
        values.put(KEY_MNAME,patient.getmName());
        values.put(KEY_EMAIL,patient.getEmail());
        values.put(KEY_PHONE,patient.getPhone());
        values.put(KEY_DOB,patient.getDob());
        values.put(KEY_DIAGNOSIS,patient.getDiagnosis());
        values.put(KEY_VISIT,patient.getVisit_Type());
        values.put(KEY_DATE,"WOOW");
        if(patient.getInsurance_switch()){
            values.put(KEY_INS_NAME,patient.getInsurance_company());
            values.put(KEY_INS_NO,patient.getInsurance_No());
        }
        //inserting into db
       long inserted = db.insert(TABLE_PATIENTS,null,values);

        if(inserted == -1){
            Toast.makeText(context,"Failed!",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Patient added successful!",Toast.LENGTH_SHORT).show();
            if(patient.getTest_switch()) {
                addTest(patient, inserted);
            }else{
                context.startActivity(new Intent(context,LandingActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        }

    }

    public void addTest(Patients patient,long sec_ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SPECIMEN,patient.getSpecimen());
        values.put(KEY_LAB,"Lab 1");
        values.put(KEY_TEST_TYP,patient.getTest_type());
        values.put(KEY_PATIENT_ID,sec_ID);

        long inserted = db.insert(TABLE_PATIENTS,null,values);

        if(inserted == -1){
            Toast.makeText(context,"Failed to insert Test!",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Test added successful!",Toast.LENGTH_SHORT).show();
            context.startActivity(new Intent(context,LandingActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
    }

    public void getAllPatients(ListView myListView, LinearLayout myLayout, TextView empty){
        List<Patients> patientsList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_PATIENTS + " ORDER BY " + KEY_ID + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Patients patient = new Patients();
                patient.setId(cursor.getString(0));
                patient.setfName(cursor.getString(1));
                patient.setmName(cursor.getString(3));
                patient.setlName(cursor.getString(2));
                patientsList.add(patient);
            } while (cursor.moveToNext());
        }
        cursor.close();
        if(patientsList.size() == 0){
            myLayout.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        }else{
            PatientsAdapter adapter = new PatientsAdapter(context,patientsList);
            myListView.setAdapter(adapter);
        }


    }

    public void search(String data,ListView myListView,TextView empty){
        myListView.setVisibility(View.VISIBLE);
        empty.setVisibility(View.GONE);
        List<Patients> patientsList = new ArrayList<>();
        String[] search = new String[1];
        search[0]= "%"+data+"%";
        String selectQuery = "SELECT  * FROM " + TABLE_PATIENTS
                + " WHERE "+KEY_FNAME+" LIKE '%"+data+"%' OR "
               +KEY_LNAME+" LIKE '%"+data+"%' OR "
                +KEY_MNAME+" LIKE '%"+data+"%' OR "
               +KEY_INS_NO+" LIKE '%"+data+"%' ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Patients patient = new Patients();
                patient.setId(cursor.getString(0));
                patient.setfName(cursor.getString(1));
                patient.setmName(cursor.getString(3));
                patient.setlName(cursor.getString(2));
                patientsList.add(patient);
            } while (cursor.moveToNext());
        }
        cursor.close();

        if(patientsList.size() == 0){
            myListView.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        }else{
            PatientsAdapter adapter = new PatientsAdapter(context,patientsList);
            myListView.setAdapter(adapter);
        }


    }

    public Patients getPatient(String patient_id) {
        Patients myPatient = new Patients();
        String selectQuery = "SELECT  * FROM " + TABLE_PATIENTS + " WHERE " + KEY_ID + " = "+Integer.valueOf(patient_id) ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            myPatient.setId(cursor.getString(0));
            myPatient.setfName(cursor.getString(1));
            myPatient.setmName(cursor.getString(3));
            myPatient.setlName(cursor.getString(2));
            myPatient.setEmail(cursor.getString(4));
            myPatient.setPhone(cursor.getString(5));
            myPatient.setDob(cursor.getString(6));
            myPatient.setDiagnosis(cursor.getString(7));
            myPatient.setVisit_Type(cursor.getString(8));
            myPatient.setDate(cursor.getString(9));
            myPatient.setInsurance_company(cursor.getString(10));
            myPatient.setInsurance_No(cursor.getString(11));
        }
        cursor.close();

        return myPatient;
    }
}
