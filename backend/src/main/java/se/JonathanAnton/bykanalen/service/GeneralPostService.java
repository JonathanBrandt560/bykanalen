package se.JonathanAnton.bykanalen.service;

import org.springframework.stereotype.Service;
import se.JonathanAnton.bykanalen.dto.CreateGeneralPostDTO;
import se.JonathanAnton.bykanalen.dto.GeneralPostDetailDTO;
import se.JonathanAnton.bykanalen.dto.GeneralPostSummaryDTO;
import se.JonathanAnton.bykanalen.exception.ResourceNotFoundException;
import se.JonathanAnton.bykanalen.mapper.GeneralPostMapper;
import se.JonathanAnton.bykanalen.model.GeneralPost;
import se.JonathanAnton.bykanalen.model.Group;
import se.JonathanAnton.bykanalen.model.User;
import se.JonathanAnton.bykanalen.repository.GeneralPostRepository;
import se.JonathanAnton.bykanalen.repository.GroupRepository;
import se.JonathanAnton.bykanalen.repository.UserRepository;

import java.util.List;

@Service
public class GeneralPostService {

    private final GeneralPostRepository generalPostRepository;
    private final GeneralPostMapper generalPostMapper;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final AuthorizationService authorizationService;

    public GeneralPostService(GeneralPostRepository generalPostRepository, GeneralPostMapper generalPostMapper, UserRepository userRepository, GroupRepository groupRepository, AuthorizationService authorizationService) {
        this.generalPostRepository = generalPostRepository;
        this.generalPostMapper = generalPostMapper;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.authorizationService = authorizationService;
    }

    public List<GeneralPostSummaryDTO> getAllGeneralPostsLatest(Long groupId) {
        authorizationService.verifyGroupMembership(groupId);
        List<GeneralPost> generalPosts = generalPostRepository.findByGroupIdOrderByPublishDateDesc(groupId);
        return generalPosts.stream()
                .map(generalPostMapper::toGeneralPostSummaryDTO)
                .toList();
    }

    public List<GeneralPostSummaryDTO> getAllGeneralPostsByLikes(Long groupId) {
        authorizationService.verifyGroupMembership(groupId);
        List<GeneralPost>  generalPosts = generalPostRepository.findByGroupIdOrderByLikeCountDesc(groupId);
        return generalPosts.stream()
                .map(generalPostMapper::toGeneralPostSummaryDTO)
                .toList();
    }


    public GeneralPostDetailDTO getGeneralPostById(Long id, Long groupId) {
        authorizationService.verifyGroupMembership(groupId);
        GeneralPost generalPost = generalPostRepository.findByIdAndGroupId(id, groupId).orElseThrow(() -> new ResourceNotFoundException("Inlägg med id " + id + " hittades inte"));
        return generalPostMapper.toGeneralPostDetailDTO(generalPost);
    }

    public GeneralPostDetailDTO createGeneralPost(CreateGeneralPostDTO dto, Long groupId, Long userId) {
        authorizationService.verifyGroupMembership(groupId);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Användare med id " + userId + " hittades inte" ));
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new ResourceNotFoundException("Grupp med id " + groupId + " hittades inte" ));
        GeneralPost generalPost = generalPostMapper.toEntity(dto, group, user);
        GeneralPost saved = generalPostRepository.save(generalPost);
        return generalPostMapper.toGeneralPostDetailDTO(saved);
    }
}
