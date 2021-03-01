package com.yoanan.musicdb.repository;

import com.yoanan.musicdb.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

   Optional<UserEntity> findByName(String name);
}
