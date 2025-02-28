package com.openclassrooms.mddapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.models.DBUser;

@Repository
public interface DBUserRepository extends JpaRepository<DBUser, Long> {
  public Optional<DBUser> findByUsername(String username);
  public Optional<DBUser> findByEmail(String email);
}
