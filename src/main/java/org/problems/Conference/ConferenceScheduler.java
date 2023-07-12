//package org.problems.Conference;
//
//import java.time.LocalTime;
//import java.util.ArrayList;
//import java.util.List;
//
//
//
//public class ConferenceScheduler {
//    private static final int MORNING_SESSION_DURATION = 180; // in minutes
//    private static final int AFTERNOON_SESSION_DURATION = 240; // in minutes
//    private static final int LUNCH_DURATION = 60; // in minutes
//    private static final int NETWORKING_EVENT_DURATION = 60; // in minutes
//
//    private List<Talk> allTalks;
//
//    public ConferenceScheduler(List<Talk> allTalks) {
//        this.allTalks = allTalks;
//    }
//
//    public List<Track> scheduleConference() {
//        List<Track> tracks = new ArrayList<>();
//        List<Talk> remainingTalks = new ArrayList<>(allTalks);
//
//        while (!remainingTalks.isEmpty()) {
//            Track track = new Track();
//            Session morningSession = createSession(LocalTime.of(9, 0), MORNING_SESSION_DURATION, remainingTalks);
//            Session afternoonSession = createSession(LocalTime.of(13, 0), AFTERNOON_SESSION_DURATION, remainingTalks);
//
//            track.addSession(morningSession);
//            track.addSession(afternoonSession);
//            tracks.add(track);
//        }
//
//        return tracks;
//    }
//
//    private Session createSession(LocalTime startTime, int sessionDuration, List<Talk> remainingTalks) {
//        Session session = new Session(startTime);
//        int remainingTime = sessionDuration;
//
//        for (Talk talk : remainingTalks) {
//            if (talk.getDuration() <= remainingTime) {
//                session.addTalk(talk);
//                remainingTime -= talk.getDuration();
//            }
//        }
//
//        remainingTalks.removeAll(session.getTalks());
//
//
//        return session;
//    }
//
//
//}
