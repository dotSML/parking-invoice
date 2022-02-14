package ee.sml.parking.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Parking {
    private Long id;
    private Instant start;
    private Instant end;
    private String customerId;
    private String propertyId;
    private StatusModifier status;

    public Parking(Long id, Instant start, Instant end, String customerId, String propertyId, StatusModifier status) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.propertyId = propertyId;
        this.status = status;
    }

    public Parking(Instant start, Instant end, String customerId, String propertyId, StatusModifier status) {
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.propertyId = propertyId;
        this.status = status;
    }

    public Parking(Instant start, Instant end, String customerId, String propertyId) {
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.propertyId = propertyId;
    }

    public Parking() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStart() {
        return start;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public Instant getEnd() {
        return end;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public static boolean isDateInRange(Instant timeStamp) {
        Instant startDate = Instant.now();
        Instant endDate = Instant.now().plus(30, ChronoUnit.MINUTES);
        System.out.println(endDate);
        return (!(timeStamp.isBefore(startDate) || timeStamp.isAfter(endDate)));
    }

    private static long hoursDifference(LocalDateTime ldt1, LocalDateTime ldt2) {
        long minutesDiff = ChronoUnit.MINUTES.between(ldt1, ldt2);
        long hoursDiff = Math.round(Math.ceil(minutesDiff / 30.0));
        return hoursDiff;
    }


    public static int determineDayCycle(int dayTimeIntervalStart, int nightTimeIntervalLateStart) {
        return nightTimeIntervalLateStart - dayTimeIntervalStart;
    }

    public BigDecimal getBillableUnits() {
        LocalDateTime start = LocalDateTime.of(2022, 2, 13, 7, 02);
        LocalDateTime end = LocalDateTime.of(2022, 2, 13, 11, 56);
        // Hourly rates
        int dayTimePricePerHour = 5;
        int nightTimePricePerHour = 10;

        // Rate intervals
        int dayTimeIntervalStart = 7;
        int nightTimeIntervalLateStart = 19;
        // Counted hours per rate
        int dayHoursTotal = 0;
        int nightHoursTotal = 0;

        int startHour = start.getHour();


        // Calculate the hours difference
        long hourDiff = hoursDifference(
                start,
                end);
        System.out.println("Hour difference found: " + hourDiff);

        // Handle parking for full days
        if (hourDiff > 24) {
            int dayCycle = determineDayCycle(dayTimeIntervalStart, nightTimeIntervalLateStart);
            long fullDays = hourDiff / 24;
            nightHoursTotal += (24 - dayCycle) * fullDays;
            dayHoursTotal += dayCycle * fullDays;
            hourDiff = hourDiff % 24;
        }

        // Handle the parking for less than full day
        while (hourDiff > 0) {
            if (startHour < dayTimeIntervalStart) { // Before the day interval -> night
                nightHoursTotal++;
            } else if (startHour < nightTimeIntervalLateStart) { // Before the night interval -> day
                dayHoursTotal++;
            } else { // After the day interval -> night
                nightHoursTotal++;
            }
            startHour++;
            if (startHour > 23) // At midnight reset the hour to 0
                startHour = 0;
            hourDiff--;
        }

        System.out.println("Day hours: " + dayHoursTotal);
        System.out.println("Night hours: " + nightHoursTotal);
        System.out.println("Total hours: " + (dayHoursTotal + nightHoursTotal));
        System.out.println("Day rate charged at " + dayTimePricePerHour + ": " + (dayHoursTotal * dayTimePricePerHour));
        System.out.println("Night rate charged at " + nightTimePricePerHour + ": " + (nightHoursTotal * nightTimePricePerHour));
        System.out.println("Total rate charged: " + ((dayHoursTotal * dayTimePricePerHour) + (nightHoursTotal * nightTimePricePerHour)));
        return new BigDecimal(((dayHoursTotal * dayTimePricePerHour) + (nightHoursTotal * nightTimePricePerHour)));
    }

}
