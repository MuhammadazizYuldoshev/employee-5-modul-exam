package uz.pdp.employee5modulexam.position;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Position {

    private Integer id;

    private String name;

    private String description;


}
