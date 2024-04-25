package com.projects.aeroplannerrestapi.dto.response;

import com.projects.aeroplannerrestapi.dto.response.UserResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasePaginationAndSortingResponse {
    private List<UserResponse> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
