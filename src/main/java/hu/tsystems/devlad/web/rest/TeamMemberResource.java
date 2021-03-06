package hu.tsystems.devlad.web.rest;

import com.codahale.metrics.annotation.Timed;
import hu.tsystems.devlad.domain.TeamMember;
import hu.tsystems.devlad.service.TeamMemberService;
import hu.tsystems.devlad.web.rest.util.HeaderUtil;
import hu.tsystems.devlad.web.rest.util.PaginationUtil;
import hu.tsystems.devlad.web.rest.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TeamMember.
 */
@RestController
@RequestMapping("/api")
public class TeamMemberResource {

    private final Logger log = LoggerFactory.getLogger(TeamMemberResource.class);

    private static final String ENTITY_NAME = "teamMember";
        
    private final TeamMemberService teamMemberService;

    public TeamMemberResource(TeamMemberService teamMemberService) {
        this.teamMemberService = teamMemberService;
    }

    /**
     * POST  /team-members : Create a new teamMember.
     *
     * @param teamMember the teamMember to create
     * @return the ResponseEntity with status 201 (Created) and with body the new teamMember, or with status 400 (Bad Request) if the teamMember has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/team-members")
    @Timed
    public ResponseEntity<TeamMember> createTeamMember(@Valid @RequestBody TeamMember teamMember) throws URISyntaxException {
        log.debug("REST request to save TeamMember : {}", teamMember);
        if (teamMember.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new teamMember cannot already have an ID")).body(null);
        }
        TeamMember result = teamMemberService.save(teamMember);
        return ResponseEntity.created(new URI("/api/team-members/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /team-members : Updates an existing teamMember.
     *
     * @param teamMember the teamMember to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated teamMember,
     * or with status 400 (Bad Request) if the teamMember is not valid,
     * or with status 500 (Internal Server Error) if the teamMember couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/team-members")
    @Timed
    public ResponseEntity<TeamMember> updateTeamMember(@Valid @RequestBody TeamMember teamMember) throws URISyntaxException {
        log.debug("REST request to update TeamMember : {}", teamMember);
        if (teamMember.getId() == null) {
            return createTeamMember(teamMember);
        }
        TeamMember result = teamMemberService.save(teamMember);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, teamMember.getId().toString()))
            .body(result);
    }

    /**
     * GET  /team-members : get all the teamMembers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of teamMembers in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/team-members")
    @Timed
    public ResponseEntity<List<TeamMember>> getAllTeamMembers(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of TeamMembers");
        Page<TeamMember> page = teamMemberService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/team-members");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /team-members/:id : get the "id" teamMember.
     *
     * @param id the id of the teamMember to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the teamMember, or with status 404 (Not Found)
     */
    @GetMapping("/team-members/{id}")
    @Timed
    public ResponseEntity<TeamMember> getTeamMember(@PathVariable Long id) {
        log.debug("REST request to get TeamMember : {}", id);
        TeamMember teamMember = teamMemberService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(teamMember));
    }

    /**
     * DELETE  /team-members/:id : delete the "id" teamMember.
     *
     * @param id the id of the teamMember to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/team-members/{id}")
    @Timed
    public ResponseEntity<Void> deleteTeamMember(@PathVariable Long id) {
        log.debug("REST request to delete TeamMember : {}", id);
        teamMemberService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
