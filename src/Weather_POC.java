
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * @author Haechan Jung
 *
 */
public class Weather_POC {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Representation of {@code this}.
     */
    private String time;
    private String condition;

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
        this.time = "";
        this.condition = "";
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
     * Constructor from {@code time} and {@code condition}.
     *
     * @param time
     *            {@code time} to initialize from
     * @param condition
     *            {@code condition} to initialize from
     */
    public Weather_POC(String time, String condition) {
        this.time = time;
        this.condition = condition;
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    public final void setWeather(String time, String condition) {
        assert time != null : "Violation of: date is not null";
        assert condition != null : "Violation of: condition is not null";
        this.time = time;
        this.condition = condition;
    }

    public final String time() {
        return this.time;
    }

    public final String condition() {
        return this.condition;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();
        String[] t = { "2024-02-02", "1234-07-23", "2006-05-22", "1988-08-18",
                "2222-04-30", "2024-10-01" };
        String[] c = { "Sunny", "Rainy", "Cloudy", "Rainy", "Stormy", "Windy" };
        Weather_POC test = new Weather_POC();
        for (int i = 0; i < t.length; i++) {
            test.setWeather(t[i], c[i]);
            boolean timeMatch = test.time().equals(t[i]);
            boolean conditionMatch = test.condition().equals(c[i]);
            out.println("1. Set time correctly:      " + timeMatch
                    + "  | Expected: " + t[i] + ", Actual: " + test.time());
            out.println("2. Set condition correctly: " + conditionMatch
                    + "  | Expected: " + c[i] + ", Actual: "
                    + test.condition());
        }

        out.close();
    }
}
