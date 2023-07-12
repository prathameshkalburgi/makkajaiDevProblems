//package org.problems.Conference;
//
//import java.io.IOException;
//import java.util.List;
//
//public class ValidateTalks {
//    public static List<Talk> validateTalksFromConsole(List<String> lines, List<Talk> talks) throws InvalidInputException {
//
//        for(String line : lines) {
//            line = line.trim();
//            int split = line.lastIndexOf(' ');
//            if(split < 1){
//                throw new InvalidInputException("Talk must contain title and duration");
//            }
//            String title = line.substring(0, split).trim();
//            String durationSpecified = line.substring(split + 1);
//            int duration;
//            if (durationSpecified.endsWith("lightning")) {
//                duration = 5; // Assuming lightning talks are always 5 minutes
//            } else {
//                checkInputs(durationSpecified,line);
//                String minutes = durationSpecified.substring(0, durationSpecified.length() - 3);
//                duration = Integer.parseInt(minutes);
//            }
//            talks.add(new Talk(title, duration));
//        }
//
//        return talks;
//    }
//
//
//    private static void checkInputs(String durationSpecified, String line) throws InvalidInputException {
//        if(!durationSpecified.contains("min")) {
//            throw new InvalidInputException("Time unit(min) not specified\n"+line);
//        }
//        try {
//            if (Integer.parseInt(durationSpecified.substring(0, durationSpecified.length() - 3)) < 1 )
//                throw new InvalidInputException("The duration must be positive\n"+line);
//        }
//        catch(NumberFormatException e){
//            throw new InvalidInputException("Value of duration should be of type numbers with the format '5min'\n"+line);
//        }
//    }
//}
