package model;

import java.util.Calendar;

public class Offer {
    private int number;

    // customer this offer is connected to
    private Customer customer;

    /*
    00: One time (not periodic)
    01: Every month
    02: Every two month
    03: Every three month
    04: Every four month
    06: Every six month
    12: Every twelve month
    */
    private int interval;

    /*
    0: Invoice (Rechnung)
    1: Transfer (Überweisung)
    2: Standing order (Dauerauftrag)
    */
    private int payMethod;

    // an optional category
    private String category;

    private Double sum;
    private Double vat;
    private Double discount;

    private Calendar start;
    private Calendar lastInvoice;

    public Offer() {
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getSum() {
        return sum;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getInterval() {
        return interval;
    }

    public int getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(int payMethod) {
        this.payMethod = payMethod;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public Double getVat() {
        return vat;
    }

    public void setVat(Double vat) {
        this.vat = vat;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Calendar getStart() {
        return start;
    }

    public void setStart(Calendar start) {
        this.start = start;
    }

    public int getTurnus() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public Calendar getLastInvoice() {
        return lastInvoice;
    }

    public void setLastInvoice(Calendar lastInvoice) {
        this.lastInvoice = lastInvoice;
    }
}
