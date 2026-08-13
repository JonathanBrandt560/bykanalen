package se.JonathanAnton.bykanalen.service;

import org.springframework.stereotype.Service;
import se.JonathanAnton.bykanalen.dto.CreateGeneralPostDTO;
import se.JonathanAnton.bykanalen.dto.GeneralPostDetailDTO;
import se.JonathanAnton.bykanalen.dto.GeneralPostSummaryDTO;
import se.JonathanAnton.bykanalen.exception.ResourceNotFoundException;
import se.JonathanAnton.bykanalen.mapper.GeneralPostMapper;
import se.JonathanAnton.bykanalen.model.GeneralPost;
import se.JonathanAnton.bykanalen.model.GroupInfo;
import se.JonathanAnton.bykanalen.model.User;
import se.JonathanAnton.bykanalen.repository.GeneralPostRepository;
import se.JonathanAnton.bykanalen.repository.GroupInfoRepository;
import se.JonathanAnton.bykanalen.repository.UserRepository;

import java.util.List;

@Service
public class GeneralPostService {

    private final GeneralPostRepository generalPostRepository;
    private final GeneralPostMapper generalPostMapper;
    private final UserRepository userRepository;
    private final GroupInfoRepository groupInfoRepository;
    private final AuthorizationService authorizationService;

    public GeneralPostService(GeneralPostRepository generalPostRepository, GeneralPostMapper generalPostMapper, UserRepository userRepository, GroupInfoRepository groupInfoRepository, AuthorizationService authorizationService) {
        this.generalPostRepository = generalPostRepository;
        this.generalPostMapper = generalPostMapper;
        this.userRepository = userRepository;
        this.groupInfoRepository = groupInfoRepository;
        this.authorizationService = authorizationService;
    }

    public List<GeneralPostSummaryDTO> getAllGeneralPostsLatest(Long groupId) {
        authorizationService.verifyGroupMembership(groupId);
        List<GeneralPost> generalPosts = generalPostRepository.findByGroupInfoIdOrderByPublishDateDesc(groupId);
        return generalPosts.stream()
                .map(generalPostMapper::toGeneralPostSummaryDTO)
                .toList();
    }

    public List<GeneralPostSummaryDTO> getAllGeneralPostsByLikes(Long groupId) {
        authorizationService.verifyGroupMembership(groupId);
        List<GeneralPost>  generalPosts = generalPostRepository.findByGroupInfoIdOrderByLikeCountDesc(groupId);
        return generalPosts.stream()
                .map(generalPostMapper::toGeneralPostSummaryDTO)
                .toList();
    }


    public GeneralPostDetailDTO getGeneralPostById(Long groupId, Long id) {
        authorizationService.verifyGroupMembership(groupId);
        GeneralPost generalPost = generalPostRepository.findByGroupInfoIdAndId(groupId, id).orElseThrow(() -> new ResourceNotFoundException("Inlägg med id " + id + " hittades inte"));
        return generalPostMapper.toGeneralPostDetailDTO(generalPost);
    }

    public GeneralPostDetailDTO createGeneralPost(CreateGeneralPostDTO dto, Long groupId, String username) {
        authorizationService.verifyGroupMembership(groupId);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Användare hittades inte"));
        GroupInfo groupInfo = groupInfoRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Grupp med id " + groupId + " hittades inte" ));
        GeneralPost generalPost = generalPostMapper.toEntity(dto, groupInfo, user);
        GeneralPost saved = generalPostRepository.save(generalPost);
        return generalPostMapper.toGeneralPostDetailDTO(saved);
    }
}
