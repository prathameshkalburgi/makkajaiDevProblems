package conferencetest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.problems.Conference.InvalidInputException;
import org.problems.Conference.Talk;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.problems.Conference.Main.scheduleConference;
import static org.problems.Conference.ReadFile.readTalksFromFile;

public class ConferenceTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalSystemOut = System.out;
    private final InputStream originalSystemIn = System.in;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalSystemOut);
        System.setIn(originalSystemIn);
    }

    //Happy Path
    @Test
    public void testScheduleConference() throws InvalidInputException, IOException {
        List<Talk> allTalks = readTalksFromFile("src/test/resources/input.txt");

        scheduleConference(allTalks);
        String consoleOutput = outputStream.toString();
        assertTrue(consoleOutput.contains("Track 1:"));
        assertTrue(consoleOutput.contains("Track 2:"));
    }

    //Talk must contain title and duration : error
    @Test
    public void testScheduleConferenceWithEmptyLine() {
        String filePath = "src/test/resources/emptyLineInInputFile.txt";

        Throwable exception = assertThrows(InvalidInputException.class, () ->
                readTalksFromFile(filePath));

        assertEquals("Talk must contain title and duration", exception.getMessage());
    }


    //Time unit(min) not specified : error
    @Test
    public void testScheduleConferenceWithOutUnitOfTime() {
        String filePath = "src/test/resources/withOutUnitOfTime.txt";

        Throwable exception = assertThrows(InvalidInputException.class, () ->
                readTalksFromFile(filePath));

        String line ="Ruby on Rails: Why We Should Move On - 60";
        assertEquals("Time unit(min) not specified\n"+line, exception.getMessage());
    }

    //The duration must be positive : error
    @Test
    public void testScheduleConferenceWithdurationLessThemZero() {
        String filePath = "src/test/resources/durationLessThenZero.txt";

        Throwable exception = assertThrows(InvalidInputException.class, () ->
                readTalksFromFile(filePath));

        String line ="Ruby on Rails: Why We Should Move On - -60min";
        assertEquals("The duration must be positive\n"+line, exception.getMessage());
    }

    //Value of duration should be of type numbers with the format '5min' : error
    @Test
    public void testScheduleConferenceWithIncorrectFormatOfTalk() {
        String filePath = "src/test/resources/incorrectFormatOfTalk.txt";

        Throwable exception = assertThrows(InvalidInputException.class, () ->
                readTalksFromFile(filePath));

        String line ="Rails Magic - llmin";
        assertEquals("Value of duration should be of type numbers with the format '5min'\n"+line, exception.getMessage());
    }
}
