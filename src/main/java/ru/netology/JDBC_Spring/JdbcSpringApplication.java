package ru.netology.JDBC_Spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

@SpringBootApplication
public class JdbcSpringApplication implements CommandLineRunner {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(JdbcSpringApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}

	@Override
	public void run(String... args) throws SQLException {
		var parameters = new HashMap<String, Object>();
		parameters.put("id", 3);

		var result = namedParameterJdbcTemplate.queryForObject("select * from netology.cars where id = :id limit 100",
				parameters, (ResultSet rs, int rowNum) -> {
			var id = rs.getInt(1);
			var brand = rs.getString(2);
			var model = rs.getString(3);

			return new Car(id, brand, model);
		});

//		var parameters = new Object[1];
//		parameters[0] = 3;
//
//		var result = jdbcTemplate.query("select * from netology.cars where id = ? limit 100", (ResultSet rs, int rowNum) -> {
//			var id = rs.getInt(1);
//			var brand = rs.getString(2);
//			var model = rs.getString(3);
//
//			return new Car(id, brand, model);
//		}, parameters);

		System.out.println(result);

//		var connection = dataSource.getConnection();
//		var statement = connection.createStatement();
//
//		var resultSet = statement.executeQuery("select * from netology.cars limit 100");
//
//		while (resultSet.next()) {
//			System.out.print(resultSet.getInt("id") + ". ");
//			System.out.print(resultSet.getString("brand") + " ");
//			System.out.println(resultSet.getString("model"));
//		}
	}

	public static class Car {
		private int id;
		private String brand;
		private String model;

		public Car(int id, String brand, String model) {
			this.id = id;
			this.brand = brand;
			this.model = model;
		}

		@Override
		public String toString() {
			return "Car{" +
					"id=" + id +
					", brand='" + brand +
					", model=" + model + '\'' +
					'}';
		}
	}
}
