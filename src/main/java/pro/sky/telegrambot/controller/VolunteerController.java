package pro.sky.telegrambot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.model.ClientRegistration;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.service.VolunteerService;

@RestController
@RequestMapping("/volunteer")
public class VolunteerController {

    private final VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @Operation(
            summary = "Поиск волонтера по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найден волонтер",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Не найден волонтер",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Сервер не может обработать запрос",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            },
            tags = "Работа с волонтерами"
    )
    @GetMapping("{id}")
    public Volunteer getVolunteer(@Parameter(description = "id клиента", example = "1") @PathVariable Long id) {
        return volunteerService.findVolunteer(id);
    }

    @Operation(
            summary = "Добавление нового волонтера",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Добавлен новый волонтер с параметрами",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class)
                            )
                    )
            },
            tags = "Работа с волонтерами",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Параметры нового волонтера",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Volunteer.class)
                    )
            )
    )
    @PostMapping
    public Volunteer createVolunteer(@Parameter(description = "id волонтера"+
            "имя", example = "1"+
            "Никита")@RequestBody Volunteer volunteer) {
        return volunteerService.createVolunteer(volunteer);
    }

    @Operation(
            summary = "Редактирование волонтера",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Обновленный волонтер",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class)
                            )
                    )
            },
            tags = "Работа с волонтерами",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Новые данные волонтера",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Volunteer.class)
                    )
            )
    )
    @PutMapping
    public Volunteer editVolunteer(@RequestBody Volunteer volunteer) {
        return volunteerService.editVolunteer(volunteer);
    }
}
