package components.weather;

import java.util.Date;
import java.util.Iterator;

import components.set.Set;
import components.set.Set1L;

/**
 * Layered implementations of secondary methods for {@code Weather}.
 */
public abstract class WeatherSecondary implements Weather {

    /*
     * Secondary methods -----------------------------------------------
     */

    /**
     * Implementation of Chart
     */
    public abstract class Chart1 implements Chart {
        /*
         * Private members -------------------------------------------
         */

        /**
         * a set of {@code Weather};
         */
        private Set<Weather> chart;

        /**
         * Creator of initial representation.
         */
        private void createNewRep() {
            this.chart = new Set1L<Weather>();
        }

        /*
         * Constructors---------------------------------------------------------
         */

        /**
         * No-argument constructor.
         */
        public Chart1() {
            this.createNewRep();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof Chart)) {
                return false;
            }
            Chart ct = (Chart) obj;
            if (this.size() != ct.size()) {
                return false;
            }
            Iterator<Weather> it1 = this.iterator();
            Iterator<Weather> it2 = ct.iterator();
            while (it1.hasNext()) {
                Weather x1 = it1.next();
                Object x2 = it2.next();
                if (!x1.equals(x2)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public int hashCode() {
            final int samples = 2;
            final int a = 37;
            final int b = 17;
            int result = 0;
            /*
             * This code makes hashCode run in O(1) time. It works because of
             * the iterator order string specification, which guarantees that
             * the (at most) samples entries returned by the it.next() calls are
             * the same when the two Queues are equal.
             */
            int n = 0;
            Iterator<Weather> it = this.iterator();
            while (n < samples && it.hasNext()) {
                n++;
                Weather x = it.next();
                result = a * result + b * x.hashCode();
            }
            result = a * result + b * this.hashCode();
            return result;
        }

        // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
        @Override
        public String toString() {
            StringBuilder result = new StringBuilder("(");
            for (Weather w : this.chart) {
                result.append("<");
                result.append(w.time() + ", " + w.condition() + ", "
                        + w.lowTemp() + ", " + w.highTemp());
                result.append(">, ");
            }
            result.delete(result.length() - 2, result.length());
            result.append(")");
            return result.toString();
        }

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
        @Override
        public void add(Weather w) {
            assert w != null : "Violation of: w is not null";
            boolean hasTime = false;
            for (Weather x : this.chart) {
                if (x.equals(w)) {
                    hasTime = true;
                }
            }
            assert !hasTime : "Violation of w.time is not in DOMAIN(this)";

            this.chart.add(w);
        }

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
        @Override
        public Weather remove(Date t) {
            assert t != null : "Violation of: t is not null";
            boolean hasTime = false;
            for (Weather w : this.chart) {
                if (w.time().equals(t)) {
                    hasTime = true;
                }
            }
            assert hasTime : "Violation of t is in DOMAIN(this)";

            Set<Weather> copy = this.chart.newInstance();
            copy.transferFrom(this.chart);
            Weather result = new Weather1();
            while (copy.size() > 0) {
                Weather w = copy.removeAny();
                if (w.time().equals(t)) {
                    result = w;
                } else {
                    this.chart.add(w);
                }

            }
            return this.chart.remove(result);
        }

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
        @Override
        public Weather removeAny() {
            assert this.chart.size() > 0 : "Violation of: |this| > 0";

            return this.chart.removeAny();
        }

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
        @Override
        public Condition condition(Date t) {
            assert t != null : "Violation of: t is not null";
            boolean hasTime = false;
            for (Weather w : this.chart) {
                if (w.time().equals(t)) {
                    hasTime = true;
                }
            }
            assert hasTime : "Violation of t is in DOMAIN(this)";

            Condition result = Condition.UNDEFINED;
            for (Weather w : this.chart) {
                if (w.time().equals(t)) {
                    result = w.condition();
                }
            }
            return result;
        }

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
        @Override
        public boolean hasTime(Date t) {
            assert t != null : "Violation of: t is not null";
            boolean hasTime = false;
            for (Weather w : this.chart) {
                if (w.time().equals(t)) {
                    hasTime = true;
                }
            }
            return hasTime;
        }

        /**
         * Reports size of {@code this}.
         *
         * @return the number of {@code Weather} in {@code this}
         * @ensures size = |this|
         */
        @Override
        public int size() {
            return this.chart.size();
        }

        /**
         * Creates and return a sequence of {@code Weather} copies in
         * {@code this} whose condition is {@code c}.
         *
         * @param c
         *            the condition to be checked
         * @return a new {@code Sequence<Weather>} whose condition is {@code c}
         * @ensures Sequence<Weather>.entries = Weather.time in this and
         *          Weather.condition = c
         */
        Set<Weather> setOfCondition(Condition c) {
            Set<Weather> s = new Set1L<Weather>();
            for (Weather w : this.chart) {
                if (w.condition().equals(c)) {
                    s.add(w);
                }
            }
            return s;
        }
    }

    /*
     * Public members ---------------------------------------------------------
     */

    /*
     * Common methods (from Object) -------------------------------------------
     */

    @Override
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Weather)) {
            return false;
        }
        Weather w = (Weather) obj;
        if (!this.time().equals(w.time())) {
            return false;
        }
        if (!this.condition().equals(w.condition())) {
            return false;
        }
        return true;
    }

    //CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public int hashCode() {
        final int a = 10;
        final int b = 17;
        int result = 0;
        result = (this.time().hashCode() / a) * b;
        return result;
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("<");
        result.append(this.time() + ", " + this.condition() + ", "
                + this.lowTemp() + this.highTemp());
        result.append(">");
        return result.toString();
    }
}
