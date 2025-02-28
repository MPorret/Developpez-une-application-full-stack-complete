package com.openclassrooms.mddapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.DTO.PostDTO;
import com.openclassrooms.mddapi.services.PostService;


@RestController
@RequestMapping("/api/posts")
public class PostsController {

  @Autowired
  private PostService postService;

  @GetMapping
  public List<PostDTO> getUsersFeed(@RequestParam Long userId) {
      return postService.getPostsByUserId(userId);
  }


  // /!\ /!\ /!\
  //
  //
  // ----- Reste à faire : -----
  //
  // * Trier les posts du feed par date de modification;
  // * Création d'articles ;
  // * Récupérer et modifier les infos de l'utilisateur ;
  // * Gestion des commentaires ;
  // * Le frontend
  //
  //
  // /!\ /!\ /!\
}
