package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

/**
 * An interval of time in minutes.
 */
public class TimeInterval {
    /**
     * Timestamp of the start of the interval.
     */
    @JsonProperty("start")
    private final Timestamp start;
    /**
     * The duration of the interval in minutes.
     */
    @JsonProperty("duration")
    private final int duration;
    /**
     * Timestamp of the end of the interval.
     */
    private final Timestamp end;

    /**
     * Makes a new time interval object.
     *
     * @param start start of the interval
     * @param duration duration of the interval
     * @throws IllegalArgumentException if duration is negative
     */
    @JsonCreator
    public TimeInterval(@JsonProperty("start") Timestamp start,
                        @JsonProperty("duration") int duration) {
        if (duration < 0) {
            throw new IllegalArgumentException("The duration of an event cannot be negative.");
        }
        this.start = Objects.requireNonNull(start);
        this.duration = duration;
        this.end = calculateEnd(start, duration);
    }

    /**
     * Calculates the end of the interval.
     *
     * @param start start of the interval
     * @param duration duration of the interval
     * @return end of the interval
     */
    private Timestamp calculateEnd(Timestamp start, int duration) {
        // TODO: calculate end based on duration
        //  there are 1440 minutes in a day
        return null;
    }

    @Override
    public String toString() {
        return start + " " + duration;
    }
}