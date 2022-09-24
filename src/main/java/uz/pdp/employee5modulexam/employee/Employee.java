package uz.pdp.employee5modulexam.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.employee5modulexam.livecountry.LiveCountry;
import uz.pdp.employee5modulexam.position.Position;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Employee {

    private Integer id;
    private String name;
    private String lastname;
    private String description;
    private Integer salary;
    private Position position;
    private Integer position_id;
    private LiveCountry country;
    private Integer livecountry_id;



}

