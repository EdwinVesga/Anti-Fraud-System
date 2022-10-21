package antifraud.limit;


import org.springframework.stereotype.Component;

@Component
public class LimitOperatorImpl implements LimitOperator{

    public Long increaseLimit(Long currentLimit, Long valueTransaction) {
        return (long) Math.ceil(0.8 * currentLimit + 0.2 * valueTransaction);
    }

    public Long decreaseLimit(Long currentLimit, Long valueTransaction) {
        return (long) Math.ceil(0.8 * currentLimit - 0.2 * valueTransaction);
    }
}
