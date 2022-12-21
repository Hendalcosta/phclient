package com.phantom.phclient.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phantom.phclient.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}

