package com.blockstars.blockstarsassignment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.blockstars.blockstarsassignment.domain.User;

@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User,String> {

	boolean existsByEmail(String email);

	Optional<User> findByEmail(String referredEmail);

	void deleteById(Long userId);

}
