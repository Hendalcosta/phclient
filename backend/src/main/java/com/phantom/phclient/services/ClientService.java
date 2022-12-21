package com.phantom.phclient.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phantom.phclient.dto.ClientDTO;
import com.phantom.phclient.entities.Client;
import com.phantom.phclient.repositories.ClientRepository;
import com.phantom.phclient.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository repository;
	
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged (PageRequest pageRequest) {
		Page<Client> clientList = repository.findAll(pageRequest);
		return clientList.map(client -> new ClientDTO(client));
	}
	
	@Transactional(readOnly = true)
	public ClientDTO findById (Long id) {
		Optional<Client> client = repository.findById(id);
		Client entity = client.orElseThrow(() -> new ResourceNotFoundException("ID Não Encontrado: " + id));
		return new ClientDTO(entity);
	}
	
	@Transactional(readOnly = true)
	public ClientDTO create (ClientDTO dto) {
		Client entity = new Client();
		copyDtoToEntity(dto, entity);
		repository.save(entity);
		return new ClientDTO(entity);		
	}
	
	@Transactional(readOnly = true)
	public ClientDTO update (Long id, ClientDTO dto) {
		try {
		Client entity = repository.getReferenceById(id);
		copyDtoToEntity(dto, entity);
		repository.save(entity);
		return new ClientDTO(entity);
		}
		catch (EntityNotFoundException err) {
			throw new ResourceNotFoundException("ID não encontrado: " + id);
		}
	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}
		catch (EmptyResultDataAccessException err) {
			throw new ResourceNotFoundException("ID não encontrado: " + id);
		}
	}
	
	private void copyDtoToEntity(ClientDTO dto, Client entity) {
		entity.setName(dto.getName());
		entity.setCpf(dto.getCpf());
		entity.setIncome(dto.getIncome());
		entity.setBirthDate(dto.getBirthDate());
		entity.setChildren(dto.getChildren());	
		}
}

