package com.spring.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.spring.dto.HackerNew;

@Repository("HackerNewsRepository")
public interface HackerNewsRepository extends CrudRepository<HackerNew, Serializable> {

	public abstract boolean existsById(int id);

	public abstract List<HackerNew> findByDeleted(boolean isDeleted);

    public abstract HackerNew findById(int id);

}
