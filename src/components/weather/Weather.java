package components.weather;

import java.util.Date;

import components.set.Set;

/**
 * {@code WeatherKernel} enhanced with secondary methods.
 *
 * @mathsubtypes <pre>
 * WEATHER_MODEL is (
 *   time: Date,
 *   condition: Condition,
 *   low: lowest air temperature,
 *   high: highest air temperature,
 *  )
 *  exemplar w
 *  constraint
 *   [w.time is not before January 1, 1970, 00:00:00 GMT]
 * </pre>
 * @mathmodel type Weather is modeled by WEATHER_MODEL
 * @initially {@code <pre>
 * ():
 *  ensures
 *   this = <current-time, UNDEFINED, -200, 200>
 * </pre>  }
 */
public interface Weather extends WeatherKernel {
    /**
     * Collection of {@code Weather}.
     *
     * @mathsubtypes <pre>
     * PARTIAL_FUNCTION is finite set of Weather
     *  exemplar wc
     *  constraint
     *   for all time1, time2: T, condition1, condition2: C
     *     where (time1 is in wc  and  time2 is in wc)
     *    (if time1 = time2 then condition1 = condition2)
     * </pre>
     * @mathdefinitions <pre>
     * DOMAIN(
     *   wc: PARTIAL_FUNCTION
     *  ): finite set of T satisfies
     *  for all time: T (time is in DOMAIN(m) iff
     *   there exists condition: C ((time, condition) is in m))
     * </pre>
     * @mathmodel type Chart is modeled by PARTIAL_FUNCTION
     * @initially <pre>
     * ():
     *  ensures
     *   this = {}
     * </pre>
     * @iterator <pre>
     * entries(~this.seen * ~this.unseen) = this  and
     * |~this.seen * ~this.unseen| = |this|
     * </pre>
     */
    interface Chart extends Iterable<Weather> {
        /**
         * Adds the Weather {@code w} to this.
         *
         * @param w
         *            the {@code Weather} to add
         * @updates this
         * @aliases reference {@code w}
         * @requires w.time is not in DOMAIN(this)
         * @ensures this = #this union {w}
         */
        void add(Weather w);

        /**
         * Removes the {@code Weather} whose first component is {@code time} and
         * returns it.
         *
         * @param t
         *            the time to be checked
         * @return {@code Weather} removed
         * @updates this
         * @requires t is in DOMAIN(this)
         * @ensures <pre>
         * remove.time = t  and
         * remove is in #this  and
         * this = #this \ {remove}
         * </pre>
         */
        Weather remove(Date t);

        /**
         * Removes and returns an arbitrary {@code Weather} from {@code this}.
         *
         * @return the {@code Weather} removed from {@code this}
         * @updates this
         * @requires |this| > 0
         * @ensures <pre>
         * removeAny is in #this and
         * this = #this \ {removeAny}
         * </pre>
         */
        Weather removeAny();

        /**
         * Reports the condition associated with {@code t} in {@code this}.
         *
         * @param t
         *            the time whose associated condition is to be reported
         * @return the condition associated with key
         * @aliases reference returned by {@code condition}
         * @requires t is in DOMAIN(this)
         * @ensures (time, condition) is in this
         */
        Condition condition(Date t);

        /**
         * Reports whether there is a {@code Weather} in {@code this} whose
         * first component is {@code t}.
         *
         * @param t
         *            the time to be checked
         * @return true iff there is a {@code Weather} in this whose first
         *         component is {@code t}
         * @ensures hasTime = (t is in DOMAIN(this))
         */
        boolean hasTime(Date t);

        /**
         * Reports size of {@code this}.
         *
         * @return the number of {@code Weather} in {@code this}
         * @ensures size = |this|
         */
        int size();

        /**
         * Creates and return a sequence of in {@code this} whose condition is
         * {@code c}.
         *
         * @param c
         *            the condition to be checked
         * @return a new {@code Sequence<Date>} of times whose condition is
         *         {@code c}
         * @ensures Sequence<Date>.entries = Weather.time in this and
         *          Weather.condition = c
         */
        Set<Date> setOfUndefined(Condition c);
    }
}
