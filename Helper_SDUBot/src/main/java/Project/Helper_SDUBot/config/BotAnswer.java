package Project.Helper_SDUBot.config;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class BotAnswer {
    //Method to send the "Best matched answer"
    public String getAnswer(Map<String, Object> QandAs, String question) {
        //Checking for any issues with questions
        if (question == null || question.isBlank()) {
            return "Please provide a valid question.";
        }
        if (QandAs == null || QandAs.isEmpty()) {
            return "No questions and answers are available.";
        }

        AtomicReference<Float> match = new AtomicReference<>((float) 0); //The percent of match
        AtomicReference<String> answer = new AtomicReference<>(""); //The answer that bot will send
        QandAs.forEach((k, v) ->{
            //Changing the variables if percentage exceeds the past ones
            if(match.get() < theMatch(k.toLowerCase(), question.toLowerCase())) {
                match.set(theMatch(k.toLowerCase(), question.toLowerCase()));
                answer.set(v.toString());
            }
        });
        if(answer.get().isBlank() || match.get() * 100 < 60) {
            return "I didn't find an answer";
        }
        return answer.get().toString();
    }

    //Method to find best matched answer
    public float theMatch(String question_QandAs, String seeked_Question) {
        float theMatch = 0; //The match percentage
        List<String> list1 = Arrays.stream(question_QandAs.split("\\s+")).toList(); //Breaking the questions down by spaces
        List<String> list2 = Arrays.stream(seeked_Question.split("\\s+")).toList();
        int n = 0, m = 0;

        for(int i = 0; i < list1.size(); i++) {
            for(int j = 0; j < list1.get(i).length(); j++) {
                n++;
            }
        }
        for(int i = 0; i < list2.size(); i++) {
            for(int j = 0; j < list2.get(i).length(); j++) {
                m++;
            }
        }

        int mx = Math.max(n, m);

        int i = 0; //Going through question words

        while(i < list1.size() && i < list2.size()) {
            int j = 0; //Going through characters
            //Max length
            while(j < list1.get(i).length() && j < list2.get(i).length()) {
                if(list1.get(i).charAt(j) == list2.get(i).charAt(j)) {
                    theMatch += 1.00 / mx;
                }
                j++;
            }
            i++;
        }

        return theMatch; //Returning match percentage
    }
}
