package edu.cscc.hibernate;

import edu.cscc.hibernate.models.Company;
import edu.cscc.hibernate.models.InsurancePolicy;
import edu.cscc.hibernate.models.InsuredMember;
import edu.cscc.hibernate.repositories.CompanyRepository;
import edu.cscc.hibernate.repositories.InsurancePolicyRepository;
import edu.cscc.hibernate.repositories.InsuredMemberRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.persistence.Persistence;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InsuredMemberRepositoryIntegrationTests {

    InsuredMemberRepository insuredMemberRepository = new InsuredMemberRepository(
            Persistence.createEntityManagerFactory("Java3Demo"));
    InsurancePolicyRepository insurancePolicyRepository = new InsurancePolicyRepository(
            Persistence.createEntityManagerFactory("Java3Demo"));
    CompanyRepository companyRepository = new CompanyRepository(
            Persistence.createEntityManagerFactory("Java3Demo"));

    InsuredMember passBetweenTestsMember;

    @Test
    @Order(0)
    public void test_00() {
        assertTrue(true);
    }

    @ParameterizedTest(name = "findById({0}) returns not null")
    @Order(1)
    @ValueSource(longs = {1, 3, 5, 7})
    public void test_01(long argument) {
        assertTrue(insuredMemberRepository.findById(argument).isPresent());
    }

    @Test
    @Order(2)
    @DisplayName("findAll returns a non empty list")
    public void test_02() {
        assertFalse(insuredMemberRepository.findAll().isEmpty());
    }

    @Test
    @Order(3)
    @DisplayName("Members have a list of policies")
    public void test_03() {
        assertFalse(insuredMemberRepository.findById(1L).get().getPolicies().isEmpty());
    }

    @Test
    @Order(4)
    @DisplayName("It can create members")
    public void test_04() {
        InsuredMember insuredMember = new InsuredMember();
        insuredMember.setFirstName("Baker");
        insuredMember.setLastName("Mayfield");
        Company company = companyRepository.findById(1L).get();
        insuredMember.setCompany(company);
        int count = insuredMemberRepository.findAll().size();
        InsuredMember savedMember = insuredMemberRepository.create(insuredMember);
        int newCount = insuredMemberRepository.findAll().size();
        assertAll(
                () -> assertTrue(newCount == count +1, "The count should be incremented"),
                () -> assertEquals(savedMember.getFirstName(),insuredMember.getFirstName(),
                        "The first names should match"),
                () -> assertEquals(savedMember.getLastName(),insuredMember.getLastName(),
                        "The last names should match"),
                () -> assertTrue(savedMember.getId() > 0, "The ID should be > 0")
        );
        this.passBetweenTestsMember = savedMember;
    }

    @Test
    @Order(5)
    @DisplayName("It can update members")
    public void test_05() {
        // Pre-condition violation: Only continue if TRUE
        assumeTrue(this.passBetweenTestsMember != null,
                "this.passBetweenTestsMember must not be null");
        this.passBetweenTestsMember.setFirstName("Emily");
        Long savedID = this.passBetweenTestsMember.getId();
        insuredMemberRepository.update(this.passBetweenTestsMember);
        Optional<InsuredMember> updatedMember = insuredMemberRepository.findById(savedID);
        assertTrue(updatedMember.isPresent());
        assertAll(
                () -> assertEquals(updatedMember.get().getFirstName(),"Emily",
                        "The first names should match"),
                () -> assertEquals(updatedMember.get().getLastName(),"Mayfield",
                        "The last names should match")
        );

    }

    @Test
    @Order(6)
    @DisplayName("It can add policies to a member")
    public void test_06() {
        // Pre-condition violation: Only continue if TRUE
        assumeTrue(this.passBetweenTestsMember != null,
                "this.passBetweenTestsMember must not be null");
        InsurancePolicy policy = insurancePolicyRepository.findById(1L).get();
        this.passBetweenTestsMember.getPolicies().add(policy);
        Long savedID = this.passBetweenTestsMember.getId();
        insuredMemberRepository.update(this.passBetweenTestsMember);
        Optional<InsuredMember> updatedMember = insuredMemberRepository.findById(savedID);
        assertTrue(updatedMember.isPresent());
        assertTrue(updatedMember.get().getPolicies().size() == 1);
    }

    @Test
    @Order(7)
    @DisplayName("It can delete member")
    public void test_07() {
        // Pre-condition violation: Only continue if TRUE
        assumeTrue(this.passBetweenTestsMember != null,
                "this.passBetweenTestsMember must not be null");
        Long savedID = this.passBetweenTestsMember.getId();
        insuredMemberRepository.delete(savedID);
        Optional<InsuredMember> updatedMember = insuredMemberRepository.findById(savedID);
        assertFalse(updatedMember.isPresent());
    }

}
