
import components.map.Map;
import components.map.Map1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.FormatChecker;

/**
 * @author Haechan Jung
 *
 */
public class Weather_POC<date, condition> {
    // 1. extend Pair<K,V> - refer to SimplePair<K, V> -- abstract class

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Representation of {@code this}.
     */
    private Map<String, String> weather;

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
        this.weather = new Map1L<String, String>();
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Weather_POC() {
        this.createNewRep();
    }

    /**
     * Constructor from {@code date} and {@code condition}.
     *
     * @param date
     *            {@code date} to initialize from
     * @param condition
     *            {@code condition} to initialize from
     */
    public Weather_POC(String date, String condition) {
        this.createNewRep();
        this.addWeather(date, condition);
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */
    /**
     * Adds the pair ({@code date}, {@code condition}) to this.
     *
     * @param key
     *            the key to be added
     * @param value
     *            the associated value to be added
     * @aliases references {@code key, value}
     * @updates this
     * @requires key is not in DOMAIN(this)
     * @ensures this = #this union {(key, value)}
     */
    public final void addWeather(String date, String condition) {
        assert date != null : "Violation of: date is not null";
        assert condition != null : "Violation of: condition is not null";
        assert this
                .isDateFormatted(date) : "Violation of: date is not formatted";
        this.weather.add(date, condition);
    }

    public final Weather_POC<date, condition> removeWeather(String date) {
        assert date != null : "Violation of: date is not null";
        assert this.isDateFormatted(date) : "Violation of: date is formatted";
        assert this.hasDate(date) : "Violation of: date is in this";
        Map.Pair<String, String> pair = this.weather.remove(date);
        return new Weather_POC<>(pair.key(), pair.value());
    }

    public final boolean hasDate(String date) {
        assert date != null : "Violation of: date is not null";
        assert this.isDateFormatted(date) : "Violation of: date is formatted";
        return this.weather.hasKey(date);
    }

    /*
     * Ideal date format: "YYYY-MM-DD"
     */
    public final boolean isDateFormatted(String date) {
        assert date != null : "Violation of: date is not null";
        boolean result = date.length() == 10;
        if (result) {
            // Check if format is appropriate
            String space = date.substring(4, 5) + date.substring(7, 8);
            if (space.equals("--")) {
                String year = date.substring(0, 4);
                // Check if year is appropriate number
                if (FormatChecker.canParseInt(year)) {
                    String month = date.substring(5, 7);
                    // Check if month is appropriate number
                    if (FormatChecker.canParseInt(month)) {
                        int MM = Integer.parseInt(month);
                        result = 1 <= MM && MM <= 12; // in range from 1 to 12
                        String day = date.substring(8, 10);
                        // Check if day is appropriate number
                        if (result && FormatChecker.canParseInt(day)) {
                            int DD = Integer.parseInt(day);
                            /*
                             * Not checking if day is truly apporiate for this
                             * minimum viable product e.g. 02/31 is not possible
                             */
                            result = 1 <= DD && DD <= 31; // in range from 1 to 31
                        } else {
                            result = false;
                        }
                    } else {
                        result = false;
                    }
                } else {
                    result = false;
                }
            } else {
                result = false;
            }

        }
        return result;

    }

    public final String condition(String date) {
        assert date != null : "Violation of: date is not null";
        assert this.isDateFormatted(date) : "Violation of: date is formatted";
        return this.weather.value(date);
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();
        String[] date = { "2024-02-02", "1234-07-23", "2006-05-22",
                "1988-08-18", "2222-04-30", "2024-10-01" };
        String[] condition = { "Sunny", "Rainy", "Cloudy", "Rainy", "Stormy",
                "Windy" };
        Weather_POC<String, String> test = new Weather_POC<>();
        for (int i = 0; i < date.length; i++) {
            test.addWeather(date[i], condition[i]);
        }
        for (int j = 0; j < date.length; j++) {
            Weather_POC<String, String> pair = test.removeWeather(date[j]);
            out.println("1. If this has the date: " + pair.hasDate(date[j]));
            out.println("2. If the date is formmated as \"YYYY-MM-DD\": "
                    + pair.isDateFormatted(date[j]));
            out.println("3. Weather Condition: " + pair.condition(date[j]));
            out.println();
        }

        out.close();
    }
}
