package xom.example.android.elab_project.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import xom.example.android.elab_project.R;
import xom.example.android.elab_project.gettersAndSetters.Patients;

/**
 * Created by Mawinda on 29-Jan-18.
 */

public class PatientsAdapter extends ArrayAdapter<Patients> {
    private Context context;
    private List<Patients> patientsList;
    public PatientsAdapter(@NonNull Context context, List<Patients> patientsList) {
        super(context, R.layout.patients_list,patientsList);
        this.context = context;
        this.patientsList = patientsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View patientView = convertView;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            assert inflater != null;
            patientView = inflater.inflate(R.layout.patients_list,parent,false);
        }
        TextView fName = patientView.findViewById(R.id.fName);
        TextView mName = patientView.findViewById(R.id.mName);
        TextView lName = patientView.findViewById(R.id.lName);

       Patients me = patientsList.get(position);
       patientView.setTag(me.getId());
       fName.setText(me.getfName());
       mName.setText(me.getmName());
       lName.setText(me.getlName());

        return patientView;
    }
}
