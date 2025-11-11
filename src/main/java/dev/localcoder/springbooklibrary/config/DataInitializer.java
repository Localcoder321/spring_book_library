package dev.localcoder.springbooklibrary.config;

import dev.localcoder.springbooklibrary.entity.RoleEntity;
import dev.localcoder.springbooklibrary.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    @Transactional
    public void init() {
        if(roleRepository.findByName("ROLE_USER").isEmpty()) {
            roleRepository.save(new RoleEntity(null, "ROLE_USER"));
        }
        if(roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
            roleRepository.save(new RoleEntity(null, "ROLE_ADMIN"));
        }
    }
}
