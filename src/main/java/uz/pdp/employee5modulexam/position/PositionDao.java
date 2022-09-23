package uz.pdp.employee5modulexam.position;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PositionDao {

    private final JdbcTemplate jdbcTemplate;


    public List<Position> getAllPositionsForSelect() {
        String sql = "select p.id, p.position_name\n" +
                "from position p;";
        return jdbcTemplate.query(sql, (rs, row) ->
                Position.builder()
                        .id(rs.getInt(1))
                        .name(rs.getString(2))
                        .build()
        );
    }


    public void savePosition(Position position) {


        //add category
        String sql = "insert into position (name,description) values (?,?)";

        jdbcTemplate.update(sql,position.getName(),position.getDescription());

    }





    public Position getPosition(int id) {
        String sql = "select * from position where id="+id;
        return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Position.class));
    }

    public void updatePosition(Position position) {

        String sql = "update position set name=?,description=? where id=?";

        jdbcTemplate.update(sql,position.getName(),position.getDescription(),position.getId());
    }



    public void delete(int id) {
        String sql = "delete from position where id=?";
        jdbcTemplate.update(sql,id);

    }


    public  int getCountOfPositions() {
        try (Connection connection = getConnection()) {

            String sql = "select count(*) from position";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Position> getAllCategories(int size,int page){

        List<Position> positionList = new ArrayList<>();
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from position limit ? offset (? - 1) * ?");
            preparedStatement.setInt(1,size);
            preparedStatement.setInt(2,page);
            preparedStatement.setInt(3,size);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Position position = Position.builder()
                        .id(resultSet.getInt(1))
                        .name(resultSet.getString(2))
                        .description(resultSet.getString(3))
                        .build();

                positionList.add(position);
            }

            connection.close();




        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return positionList;
    }


    static String url = "jdbc:postgresql://localhost:5432/postgres";
    static String username = "postgres";
    static String password = "root123";
    public static Connection getConnection(){

        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
