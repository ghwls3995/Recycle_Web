package com.appliances.recyle.service;

import com.appliances.recyle.domain.Question;
import com.appliances.recyle.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public Question readQuestion(Long qno) {
        return questionRepository.findById(qno).orElseThrow(() -> new IllegalArgumentException("Invalid question ID: " + qno));
    }

    @Override
    public List<Question> readAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public Question updateQuestion(Long qno, Question question) {
        if (questionRepository.existsById(qno)) {
            question.setQno(qno);
            return questionRepository.save(question);
        } else {
            throw new IllegalArgumentException("Invalid question ID: " + qno);
        }
    }

    @Override
    public void deleteQuestion(Long qno) {
        if (questionRepository.existsById(qno)) {
            questionRepository.deleteById(qno);
        } else {
            throw new IllegalArgumentException("Invalid question ID: " + qno);
        }
    }

    @Override
    public Page<Question> getQuestions(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    // 이메일을 기준으로 페이지네이션된 문의 목록을 가져오는 메서드 구현
    @Override
    public Page<Question> findQuestionsByMemberEmail(String email, Pageable pageable) {
        return questionRepository.findByMemberEmail(email, pageable);
    }
}
