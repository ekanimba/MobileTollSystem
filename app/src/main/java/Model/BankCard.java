package Model;

public class BankCard {

    String cardNumber;
    String cardHolderName;
    String cardExpNumberMonth;
    String cardExpNumberYear;
    String cardCcv;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCardExpNumberMonth() {
        return cardExpNumberMonth;
    }

    public void setCardExpNumberMonth(String cardExpNumberMonth) {
        this.cardExpNumberMonth = cardExpNumberMonth;
    }

    public String getCardExpNumberYear() {
        return cardExpNumberYear;
    }

    public void setCardExpNumberYear(String cardExpNumberYear) {
        this.cardExpNumberYear = cardExpNumberYear;
    }

    public String getCardCcv() {
        return cardCcv;
    }

    public void setCardCcv(String cardCcv) {
        this.cardCcv = cardCcv;
    }


    public BankCard(String cadNumber, String cardHolderName, String cardExpNumberMonth, String cardExpNumberYear,String cardCcv){
        this.cardNumber = cadNumber;
        this.cardHolderName = cardHolderName;
        this.cardExpNumberMonth = cardExpNumberMonth;
        this.cardExpNumberYear = cardExpNumberYear;
        this.cardCcv = cardCcv;
    }

}
