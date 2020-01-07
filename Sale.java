package sample;

public class Sale {
    String saleid,customerid,customername,datentime;

    public Sale(String saleid, String customerid, String customername, String datentime) {
        this.saleid = saleid;
        this.customerid = customerid;
        this.customername = customername;
        this.datentime = datentime;
    }

    public String getSaleid() {
        return saleid;
    }

    public String getCustomerid() {
        return customerid;
    }

    public String getCustomername() {
        return customername;
    }

    public String getDatentime() {
        return datentime;
    }
}
