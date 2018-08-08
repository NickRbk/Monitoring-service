package com.petproject.monitoring.service.impl;

import com.petproject.monitoring.domain.model.SocialMedia;
import com.petproject.monitoring.domain.model.TargetUser;
import com.petproject.monitoring.domain.model.TwitterProfile;
import com.petproject.monitoring.domain.repository.SocialMediaRepository;
import com.petproject.monitoring.domain.repository.TwitterProfileRepository;
import com.petproject.monitoring.domain.repository.TargetUserRepository;
import com.petproject.monitoring.exception.NotFoundException;
import com.petproject.monitoring.service.ITargetUserService;
import com.petproject.monitoring.web.dto.EntityAdapter;
import com.petproject.monitoring.web.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TargetUserService implements ITargetUserService {
    private TargetUserRepository targetUserRepository;
    private SocialMediaRepository smRepository;
    private TwitterProfileRepository tpRepository;

    @Override
    public List<TargetUser> getUsers() {
        return targetUserRepository.findAll();
    }

    @Override
    @Transactional
    public void add(UserDTO userDTO) {
        TargetUser targetUser = targetUserRepository.save(EntityAdapter.getUserFromDTO(null, null, userDTO));
        TwitterProfile twitterProfile = tpRepository.save(TwitterProfile.builder().targetUserId(targetUser.getId()).build());
        SocialMedia sm = smRepository.save(
                SocialMedia.builder()
                        .userId(targetUser.getId())
                        .twitterProfile(twitterProfile)
                        .build());
        targetUserRepository.setSocialMediaRef(sm.getId(), targetUser.getId());
    }

    @Override
    public void update(Long userId, UserDTO userDTO) {
        Optional<TargetUser> user = targetUserRepository.findById(userId);
        if(user.isPresent()) {
            targetUserRepository.save(EntityAdapter.getUserFromDTO(userId, user.get().getSocialMedia(), userDTO));
        } else throw new NotFoundException();
    }

    @Override
    public void delete(Long userId) {
        TargetUser targetUser = targetUserRepository.findById(userId).orElseThrow(NotFoundException::new);
        targetUserRepository.delete(targetUser);
    }
}
