package com.petproject.monitoring.service.impl;

import com.petproject.monitoring.domain.model.SocialMedia;
import com.petproject.monitoring.domain.model.TargetUser;
import com.petproject.monitoring.domain.model.TwitterProfile;
import com.petproject.monitoring.domain.repository.SocialMediaRepository;
import com.petproject.monitoring.domain.repository.TwitterProfileRepository;
import com.petproject.monitoring.domain.repository.TargetUserRepository;
import com.petproject.monitoring.exception.NotFoundException;
import com.petproject.monitoring.service.IEntityAdapterService;
import com.petproject.monitoring.service.ITargetUserService;
import com.petproject.monitoring.web.dto.TargetUserDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TargetUserService implements ITargetUserService {

    private IEntityAdapterService entityAdapterService;
    private TargetUserRepository targetUserRepository;
    private SocialMediaRepository smRepository;
    private TwitterProfileRepository tpRepository;

    @Override
    public List<TargetUser> getUsers() {
        return targetUserRepository.findAll();
    }

    @Override
    @Transactional
    public void add(TargetUserDTO targetUserDTO) {
        TargetUser targetUser = targetUserRepository.save(entityAdapterService.getUserFromDTO(null, null, targetUserDTO));
        TwitterProfile twitterProfile = tpRepository.save(TwitterProfile.builder().targetUserId(targetUser.getId()).build());
        SocialMedia sm = smRepository.save(
                SocialMedia.builder()
                        .targetUserId(targetUser.getId())
                        .twitterProfile(twitterProfile)
                        .build());
        targetUserRepository.setSocialMediaRef(sm.getId(), targetUser.getId());
    }

    @Override
    public void update(Long userId, TargetUserDTO targetUserDTO) {
        Optional<TargetUser> user = targetUserRepository.findById(userId);
        if(user.isPresent()) {
            targetUserRepository.save(entityAdapterService.getUserFromDTO(userId, user.get().getSocialMedia(), targetUserDTO));
        } else throw new NotFoundException();
    }

    @Override
    public void delete(Long userId) {
        TargetUser targetUser = targetUserRepository.findById(userId).orElseThrow(NotFoundException::new);
        targetUserRepository.delete(targetUser);
    }
}
