package org.problems.ConferenceProblem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TalkParser {
    private List<Talk> talks;

    public TalkParser() {
        this.talks = new ArrayList<>();
    }

    public List<Talk> parseTalksFromUserInput() throws InvalidInputException {
        Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the number of talks you want to insert: ");
            int noOfTalks = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Enter talks with duration in the format 'Rails Magic - 60min':");
            for (int i = 0; i < noOfTalks; i++) {
                String line = scanner.nextLine();
                Talk talk = parseTalk(line);
                talks.add(talk);
            }
        return talks;
    }

    private Talk parseTalk(String line) throws InvalidInputException {
        line = line.trim();
        int split = line.lastIndexOf(' ');
        if (split < 1) {
            throw new InvalidInputException("Talk must contain title and duration");
        }
        String title = line.substring(0, split).trim();
        String durationSpecified = line.substring(split + 1);
        int duration;
        if (durationSpecified.endsWith("lightning")) {
            duration = 5; // Assuming lightning talks are always 5 minutes
        } else {
            duration = parseDuration(durationSpecified, line);
        }
        return TalkFactory.createTalk(title, duration);
    }

    private int parseDuration(String durationSpecified, String line) throws InvalidInputException {
        if (!durationSpecified.contains("min")) {
            throw new InvalidInputException("Time unit (min) not specified\n" + line);
        }
        try {
            int minutes = Integer.parseInt(durationSpecified.substring(0, durationSpecified.length() - 3));
            if (minutes < 1) {
                throw new InvalidInputException("The duration must be positive\n" + line);
            }
            return minutes;
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Value of duration should be of type numbers with the format '5min'\n" + line);
        }
    }
}