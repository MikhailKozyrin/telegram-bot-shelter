package pro.sky.telegrambot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.model.ClientRegistration;
import pro.sky.telegrambot.service.ClientService;

@RestController
@RequestMapping("client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @Operation(
            summary = "Поиск клиента по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найден клиент с параметрами",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            },
            tags = "Работа с клиентами"
    )
    @GetMapping("{id}")
    public ClientRegistration getClient(@Parameter(description = "id клиента", example = "1") @PathVariable Long id) {
        return clientService.findClient(id);
    }

    @Operation(
            summary = "Добавление нового клиента",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Добавлен новый клиент с параметрами",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            },
            tags = "Работа с клиентами"
//            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
//                    description = "Параметры нового клиента",
//                    content = @Content(
//                            mediaType = MediaType.APPLICATION_JSON_VALUE
//                    )
    )

    @PostMapping
    public ClientRegistration createClient(@RequestBody ClientRegistration clientRegistration) {
        return clientService.createClient(clientRegistration);
    }

    @Operation(
            summary = "Редактирование клиента",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Обновленный клиент с параметрами",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            },
            tags = "Работа с клиентами"
//            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
//                    description = "Новые параметры клиента",
//                    content = @Content(
//                            mediaType = MediaType.APPLICATION_JSON_VALUE
//                    )
    )

    @PutMapping
    public ClientRegistration editClient(@RequestBody ClientRegistration clientRegistration) {
        return clientService.editClient(clientRegistration);
    }
}


