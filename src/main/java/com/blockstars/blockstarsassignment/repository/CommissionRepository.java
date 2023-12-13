package com.blockstars.blockstarsassignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.blockstars.blockstarsassignment.domain.Commission;

@EnableJpaRepositories
public interface CommissionRepository extends JpaRepository<Commission,Long> {

}
