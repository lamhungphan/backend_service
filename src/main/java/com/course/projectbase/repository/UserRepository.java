package com.course.projectbase.repository;

import com.course.projectbase.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query(value = "FROM UserEntity u " +
                    "WHERE u.status = 'ACTIVE' " +
                    "AND lower(u.firstName) LIKE :keyword " +
                    "OR lower(u.lastName) LIKE :keyword " +
                    "OR u.phone LIKE :keyword " +
                    "OR lower(u.email) LIKE :keyword")
    Page<UserEntity> searchByKeyword(String keyword, Pageable pageable);
}
