package com.course.projectbase.repository;

import com.course.projectbase.model.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    AddressEntity findByUserIdAndAddressType(Long userId, Integer addressType);
}
