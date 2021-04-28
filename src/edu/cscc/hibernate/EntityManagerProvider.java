package edu.cscc.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerProvider {

    /*
    SessionFactory (org.hibernate.SessionFactory)
    A thread-safe (and immutable) representation of the mapping of the application domain model to a database. Acts as a factory for org.hibernate.Session instances. The EntityManagerFactory is the JPA equivalent of a SessionFactory and basically, those two converge into the same SessionFactory implementation.
    A SessionFactory is very expensive to create, so, for any given database, the application should have only one associated SessionFactory. The SessionFactory maintains services that Hibernate uses across all Session(s) such as second level caches, connection pools, transaction system integrations, etc.
     */


    EntityManager entityManager;
    private static EntityManagerProvider instance;

    private EntityManagerProvider(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public static EntityManagerProvider getInstance() {
        if (instance == null) {
            instance = new EntityManagerProvider(
               Persistence.createEntityManagerFactory("Java3Demo").createEntityManager()
            );
        }
        return instance;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
