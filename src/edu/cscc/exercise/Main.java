package edu.cscc.exercise;

import edu.cscc.exercise.models.Company;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty(DataSourceFactory.MYSQL_DB_URL, "jdbc:mysql://localhost:3306/java_3_db");
        properties.setProperty(DataSourceFactory.MYSQL_DB_USERNAME, "root");
        properties.setProperty(DataSourceFactory.MYSQL_DB_PASSWORD, "password");

        DataSource dataSource = DataSourceFactory.buildDataSource(properties);
        System.out.printf("Connection open? %b\n", !dataSource.getConnection().isClosed());

        CompanyRepository companyRepository = new CompanyRepository(dataSource);

        // Current State - get all companies:

        printAllCompanies(companyRepository);

        // Get by ID

        System.out.printf("First company: %s\n", companyRepository.findById(1));

        // Create:

        Company newCompany = new Company();
        newCompany.setName("Jeff's company");
        System.out.printf("New new company before insert: %s\n", newCompany);
        newCompany = companyRepository.create(newCompany);
        System.out.printf("New new company after insert: %s\n", newCompany);

        printAllCompanies(companyRepository);

        // Update:

        Optional<Company> companyToUpdate = companyRepository.findById(newCompany.getId());
        if (companyToUpdate.isPresent()) {
            Company tmpCompany = companyToUpdate.get();
            tmpCompany.setName("Jeff's Fortune 100 Company");
            companyRepository.update(tmpCompany);

            System.out.println("Updated company " + tmpCompany.getId() + "'s name to " + tmpCompany.getName());
            printAllCompanies(companyRepository);
        } else {
            System.out.printf("A company with the id %d could not be found.\n", newCompany.getId());
        }

        // Delete
        Optional<Company> companyToDelete = companyRepository.findById(newCompany.getId());
        if (companyToDelete.isPresent()) {
            Company tmpCompany = companyToDelete.get();
            System.out.println("Deleting company " + tmpCompany.getId());
            companyRepository.delete(tmpCompany.getId());
        } else {
            System.out.printf("A company with the id %d could not be found.\n", newCompany.getId());
        }

        printAllCompanies(companyRepository);
    }

    private static void printAllCompanies(CompanyRepository companyRepository) throws SQLException {
        List<Company> companies = companyRepository.findAll();
        companies.forEach(company -> {
            System.out.println(company);
        });
    }
}
