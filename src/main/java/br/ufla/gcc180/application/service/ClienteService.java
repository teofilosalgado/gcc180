package br.ufla.gcc180.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufla.gcc180.domain.entity.Cliente;
import br.ufla.gcc180.infrastructure.repository.ClienteRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public List<Cliente> findAll(String pesquisa) {
        return clienteRepository.findByNomeContainingIgnoreCaseOrCpfContaining(pesquisa, pesquisa);
    }

    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }

    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
    
    public void delete(Cliente cliente) {
        clienteRepository.delete(cliente);
    }
}
