package edu.cscc.exercise;

import edu.cscc.exercise.models.Company;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty(DataSourceFactory.MYSQL_DB_URL, "jdbc:mysql://localhost:3306/java_3_db");
        properties.setProperty(DataSourceFactory.MYSQL_DB_USERNAME, "root");
        properties.setProperty(DataSourceFactory.MYSQL_DB_PASSWORD, "password");

        DataSource dataSource = DataSourceFactory.buildDataSource(properties);
        System.out.printf("Connection open? %b\n", !dataSource.getConnection().isClosed());

        InsuranceService insuranceService = new InsuranceService(dataSource);

        // Current State - get all companies:

        printAllCompanies(insuranceService);

        // Get by ID

        System.out.printf("First company: %s\n", insuranceService.getCompany(1));

        // Create:

        Company newCompany = new Company();
        newCompany.setName("Jeff's company");
        System.out.printf("New new company before insert: %s\n", newCompany);
        newCompany = insuranceService.create(newCompany);
        System.out.printf("New new company after insert: %s\n", newCompany);

        printAllCompanies(insuranceService);

        // Update:

        Company companyToUpdate = insuranceService.getCompany(newCompany.getId());
        companyToUpdate.setName("Jeff's Fortune 100 Company");
        insuranceService.update(companyToUpdate);

        System.out.println("Updated company " + companyToUpdate.getId() + "'s name to " + companyToUpdate.getName());
        printAllCompanies(insuranceService);

        // Delete
        Company companyToDelete = insuranceService.getCompany(newCompany.getId());
        System.out.println("Deleting company " + companyToDelete.getId());
        insuranceService.delete(companyToDelete.getId());

        printAllCompanies(insuranceService);
    }

    private static void printAllCompanies(InsuranceService insuranceService) throws SQLException {
        List<Company> companies = insuranceService.getCompanies();
        companies.forEach(company -> {
            System.out.println(company);
        });
    }
}
