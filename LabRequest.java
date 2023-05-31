public class LabRequest
{
    private String RequestUID;
    private String RequestDate;
    private String PatientUID;
    private String RequestTime;
    private String Result;
    private char Delete;
    private String Reason;



    public LabRequest(String requestUID, String patientUID, String requestDate, String requestTime, String result) {
        RequestUID = requestUID;
        RequestDate = requestDate;
        PatientUID = patientUID;
        RequestTime = requestTime;
        Result = result;
    }

    public LabRequest(String requestUID, String patientUID, String requestDate, String requestTime, String result, char delete, String reason) {
        RequestUID = requestUID;
        RequestDate = requestDate;
        PatientUID = patientUID;
        RequestTime = requestTime;
        Result = result;
        Delete = delete;
        Reason = reason;
    }

    public String getRequestUID() {
        return RequestUID;
    }

    public void setRequestUID(String requestUID) {
        RequestUID = requestUID;
    }

    public String getRequestDate() {
        return RequestDate;
    }

    public void setRequestDate(String requestDate) {
        RequestDate = requestDate;
    }

    public String getPatientUID() {
        return PatientUID;
    }

    public void setPatientUID(String patientUID) {
        PatientUID = patientUID;
    }

    public String getRequestTime() {
        return RequestTime;
    }

    public void setRequestTime(String requestTime) {
        RequestTime = requestTime;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
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
