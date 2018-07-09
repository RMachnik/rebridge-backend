package dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InspirationDto {
    String name;
    InspirationDetailDto inspirationDetail;

}
