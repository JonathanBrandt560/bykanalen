package se.JonathanAnton.bykanalen.mapper;

import org.springframework.stereotype.Component;
import se.JonathanAnton.bykanalen.dto.CreateGeneralPostDTO;
import se.JonathanAnton.bykanalen.dto.GeneralPostDetailDTO;
import se.JonathanAnton.bykanalen.dto.GeneralPostSummaryDTO;
import se.JonathanAnton.bykanalen.model.GeneralPost;
import se.JonathanAnton.bykanalen.model.Group;
import se.JonathanAnton.bykanalen.model.User;

@Component
public class GeneralPostMapper {

    public GeneralPostDetailDTO toGeneralPostDetailDTO(GeneralPost generalPost) {
        return new GeneralPostDetailDTO(
                generalPost.getTitle(),
                generalPost.getImage(),
                generalPost.getPublishDate(),
                generalPost.getDescription(),
                generalPost.getLikeCount(),
                generalPost.getUser() != null ? generalPost.getUser().getId() : null,
                generalPost.getGroup() != null ? generalPost.getGroup().getId() : null,
                generalPost.getUser() !=null ? generalPost.getUser().getUsername() : null
        );
    }

    public GeneralPostSummaryDTO toGeneralPostSummaryDTO(GeneralPost generalPost) {
        return new GeneralPostSummaryDTO(
                generalPost.getId(),
                generalPost.getTitle(),
                generalPost.getPublishDate(),
                generalPost.getLikeCount()
        );
    }

    public GeneralPost toEntity(CreateGeneralPostDTO dto, Group group, User user) {
        GeneralPost generalPost = new GeneralPost();
        generalPost.setTitle(dto.getTitle());
        generalPost.setImage(dto.getImage());
        generalPost.setDescription(dto.getDescription());
        generalPost.setGroup(group);
        generalPost.setUser(user);
        return generalPost;
    }
}
