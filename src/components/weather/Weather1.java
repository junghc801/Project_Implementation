package components.weather;

import java.util.Date;

/**
 * Implementations of {@code Weather} primary methods
 *
 * @convention <pre>
 * [$this.time is time of the record] and
 * [$this.condition is a weather condition] and
 * [$this.low and $this.high is the lowest and highest temperature of the day] and
 * [for every low and high of Weather falls in (-200, 200)].
 * </pre>
 * @correspondence this = <$this.time, $this.condition, $this.low, $this.high>
 *
 *
 * @author Haechan Jung
 *
 */
public class Weather1 extends WeatherSecondary {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * time.
     */
    private Date time;

    /**
     * condition.
     */
    private Condition condition;

    /**
     * lowest temperature.
     */
    private float low;

    /**
     * highest temperature.
     */
    private float high;

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
        this.time = new Date();
        this.condition = Condition.UNDEFINED;
        this.low = -200f;
        this.high = 200f;
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Weather1() {
        this.createNewRep();
    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @SuppressWarnings("unchecked")
    @Override
    public final Weather newInstance() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    @Override
    public final void clear() {
        this.createNewRep();
    }

    @Override
    public final void transferFrom(Weather source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof Weather1 : ""
                + "Violation of: source is of dynamic type Set3<?>";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case: source must be of dynamic type Set3a<?>, and
         * the ? must be T or the call would not have compiled.
         */
        Weather1 localSource = (Weather1) source;
        this.time = localSource.time;
        this.condition = localSource.condition;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    /**
     * Replaces the time of {@code this} with {@code time}.
     *
     * @param t
     *            the time replacing the old one
     * @replaces this.time
     * @requires [t is a valid Date]
     * @ensures this.time = t
     */
    @Override
    public void setTime(Date t) {
        assert t != null : "Violation of: t is not null";
        this.time = t;
    }

    /**
     * Returns the time of {@code this}.
     *
     * @return the time of {@code this}
     * @aliases reference returned by {@code time}
     * @ensures time = this.time
     */
    @Override
    public Date time() {
        return this.time;
    }

    /**
     * Replaces the weather condiiton of {@code this} with {@code time}.
     *
     * @param c
     *            the condition replacing the old one
     * @replaces this.condition
     * @requires [c is a valid Condition]
     * @ensures this.condition = c
     */
    @Override
    public void setCondition(Condition c) {
        assert c != null : "Violation of: c is not null";
        this.condition = c;
    }

    /**
     * Returns the weather condition of {@code this}.
     *
     * @return the condition of {@code this}
     * @aliases reference returned by {@code condition}
     * @ensures condition = this.condition
     */
    @Override
    public Condition condition() {
        return this.condition;
    }

    /**
     * Replaces the lowest and highest temperature of {@code this} with
     * {@code low} and {@code high}.
     *
     * @param lowest
     *            the lowest temperature replacing the old one
     * @param highest
     *            the highest temperature replacing the old one
     * @replaces this.low, this.high
     * @requires {@code lowest <= highest and -200 < lowest, highest < 200}
     * @ensures this.low = lowest and this.high = highest
     */
    @Override
    public void setTemp(float lowest, float highest) {
        this.low = lowest;
        this.high = highest;
    }

    /**
     * Returns the lowest temperature of {@code this}.
     *
     * @return the low of {@code this}
     * @ensures lowTemp = this.low
     */
    @Override
    public float lowTemp() {
        return this.low;
    }

    /**
     * Returns the highest temperature of {@code this}.
     *
     * @return the high of {@code this}
     * @ensures highTemp = this.low
     */
    @Override
    public float highTemp() {
        return this.high;
    }

}
