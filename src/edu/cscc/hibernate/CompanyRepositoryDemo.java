package edu.cscc.hibernate;

import edu.cscc.hibernate.models.Company;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Optional;
import java.util.stream.LongStream;

public class CompanyRepositoryDemo {
    public static void main(String[] args) {
        // A SessionFactory is very expensive to create, so, for any given database, the application should have only one associated SessionFactory. The SessionFactory maintains services that Hibernate uses across all Session(s) such as second level caches, connection pools, transaction system integrations, etc.
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Java3Demo");
        CompanyRepository companyRepository = new CompanyRepository(entityManagerFactory);
        LongStream.rangeClosed(1, 5).forEach((searchKey) -> {
            Optional<Company> company = companyRepository.findById(searchKey);
            if (company.isPresent()) {
                System.out.printf("Company %d is %s\n", searchKey, company.get().getName());
            } else {
                System.out.printf("Company %d  not found.\n", searchKey);
            }
        });

        System.out.println("ALL Companies:");
        companyRepository.findAll().forEach(System.out::println);

        System.out.println("By Name:");
        companyRepository.findByName("%Castle%").forEach(System.out::println);

        System.out.println("CREATE:");
        Company newCompany = new Company();
        newCompany.setName("Jeff's company");
        System.out.printf("New new company before insert: %s\n", newCompany);
        newCompany = companyRepository.create(newCompany);
        System.out.printf("New new company after insert: %s\n", newCompany);

        companyRepository.findAll().forEach(System.out::println);

        System.out.println("UPDATE:");
        newCompany.setName("Jeff's Fortune 100 Company");
        companyRepository.update(newCompany);
        companyRepository.findAll().forEach(System.out::println);

        System.out.println("DELETE:");
        System.out.println("Before delete of: " + newCompany);
        companyRepository.delete(newCompany.getId());
        System.out.println("After delete:");
        companyRepository.findAll().forEach(System.out::println);
    }
}
