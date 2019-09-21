package com.ipn.gasolimetro.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ipn.gasolimetro.entity.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long>{
	public abstract Optional<Login> findByEmailAndPassword(String email, String password);
}
