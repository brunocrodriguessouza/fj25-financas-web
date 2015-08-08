package br.com.caelum.financas.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.caelum.financas.modelo.Categoria;

@Stateless
public class CategoriaDao {

	@PersistenceContext
	private EntityManager manager;

	public Categoria procura(Integer id) {
		// busca pela id
		manager.joinTransaction();
		return this.manager.find(Categoria.class, id);
	}

	public List<Categoria> lista() {
		// retorna todas as categorias
		return this.manager.createQuery("select c from Categoria c",
				Categoria.class).getResultList();

	}

}
