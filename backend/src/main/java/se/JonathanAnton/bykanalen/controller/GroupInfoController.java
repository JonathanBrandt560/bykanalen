package se.JonathanAnton.bykanalen.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.JonathanAnton.bykanalen.dto.GroupInfoDTO;
import se.JonathanAnton.bykanalen.dto.GroupSummaryDTO;
import se.JonathanAnton.bykanalen.service.GroupInfoService;

import java.util.List;

/** REST-controller för att hantera HTTP-förfrågningar kopplade till grupper/byar (GroupInfos)
 i Bykanalen - dels vilka grupper en användare tillhör, dels detaljerad information om en grupp.
 Alla anrop till denna controller startar med bas-URL:en /api/groups */
@RestController
@RequestMapping("/api/groups")
public class GroupInfoController {

    private final GroupInfoService groupInfoService;


    public GroupInfoController(GroupInfoService groupInfoService) {
        this.groupInfoService = groupInfoService;
    }

    /* Endpoint för att hämta de grupper/byar den inloggade användaren tillhör.
    Returnerar GroupSummaryDTO (bara id + namn) eftersom detta typiskt används
    för listor/väljare där full gruppinfo (text/bilder) inte behövs. */
    @GetMapping("/mine")
    public ResponseEntity<List<GroupSummaryDTO>> getMyGroups() {
        return ResponseEntity.ok(groupInfoService.getMyGroups());
    }

    // Hämtar detaljerad info (text1-3, image1-3 osv.) om en specifik grupp
    @GetMapping("/{groupId}")
    public ResponseEntity<GroupInfoDTO> getGroupInfo(@PathVariable Long groupId) {
        return ResponseEntity.ok(groupInfoService.getGroupInfo(groupId));
    }
}