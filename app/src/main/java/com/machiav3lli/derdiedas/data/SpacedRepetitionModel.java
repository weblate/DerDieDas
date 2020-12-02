package com.machiav3lli.derdiedas.data;

import java.util.List;

public class SpacedRepetitionModel {

    private static final int REPETITION_FOR_WRONG = 10;
    private static final int REPETITION_FOR_CORRECT = 20;
    private static final int TIMES_TO_ANSWER_TO_REMOVE = 5;

    public List<Noun> getUpdatedNounList(List<Noun> nounList, Noun noun, boolean isCorrect) {
        if (nounList.isEmpty()) {
            return null;
        }
        nounList.remove(0);
        if (!isCorrect) {
            noun.setTimesAnswered(0);
            nounList.add(Math.min(REPETITION_FOR_WRONG, nounList.size()), noun);

        } else {
            noun.setTimesAnswered(noun.getTimesAnswered() + 1);
            if (noun.getTimesAnswered() < TIMES_TO_ANSWER_TO_REMOVE) {
                nounList.add(Math.min(REPETITION_FOR_CORRECT, nounList.size()), noun);
            }
        }
        return nounList;
    }
}
