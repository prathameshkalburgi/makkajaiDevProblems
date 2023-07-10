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

public class Main {
    public static void main(String[] args) throws InvalidInputException, IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the file path: ");
        String filePath = scanner.nextLine();

        List<Talk> allTalks = readTalksFromFile(filePath);
        if(allTalks.isEmpty()){
            System.out.println("File is Empty: No Talks");
            System.exit(0);
        }
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

    private static List<Talk> readTalksFromFile(String filename) throws IOException, InvalidInputException {
        List<Talk> talks = new ArrayList<>();

            List<String> lines = Files.readAllLines(Paths.get(filename));
            for(String line : lines) {
                line = line.trim();
                int split = line.lastIndexOf(' ');
                if(split < 1){
                    throw new InvalidInputException("Talk must contain title and duration\n"+line);
                }
                String title = line.substring(0, split).trim();
                String durationSpecified = line.substring(split + 1);
                int duration;
                if (durationSpecified.endsWith("lightning")) {
                    duration = 5; // Assuming lightning talks are always 5 minutes
                } else {
                    checkInputs(durationSpecified,line);
                        String minutes = durationSpecified.substring(0, durationSpecified.length() - 3);
                        duration = Integer.parseInt(minutes);
                }
                talks.add(new Talk(title, duration));
            }

        return talks;
    }


    private static void checkInputs(String durationSpecified, String line) throws InvalidInputException {
        if(!durationSpecified.contains("min")) {
            throw new InvalidInputException("Time unit(min) not specified\n"+line);
        }
        try {
            if (Integer.parseInt(durationSpecified.substring(0, durationSpecified.length() - 3)) < 1 )
                throw new InvalidInputException("The duration must be positive.");
        }
        catch(NumberFormatException e){
            throw new InvalidInputException("Value of duration should be of type numbers with the format '5min'\n"+line);
        }
    }
}
