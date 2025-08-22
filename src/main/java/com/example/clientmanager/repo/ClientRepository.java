package com.example.clientmanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.clientmanager.domain.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
