package model;

public class Customer {
    private Integer number;

    private String salutation;
    private String firstName;
    private String lastName;
    private String firstAddition;
    private String secondAddition;
    private String street;
    private String streetNumber;
    private String postcode;
    private String city;
    private String country;
    private String telephone;
    private String mobile;
    private String fax;
    private String mail;
    private String remark;

    // private ArrayList<Offer> connected_offers;
    // private ArrayList<Invoice> connected_invoices;

    public Customer() {
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstAddition() {
        return firstAddition;
    }

    public void setFirstAddition(String firstAddition) {
        this.firstAddition = firstAddition;
    }

    public String getSecondAddition() {
        return secondAddition;
    }

    public void setSecondAddition(String secondAddition) {
        this.secondAddition = secondAddition;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
