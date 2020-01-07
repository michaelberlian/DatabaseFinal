package sample;

public class Purchase {
    String purchaseid,supplierid,suppliername,datentime;

    public Purchase(String purchaseid, String supplierid, String suppliername, String datentime) {
        this.purchaseid = purchaseid;
        this.supplierid = supplierid;
        this.suppliername = suppliername;
        this.datentime = datentime;
    }

    public String getPurchaseid() {
        return purchaseid;
    }

    public String getSupplierid() {
        return supplierid;
    }

    public String getSuppliername() {
        return suppliername;
    }

    public String getDatentime() {
        return datentime;
    }
}
