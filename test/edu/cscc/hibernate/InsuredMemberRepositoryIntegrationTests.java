package edu.cscc.hibernate;

import edu.cscc.hibernate.models.InsuredMember;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Optional;


public class InsuredMemberRepositoryIntegrationTests {

    InsuredMemberRepository insuredMemberRepository = new InsuredMemberRepository(
            Persistence.createEntityManagerFactory("Java3Demo"));

    @ParameterizedTest(name = "findById({0}) returns not null")
    @ValueSource(longs = {1, 3, 5, 7})
    public void getByIdTest(long argument) {
        assertTrue(insuredMemberRepository.findById(argument).isPresent());
    }

    @Test
    @DisplayName("findAll returns a non empty list")
    public void testFindAll() {
        assertTrue(insuredMemberRepository.findAll().iterator().hasNext());
    }

    @Test
    @DisplayName("Members have a list of policies")
    public void testMemberHasPolicies() {
        assertFalse(insuredMemberRepository.findById(1L).get().getPolicies().isEmpty());
    }



}
