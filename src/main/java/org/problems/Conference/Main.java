package org.problems.Conference;

import javax.xml.datatype.Duration;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.problems.Conference.ReadFile.readTalksFromFile;

public class Main {
    public static void main(String[] args) throws InvalidInputException, IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the file path: ");
        String filePath = scanner.nextLine();

        List<Talk> allTalks = readTalksFromFile(filePath);
        if(allTalks.isEmpty()){
            throw new InvalidInputException("File is Empty: No Talks");
        }
        scheduleConference(allTalks);
    }

    public static void scheduleConference(List<Talk> allTalks) {
        ConferenceScheduler conferenceScheduler = new ConferenceScheduler(allTalks);
        List<Track> tracks = conferenceScheduler.scheduleConference();
        boolean repeatCheck = true;
        int count = 1;
        for (Track track : tracks) {
            System.out.println("Track " + count + ":");
            List<Session> sessions = track.getSessions();
            Collections.sort(sessions, Comparator.comparing(Session::getStartTime));

            for (Session session : sessions) {
                List<Talk> talks = session.getTalks();
                talks.sort(Comparator.comparing(Talk::getDuration).reversed());

                LocalTime sessionStartTime = session.getStartTime();
                for (Talk talk : talks) {
                    int hour = sessionStartTime.getHour();
                    int minute = sessionStartTime.getMinute();
                    String time = String.format("%02d:%02d", hour, minute);

                    if (hour >= 12) {
                        if (hour > 12) {
                            hour -= 12;
                        }
                        time = String.format("%02d:%02d PM", hour, minute);
                    } else {
                        time = String.format("%02d:%02d AM", hour, minute);
                    }

                    System.out.printf("%s %s %dmin%n", time, talk.getTitle(), talk.getDuration());
                    sessionStartTime = sessionStartTime.plusMinutes(talk.getDuration());
                }

                if (session.getTalks().size() > 0 && repeatCheck) {
                    LocalTime lunchTime = LocalTime.of(12, 0);
                    System.out.printf("%02d:%02d PM Lunch%n", lunchTime.getHour(), lunchTime.getMinute());
                    repeatCheck = false;
                }
            }

            LocalTime networkingEventTime = LocalTime.of(5, 0);
            System.out.printf("%02d:%02d PM Networking Event%n%n", networkingEventTime.getHour(),
                    networkingEventTime.getMinute());
            repeatCheck = true;
            count++;
        }
    }

}
