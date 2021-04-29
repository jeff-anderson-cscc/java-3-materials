package edu.cscc.hibernate;

import edu.cscc.hibernate.models.Company;
import edu.cscc.hibernate.models.InsurancePolicy;
import edu.cscc.hibernate.models.InsurancePolicyType;
import edu.cscc.hibernate.repositories.CompanyRepository;
import edu.cscc.hibernate.repositories.InsurancePolicyRepository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Optional;
import java.util.stream.LongStream;

public class InsurancePolicyRepoDemo {
    public static void main(String[] args) {
        // A SessionFactory is very expensive to create, so, for any given database, the application should have only one associated SessionFactory. The SessionFactory maintains services that Hibernate uses across all Session(s) such as second level caches, connection pools, transaction system integrations, etc.
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Java3Demo");
        InsurancePolicyRepository insurancePolicyRepository = new InsurancePolicyRepository(entityManagerFactory);

        System.out.println("Find by ID");
        LongStream.rangeClosed(1, 5).forEach((searchKey) -> {
            Optional<InsurancePolicy> insurancePolicy = insurancePolicyRepository.findById(searchKey);
            if (insurancePolicy.isPresent()) {
                System.out.printf("Policy %d is %s for Company %s\n",
                        searchKey, insurancePolicy.get().getType(),
                        insurancePolicy.get().getCompany().getName());
            } else {
                System.out.printf("Policy %d  not found.\n", searchKey);
            }
        });

        System.out.println("ALL Insurance Policies:");
        insurancePolicyRepository.findAll().forEach(System.out::println);

        System.out.println("CREATE:");

        CompanyRepository companyRepository = new CompanyRepository(entityManagerFactory);
        Optional<Company> company = companyRepository.findById(3L);
        if (company.isPresent()) {
            InsurancePolicy newPolicy = new InsurancePolicy();
            newPolicy.setType(InsurancePolicyType.DENTAL);
            newPolicy.setCompany(company.get());
            System.out.printf("Policy before insert: %s\n", newPolicy);
            insurancePolicyRepository.create(newPolicy);
            System.out.printf("Policy after insert: %s\n", newPolicy);

            insurancePolicyRepository.findAll().forEach(System.out::println);

            System.out.println("UPDATE:");
            newPolicy.setType(InsurancePolicyType.LIFE);
            insurancePolicyRepository.update(newPolicy);
            insurancePolicyRepository.findAll().forEach(System.out::println);

            System.out.println("DELETE:");
            System.out.println("Before delete of: " + newPolicy);
            insurancePolicyRepository.delete(newPolicy.getId());
            System.out.println("After delete:");
            insurancePolicyRepository.findAll().forEach(System.out::println);

        }
    }
}
