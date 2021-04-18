package edu.cscc.exercise;

import edu.cscc.exercise.models.Company;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InsuranceService {

    private final DataSource dataSource;

    public InsuranceService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Company> getCompanies() throws SQLException {
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

    public Company getCompany(Integer id) throws SQLException {
        String sql = "select c.id, c.name from companies c where c.id = ?";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int companyId = resultSet.getInt(1);
            String name = resultSet.getString(2);
            return new Company(companyId, name);
        }
        return null;
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

    public boolean delete(Integer id) throws SQLException {
        String sql = "delete from companies c where c.id = ?";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        int rows = preparedStatement.executeUpdate();
        if (rows > 0) {
            return true;
        }
        return false;
    }


}
