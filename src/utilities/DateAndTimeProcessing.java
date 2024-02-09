package utilities;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * The DateAndTimeProcessing class contains methods for processing dates and times as needed elsewhere in the application.
 */

public class DateAndTimeProcessing {

    /**
     * The ymd method formats a LocalDateTime into the format year-month-day.
     *
     * @param thisTime The LocalDateTime to format.
     * @return The formatted LocalDateTime.
     */
    public static String ymd(LocalDateTime thisTime){
        DateTimeFormatter ymd = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return ymd.format(thisTime);
    }

    /**
     * The hma method formats a LocalDateTime into the format hour:minute am/pm.
     *
     * @param thisTime The LocalDateTime to format.
     * @return The formatted LocalDateTime.
     */
    public static String hma(LocalDateTime thisTime){
        DateTimeFormatter hma = DateTimeFormatter.ofPattern("hh:mm a");
        return hma.format(thisTime);
    }

    /**
     * The ymdhma method formats a LocalDateTime into the format year-month-day, hour:minute am/pm.
     *
     * @param thisTime The LocalDateTime to format.
     * @return The formatted LocalDateTime.
     */
    public static String ymdhma(LocalDateTime thisTime){
        DateTimeFormatter ymdhma = DateTimeFormatter.ofPattern("yyyy-MM-dd, hh:mm a");
        return ymdhma.format(thisTime);
    }

    /**
     * The toUTC method converts a LocalDateTime from the system timezone into the UTC timezone.
     *
     * @param time The LocalDateTime to be converted.
     * @return The converted LocalDateTime.
     */
    public static Instant toUTC(LocalDateTime time) {
        ZonedDateTime timeZ = time.atZone(ZoneId.systemDefault());

        return timeZ.toInstant();
    }

    /**
     * The findWeekLength method finds the remaining length of a week based on the system's current
     * date. If the current day of the week is Saturday, the method returns the length of a full week.
     *
     * @return The remaining days in the current week.
     */
    public static int findWeekLength() {
        DayOfWeek now = LocalDateTime.now().getDayOfWeek();
        int weekLength = switch (now) {
            case MONDAY -> 5;
            case TUESDAY -> 4;
            case WEDNESDAY -> 3;
            case THURSDAY -> 2;
            case FRIDAY -> 1;
            case SATURDAY -> 7;
            case SUNDAY -> 6;
        };

        return weekLength;
    }

    /**
     * The checkAppointmentInsideHours method checks to see if the timeS and timeE parameters are both within the required business
     * hours of 8am - 10pm, Monday - Friday, EST. It first converts each LocalDateTime to a ZonedDateTime in the EST timezone. Then it
     * compares them to a ZonedDateTime of 7:59:59 EST and a ZonedDateTime of 22:00:01 EST. Finally, it checks each time parameter to ensure the
     * current day of the week is neither Saturday or Sunday. If all conditions are met, the method returns true.
     *
     * @param timeS The start time to check.
     * @param timeE The end time to check.
     * @return A boolean that is set to true if the times are both within requirements and false if they are not.
     */
    public static boolean checkAppointmentInsideHours(LocalDateTime timeS, LocalDateTime timeE){
        boolean startValid = false;
        boolean isValid = false;

        ZonedDateTime timeZS = timeS.atZone(ZoneId.systemDefault());
        Instant timeSInst = timeZS.toInstant();
        ZonedDateTime timeStartEST = timeSInst.atZone(ZoneOffset.of("-05:00"));

        ZonedDateTime timeZE = timeE.atZone(ZoneId.systemDefault());
        Instant timeEInst = timeZE.toInstant();
        ZonedDateTime timeEndEST = timeEInst.atZone(ZoneOffset.of("-05:00"));

        ZonedDateTime dayStart = ZonedDateTime.of(2022, 1, 1, 7, 59, 59, 0, ZoneOffset.of("-05:00"));
        ZonedDateTime dayEnd = ZonedDateTime.of(2022, 12, 31, 22, 0, 1, 0, ZoneOffset.of("-05:00"));

        if(timeStartEST.toLocalTime().isAfter(dayStart.toLocalTime()) && timeStartEST.toLocalTime().isBefore(dayEnd.toLocalTime()) && !timeStartEST.getDayOfWeek().equals(DayOfWeek.SATURDAY) && !timeStartEST.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            startValid = true;
        }
        if(startValid){
            if(timeEndEST.toLocalTime().isAfter(dayStart.toLocalTime()) && timeEndEST.toLocalTime().isBefore(dayEnd.toLocalTime()) && !timeEndEST.getDayOfWeek().equals(DayOfWeek.SATURDAY) && !timeEndEST.getDayOfWeek().equals(DayOfWeek.SUNDAY)){
                isValid = true;
            }
        }

        return isValid;
    }
}