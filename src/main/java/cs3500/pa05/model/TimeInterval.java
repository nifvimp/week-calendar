package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import jdk.jfr.Description;

/**
 * An interval of time in a bullet journal.
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
     * @param duration duration of the interval (minutes)
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
        int endTime = start.numRepresentation() + duration;
        DayOfWeek[] daysOfWeek = DayOfWeek.values();
        int last = daysOfWeek.length - 1;
        DayOfWeek day = daysOfWeek[Math.min(last, endTime / 1440)];
        int time = (day.ordinal() == last) ? endTime - (last * 1440) : endTime % 1440;
        return new Timestamp(day, time);
    }

    /**
     * Gets teh timestamp of the start of the time interval.
     *
     * @return timestamp of the start of the time interval
     */
    public Timestamp start() {
        return this.start;
    }

    /**
     * Gets the timestamp of the end of the time interval.
     *
     * @return timestamp of the end of the time interval
     */
    public Timestamp end() {
        return this.end;
    }

    /**
     * Gets the duration in minutes of the time interval.
     *
     * @return duration in minutes
     */
    public int duration() {
        return this.duration;
    }

    /**
     * Gets the formatted duration of the time interval
     *
     * @return formatted duration
     */
    public String formatDuration() {
        return String.format("%dh %02dm", duration / 60, duration % 60);
    }
}