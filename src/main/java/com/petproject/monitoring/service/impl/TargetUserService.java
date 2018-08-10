package com.petproject.monitoring.service.impl;

import com.petproject.monitoring.domain.model.SocialMedia;
import com.petproject.monitoring.domain.model.TargetUser;
import com.petproject.monitoring.domain.model.TwitterProfile;
import com.petproject.monitoring.domain.repository.SocialMediaRepository;
import com.petproject.monitoring.domain.repository.TwitterProfileRepository;
import com.petproject.monitoring.domain.repository.TargetUserRepository;
import com.petproject.monitoring.exception.NotFoundException;
import com.petproject.monitoring.service.IHelperService;
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

    private IHelperService helperService;
    private TargetUserRepository targetUserRepository;
    private SocialMediaRepository smRepository;
    private TwitterProfileRepository tpRepository;

    @Override
    public List<TargetUser> getUsersByCustomerId(Long customerId) {
        return targetUserRepository.getAllByCustomerId(customerId);
    }

    @Override
    @Transactional
    public void add(Long customerId, TargetUserDTO targetUserDTO) {
        TargetUser targetUser = targetUserRepository.save(
                helperService.getUserFromDTO(customerId, null, null, targetUserDTO));
        TwitterProfile twitterProfile = tpRepository.save(TwitterProfile.builder().targetUserId(targetUser.getId()).build());
        SocialMedia sm = smRepository.save(
                SocialMedia.builder()
                        .targetUserId(targetUser.getId())
                        .twitterProfile(twitterProfile)
                        .build());
        targetUserRepository.setSocialMediaRef(sm.getId(), targetUser.getId());
    }

    @Override
    public void update(Long customerId, Long targetUserId, TargetUserDTO targetUserDTO) {
        Optional<TargetUser> user = targetUserRepository.findByIdAndCustomerId(targetUserId, customerId);
        if(user.isPresent()) {
            targetUserRepository.save(helperService.getUserFromDTO(
                    customerId, targetUserId, user.get().getSocialMedia(), targetUserDTO));
        } else throw new NotFoundException();
    }

    @Override
    @Transactional
    public void delete(Long customerId, Long targetUserId) {
        TargetUser targetUser = targetUserRepository.findByIdAndCustomerId(targetUserId, customerId)
                .orElseThrow(NotFoundException::new);
        targetUserRepository.delete(targetUser);
        helperService.disableTwitterUserAsTargetIfNeeded(targetUser);
    }
}
