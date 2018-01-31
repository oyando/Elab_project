package xom.example.android.elab_project.myClass;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import xom.example.android.elab_project.R;

/**
 * Created by Mawinda on 31-Jan-18.
 */

public class CustomDialog extends Dialog implements View.OnClickListener {
    private Activity activity;
    private long personID;
    private int editID;
    private Button submit;
    private Button cancel;
    private String mySpecimen = "";
    private String myTest_type = "";
    private String myLab = "";
    private String[] test;
    private DatabaseHandler DB;


    public CustomDialog(@NonNull Activity activity, long personID, int editID) {
        super(activity);
        this.activity = activity;
        this.personID = personID;
        this.editID = editID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);

        DB = new DatabaseHandler(activity);

        submit = (Button) findViewById(R.id.submitBtn);
        submit.setOnClickListener(this);
        cancel = (Button) findViewById(R.id.cancelBtn);
        cancel.setOnClickListener(this);

        final Spinner specimen = (Spinner) findViewById(R.id.specimen);
        final Spinner test_type = (Spinner) findViewById(R.id.test_type);
        final Spinner lab = (Spinner) findViewById(R.id.test_lab);

        //select specimen
        ArrayAdapter<String> mySpecimenAdapter = new ArrayAdapter<String>(getContext(), R.layout.list_spinner, Mystrings.specimens);
        mySpecimenAdapter.setDropDownViewResource(R.layout.list_spinner);
        specimen.setAdapter(mySpecimenAdapter);

        specimen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0)
                    mySpecimen = Mystrings.specimens[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mySpecimen = "";
            }
        });
        //select test type
        ArrayAdapter<String> myTestAdapter = new ArrayAdapter<String>(getContext(), R.layout.list_spinner, Mystrings.testTypes);
        myTestAdapter.setDropDownViewResource(R.layout.list_spinner);
        test_type.setAdapter(myTestAdapter);

        test_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0)
                    myTest_type = Mystrings.testTypes[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                myTest_type = "";
            }
        });

        //select lab to carry test
        ArrayAdapter<String> myLabAdapter = new ArrayAdapter<String>(getContext(), R.layout.list_spinner, Mystrings.labs);
        myLabAdapter.setDropDownViewResource(R.layout.list_spinner);
        lab.setAdapter(myLabAdapter);

        lab.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0)
                    myLab = Mystrings.labs[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                myLab = "";

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submitBtn:
                if (validated(mySpecimen, myTest_type, myLab)) {

                    if (editID == 0) {
                        if (DB.addSingleTest(personID, test)) {
                            Toast.makeText(getContext(), "Added successful", Toast.LENGTH_SHORT).show();
                            dismiss();
                            activity.recreate();
                        }

                    } else {
                        if (DB.editTest(editID, personID, test)) {
                            Toast.makeText(getContext(), "Edited successful", Toast.LENGTH_SHORT).show();
                            dismiss();
                            activity.recreate();
                        }
                    }

                }
                break;
            case R.id.cancelBtn:
                dismiss();
                break;
            default:
                break;
        }

    }

    private boolean validated(String mySpecimen, String myTest_type, String myLab) {
        if (mySpecimen.isEmpty()) {
            Toast.makeText(getContext(), "choose specimen", Toast.LENGTH_SHORT).show();
            return false;
        } else if (myTest_type.isEmpty()) {
            Toast.makeText(getContext(), "choose test", Toast.LENGTH_SHORT).show();
            return false;
        } else if (myLab.isEmpty()) {
            Toast.makeText(getContext(), "choose Lab", Toast.LENGTH_SHORT).show();
            return false;
        }
        test = new String[]{mySpecimen, myTest_type, myLab};
        return true;
    }
}
