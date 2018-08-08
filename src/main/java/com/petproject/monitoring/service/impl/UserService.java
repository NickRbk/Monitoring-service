package com.petproject.monitoring.service.impl;

import com.petproject.monitoring.domain.model.SocialMedia;
import com.petproject.monitoring.domain.model.TargetUser;
import com.petproject.monitoring.domain.model.TwitterProfile;
import com.petproject.monitoring.domain.repository.SocialMediaRepository;
import com.petproject.monitoring.domain.repository.TwitterProfileRepository;
import com.petproject.monitoring.domain.repository.UserRepository;
import com.petproject.monitoring.exception.NotFoundException;
import com.petproject.monitoring.service.IUserService;
import com.petproject.monitoring.web.dto.EntityAdapter;
import com.petproject.monitoring.web.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private UserRepository userRepository;
    private SocialMediaRepository smRepository;
    private TwitterProfileRepository tpRepository;

    @Override
    public List<TargetUser> getUsers() {
        log.error("---------------------------ERROR HERE---------------------------");
        userRepository.findAll().forEach(targetUser -> {
            log.error("-------------------------NOT EXECUTED-----------------------------");
            log.error(targetUser.toString());
        });
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void add(UserDTO userDTO) {
        TargetUser targetUser = userRepository.save(EntityAdapter.getUserFromDTO(null, null, userDTO));
        TwitterProfile twitterProfile = tpRepository.save(TwitterProfile.builder().targetUserId(targetUser.getId()).build());
        SocialMedia sm = smRepository.save(
                SocialMedia.builder()
                        .userId(targetUser.getId())
                        .twitterProfile(twitterProfile)
                        .build());
        userRepository.setSocialMediaRef(sm.getId(), targetUser.getId());
    }

    @Override
    public void update(Long userId, UserDTO userDTO) {
        Optional<TargetUser> user = userRepository.findById(userId);
        if(user.isPresent()) {
            userRepository.save(EntityAdapter.getUserFromDTO(userId, user.get().getSocialMedia(), userDTO));
        } else throw new NotFoundException();
    }

    @Override
    public void delete(Long userId) {
        TargetUser targetUser = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        userRepository.delete(targetUser);
    }
}
