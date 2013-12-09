package org.optaplanner.core.api.domain.value;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.optaplanner.core.api.domain.value.buildin.primint.IntValueRange;
import org.optaplanner.core.api.domain.variable.PlanningVariable;
import org.optaplanner.core.config.solver.EnvironmentMode;
import org.optaplanner.core.impl.heuristic.selector.value.ValueSelector;

/**
 * A ValueRange is a set of a values for a {@link PlanningVariable}.
 * These values might be stored in memory as a {@link Collection} (usually a {@link List} or {@link Set}),
 * but if the values are numbers, they can also be stored in memory by their bounds
 * to use less memory and provide more opportunities.
 * <p/>
 * A ValueRange is stateful (unlike a {@link ValueSelector} which is stateless.
 * <p/>
 * An implementation must extend {@link AbstractValueRange} to ensure backwards compatibility in future versions.
 * @see AbstractValueRange
 * @see IntValueRange
 */
public interface ValueRange<T> {

    /**
     * @return false if the value range is not countable
     * (for example a double value range between 1.2 and 1.4 is not countable)
     */
    boolean isCountable();

    /**
     * Used by uniform random selection in a composite or nullable ValueRange.
     * @return the exact number of elements generated by this {@link ValueRange}, always >= 0
     * @throws IllegalStateException if {@link #isCountable} returns false
     */
    long getSize();

    /**
     * Used by uniform random selection in a composite or nullable ValueRange.
     * @param index always < {@link #getSize()}
     * @return sometimes null (if {@link PlanningVariable#nullable()} is true)
     * @throws IllegalStateException if {@link #isCountable} returns false
     */
    T get(long index);

    /**
     *
     * @return never null
     */
    Iterator<T> createOriginalIterator();

    /**
     *
     * @param workingRandom never null, the {@link Random} to use when any random number is needed,
     * so {@link EnvironmentMode#REPRODUCIBLE} works correctly
     * @return never null
     */
    Iterator<T> createRandomIterator(Random workingRandom);

}
