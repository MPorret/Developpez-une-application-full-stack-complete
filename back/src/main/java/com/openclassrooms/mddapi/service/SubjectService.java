package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.*;
import com.openclassrooms.mddapi.mapper.CommentMapper;
import com.openclassrooms.mddapi.mapper.SubjectMapper;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.Subject;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.SubjectRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final CommentRepository commentRepository;
    private final SubjectMapper mapper;
    private final CommentMapper commentMapper;
    private final TopicService topicService;

    public void createSubject (SubjectDTO subjectDTO){
        User user = userRepository.findById(subjectDTO.getAuthor_id()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Topic topic = topicRepository.findById(subjectDTO.getTopic_id()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found"));
        Subject subject = mapper.toEntity(subjectDTO, user, topic);

        subjectRepository.save(subject);
    }

    public SubjectResponseDTO findSubject (Integer id){
        Subject subject = subjectRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subject not found"));
        SubjectResponseDTO response = mapper.toDto(subject);
        List<MinimalCommentResponseDTO> comments = commentMapper.toDto(commentRepository.findAll().stream()
                .filter(comment -> Objects.equals(comment.getSubject().getId(), response.getId()))
                .toList());
        response.setComments(comments);
        return response;
    }

    public List<SubjectResponseDTO> findSubscribedSubjects (Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        List<Topic> topics = topicService.findAllTopicsByUser(user);
        List<Subject> subjects = subjectRepository
                .findAll().stream()
                .filter(subject -> topics.stream()
                        .anyMatch(topic -> Objects.equals(topic.getId(), subject.getTopic().getId())))
                .toList();

        return mapper.toDto(subjects);
    }

    public SubjectResponseDTO addComment (CommentDTO commentDTO) {
        User user = userRepository.findById(commentDTO.getAuthor_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Subject subject = subjectRepository.findById(commentDTO.getSubject_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subject not found"));
        Comment comment = commentMapper.toEntity(commentDTO, user, subject);
        commentRepository.save(comment);
        return this.findSubject(comment.getSubject().getId());
    }
}
