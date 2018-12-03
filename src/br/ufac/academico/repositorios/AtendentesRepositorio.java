package br.ufac.academico.repositorios;

import java.util.*;
import javax.persistence.*;
import br.ufac.academico.entidades.*;

public class AtendentesRepositorio {

	private EntityManagerFactory emf;
	private EntityManager em;	
	
	public AtendentesRepositorio() {
		emf = Persistence.createEntityManagerFactory("AcademicoJPA");
		em = emf.createEntityManager();
	}

	public void adicionar(Atendente atendente) {	
		em.getTransaction().begin();
		em.persist(atendente);
		em.getTransaction().commit();
		
	}
	
	public Atendente recuperar(long id) {
		return em.find(Atendente.class, id);
	}
	
	public void atualizar (Atendente Atendentes) {
		em.getTransaction().begin();
		em.merge(Atendentes);
		em.getTransaction().commit();
	}
	
	public void remover(Atendente Atendentes) {
		em.getTransaction().begin();
		em.remove(Atendentes);
		em.getTransaction().commit();
		
	}
	
	public List<Atendente> recuperarTodos(){
		Query query = em.createNamedQuery("Atendentes.todos");
		return query.getResultList();
		
	}

	public List<Atendente> recuperarTodosPorNome(){
		Query query = em.createNamedQuery("Atendentes.todosPorNome");
		return query.getResultList();
		
	}

	
	public void encerrar() {
		em.close();
		emf.close();
	}
	
}
