package uz.pdp.employee5modulexam.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import uz.pdp.employee5modulexam.livecountry.LiveCountry;
import uz.pdp.employee5modulexam.position.Position;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static uz.pdp.employee5modulexam.position.PositionDao.getConnection;

@Component
@RequiredArgsConstructor
public class EmployeeDao {

    private final JdbcTemplate jdbcTemplate;

    public List<Employee> getAllEmployees(int size, int page){
        List<Employee> employeeList = new ArrayList<>();
        Connection connection = getConnection();
        String sql = "select e.id,e.name,e.lastname,e.salary,e.position_id,e.livecountry_id,p.position_name,l.country_name from employee e join position p on e.position_id=p.id join live_country l on e.livecountry_id=l.id  limit ? offset (?-1) * ?";


        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, size);
            preparedStatement.setInt(2, page);
            preparedStatement.setInt(3, size);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String lastname = resultSet.getString(3);
                int salary = resultSet.getInt(4);
                int positionId = resultSet.getInt(5);
                int countryId = resultSet.getInt(6);
                String positionName = resultSet.getString(7);
                String countryName = resultSet.getString(8);

                employeeList.add(
                        Employee.builder()
                                .id(id)
                                .name(name)
                                .lastname(lastname)
                                .salary(salary)
                                .position(
                                        Position.builder()
                                                .id(positionId)
                                                .position_name(positionName)
                                                .build()
                                )
                                .country(LiveCountry.builder()
                                        .id(countryId)
                                        .country_name(countryName)
                                        .build())
                                .build()
                );

            }
            return employeeList;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    public int getCountOfEmployees(){
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select count(*) from employee");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public void saveEmployee(Employee employee) {
        String sql = "insert into employee (name,lastname,salary,biography,position_id,livecountry_id) values(?,?,?,?,?,?)";
        jdbcTemplate.update(sql,employee.getName(),employee.getLastname(),employee.getSalary(),employee.getBiography(),employee.getPosition_id(),employee.getLivecountry_id());
    }

    public  Employee getEmployeeById(int id) {

        try (Connection connection = getConnection()) {

            String sql = "select e.id,e.name,e.lastname,e.salary,p.id,l.id from employee e join position p on p.id = e.position_id join live_country l on l.id=e.livecountry_id where e.id = ?"; // TODO: 25/08/22 select additional informations...
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Employee employee = Employee.builder()
                        .id(resultSet.getInt(1))
                        .name(resultSet.getString(2))
                        .lastname(resultSet.getString(3))
                        .salary(resultSet.getInt(4))
                        .position_id(resultSet.getInt(5))
                        .livecountry_id(resultSet.getInt(6))
                        .build();
                return employee;
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void updateEmployee(Employee employee) {
        String sql = "update employee set name=?,lastname=?,salary=?,biography=?,position_id=?,livecountry_id=? where id=?";
        jdbcTemplate.update(sql,employee.getName(),employee.getLastname(),employee.getSalary(),employee.getBiography(),employee.getPosition_id(),employee.getLivecountry_id(),employee.getId());
    }


    public void delete(int id) {
        String sql = "delete from employee where id=?";
        jdbcTemplate.update(sql,id);
    }

    public Employee getBiographyById(int id) {

        String sql = "select * from employee where id="+id;
        return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Employee.class));

    }
}
