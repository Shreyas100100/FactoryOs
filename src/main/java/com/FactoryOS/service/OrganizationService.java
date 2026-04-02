package com.FactoryOS.service;


import com.FactoryOS.dto.RegisterRequest;
import com.FactoryOS.model.Organization;
import com.FactoryOS.model.enums.Plan;
import com.FactoryOS.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    public Organization create(RegisterRequest request) {
        Organization organization = Organization.builder()
                .name(request.getOrgName())
                .gstNumber(request.getGstNumber())
                .stateCode(request.getStateCode())
                .phone(request.getPhone())
                .joinCode(generateJoinCode(request.getOrgName()))
                .plan(Plan.FREE)
                .build();

        return organizationRepository.save(organization);
    }

    public boolean existsByGstNumber(String gstNumber) {
        return organizationRepository.existsByGstNumber(gstNumber);
    }

    private String generateJoinCode(String orgName) {
        String processed = orgName.trim()
                .toUpperCase()
                .replaceAll("\\s+", "");
        String prefix = processed.substring(0, Math.min(4, processed.length()));
        int randomNumber = (int) (Math.random() * 9000) + 1000;
        return prefix + "-" + randomNumber;
    }
}
