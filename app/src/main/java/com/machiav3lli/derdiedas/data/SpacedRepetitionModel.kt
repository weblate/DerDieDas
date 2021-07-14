package com.machiav3lli.derdiedas.data

fun MutableList<Noun>.getUpdatedNounList(noun: Noun, isCorrect: Boolean): MutableList<Noun> {
    if (isEmpty()) return mutableListOf()

    removeAt(0)
    if (!isCorrect) {
        noun.timesAnswered = 0
        add(REPETITION_FOR_WRONG.coerceAtMost(size), noun)
    } else {
        noun.timesAnswered = noun.timesAnswered + 1
        if (noun.timesAnswered < TIMES_TO_ANSWER_TO_REMOVE) {
            add(REPETITION_FOR_CORRECT.coerceAtMost(size), noun)
        }
    }
    return this
}

private const val REPETITION_FOR_WRONG = 10
private const val REPETITION_FOR_CORRECT = 20
private const val TIMES_TO_ANSWER_TO_REMOVE = 5