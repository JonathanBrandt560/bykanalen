package se.JonathanAnton.bykanalen.service;

import org.springframework.stereotype.Service;
import se.JonathanAnton.bykanalen.exception.ResourceNotFoundException;
import se.JonathanAnton.bykanalen.model.GroupInfo;
import se.JonathanAnton.bykanalen.model.MemberlistGroup;
import se.JonathanAnton.bykanalen.model.User;
import se.JonathanAnton.bykanalen.repository.GroupInfoRepository;
import se.JonathanAnton.bykanalen.repository.MemberlistGroupRepository;

import java.util.List;

@Service
public class GroupInfoService {

    private final MemberlistGroupRepository memberlistGroupRepository;
    private final GroupInfoRepository groupInfoRepository;
    private final AuthorizationService authorizationService;

    public GroupInfoService(MemberlistGroupRepository memberlistGroupRepository,
                            GroupInfoRepository groupInfoRepository,
                            AuthorizationService authorizationService) {
        this.memberlistGroupRepository = memberlistGroupRepository;
        this.groupInfoRepository = groupInfoRepository;
        this.authorizationService = authorizationService;
    }

    // Hämtar de grupper/byar den inloggade användaren tillhör
    public List<GroupInfo> getMyGroups() {
        User user = authorizationService.getCurrentUser();
        return memberlistGroupRepository.findByUserId(user.getId()).stream()
                .map(MemberlistGroup::getGroup)
                .toList();
    }

    // Hämtar detaljerad info om en specifik grupp. Kräver att användaren är medlem.
    public GroupInfo getGroupInfo(Long groupId) {
        User user = authorizationService.getCurrentUser();
        authorizationService.verifyGroupMembership(groupId, user.getId());

        return groupInfoRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Grupp med id " + groupId + " hittades inte"));
    }
}