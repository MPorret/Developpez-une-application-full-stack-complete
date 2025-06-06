package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.dto.SubjectDTO;
import com.openclassrooms.mddapi.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subject")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

    @PostMapping("")
    public ResponseEntity<?> createSubject (@RequestBody SubjectDTO subjectDTO){
        subjectService.createSubject(subjectDTO);
        return ResponseEntity.ok().body("");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSubjectInfos (@PathVariable Integer id){
        return ResponseEntity.ok().body(subjectService.findSubject(id));
    }

    @GetMapping("")
    public ResponseEntity<?> getAllSubscribedSubjects (Authentication authentication) {
        return ResponseEntity.ok().body(subjectService.findSubscribedSubjects(authentication));
    }

    @PostMapping("/comment")
    public ResponseEntity<?> addComment (@RequestBody CommentDTO commentDTO){
        return ResponseEntity.ok().body(subjectService.addComment(commentDTO));
    }
}
