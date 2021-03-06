package com.yoanan.musicdb.repository;

import com.yoanan.musicdb.model.entity.UserRoleEntity;
import com.yoanan.musicdb.model.entity.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

    Optional<UserRoleEntity>findByRole(UserRole userRole);
}
