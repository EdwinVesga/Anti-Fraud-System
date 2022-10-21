package antifraud.limit;

public interface LimitOperator {
    Long increaseLimit(Long currentLimit, Long transactionValue);

    Long decreaseLimit(Long currentLimit, Long transactionValue);
}
