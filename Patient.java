public class Patient {
    private String Fname;
    private String Lname;
    private String Mname;
    private String Bday;
    private char Gender;
    private String Address;
    private String PhoneNo;
    private String NIDNo;
    private String PUI;
    private char Delete;
    private String Reason;

    public Patient(String PUI, String lname, String fname, String mname, String bday, char gender, String address, String phoneNo, String NIDNo) {
        Fname = fname;
        Lname = lname;
        Mname = mname;
        Bday = bday;
        Gender = gender;
        Address = address;
        PhoneNo = phoneNo;
        this.NIDNo = NIDNo;
        this.PUI = PUI;
    }

    public Patient(String PUI, String lname, String fname, String mname, String bday, char gender, String address, String phoneNo, String NIDNo, char delete, String reason) {
        Fname = fname;
        Lname = lname;
        Mname = mname;
        Bday = bday;
        Gender = gender;
        Address = address;
        PhoneNo = phoneNo;
        this.NIDNo = NIDNo;
        this.PUI = PUI;
        Delete = delete;
        Reason = reason;
    }

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public String getLname() {
        return Lname;
    }

    public void setLname(String lname) {
        Lname = lname;
    }

    public String getMname() {
        return Mname;
    }

    public void setMname(String mname) {
        Mname = mname;
    }

    public String getBday() {
        return Bday;
    }

    public void setBday(String bday) {
        Bday = bday;
    }

    public char getGender() {
        return Gender;
    }

    public void setGender(char gender) {
        Gender = gender;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getNIDNo() {
        return NIDNo;
    }

    public void setNIDNo(String NIDNo) {
        this.NIDNo = NIDNo;
    }

    public String getPUI() {
        return PUI;
    }

    public void setPUI(String PUI) {
        this.PUI = PUI;
    }

    public char getDelete() {
        return Delete;
    }

    public void setDelete(char delete) {
        Delete = delete;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }
}
