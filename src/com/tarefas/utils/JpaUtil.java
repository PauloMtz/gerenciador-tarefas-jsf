package com.tarefas.utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtil {

	private static EntityManagerFactory entityManagerFactory;

    public static EntityManagerFactory getEntityManagerFactory() {
    	
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory("tarefas");
        }

        return entityManagerFactory;
    }
}
