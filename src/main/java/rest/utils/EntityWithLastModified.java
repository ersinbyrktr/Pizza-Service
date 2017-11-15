package rest.utils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Immutable
public class EntityWithLastModified<T> {

    private final Optional<T> _entity;
    private final Optional<Date> _lastModified;

    public EntityWithLastModified(@Nonnull final Optional<T> entity, @Nonnull final Optional<Date> lastModified) {
        _entity = entity;
        _lastModified = lastModified;
    }

    public EntityWithLastModified(@Nullable final T entity, @Nullable final Date lastModified) {
        _entity = Optional.ofNullable(entity);
        _lastModified = Optional.ofNullable(lastModified);
    }

    public static <T> EntityWithLastModified<T> empty(){
        return new EntityWithLastModified<>(Optional.empty(), Optional.empty());
    }

    @Nonnull
    public Optional<T> getEntity() {
        return _entity;
    }

    @Nonnull
    public Optional<Date> getLastModified() {
        return _lastModified;
    }

    @Override
    public int hashCode() {
        return Objects.hash(_entity, _lastModified);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(final Object obj) {
        boolean result = obj == this;

        if (!result && obj instanceof EntityWithLastModified<?>) {
            final EntityWithLastModified<?> other = (EntityWithLastModified<?>) obj;
            result = _entity.equals(other.getEntity()) && _lastModified.equals(other.getLastModified());
        }

        return result;
    }

    @Override
    public String toString() {
        return String.format("%s: %s", _entity, _lastModified);
    }
}
