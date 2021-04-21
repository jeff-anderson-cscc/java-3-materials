package edu.cscc.exercise;

import edu.cscc.exercise.models.Company;
import edu.cscc.jdbc.CrudRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompanyRepository implements CrudRepository<Company, Long> {
    private final DataSource dataSource;

    public CompanyRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Company> findAll() throws SQLException {
        List<Company> companies = new ArrayList<>();
        String sql = "select c.id, c.name from companies c";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long companyId = resultSet.getLong(1);
                String name = resultSet.getString(2);
                Company company = new Company(companyId, name);
                companies.add(company);
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return companies;
    }

    public Optional<Company> findById(Long id) throws SQLException {
        String sql = "select c.id, c.name from companies c where c.id = ?";
        // Try with resources
        // https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        long companyId = resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        return Optional.of(new Company(companyId, name));
                    }
                }
            }
        }
        return Optional.empty();
    }

    public Company create(Company company) throws SQLException {
        String sql = "insert into companies (name) values (?)";
        try (Connection connection = dataSource.getConnection())
        {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, company.getName());
                preparedStatement.executeUpdate();
                try (ResultSet tableKeys = preparedStatement.getGeneratedKeys()) {
                    tableKeys.next();
                    company.setId(tableKeys.getLong(1));
                }
            }
        }
        return company;
    }

    public int update(Company company) throws SQLException {
        int rowsUpdated;
        String sql = "update companies set name = ? where id = ?";
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, company.getName());
                preparedStatement.setLong(2, company.getId());
                rowsUpdated = preparedStatement.executeUpdate();
            }
        }
        return rowsUpdated;
    }

    public int dangerousUpdate(Company company) throws SQLException {
        // NEVER do this
        int rowsUpdated;
        String sql = String.format("update companies set name = '%s' where id = %d",
            company.getName(), company.getId());
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                rowsUpdated = statement.executeUpdate(sql);
            }
        }
        return rowsUpdated;
    }


    public int delete(Long id) throws SQLException {
        int rowsUpdated;
        String sql = "delete from companies c where c.id = ?";
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, id);
                rowsUpdated = preparedStatement.executeUpdate();
            }
        }
        return rowsUpdated;
    }


}
