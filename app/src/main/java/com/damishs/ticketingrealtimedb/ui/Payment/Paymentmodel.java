package com.damishs.ticketingrealtimedb.ui.Payment;

public class Paymentmodel {

        String account_Id;
        String balance;
        String cardname;
        String cardnumber;
        String expdate;
        String CVV;
        String amount;
   public Paymentmodel(){}

    public Paymentmodel(String account_Id, String balance, String cardname, String cardnumber, String expdate, String CVV, String amount){

       this.account_Id=account_Id;
       this.balance=balance;
       this.cardname=cardname;
       this.cardnumber=cardnumber;
       this.expdate=expdate;
       this.CVV=CVV;
       this.amount=amount;
    }

    public String getAccount_Id() {
        return account_Id;
    }

    public String getBalance() {
        return balance;
    }
    public String getCardname() {
        return cardname;
    }
    public String getCardnumber() {
        return cardnumber;
    }

    public String getExpdate() {
        return expdate;
    }

    public String getCVV() {
        return CVV;
    }

    public String getAmount() {
        return amount;
    }
}
