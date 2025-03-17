package com.openclassrooms.mddapi.DTO;

public class TopicDTO {

  private Long id;
  private String name;
  private String description;

  public TopicDTO(Long id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
  }

  public Long getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public String getDescription() {
    return this.description;
  }
}
