package components.weather;

import java.util.Date;

import components.standard.Standard;

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
public interface WeatherKernel extends Standard<Weather> {
    /**
     * The possible weather conditions.
     */
    enum Condition {
        SUNNY, CLOUDY, RAINY, SNOWY, WINDY, UNDEFINED
    }

    /**
     * Replaces the time of {@code this} with {@code time}.
     *
     * @param t
     *            the time replacing the old one
     * @replaces this.time
     * @requires [t is a valid Date]
     * @ensures this.time = t
     */
    void setTime(Date t);

    /**
     * Returns the time of {@code this}.
     *
     * @return the time of {@code this}
     * @aliases reference returned by {@code time}
     * @ensures time = this.time
     */
    Date time();

    /**
     * Replaces the weather condiiton of {@code this} with {@code time}.
     *
     * @param c
     *            the condition replacing the old one
     * @replaces this.condition
     * @requires [c is a valid Condition]
     * @ensures this.condition = c
     */
    void setCondition(Condition c);

    /**
     * Returns the weather condition of {@code this}.
     *
     * @return the condition of {@code this}
     * @aliases reference returned by {@code condition}
     * @ensures condition = this.condition
     */
    Condition condition();

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
    void setTemp(float lowest, float highest);

    /**
     * Returns the lowest temperature of {@code this}.
     *
     * @return the low of {@code this}
     * @ensures lowTemp = this.low
     */
    float lowTemp();

    /**
     * Returns the highest temperature of {@code this}.
     *
     * @return the high of {@code this}
     * @ensures highTemp = this.low
     */
    float highTemp();

}
