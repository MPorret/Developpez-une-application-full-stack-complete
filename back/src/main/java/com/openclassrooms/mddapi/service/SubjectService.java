package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.SubjectDTO;
import com.openclassrooms.mddapi.dto.SubjectResponseDTO;
import com.openclassrooms.mddapi.dto.TopicsDTO;
import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.mapper.SubjectMapper;
import com.openclassrooms.mddapi.model.Subject;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final SubjectMapper mapper;
    private final TopicService topicService;

    public void createSubject (SubjectDTO subjectDTO){
        User user = userRepository.findById(subjectDTO.getAuthor_id()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Topic topic = topicRepository.findById(subjectDTO.getTopic_id()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found"));
        Subject subject = mapper.toEntity(subjectDTO, user, topic);

        subjectRepository.save(subject);
    }

    public SubjectResponseDTO findSubject (Integer id){
        Subject subject = subjectRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subject not found"));
        return mapper.toDto(subject);
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
}
