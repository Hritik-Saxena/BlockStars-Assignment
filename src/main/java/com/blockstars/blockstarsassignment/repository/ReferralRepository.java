package com.blockstars.blockstarsassignment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.blockstars.blockstarsassignment.domain.Referral;
import com.blockstars.blockstarsassignment.domain.User;

@EnableJpaRepositories
public interface ReferralRepository extends JpaRepository<Referral,Long> {

	List<Referral> findAllByReferrerAndLevel(User user, int level);

}
