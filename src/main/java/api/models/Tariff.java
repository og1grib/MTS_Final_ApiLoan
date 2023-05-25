package api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Getter
@ToString
public class Tariff {
    private Integer id;
    private String type;
    @JsonProperty("interest_rate")
    private String interestRate;
}
