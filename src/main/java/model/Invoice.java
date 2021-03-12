package model;

import java.util.Calendar;

public class Invoice {
    private int number;

    // customer this invoice is connected to
    private Customer customer;

    private Double sum;
    private Double vat;
    private Double discount;

    private Calendar start;
    private Calendar printed;
    private Calendar payed;
    private Calendar credit;
    private Calendar warned;
    private Calendar canceled;

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

    private int warnLevel;

    public Invoice() {
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Double getSum() {
        return sum;
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

    public Calendar getPrinted() {
        return printed;
    }

    public void setPrinted(Calendar printed) {
        this.printed = printed;
    }

    public Calendar getPayed() {
        return payed;
    }

    public void setPayed(Calendar payed) {
        this.payed = payed;
    }

    public Calendar getCredit() {
        return credit;
    }

    public void setCredit(Calendar credit) {
        this.credit = credit;
    }

    public Calendar getWarned() {
        return warned;
    }

    public void setWarned(Calendar warned) {
        this.warned = warned;
    }

    public Calendar getCanceled() {
        return canceled;
    }

    public void setCanceled(Calendar canceled) {
        this.canceled = canceled;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(int payMethod) {
        this.payMethod = payMethod;
    }

    public int getWarnLevel() {
        return warnLevel;
    }

    public void setWarnLevel(int warnLevel) {
        this.warnLevel = warnLevel;
    }
}
