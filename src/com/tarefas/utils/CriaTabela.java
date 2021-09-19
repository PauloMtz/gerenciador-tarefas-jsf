package com.tarefas.utils;

import javax.persistence.Persistence;

public class CriaTabela {

public static void main(String[] args) {
		
		Persistence.createEntityManagerFactory("tarefas");
	}
}
