package pro.sky.telegrambot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.model.UserDog;
import pro.sky.telegrambot.service.UserDogService;

@RestController
@RequestMapping("/userDog")
public class UserDogController {
    private final UserDogService userDogService;

    public UserDogController(UserDogService userDogService) {
        this.userDogService = userDogService;
    }


    @Operation(
            summary = "Поиск пользователя приюта для собак по chat_id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найден пользователь",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Не найден пользователь",
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
            tags = "Работа с пользователями приюта для собак"
    )
    @GetMapping("{chatId}")
    public UserDog getUser(@Parameter(description = "chat_id пользователя", example = "1236554")
                        @PathVariable Long chatId) {
        return userDogService.findUser(chatId);
    }

    @Operation(
            summary = "Добавление нового пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Добавлен новый пользователь с параметрами",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDog.class)
                            )
                    )
            },
            tags = "Работа с пользователями приюта для собак",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Параметры нового пользователя",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserDog.class)
                    )
            )
    )
    @PostMapping
    public UserDog createUser(@Parameter(description = "chat_id пользователя", example = "1259874")
                           @RequestBody UserDog userDog) {
        return userDogService.createUser(userDog);
    }

    @Operation(
            summary = "Редактирование пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Обновленный пользователь",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDog.class)
                            )
                    )
            },
            tags = "Работа с пользователями приюта для собак",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Новые данные пользователя",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserDog.class)
                    )
            )
    )
    @PutMapping
    public UserDog editUser(@RequestBody UserDog userDog) {
        return userDogService.editUser(userDog);
    }
}
