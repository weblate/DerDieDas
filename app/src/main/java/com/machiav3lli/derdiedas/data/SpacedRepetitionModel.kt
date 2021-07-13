package com.machiav3lli.derdiedas.data

class SpacedRepetitionModel {
    fun getUpdatedNounList(
        nounList: MutableList<Noun?>,
        noun: Noun,
        isCorrect: Boolean
    ): List<Noun?>? {
        if (nounList.isEmpty()) {
            return null
        }
        nounList.removeAt(0)
        if (!isCorrect) {
            noun.timesAnswered = 0
            nounList.add(REPETITION_FOR_WRONG.coerceAtMost(nounList.size), noun)
        } else {
            noun.timesAnswered = noun.timesAnswered + 1
            if (noun.timesAnswered < TIMES_TO_ANSWER_TO_REMOVE) {
                nounList.add(REPETITION_FOR_CORRECT.coerceAtMost(nounList.size), noun)
            }
        }
        return nounList
    }

    companion object {
        private const val REPETITION_FOR_WRONG = 10
        private const val REPETITION_FOR_CORRECT = 20
        private const val TIMES_TO_ANSWER_TO_REMOVE = 5
    }
}