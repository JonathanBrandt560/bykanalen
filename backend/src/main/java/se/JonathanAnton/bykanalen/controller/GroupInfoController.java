package se.JonathanAnton.bykanalen.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.JonathanAnton.bykanalen.dto.GroupInfoDTO;
import se.JonathanAnton.bykanalen.dto.GroupSummaryDTO;
import se.JonathanAnton.bykanalen.mapper.GroupInfoMapper;
import se.JonathanAnton.bykanalen.service.GroupInfoService;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupInfoController {

    private final GroupInfoService groupInfoService;
    private final GroupInfoMapper groupInfoMapper;

    public GroupInfoController(GroupInfoService groupInfoService, GroupInfoMapper groupInfoMapper) {
        this.groupInfoService = groupInfoService;
        this.groupInfoMapper = groupInfoMapper;
    }

    @GetMapping("/mine")
    public ResponseEntity<List<GroupSummaryDTO>> getMyGroups() {
        List<GroupSummaryDTO> groups = groupInfoService.getMyGroups().stream()
                .map(groupInfoMapper::toSummaryDTO)
                .toList();
        return ResponseEntity.ok(groups);
    }

    // Hämtar detaljerad info (text1-3, image1-3 osv.) om en specifik grupp
    @GetMapping("/{groupId}")
    public ResponseEntity<GroupInfoDTO> getGroupInfo(@PathVariable Long groupId) {
        return ResponseEntity.ok(groupInfoMapper.toGroupInfoDTO(groupInfoService.getGroupInfo(groupId)));
    }
}