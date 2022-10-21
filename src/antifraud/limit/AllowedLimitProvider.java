package antifraud.limit;

import antifraud.entity.api.Limit;
import antifraud.repository.LimitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component(value = "allowedProvider")
public class AllowedLimitProvider implements LimitProvider {

    private static final String ALLOWED = "ALLOWED";

    private static final Long INITIAL_VALUE = 200L;

    private Limit limitAllowed;

    private LimitOperator limitOperator;

    private LimitRepository limitRepository;

    @Autowired
    public AllowedLimitProvider(LimitOperator limitOperator,
                                LimitRepository limitRepository) {
        this.limitOperator = limitOperator;
        this.limitRepository = limitRepository;
        init();
    }

    private void init() {
        Optional<Limit> limitOptional = limitRepository.findByType(ALLOWED);
        limitOptional.ifPresentOrElse(l -> limitAllowed = l, () -> {
            Limit limit = new Limit();
            limit.setLimit(INITIAL_VALUE);
            limit.setType(ALLOWED);
            limitAllowed = limitRepository.save(limit);
        });
    }

    @Override
    public Long getLimit() {
        return limitAllowed.getLimit();
    }

    @Override
    public void increaseLimit(Long transactionValue) {
        limitAllowed.setLimit(limitOperator.increaseLimit(limitAllowed.getLimit(), transactionValue));
        updateLimit();
    }

    @Override
    public void decreaseLimit(Long transactionValue) {
        limitAllowed.setLimit(limitOperator.decreaseLimit(limitAllowed.getLimit(), transactionValue));
        updateLimit();
    }

    private void updateLimit() {
        limitRepository.save(limitAllowed);
    }
}
