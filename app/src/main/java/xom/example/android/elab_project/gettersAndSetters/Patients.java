package xom.example.android.elab_project.gettersAndSetters;


import java.util.List;

/**
 * Created by Mawinda on 28-Jan-18.
 */

public class Patients {
    private String id, fName, mName, lName, email, phone, dob, visit_Type, date, insurance_company, insurance_No, diagnosis, specimen, test_type, lab;
    private Boolean insurance_switch, test_switch;
    private List<String[]> tests;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getInsurance_company() {
        return insurance_company;
    }

    public void setInsurance_company(String insurance_company) {
        this.insurance_company = insurance_company;
    }

    public String getInsurance_No() {
        return insurance_No;
    }

    public void setInsurance_No(String insurance_No) {
        this.insurance_No = insurance_No;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getSpecimen() {
        return specimen;
    }

    public void setSpecimen(String specimen) {
        this.specimen = specimen;
    }

    public String getTest_type() {
        return test_type;
    }

    public void setTest_type(String test_type) {
        this.test_type = test_type;
    }

    public Boolean getInsurance_switch() {
        return insurance_switch;
    }

    public void setInsurance_switch(Boolean insurance_switch) {
        this.insurance_switch = insurance_switch;
    }

    public Boolean getTest_switch() {
        return test_switch;
    }

    public void setTest_switch(Boolean test_switch) {
        this.test_switch = test_switch;
    }

    public String getVisit_Type() {
        return visit_Type;
    }

    public void setVisit_Type(String visit_Type) {
        this.visit_Type = visit_Type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String[]> getTests() {
        return tests;
    }

    public void setTests(List<String[]> tests) {
        this.tests = tests;
    }

    public String getLab() {
        return lab;
    }

    public void setLab(String lab) {
        this.lab = lab;
    }
}
