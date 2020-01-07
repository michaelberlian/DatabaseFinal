package sample;

public class PurchaseDetail {
    String purchasedetailid, purchaseid,employeeid,employeename,itemid,itemname,unitprice,itemquantity,price;

    public PurchaseDetail(String purchasedetailid, String purchaseid, String employeeid, String employeename, String itemid, String itemname, String unitprice, String itemquantity, String price) {
        this.purchasedetailid = purchasedetailid;
        this.purchaseid = purchaseid;
        this.employeeid = employeeid;
        this.employeename = employeename;
        this.itemid = itemid;
        this.itemname = itemname;
        this.unitprice = unitprice;
        this.itemquantity = itemquantity;
        this.price = price;
    }

    public String getPurchasedetailid() {
        return purchasedetailid;
    }

    public String getPurchaseid() {
        return purchaseid;
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
