package domain;

import java.util.Optional;

public interface DomainRepository<I, T extends Aggregate> {
    Optional<T> findById(I id);
    void save(T t);
}
