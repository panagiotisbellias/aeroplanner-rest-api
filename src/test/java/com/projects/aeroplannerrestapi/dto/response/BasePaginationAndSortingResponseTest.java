package com.projects.aeroplannerrestapi.dto.response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class BasePaginationAndSortingResponseTest {

    @Mock
    UserResponse userResponse;

    @Test
    void testAllArgsConstructor() {
        BasePaginationAndSortingResponse basePaginationAndSortingResponse = new BasePaginationAndSortingResponse(List.of(userResponse), 0, 1, 2L, 3, true);
        assertEquals(basePaginationAndSortingResponse);
    }

    @Test
    void testNoArgsConstructor() {
        BasePaginationAndSortingResponse basePaginationAndSortingResponse = new BasePaginationAndSortingResponse();
        Assertions.assertInstanceOf(BasePaginationAndSortingResponse.class, basePaginationAndSortingResponse);
    }

    @Test
    void testSetters() {
        BasePaginationAndSortingResponse basePaginationAndSortingResponse = new BasePaginationAndSortingResponse();
        basePaginationAndSortingResponse.setContent(List.of(userResponse));
        basePaginationAndSortingResponse.setPageNumber(0);
        basePaginationAndSortingResponse.setPageSize(1);
        basePaginationAndSortingResponse.setTotalElements(2L);
        basePaginationAndSortingResponse.setTotalPages(3);
        basePaginationAndSortingResponse.setLast(true);
        assertEquals(basePaginationAndSortingResponse);
    }

    void assertEquals(BasePaginationAndSortingResponse basePaginationAndSortingResponse) {
        List<Object> content = basePaginationAndSortingResponse.getContent();
        Assertions.assertEquals(1, content.size());
        Assertions.assertEquals(userResponse, content.get(0));
        Assertions.assertEquals(0, basePaginationAndSortingResponse.getPageNumber());
        Assertions.assertEquals(1, basePaginationAndSortingResponse.getPageSize());
        Assertions.assertEquals(2L, basePaginationAndSortingResponse.getTotalElements());
        Assertions.assertEquals(3, basePaginationAndSortingResponse.getTotalPages());
        Assertions.assertTrue(basePaginationAndSortingResponse.isLast());
    }

}
