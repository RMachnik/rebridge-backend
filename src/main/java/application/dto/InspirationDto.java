package application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InspirationDto {

    String id;
    String name;
    InspirationDetailDto inspirationDetail;
}