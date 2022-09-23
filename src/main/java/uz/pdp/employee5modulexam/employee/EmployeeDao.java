package uz.pdp.employee5modulexam.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
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
        String sql = "select e.id,e.name,e.lastname,e.salary,e.position_id,p.position_name from employee e join position p on e.position_id=p.id  limit ? offset (?-1) * ?";


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
                String positionName = resultSet.getString(6);

                employeeList.add(
                        Employee.builder()
                                .id(id)
                                .name(name)
                                .lastname(lastname)
                                .salary(salary)
                                .position(
                                        Position.builder()
                                                .id(positionId)
                                                .name(positionName)
                                                .build()
                                )
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
        String sql = "insert into employee (name,,lastname,salary,position_id) values(?,?,?,?)";
        jdbcTemplate.update(sql,employee.getName(),employee.getLastname(),employee.getSalary(),employee.getPosition_id());
    }

    public  Employee getEmployeeById(int id) {

        try (Connection connection = getConnection()) {

            String sql = "select e.id,e.name,e.lastname,e.salary,e.position_id,p.position_name from employee e join position p on p.id = e.position_id where e.id = ?"; // TODO: 25/08/22 select additional informations...
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Employee employee = Employee.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .lastname(resultSet.getString("lastname"))
                        .salary(resultSet.getInt("salary"))
                        .position(Position.builder()
                                .id(resultSet.getInt("position_id"))
                                .name(resultSet.getString("position_name"))
                                .build())
                        .build();
                return employee;
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void updateEmployee(Employee employee) {
        String sql = "update employee set name=?,lastname=?,salary=?,position_id=? where id=?";
        jdbcTemplate.update(sql,employee.getName(),employee.getLastname(),employee.getSalary(),employee.getPosition_id(),employee.getId());
    }


    public void delete(int id) {
        String sql = "delete from employee where id=?";
        jdbcTemplate.update(sql,id);
    }
}
