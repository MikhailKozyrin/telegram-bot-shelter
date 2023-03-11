package pro.sky.telegrambot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.model.User;
import pro.sky.telegrambot.service.UserService;


@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
    public User getUser(@Parameter(description = "chat_id пользователя", example = "1236554")
                                    @PathVariable Long chatId) {
        return userService.findUser(chatId);
    }

    @Operation(
            summary = "Добавление нового пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Добавлен новый пользователь с параметрами",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class)
                            )
                    )
            },
            tags = "Работа с пользователями приюта для собак",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Параметры нового пользователя",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = User.class)
                    )
            )
    )
    @PostMapping
    public User createUser(@Parameter(description = "chat_id пользователя", example = "1259874")
                                       @RequestBody User user) {
        return userService.createUser(user);
    }

    @Operation(
            summary = "Редактирование пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Обновленный пользователь",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class)
                            )
                    )
            },
            tags = "Работа с пользователями приюта для собак",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Новые данные пользователя",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = User.class)
                    )
            )
    )
    @PutMapping
    public User editUser(@RequestBody User user) {
        return userService.editUser(user);
    }
}


