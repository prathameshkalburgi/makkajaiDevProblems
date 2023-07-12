package org.problems.ConferenceProblem;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;



public class Session {
    private LocalTime startTime;
    private List<Talk> talks;

    public Session(LocalTime startTime) {
        this.startTime = startTime;
        this.talks = new ArrayList<>();
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void addTalk(Talk talk) {
        talks.add(talk);
    }

    public List<Talk> getTalks() {
        return talks;
    }
}