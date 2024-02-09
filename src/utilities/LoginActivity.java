package utilities;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * The LoginActivity class provides a method to create and append a user activity report.
 */

public class LoginActivity {

    private static final String filename = "login_activity.txt";

    /**
     * The printToLog method creates and appends a report that tracks user activity for login attempts. The userName, date and time,
     * and success message are recorded to a file called "login_activity.txt", which can be found in the Project folder.
     *
     * @param userName The user input from the Login screen.
     * @param success The success or fail message.
     * @throws IOException Signals that an IOException has occurred in the event an issue with reading or writing to the report happens.
     */
    public static void printToLog(String userName, String success) throws IOException {
        String formattedTime = Timestamp.from(Instant.now()).toString();

        FileWriter fwOutput = new FileWriter(filename, true);
        PrintWriter output = new PrintWriter(fwOutput);

        output.println("~~New Login Attempt~~");
        output.println("Username Entered: " + userName);
        output.println("Date and Time: " + formattedTime);
        output.println(success);
        output.println("~~End Record~~");
        output.println();
        output.close();
    }

}
