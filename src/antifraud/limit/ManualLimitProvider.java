package antifraud.limit;

import antifraud.entity.api.Limit;
import antifraud.repository.LimitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Optional;

@Component(value = "manualProvider")
public class ManualLimitProvider implements LimitProvider {

    private static final String MANUAL_PROCESSING = "MANUAL_PROCESSING";

    private static final Long INITIAL_VALUE = 1500L;

    private Limit manualLimit;

    private LimitOperator limitOperator;

    private LimitRepository limitRepository;

    @Autowired
    public ManualLimitProvider(LimitOperator limitOperator,
                               LimitRepository limitRepository) {
        this.limitRepository = limitRepository;
        this.limitOperator = limitOperator;
        init();
    }

    private void init() {
        Optional<Limit> limitOptional = limitRepository.findByType(MANUAL_PROCESSING);
        limitOptional.ifPresentOrElse(l -> manualLimit = l, () -> {
            Limit limit = new Limit();
            limit.setLimit(INITIAL_VALUE);
            limit.setType(MANUAL_PROCESSING);
            manualLimit = limitRepository.save(limit);
        });
    }

    @Override
    public Long getLimit() {
        return manualLimit.getLimit();
    }

    @Override
    public void increaseLimit(Long transactionValue) {
        manualLimit.setLimit(limitOperator.increaseLimit(manualLimit.getLimit(), transactionValue));
        updateLimit();
    }

    @Override
    public void decreaseLimit(Long transactionValue) {
        manualLimit.setLimit(limitOperator.decreaseLimit(manualLimit.getLimit(), transactionValue));
        updateLimit();
    }

    private void updateLimit() {
        limitRepository.save(manualLimit);
    }
}
