package com.alza.android.quiz;


import com.alza.quiz.model.QuizLevel;
import com.alza.quiz.model.Quiz;
import com.alza.quiz.model.QuizStats;
import com.alza.quiz.qfactory.Util;
import com.alza.quiz.qfactory.IQuestionFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ewin.sutriandi@gmail.com on 04/12/16.
 */

public class QuizManager {
    private List<IQuestionFactory> qfs = Util.getAllRomansQuestionFactory();
    private QuizStats stats;
    private int accumScore =0;
    private int timeLimit = 180;
    private Quiz currentQuiz;
    public int curQuizPos=0;
    private List<Quiz> quizList;
    private int maxGrade;
    public QuizManager(int grade){
        this.maxGrade = grade;
        populateQuizzes();
        stats = new QuizStats();
        getNextQuiz();
    }
    public int getCurQuizPos(){
        return curQuizPos;
    }
    public int getQuizListSize(){
        return quizList.size();
    }
    public Quiz getCurrentQuiz(){
        return currentQuiz;
    }
    public int getAccumScore(){
        return accumScore;
    }
    public QuizStats getStats(){
        return stats;
    }
    private Quiz getNextQuiz(){
        if (curQuizPos < quizList.size()){
            currentQuiz = quizList.get(curQuizPos);
        } else {
            currentQuiz = null;
        }
        curQuizPos++;
        return currentQuiz;
    }
    public void populateQuizzes(){
        List<Quiz> qltemp = new ArrayList<Quiz>();
        quizList = new ArrayList<Quiz>();
        for (int i=0 ; i <  qfs.size(); i++ ){
            qltemp.addAll(qfs.get(i).generateQuizList());

        }
        for (Quiz q:qltemp) {
            if (q.getLessonGrade()<=maxGrade){
                quizList.add(q);
            }
        }
        Collections.sort(quizList);

    }
    public long getTimeSlotInMilis(){
        if (currentQuiz.getQuizLevel() == QuizLevel.MUDAH){
            return 60000;
        } else if (currentQuiz.getQuizLevel() == QuizLevel.SEDANG) {
            return 120000;
        } else if (currentQuiz.getQuizLevel() == QuizLevel.SULIT) {
            return 180000;
        }
        return 30000;
    }
    public boolean answer(String answer, long time){
        boolean correct = false;
        correct = currentQuiz.isCorrect(answer);
        int score = calcScore(time, correct);
        this.accumScore = accumScore + score;
        long timeTaken = (getTimeSlotInMilis()/1000)-time;
        stats.addStats(curQuizPos, correct,currentQuiz,timeTaken, score);
        getNextQuiz();
        return correct;
    }

    private int calcScore(long time, boolean correct){
        int score=0;
        if (correct){
            score = calcCorrectScore(time);
        } else if (accumScore >= 5){
            score = -5;
        }
        return score;
    }
    private int calcCorrectScore(long time){
        int score=0;
        double minScore = 5;
        double maxScore = 0;
        if (time < timeLimit){
            switch (currentQuiz.getQuizLevel()) {
                case SULIT:
                    maxScore = 25;
                    break;
                case SEDANG:
                    maxScore = 15;
                    break;
                case MUDAH:
                    maxScore = 10;
                    break;
                default:
                    maxScore = minScore;
                    break;
            }
            double fraction = (timeLimit-time);
            fraction = fraction / timeLimit;
            double fractionScore = (maxScore-minScore) * fraction;
            score = (int)( minScore + fractionScore);
        } else {
            score = (int) minScore;
        }
        return score;
    }
}
