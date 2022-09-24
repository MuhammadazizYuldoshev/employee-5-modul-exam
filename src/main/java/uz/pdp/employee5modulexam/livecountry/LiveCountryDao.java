package uz.pdp.employee5modulexam.livecountry;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LiveCountryDao {

    private final JdbcTemplate jdbcTemplate;


    public List<LiveCountry> getAllCountryForSelect() {
        String sql = "select l.id, l.country_name\n" +
                "from live_country l;";
        return jdbcTemplate.query(sql, (rs, row) ->
                LiveCountry.builder()
                        .id(rs.getInt(1))
                        .country_name(rs.getString(2))
                        .build()
        );
    }


    public void saveCountry(LiveCountry country) {

        String sql = "insert into live_country (country_name) values (?)";

        jdbcTemplate.update(sql,country.getCountry_name());

    }


    public LiveCountry getCountry(int id) {
        String sql = "select * from live_country where id="+id;
        return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(LiveCountry.class));
    }

    public void updateCountry(LiveCountry country) {
        //update country
        String sql = "update live_country set country_name=? where id=?";

        jdbcTemplate.update(sql,country.getCountry_name(),country.getId());
    }



    public void delete(int id) {
        String sql = "delete from live_country where id=?";
        jdbcTemplate.update(sql,id);

    }


    public  int getCountOfCountries() {
        try (Connection connection = getConnection()) {

            String sql = "select count(*) from live_country";
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

    public List<LiveCountry> getAllCountry(int size, int page){

        List<LiveCountry> countryList = new ArrayList<>();
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from live_country limit ? offset (? - 1) * ?");
            preparedStatement.setInt(1,size);
            preparedStatement.setInt(2,page);
            preparedStatement.setInt(3,size);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                LiveCountry country = LiveCountry.builder()
                        .id(resultSet.getInt(1))
                        .country_name(resultSet.getString(2))
                        .build();

                countryList.add(country);
            }

            connection.close();




        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return countryList;
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
