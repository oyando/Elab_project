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
 * Created by Mawinda on 30-Jan-18.
 */

public class TestsAdapter extends ArrayAdapter<Patients> {
    private Context context;
    private List<Patients> myTestList;

    public TestsAdapter(@NonNull Context context, List<Patients> myTestList) {
        super(context, R.layout.tests_list, myTestList);
        this.context = context;
        this.myTestList = myTestList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            assert inflater != null;
            convertView = inflater.inflate(R.layout.tests_list, parent, false);
        }
        TextView test = (TextView) convertView.findViewById(R.id.test);
        TextView specimen = (TextView) convertView.findViewById(R.id.specimen);
        TextView test_type = (TextView) convertView.findViewById(R.id.test_type);
        TextView lab = (TextView) convertView.findViewById(R.id.test_lab);


        Patients myTest = myTestList.get(position);
        convertView.setTag(myTest.getId());
        String test_ = "test " + (position + 1);
        test.setText(test_);
        specimen.setText(myTest.getSpecimen());
        test_type.setText(myTest.getTest_type());
        lab.setText(myTest.getLab());
        return convertView;
    }
}
