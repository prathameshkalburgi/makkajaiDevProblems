package org.problems.Conference;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



public class ConferenceScheduler {
    private static final int MORNING_SESSION_DURATION = 180; // in minutes
    private static final int AFTERNOON_SESSION_DURATION = 240; // in minutes
    private static final int LUNCH_DURATION = 60; // in minutes
    private static final int NETWORKING_EVENT_DURATION = 60; // in minutes

    private List<Talk> allTalks;

    public ConferenceScheduler(List<Talk> allTalks) {
        this.allTalks = allTalks;
    }

    public List<Track> scheduleConference() {
        List<Track> tracks = new ArrayList<>();
        List<Talk> remainingTalks = new ArrayList<>(allTalks);

        while (!remainingTalks.isEmpty()) {
            Track track = new Track();
            Session morningSession = createSession(LocalTime.of(9, 0), MORNING_SESSION_DURATION, remainingTalks);
            Session afternoonSession = createSession(LocalTime.of(13, 0), AFTERNOON_SESSION_DURATION, remainingTalks);

            if (morningSession != null && afternoonSession != null) {
                track.addSession(morningSession);
                track.addSession(afternoonSession);
                tracks.add(track);
            } else {
                // Not enough talks left to schedule, exit the loop
                break;
            }
        }

        return tracks;
    }

    private Session createSession(LocalTime startTime, int sessionDuration, List<Talk> remainingTalks) {
        Session session = new Session(startTime);
        int remainingTime = sessionDuration;

        for (Talk talk : remainingTalks) {
            if (talk.getDuration() <= remainingTime) {
                session.addTalk(talk);
                remainingTime -= talk.getDuration();
            }
        }

        remainingTalks.removeAll(session.getTalks());

        if (!session.getTalks().isEmpty()) {
            if (remainingTime >= LUNCH_DURATION) {
                // Add lunch break if there is enough time remaining
                Talk lunchBreak = new Talk("Lunch", LUNCH_DURATION);
                session.addTalk(lunchBreak);
                remainingTime -= LUNCH_DURATION;
            }
        }

        return session;
    }

    public static void main(String[] args) {
        List<Talk> allTalks = new ArrayList<>();
        allTalks.add(new Talk("Writing Fast Tests Against Enterprise Rails", 60));
        allTalks.add(new Talk("Overdoing it in Python", 45));
        allTalks.add(new Talk("Lua for the Masses", 30));
        allTalks.add(new Talk("Ruby Errors from Mismatched Gem Versions", 45));
        allTalks.add(new Talk("Common Ruby Errors", 45));
        allTalks.add(new Talk("Rails for Python Developers", 5));
        allTalks.add(new Talk("Communicating Over Distance", 60));
        allTalks.add(new Talk("Accounting-Driven Development", 45));
        allTalks.add(new Talk("Woah", 30));
        allTalks.add(new Talk("Sit Down and Write", 30));
        allTalks.add(new Talk("Pair Programming vs Noise", 45));
        allTalks.add(new Talk("Rails Magic", 60));
        allTalks.add(new Talk("Ruby on Rails: Why We Should Move On", 60));
        allTalks.add(new Talk("Clojure Ate Scala (on my project)", 45));
        allTalks.add(new Talk("Programming in the Boondocks of Seattle", 30));
        allTalks.add(new Talk("Ruby vs. Clojure for Back-End Development", 30));
        allTalks.add(new Talk("Ruby on Rails Legacy App Maintenance", 60));
        allTalks.add(new Talk("A World Without HackerNews", 30));
        allTalks.add(new Talk("User Interface CSS in Rails Apps", 30));

        ConferenceScheduler conferenceScheduler = new ConferenceScheduler(allTalks);
        List<Track> tracks = conferenceScheduler.scheduleConference();
        boolean repeatCheck = true;
        for (Track track : tracks) {
            System.out.println("Track:");
            List<Session> sessions = track.getSessions();
            Collections.sort(sessions, Comparator.comparing(Session::getStartTime));

            for (Session session : sessions) {
//                System.out.println("Session:");
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
                    // Add lunch break at 12:00 PM if there are talks in the session
                    LocalTime lunchTime = LocalTime.of(12, 0);
                    System.out.printf("%02d:%02d PM Lunch%n", lunchTime.getHour(), lunchTime.getMinute());
                    repeatCheck = false;
                }
            }

            // Add networking event at 5:00 PM
            LocalTime networkingEventTime = LocalTime.of(5, 0);
            System.out.printf("%02d:%02d PM Networking Event%n%n", networkingEventTime.getHour(), networkingEventTime.getMinute());
            repeatCheck = true;
        }
    }
}
