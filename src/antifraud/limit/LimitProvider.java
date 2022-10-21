package antifraud.limit;

public interface LimitProvider {

    Long getLimit();

    void increaseLimit(Long transactionValue);

    void decreaseLimit(Long transactionValue);
}
