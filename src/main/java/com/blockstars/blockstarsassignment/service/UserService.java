package com.blockstars.blockstarsassignment.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blockstars.blockstarsassignment.constants.UserRole;
import com.blockstars.blockstarsassignment.domain.Commission;
import com.blockstars.blockstarsassignment.domain.Referral;
import com.blockstars.blockstarsassignment.domain.User;
import com.blockstars.blockstarsassignment.dto.UserDto;
import com.blockstars.blockstarsassignment.repository.CommissionRepository;
import com.blockstars.blockstarsassignment.repository.ReferralRepository;
import com.blockstars.blockstarsassignment.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final ReferralRepository referralRepository;
    private final CommissionRepository commissionRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, ReferralRepository referralRepository,
                       CommissionRepository commissionRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.referralRepository = referralRepository;
        this.commissionRepository = commissionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user.
     *
     * @param payload UserDto object containing user information.
     * @return The registered user.
     */
    @Transactional
    public User registerUser(UserDto payload) {
        log.info("Registering user with email: {}", payload.getEmail());

        validateUserRegistration(payload);

        User user = createUser(payload);
        user = userRepository.save(user);

        log.info("User registered successfully: {}", user);
        return user;
    }

    /**
     * Refers a user to another user.
     *
     * @param referrerId     ID of the referrer user.
     * @param referredEmail  Email of the referred user.
     * @param level          Referral level.
     */
    @Transactional
    public void referUser(String referrerId, String referredEmail, int level) {
        log.info("Referring user with referrerId: {} to referredEmail: {} at level: {}", referrerId, referredEmail, level);

        User referrer = getUserById(referrerId);
        User referredUser = getUserByEmail(referredEmail);
        saveReferral(referrer, referredUser, level);

        log.info("User referred successfully.");
    }

    /**
     * Retrieves commissions for a user.
     *
     * @param userId ID of the user.
     * @return List of commissions.
     */
    @Transactional
    public List<Commission> viewCommissions(String userId) {
        log.info("Viewing commissions for user with userId: {}", userId);

        User user = getUserById(userId);
        List<Commission> commissions = calculateCommissions(user);

        for (Commission commission : commissions) {
            commission.setCommissionDate(LocalDateTime.now());
            commissionRepository.save(commission);
            log.info("Commission saved successfully: {}", commission);
        }
        return commissions;
    }

    /**
     * Validates user registration to ensure there are no duplicate emails.
     *
     * @param payload UserDto object containing user information.
     */
    private void validateUserRegistration(UserDto payload) {
        if (userRepository.existsByEmail(payload.getEmail())) {
            log.error("User with email {} already exists.", payload.getEmail());
            throw new IllegalArgumentException("User with this email already exists");
        }
    }

    /**
     * Creates a new user object from the provided UserDto.
     *
     * @param payload UserDto object containing user information.
     * @return The created User object.
     */
    private User createUser(UserDto payload) {
        validateEmail(payload.getEmail()); // Validate email format

        User user = new User();
        user.setEmail(payload.getEmail());
        user.setFullName(payload.getFullName());
        String hashedPassword = passwordEncoder.encode(payload.getPassword());
        user.setPassword(hashedPassword);
        user.setRole(UserRole.USER);
        user.setTotalSales(payload.getTotalSales());
        user.setCreatedTs(LocalDateTime.now());
        user.setUpdatedTs(LocalDateTime.now());

        log.debug("User created: {}", user);
        return user;
    }

    /**
     * Validates the format of an email using a simple regex.
     *
     * @param email The email to validate.
     */
    private void validateEmail(String email) {
        // Implement email format validation logic
        // For example, you can use regular expressions or a library like Apache Commons Validator
        // Here's a simple example using regex:
        if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}$")) {
            log.error("Invalid email format: {}", email);
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    /**
     * Retrieves a user by ID.
     *
     * @param userId ID of the user.
     * @return The user.
     * @throws IllegalArgumentException if the user is not found.
     */
    private User getUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", userId);
                    return new IllegalArgumentException("User not found");
                });
    }

    /**
     * Retrieves a user by email.
     *
     * @param email The email of the user.
     * @return The user.
     * @throws IllegalArgumentException if the user is not found.
     */
    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("User not found with email: {}", email);
                    return new IllegalArgumentException("User not found");
                });
    }

    /**
     * Saves a referral in the database.
     *
     * @param referrer      The referrer user.
     * @param referredUser  The referred user.
     * @param level         Referral level.
     */
    private void saveReferral(User referrer, User referredUser, int level) {
        Referral referral = new Referral();
        referral.setReferrer(referrer);
        referral.setReferred(referredUser);
        referral.setLevel(level);
        referral.setReferralDate(LocalDateTime.now());
        referral.setCreatedTs(LocalDateTime.now());
        referral.setUpdatedTs(LocalDateTime.now());
        referralRepository.save(referral);

        log.info("Referral saved successfully: {}", referral);
    }

    /**
     * Calculates commissions for a user based on referral levels and rates.
     *
     * @param user        The user for whom commissions are calculated.
     * @return List of commissions.
     */
    private List<Commission> calculateCommissions(User user) {
        List<Commission> commissions = new ArrayList<>();

        BigDecimal level1Rate = new BigDecimal("0.10");
        BigDecimal level2Rate = new BigDecimal("0.05");
        BigDecimal level3Rate = new BigDecimal("0.03");

        calculateCommissionForLevel(user, 1, level1Rate, commissions);
        calculateCommissionForLevel(user, 2, level2Rate, commissions);
        calculateCommissionForLevel(user, 3, level3Rate, commissions);

        return commissions;
    }

    /**
     * Calculates commissions for a specific referral level and adds them to the list of commissions.
     *
     * @param user        The user for whom commissions are calculated.
     * @param level       Referral level.
     * @param rate        Commission rate for the level.
     * @param commissions List of commissions.
     */
    private void calculateCommissionForLevel(User user, int level, BigDecimal rate, List<Commission> commissions) {
        List<Referral> referrals = referralRepository.findAllByReferrerAndLevel(user, level);

        for (Referral referral : referrals) {
            if (referral != null) {
                BigDecimal totalSales = referral.getReferred().getTotalSales();
                BigDecimal commissionAmount = totalSales.multiply(rate);

                Commission commission = new Commission();
                commission.setUser(user);
                commission.setCommissionAmount(commissionAmount);

                commissions.add(commission);
                log.debug("Commission calculated for user: {} - Amount: {}", user, commissionAmount);
            }
        }
    }
}
