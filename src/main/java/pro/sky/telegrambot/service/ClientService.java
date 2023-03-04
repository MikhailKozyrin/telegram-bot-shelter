package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.exception.ClientNotFoundException;
import pro.sky.telegrambot.model.ClientRegistration;
import pro.sky.telegrambot.repository.ClientRepository;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientRegistration findClient(Long id) {
        ClientRegistration clientRegistration = clientRepository.findById(id).orElse(null);
        if (clientRegistration == null) {
            throw new ClientNotFoundException(id);
        }
        return clientRegistration;
    }

    public ClientRegistration createClient(ClientRegistration clientRegistration) {
        return clientRepository.save(clientRegistration);
    }

    public ClientRegistration editClient(ClientRegistration clientRegistration) {
        if (clientRepository.findById(clientRegistration.getId()).orElse(null) == null) {
            return null;
        }
        return clientRepository.save(clientRegistration);
    }
}
