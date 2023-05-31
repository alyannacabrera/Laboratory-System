public class Services {


    private String serviceCode;
    private String serviceDescription;
    private int servicePrice;
    private char Delete;
    private String Reason;

    public Services(String serviceCode, String serviceDescription, int servicePrice)
    {
        this.serviceCode = serviceCode;
        this.serviceDescription = serviceDescription;
        this.servicePrice = servicePrice;
    }

    public Services(String serviceCode, String serviceDescription, int servicePrice, char Delete, String Reason)
    {
        this.serviceCode = serviceCode;
        this.serviceDescription = serviceDescription;
        this.servicePrice = servicePrice;
        this.Delete = Delete;
        this.Reason = Reason;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public int getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(int servicePrice) {
        this.servicePrice = servicePrice;
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