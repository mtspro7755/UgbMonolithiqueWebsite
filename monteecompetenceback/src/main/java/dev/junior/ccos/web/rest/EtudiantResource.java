package dev.junior.ccos.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import dev.junior.ccos.repository.EtudiantRepository;
import dev.junior.ccos.service.EtudiantService;
import dev.junior.ccos.service.dto.EtudiantDTO;
import dev.junior.ccos.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link dev.junior.ccos.domain.Etudiant}.
 */
@RestController
@RequestMapping("/api/etudiants")
public class EtudiantResource {

    private final Logger log = LoggerFactory.getLogger(EtudiantResource.class);

    private static final String ENTITY_NAME = "etudiant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EtudiantService etudiantService;

    private final EtudiantRepository etudiantRepository;

    public EtudiantResource(EtudiantService etudiantService, EtudiantRepository etudiantRepository) {
        this.etudiantService = etudiantService;
        this.etudiantRepository = etudiantRepository;
    }

    /**
     * {@code POST  /etudiants} : Create a new etudiant.
     *
     * @param etudiantDTO the etudiantDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new etudiantDTO, or with status {@code 400 (Bad Request)} if the etudiant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EtudiantDTO> createEtudiant(@Valid @RequestBody EtudiantDTO etudiantDTO) throws URISyntaxException {
        log.debug("REST request to save Etudiant : {}", etudiantDTO);
        if (etudiantDTO.getId() != null) {
            throw new BadRequestAlertException("A new etudiant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EtudiantDTO result = etudiantService.save(etudiantDTO);
        return ResponseEntity
            .created(new URI("/api/etudiants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /etudiants/:id} : Updates an existing etudiant.
     *
     * @param id the id of the etudiantDTO to save.
     * @param etudiantDTO the etudiantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etudiantDTO,
     * or with status {@code 400 (Bad Request)} if the etudiantDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the etudiantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EtudiantDTO> updateEtudiant(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EtudiantDTO etudiantDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Etudiant : {}, {}", id, etudiantDTO);
        if (etudiantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, etudiantDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!etudiantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EtudiantDTO result = etudiantService.update(etudiantDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etudiantDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /etudiants/:id} : Partial updates given fields of an existing etudiant, field will ignore if it is null
     *
     * @param id the id of the etudiantDTO to save.
     * @param etudiantDTO the etudiantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etudiantDTO,
     * or with status {@code 400 (Bad Request)} if the etudiantDTO is not valid,
     * or with status {@code 404 (Not Found)} if the etudiantDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the etudiantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EtudiantDTO> partialUpdateEtudiant(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EtudiantDTO etudiantDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Etudiant partially : {}, {}", id, etudiantDTO);
        if (etudiantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, etudiantDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!etudiantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EtudiantDTO> result = etudiantService.partialUpdate(etudiantDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etudiantDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /etudiants} : get all the etudiants.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of etudiants in body.
     */
    @GetMapping("")
    public ResponseEntity<List<EtudiantDTO>> getAllEtudiants(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Etudiants");
        Page<EtudiantDTO> page = etudiantService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /etudiants/:id} : get the "id" etudiant.
     *
     * @param id the id of the etudiantDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the etudiantDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EtudiantDTO> getEtudiant(@PathVariable("id") Long id) {
        log.debug("REST request to get Etudiant : {}", id);
        Optional<EtudiantDTO> etudiantDTO = etudiantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(etudiantDTO);
    }

    /**
     * {@code GET  /etudiants/by-user/{userId}} : get the etudiant by user ID.
     *
     * @param userId the id of the user linked to the etudiant.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the etudiantDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/by-user/{userId}")
    public ResponseEntity<EtudiantDTO> getEtudiantByUser(@PathVariable("userId") Long userId) {
        log.debug("REST request to get Etudiant by user ID : {}", userId);
        Optional<EtudiantDTO> etudiantDTO = etudiantService.findByUserId(userId);
        return ResponseUtil.wrapOrNotFound(etudiantDTO);
    }

    /**
     * {@code DELETE  /etudiants/:id} : delete the "id" etudiant.
     *
     * @param id the id of the etudiantDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEtudiant(@PathVariable("id") Long id) {
        log.debug("REST request to delete Etudiant : {}", id);
        etudiantService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
