package se.JonathanAnton.bykanalen.mapper;

import org.springframework.stereotype.Component;
import se.JonathanAnton.bykanalen.dto.GroupInfoDTO;
import se.JonathanAnton.bykanalen.dto.GroupSummaryDTO;
import se.JonathanAnton.bykanalen.model.GroupInfo;

@Component
public class GroupInfoMapper {

    public GroupSummaryDTO toSummaryDTO(GroupInfo groupInfo) {
        return new GroupSummaryDTO(groupInfo.getId(), groupInfo.getGroupName());
    }

    public GroupInfoDTO toGroupInfoDTO(GroupInfo groupInfo) {
        return new GroupInfoDTO(
                groupInfo.getId(),
                groupInfo.getGroupName(),
                groupInfo.getText1(),
                groupInfo.getText2(),
                groupInfo.getText3(),
                groupInfo.getImage1(),
                groupInfo.getImage2(),
                groupInfo.getImage3(),
                groupInfo.getCreatedDate()
        );
    }
}