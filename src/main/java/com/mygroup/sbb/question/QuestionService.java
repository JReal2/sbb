package com.mygroup.sbb.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.mygroup.sbb.DataNotFoundException;

import com.mygroup.sbb.user.SiteUser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Page<Question> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.questionRepository.findAll(pageable);
    }

    public Question getQuestion(Integer id) {
        return this.questionRepository.findById(id).orElseThrow(() -> new DataNotFoundException("존재하지 않는 질문입니다." + id));
    }

    public void create(String subject, String content, SiteUser author) {
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        q.setAuthor(author);
        this.questionRepository.save(q);
    }

}