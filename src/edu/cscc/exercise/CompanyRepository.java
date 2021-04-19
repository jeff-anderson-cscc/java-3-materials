package edu.cscc.exercise;

import edu.cscc.exercise.models.Company;
import edu.cscc.jdbc.CrudRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompanyRepository implements CrudRepository<Company, Integer> {

    private final DataSource dataSource;

    public CompanyRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Company> findAll() throws SQLException {
        List<Company> companies = new ArrayList<>();
        String sql = "select c.id, c.name from companies c";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int companyId = resultSet.getInt(1);
            String name = resultSet.getString(2);
            Company company = new Company(companyId, name);
            companies.add(company);
        }

        return companies;
    }

    public Optional<Company> findById(Integer id) throws SQLException {
        String sql = "select c.id, c.name from companies c where c.id = ?";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int companyId = resultSet.getInt(1);
            String name = resultSet.getString(2);
            return Optional.of(new Company(companyId, name));
        }
        return Optional.empty();
    }

    public Company create(Company company) throws SQLException {
        Connection connection = dataSource.getConnection();
        String sql = "insert into companies (name) values (?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, company.getName());
        preparedStatement.executeUpdate();
        ResultSet tableKeys = preparedStatement.getGeneratedKeys();
        tableKeys.next();
        company.setId(tableKeys.getInt(1));
        return company;
    }

    public int update(Company company) throws SQLException {
        Connection connection = dataSource.getConnection();
        String sql = "update companies set name = ? where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, company.getName());
        preparedStatement.setInt(2, company.getId());

        return preparedStatement.executeUpdate();
    }

    public int delete(Integer id) throws SQLException {
        String sql = "delete from companies c where c.id = ?";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        return preparedStatement.executeUpdate();
    }


}
