package com.alkemy.ong.context;

import com.alkemy.ong.ContextTests;
import com.alkemy.ong.models.entity.OrganizationEntity;
import com.alkemy.ong.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class OrganizationContextTest extends ContextTests {

    @Autowired
    private OrganizationRepository organizationRepository;


    protected OrganizationEntity createOrganization(){
        return organizationRepository.save(OrganizationEntity
                .builder()
                .name("Organization Test")
                .image("https://s3.sa-east-1.amazonaws.com/myawsbucket1234567987/1657752046201-file_1657752046201.jpeg")
                .address("Address Test")
                .phone(465626865)
                .email(generateEmail())
                .welcomeText("Welcome")
                .aboutUsText("About organization test")
                .build());
    }


}
