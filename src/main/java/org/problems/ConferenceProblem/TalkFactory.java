package org.problems.ConferenceProblem;

public class TalkFactory {
    public static Talk createTalk(String title, int duration) {
        return new Talk(title, duration);
    }
}