package com.phone.catalog.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneRepository extends JpaRepository<PhoneEntity, Long> {

    List<PhoneEntity> findByIdIn(final List<Long> ids);

}
