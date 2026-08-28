package se.JonathanAnton.bykanalen.service;

import org.springframework.stereotype.Service;
import se.JonathanAnton.bykanalen.dto.GroupInfoDTO;
import se.JonathanAnton.bykanalen.dto.GroupSummaryDTO;
import se.JonathanAnton.bykanalen.exception.ResourceNotFoundException;
import se.JonathanAnton.bykanalen.mapper.GroupInfoMapper;
import se.JonathanAnton.bykanalen.model.GroupInfo;
import se.JonathanAnton.bykanalen.model.MemberlistGroup;
import se.JonathanAnton.bykanalen.model.User;
import se.JonathanAnton.bykanalen.repository.GroupInfoRepository;
import se.JonathanAnton.bykanalen.repository.MemberlistGroupRepository;

import java.util.List;
/** Service-lager för hantering av grupprelaterad information (GroupInfos) i Bykanalen. */
@Service
public class GroupInfoService {

    private final MemberlistGroupRepository memberlistGroupRepository;
    private final GroupInfoRepository groupInfoRepository;
    private final AuthorizationService authorizationService;
    private final GroupInfoMapper groupInfoMapper;

    public GroupInfoService(MemberlistGroupRepository memberlistGroupRepository,
                            GroupInfoRepository groupInfoRepository,
                            AuthorizationService authorizationService,
                            GroupInfoMapper groupInfoMapper) {
        this.memberlistGroupRepository = memberlistGroupRepository;
        this.groupInfoRepository = groupInfoRepository;
        this.authorizationService = authorizationService;
        this.groupInfoMapper = groupInfoMapper;
    }

    /* Hämtar de grupper/byar den inloggade användaren tillhör, mappat till lätta GroupSummaryDTO:er
    Går via MemberlistGroup (kopplingstabellen för M-M-relationen mellan User och GroupInfo)
    och plockar ut GroupInfo-objektet från varje medlemskapsrad. */
    public List<GroupSummaryDTO> getMyGroups() {
        User user = authorizationService.getCurrentUser();
        return memberlistGroupRepository.findByUserId(user.getId()).stream()
                .map(MemberlistGroup::getGroup)
                .map(groupInfoMapper::toSummaryDTO)
                .toList();
    }

    // Hämtar detaljerad info (text1-3, image1-3 osv.) om en specifik grupp, mappat till GroupInfoDTO.
    public GroupInfoDTO getGroupInfo(Long groupId) {
        // Säkerställer att endast inloggade medlemmar av gruppen kan hämta gruppinfo i den.
        User user = authorizationService.getCurrentUser();
        authorizationService.verifyGroupMembership(groupId, user.getId());

        GroupInfo groupInfo = groupInfoRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Grupp med id " + groupId + " hittades inte"));

        return groupInfoMapper.toGroupInfoDTO(groupInfo);
    }
}