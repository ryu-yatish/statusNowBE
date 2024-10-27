package status.now.backend.statusNowBackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import status.now.backend.statusNowBackend.Entity.OrganizationEntity;
import status.now.backend.statusNowBackend.repositories.OrganizationRepository;

import java.util.List;

@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {
    @Autowired
    private OrganizationRepository organizationRepository;

    @PostMapping
    public OrganizationEntity createOrganization(@RequestBody OrganizationEntity organizationEntity) {
        return organizationRepository.save(organizationEntity);
    }

    @GetMapping
    public List<OrganizationEntity> getAllOrganizations() {
        return organizationRepository.findAll();
    }

    @GetMapping("/{id}")
    public OrganizationEntity getOrganizationById(@PathVariable Long id) {
        return organizationRepository.findById(id).orElse(null);
    }
}
