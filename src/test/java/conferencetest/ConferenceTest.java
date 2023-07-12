package conferencetest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.problems.ConferenceProblem.ConferenceApp;
import org.problems.ConferenceProblem.InvalidInputException;
import org.problems.ConferenceProblem.Talk;
import org.problems.ConferenceProblem.TalkParser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class ConferenceTest {
    @Mock
    private TalkParser talkParser;

    private List<Talk> allTalks;
    private ConferenceApp conferenceApp;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        allTalks = new ArrayList<>();
        conferenceApp = new ConferenceApp(allTalks);
    }

    @Test
    public void testRun_ConferenceAppWithValidInput_ShouldScheduleConference() throws InvalidInputException {
        // Mock user input
        String userInput = "3\nTalk 1 - 45min\nTalk 2 - 30min\nTalk 3 - 60min";
        InputStream inputStream = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(inputStream);

        // Mock talk parsing
        List<Talk> userTalks = new ArrayList<>();
        userTalks.add(new Talk("Talk 1", 45));
        userTalks.add(new Talk("Talk 2", 30));
        userTalks.add(new Talk("Talk 3", 60));
        when(talkParser.parseTalksFromUserInput()).thenReturn(userTalks);

        // Run the ConferenceApp
        conferenceApp.run();

        // Verify the scheduled sessions
        List<Talk> scheduledTalks = allTalks;
        assertEquals(3, scheduledTalks.size());
        assertEquals("Talk 1 -", scheduledTalks.get(0).getTitle());
        assertEquals(45, scheduledTalks.get(0).getDuration());
        assertEquals("Talk 2 -", scheduledTalks.get(1).getTitle());
        assertEquals(30, scheduledTalks.get(1).getDuration());
        assertEquals("Talk 3 -", scheduledTalks.get(2).getTitle());
        assertEquals(60, scheduledTalks.get(2).getDuration());
    }

    @Test
    public void testScheduleConferenceWithOutUnitOfTime() {
        // Mock user input
        String userInput = "3\nTalk 1 - 45min\nTalk 2 \nTalk 3 - 60min";
        InputStream inputStream = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(inputStream);

        // Run the ConferenceApp and assert that the InvalidInputException is thrown
//        assertThrows(InvalidInputException.class, conferenceApp::run);
        Throwable exception = assertThrows(InvalidInputException.class, () -> {
            conferenceApp.run();
        });
        String expectedMessage = "Time unit (min) not specified\nTalk 2";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }



    //Talk must contain title and duration : error
    @Test
    public void testScheduleConferenceWithEmptyLine() throws IOException {
        String userInput = "3\nTalk 1 - 45min\n \nTalk 3 - 60min";
        InputStream inputStream = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(inputStream);

        Throwable exception = assertThrows(InvalidInputException.class, () -> {

            conferenceApp.run();
        });

        assertEquals("Talk must contain title and duration", exception.getMessage());
    }


    //The duration must be positive : error
    @Test
    public void testScheduleConferenceWithdurationLessThemZero() {
        String userInput = "2\nTalk 1 - 45min\n Talk 3 - -60min";
        InputStream inputStream = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(inputStream);

        Throwable exception = assertThrows(InvalidInputException.class, () -> {

            conferenceApp.run();
        });
        assertEquals("The duration must be positive\nTalk 3 - -60min", exception.getMessage());
    }

    //Value of duration should be of type numbers with the format '5min' : error
    @Test
    public void testScheduleConferenceWithIncorrectFormatOfTalk() {
        String userInput = "2\nTalk 1 - 45min\nTalk 3 - llmmin";
        InputStream inputStream = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(inputStream);

        Throwable exception = assertThrows(InvalidInputException.class, () -> {

            conferenceApp.run();
        });

        String line ="Talk 3 - llmmin";
        assertEquals("Value of duration should be of type numbers with the format '5min'\n"+line, exception.getMessage());
    }
}
