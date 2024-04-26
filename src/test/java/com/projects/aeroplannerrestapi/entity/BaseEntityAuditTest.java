package com.projects.aeroplannerrestapi.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class BaseEntityAuditTest {

    @Test
    void testBaseEntityAudit() {
        LocalDateTime createdAt = Mockito.mock(LocalDateTime.class);
        LocalDateTime updatedAt = Mockito.mock(LocalDateTime.class);
        BaseEntityAudit baseEntityAudit = new BaseEntityAudit();

        baseEntityAudit.setCreatedAt(createdAt);
        baseEntityAudit.setUpdatedAt(updatedAt);

        Assertions.assertEquals(createdAt, baseEntityAudit.getCreatedAt());
        Assertions.assertEquals(updatedAt, baseEntityAudit.getUpdatedAt());
    }

}
