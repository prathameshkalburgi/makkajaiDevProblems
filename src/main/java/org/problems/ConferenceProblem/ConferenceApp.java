package org.problems.ConferenceProblem;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class ConferenceApp {
    private final List<Talk> allTalks;

    public ConferenceApp(List<Talk> allTalks) {
        this.allTalks = allTalks;
    }

    public void run() throws InvalidInputException {
            TalkParser talkParser = new TalkParser();
            List<Talk> userTalks = talkParser.parseTalksFromUserInput();
            if (userTalks.isEmpty()) {
                throw new InvalidInputException("No Talks Entered");
            }
            allTalks.addAll(userTalks);
            scheduleConference();
    }

    private void scheduleConference() {
        ConferenceScheduler conferenceScheduler = new ConferenceScheduler(allTalks);
        List<Track> tracks = conferenceScheduler.scheduleConference();
        boolean repeatCheck = true;
        int count = 1;
        for (Track track : tracks) {
            System.out.println("Track " + count + ":");
            List<Session> sessions = track.getSessions();
            sessions.sort(Comparator.comparing(Session::getStartTime));

            for (Session session : sessions) {
                List<Talk> talks = session.getTalks();
                talks.sort(Comparator.comparing(Talk::getDuration).reversed());

                for (Talk talk : talks) {
                    String formattedTime = formatTime(session.getStartTime());
                    System.out.printf("%s %s %dmin%n", formattedTime, talk.getTitle(), talk.getDuration());
                    session.setStartTime(session.getStartTime().plusMinutes(talk.getDuration()));
                }

                if (!session.getTalks().isEmpty() && repeatCheck) {
                    System.out.printf("12:00 PM Lunch%n");
                    repeatCheck = false;
                }
            }

            System.out.printf("05:00 PM Networking Event%n%n");
            repeatCheck = true;
            count++;
        }
    }

    private String formatTime(LocalTime time) {
        int hour = time.getHour();
        int minute = time.getMinute();
        String formattedTime = String.format("%02d:%02d", hour, minute);

        if (hour >= 12) {
            if (hour > 12) {
                hour -= 12;
            }
            formattedTime = String.format("%02d:%02d PM", hour, minute);
        } else {
            formattedTime = String.format("%02d:%02d AM", hour, minute);
        }

        return formattedTime;
    }

    public static void main(String[] args) throws InvalidInputException {
        List<Talk> talks = new ArrayList<>();
        ConferenceApp conferenceApp = new ConferenceApp(talks);
        conferenceApp.run();
    }
}
