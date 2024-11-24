package org.example.avanzoapi.services.implementations.quizImplementations;

import lombok.extern.slf4j.Slf4j;
import org.example.avanzoapi.domain.dtos.quiz.AnswerDTO;
import org.example.avanzoapi.domain.dtos.quiz.QuestionDTO;
import org.example.avanzoapi.domain.entitites.Answer;
import org.example.avanzoapi.domain.entitites.Question;
import org.example.avanzoapi.repositories.quiz.AnswerRepository;
import org.example.avanzoapi.repositories.quiz.QuestionRepository;
import org.example.avanzoapi.services.QuestionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

@Service
@Slf4j
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    @Override
    public List<Integer> getQuestionsBySubject(String subject) {
        List<Question> allQuestions = questionRepository.findAll();
        List<Integer> questionIds = new ArrayList<>();

        for (Question question : allQuestions) {
            String obtained = question.getSubject().getSubjectName();
            if (obtained.equals(subject)) {
                questionIds.add(question.getQuestionId());
            }
        }

        return questionIds;
    }

    @Override
    public List<Integer> shuffleQuestions(List<Integer> questionsIds) {
        Collections.shuffle(questionsIds);

        return new ArrayList<>(questionsIds.subList(0, Math.min(15, questionsIds.size())));
    }

    @Override
    public List<Question> getQuestionsById(List<Integer> questionsIds) {
        List<Question> questions = new ArrayList<>();

        questionsIds.forEach(questionsId -> {
            questions.add(questionRepository.findByQuestionId(questionsId));
        });

        return questions;
    }

    @Override
    public List<QuestionDTO> getQuestions(List<Integer> questionsIds) {
        List<Question> questions = new ArrayList<>();
        List<QuestionDTO> questionsDTO = new ArrayList<>();

        questionsIds.forEach(questionId -> {
            questions.add(questionRepository.findByQuestionId(questionId));
        });

        questions.forEach(question -> {
            List<Answer> getAnswers = answerRepository.findAllByQuestion(question);

            List<AnswerDTO> answersDTOS = new ArrayList<>();
            getAnswers.forEach(answer -> {
                AnswerDTO answerDTO = new AnswerDTO();
                answerDTO.setAnswerId(answer.getAnswerId());
                answerDTO.setAnswer(answer.getAnswer());
                answersDTOS.add(answerDTO);
            });

            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setQuestionId(question.getQuestionId());
            questionDTO.setQuestion(question.getQuestion());
            questionDTO.setSubjectId(question.getSubject().getSubjectId().toString());
            questionDTO.setAnswers(answersDTOS);

            questionsDTO.add(questionDTO);
        });

        return questionsDTO;
    }

    @Override
    public List<QuestionDTO> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();
        List<QuestionDTO> allQuestionsDTO = new ArrayList<>();

        questions.forEach(question -> {
            List<Answer> getAnswers = answerRepository.findAllByQuestion(question);

            List<AnswerDTO> answersDTOS = new ArrayList<>();
            getAnswers.forEach(answer -> {
                AnswerDTO answerDTO = new AnswerDTO();
                answerDTO.setAnswerId(answer.getAnswerId());
                answerDTO.setAnswer(answer.getAnswer());
                answersDTOS.add(answerDTO);
            });

            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setQuestionId(question.getQuestionId());
            questionDTO.setSubjectId(question.getSubject().getSubjectId().toString());
            questionDTO.setQuestion(question.getQuestion());
            questionDTO.setAnswers(answersDTOS);
            questionDTO.setCorrectAnswer(question.getCorrectAnswer());

            allQuestionsDTO.add(questionDTO);
        });

        return allQuestionsDTO;
    }

    @Override
    public List<Integer> getAllByAnswerAndQuestion(List<String> answers, List<Question> questions) {
        List<Integer> selectedAnswerInQuestion = new ArrayList<>();

        for (int i = 0; i < questions.size(); i++) {
            Answer selected = answerRepository.findByAnswerAndQuestion(answers.get(i), questions.get(i));
            selectedAnswerInQuestion.add(selected.getAnswerId());
        }

        return selectedAnswerInQuestion;
    }
}
