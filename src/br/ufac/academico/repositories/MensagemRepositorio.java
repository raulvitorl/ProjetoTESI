package br.ufac.academico.repositories;

import java.util.*;
import javax.persistence.*;

import br.ufac.academico.domain.*;

public class MensagemRepositorio {

	private EntityManagerFactory emf;
	private EntityManager em;	
	
	public MensagemRepositorio() {
		emf = Persistence.createEntityManagerFactory("AcademicoJPA");
		em = emf.createEntityManager();
	}

	public void adicionar(Mensagem mensagem) {	
		@SuppressWarnings("unused")
		Date d1;
		mensagem.setDataEnvio(d1 = new Date());
		em.getTransaction().begin();
		em.persist(mensagem);
		em.getTransaction().commit();
		
	}
	
	public Mensagem recuperar(long id) {
		return em.find(Mensagem.class, id);
	}
	
	public void atualizar (Mensagem Mensagens) {
		em.getTransaction().begin();
		em.merge(Mensagens);
		em.getTransaction().commit();
	}
	
	public void remover(Mensagem Mensagens) {
		em.getTransaction().begin();
		em.remove(Mensagens);
		em.getTransaction().commit();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Mensagem> recuperarTodos(){
		Query query = em.createNamedQuery("Mensagens.todos");
		return query.getResultList();
		
	}

	public void encerrar() {
		em.close();
		emf.close();
	}
	
}
