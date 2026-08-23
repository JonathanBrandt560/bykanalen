package se.JonathanAnton.bykanalen.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.JonathanAnton.bykanalen.dto.CreateGeneralPostDTO;
import se.JonathanAnton.bykanalen.dto.GeneralPostDetailDTO;
import se.JonathanAnton.bykanalen.dto.GeneralPostSummaryDTO;
import se.JonathanAnton.bykanalen.dto.PatchGeneralPostDTO;
import se.JonathanAnton.bykanalen.exception.ResourceNotFoundException;
import se.JonathanAnton.bykanalen.mapper.GeneralPostMapper;
import se.JonathanAnton.bykanalen.model.GeneralPost;
import se.JonathanAnton.bykanalen.model.GroupInfo;
import se.JonathanAnton.bykanalen.model.User;
import se.JonathanAnton.bykanalen.repository.GeneralPostLikeRepository;
import se.JonathanAnton.bykanalen.repository.GeneralPostRepository;
import se.JonathanAnton.bykanalen.repository.GroupInfoRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GeneralPostService {

    private final GeneralPostRepository generalPostRepository;
    private final GeneralPostMapper generalPostMapper;
    private final GroupInfoRepository groupInfoRepository;
    private final AuthorizationService authorizationService;
    private final GeneralPostLikeRepository likeRepository;

    public GeneralPostService(GeneralPostRepository generalPostRepository,
                              GeneralPostMapper generalPostMapper,
                              GroupInfoRepository groupInfoRepository,
                              AuthorizationService authorizationService,
                              GeneralPostLikeRepository likeRepository) {
        this.generalPostRepository = generalPostRepository;
        this.generalPostMapper = generalPostMapper;
        this.groupInfoRepository = groupInfoRepository;
        this.authorizationService = authorizationService;
        this.likeRepository = likeRepository;
    }

    // Hämtar alla allmänna inlägg som tillhör specificerad grupp sorterat efter datum (nyast först)
    public List<GeneralPostSummaryDTO> getAllGeneralPostsLatest(Long groupId) {
        User user = authorizationService.getCurrentUser();
        authorizationService.verifyGroupMembership(groupId, user.getId());

        List<GeneralPost> generalPosts = generalPostRepository.findByGroupInfoIdOrderByPublishDateDesc(groupId);
        return attachLikedStatus(generalPosts, user);
    }

    // Hämtar alla allmänna inlägg som tillhör specificerad grupp sorterat efter antalet likes
    public List<GeneralPostSummaryDTO> getAllGeneralPostsByLikes(Long groupId) {
        User user = authorizationService.getCurrentUser();
        authorizationService.verifyGroupMembership(groupId, user.getId());

        List<GeneralPost> generalPosts = generalPostRepository.findByGroupInfoIdOrderByLikeCountDesc(groupId);
        return attachLikedStatus(generalPosts, user);
    }

    /* Kopplar på like-status för de allmänna inlägg en inloggad användare har like:at.
    Tar emot en array-list med GeneralPost-objekt och returnerar en array-list med GeneralPostSummary-dtos */
    private List<GeneralPostSummaryDTO> attachLikedStatus(List<GeneralPost> generalPosts, User user) {
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

    // Hämtar det allmänna inlägg vars id specificerats mappat till detalj-vy
    public GeneralPostDetailDTO getGeneralPostById(Long groupId, Long id) {
        User user = authorizationService.getCurrentUser();
        authorizationService.verifyGroupMembership(groupId, user.getId());

        GeneralPost generalPost = generalPostRepository.findByGroupInfoIdAndId(groupId, id)
                .orElseThrow(() -> new ResourceNotFoundException("Inlägg med id " + id + " hittades inte"));
        return generalPostMapper.toGeneralPostDetailDTO(generalPost);
    }

    /* Skapar ett nytt allmänt inlägg med inloggad användare som författare. Tar emot en CreateGeneralPost-DTO
    och returnerar en detaljvy-DTO*/
    public GeneralPostDetailDTO createGeneralPost(CreateGeneralPostDTO dto, Long groupId) {
        User user = authorizationService.getCurrentUser();
        authorizationService.verifyGroupMembership(groupId, user.getId());

        GroupInfo groupInfo = groupInfoRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Grupp med id " + groupId + " hittades inte" ));

        GeneralPost generalPost = generalPostMapper.toEntity(dto, groupInfo, user);
        GeneralPost saved = generalPostRepository.save(generalPost);
        return generalPostMapper.toGeneralPostDetailDTO(saved);
    }

    /* Raderar det allmänna inlägg vars id specificerats.
    Utförs som en transaktion för att se till att alla tillhörande operationer utförs/inte utförs */
    @Transactional
    public void deleteGeneralPost(Long groupId, Long id) {
        User user = authorizationService.getCurrentUser();
        authorizationService.verifyGroupMembership(groupId, user.getId());

        GeneralPost generalPost = generalPostRepository.findByUserIdAndId(user.getId(), id)
                .orElseThrow(() -> new ResourceNotFoundException("Inlägg med id " + id + " hittades inte"));

        likeRepository.deleteAllByPostId(id);
        generalPostRepository.delete(generalPost);
    }

    /* Patchar (delvis uppdaterar) det almänna inlägg vars id specificerats.
    Tar emot en PatchGeneralPost-DTO, där titel och/eller beskrivning (description) kan ha ändrats för inlägget.
    Utförs som en transaktion för att se till att alla tillhörande operationer utförs/inte utförs */
    @Transactional
    public GeneralPostDetailDTO patchGeneralPost(Long groupId, Long id, PatchGeneralPostDTO dto) {
        User user = authorizationService.getCurrentUser();
        authorizationService.verifyGroupMembership(groupId, user.getId());

        GeneralPost generalPost = generalPostRepository.findByUserIdAndId(user.getId(), id)
                .orElseThrow(() -> new ResourceNotFoundException("Inlägg med id " + id + " hittades inte"));

        /* Om titelns värde inte är null i PatchGeneralPost-DTO:n,
        så sätts det nya värdet till allmänna inläggets titel */
        if (dto.getTitle() != null) {
            generalPost.setTitle(dto.getTitle());
        /* Om beskrivningens värde inte är null i PatchGeneralPost-DTO:n,
        så sätts det nya värdet till allmänna inläggets beskrivning */
        }
        if (dto.getDescription() != null) {
            generalPost.setDescription(dto.getDescription());
        }

        GeneralPost updated = generalPostRepository.save(generalPost);
        return generalPostMapper.toGeneralPostDetailDTO(updated);
    }
}
