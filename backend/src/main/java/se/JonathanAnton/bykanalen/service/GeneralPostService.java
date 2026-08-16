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
import se.JonathanAnton.bykanalen.repository.GeneralPostLikeRepository;
import se.JonathanAnton.bykanalen.repository.GeneralPostRepository;
import se.JonathanAnton.bykanalen.repository.GroupInfoRepository;
import se.JonathanAnton.bykanalen.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GeneralPostService {

    private final GeneralPostRepository generalPostRepository;
    private final GeneralPostMapper generalPostMapper;
    private final UserRepository userRepository;
    private final GroupInfoRepository groupInfoRepository;
    private final AuthorizationService authorizationService;
    private final GeneralPostLikeRepository likeRepository;

    public GeneralPostService(GeneralPostRepository generalPostRepository,
                              GeneralPostMapper generalPostMapper,
                              UserRepository userRepository,
                              GroupInfoRepository groupInfoRepository,
                              AuthorizationService authorizationService,
                              GeneralPostLikeRepository likeRepository) {
        this.generalPostRepository = generalPostRepository;
        this.generalPostMapper = generalPostMapper;
        this.userRepository = userRepository;
        this.groupInfoRepository = groupInfoRepository;
        this.authorizationService = authorizationService;
        this.likeRepository = likeRepository;
    }

    public List<GeneralPostSummaryDTO> getAllGeneralPostsLatest(Long groupId, String username) {
        authorizationService.verifyGroupMembership(groupId);
        List<GeneralPost> generalPosts = generalPostRepository.findByGroupInfoIdOrderByPublishDateDesc(groupId);
        return attachLikedStatus(generalPosts, username);

    }

    public List<GeneralPostSummaryDTO> getAllGeneralPostsByLikes(Long groupId, String username) {
        authorizationService.verifyGroupMembership(groupId);
        List<GeneralPost> generalPosts = generalPostRepository.findByGroupInfoIdOrderByLikeCountDesc(groupId);
        return attachLikedStatus(generalPosts, username);
    }

    private List<GeneralPostSummaryDTO> attachLikedStatus(List<GeneralPost> generalPosts, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Användare hittades inte"));

        List<Long> postIds = generalPosts.stream().map(GeneralPost::getId).toList();
        Set<Long> likedPostIds = new HashSet<>(likeRepository.findLikedPostIds(user.getId(), postIds));

        return generalPosts.stream()
                .map(post -> {
                    GeneralPostSummaryDTO dto = generalPostMapper.toGeneralPostSummaryDTO(post);
                    dto.setLikedByCurrentUser(likedPostIds.contains(post.getId()));
                    return dto;
                })
                .toList();
    }

    public GeneralPostDetailDTO getGeneralPostById(Long groupId, Long id) {
        authorizationService.verifyGroupMembership(groupId);
        GeneralPost generalPost = generalPostRepository.findByGroupInfoIdAndId(groupId, id)
                .orElseThrow(() -> new ResourceNotFoundException("Inlägg med id " + id + " hittades inte"));
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
