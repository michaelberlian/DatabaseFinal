package sample;

public class SaleDetail {
    String saledetailid, saleid,employeeid,employeename,itemid,itemname,unitprice,itemquantity,price;

    public SaleDetail(String saledetailid, String saleid, String employeeid, String employeename, String itemid, String itemname, String unitprice, String itemquantity, String price) {
        this.saledetailid = saledetailid;
        this.saleid = saleid;
        this.employeeid = employeeid;
        this.employeename = employeename;
        this.itemid = itemid;
        this.itemname = itemname;
        this.unitprice = unitprice;
        this.itemquantity = itemquantity;
        this.price = price;
    }

    public String getSaledetailid() {
        return saledetailid;
    }

    public String getSaleid() {
        return saleid;
    }

    public String getEmployeeid() {
        return employeeid;
    }

    public String getEmployeename() {
        return employeename;
    }

    public String getItemid() {
        return itemid;
    }

    public String getItemname() {
        return itemname;
    }

    public String getUnitprice() {
        return unitprice;
    }

    public String getItemquantity() {
        return itemquantity;
    }

    public String getPrice() {
        return price;
    }
}
