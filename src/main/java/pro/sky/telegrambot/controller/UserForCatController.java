package pro.sky.telegrambot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.model.UserForCat;
import pro.sky.telegrambot.service.UserForCatService;

@RestController
@RequestMapping("/userForCat")
public class UserForCatController {
    private final UserForCatService userForCatService;

    public UserForCatController(UserForCatService userForCatService) {
        this.userForCatService = userForCatService;
    }

    @Operation(
            summary = "Поиск пользователя приюта для котов по chat_id",
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
            tags = "Работа с пользователями приюта для котов"
    )
    @GetMapping("{chatId}")
    public UserForCat getUserForCat(@Parameter(description = "chat_id пользователя", example = "1236554")
                                        @PathVariable Long chatId) {
        return userForCatService.findUserForCat(chatId);
    }

    @Operation(
            summary = "Добавление нового пользователя приюта для котов",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Добавлен новый пользователь с параметрами",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserForCat.class)
                            )
                    )
            },
            tags = "Работа с пользователями приюта для котов",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Параметры нового пользователя",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserForCat.class)
                    )
            )
    )
    @PostMapping
    public UserForCat createUserForCat(@Parameter(description = "chat_id пользователя", example = "1259874")
                                           @RequestBody UserForCat userForCat) {
        return userForCatService.createUserForCat(userForCat);
    }

    @Operation(
            summary = "Редактирование пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Обновленный пользователь",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserForCat.class)
                            )
                    )
            },
            tags = "Работа с пользователями приюта для котов",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Новые данные пользователя",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserForCat.class)
                    )
            )
    )
    @PutMapping
    public UserForCat editVolunteer(@RequestBody UserForCat userForCat) {
        return userForCatService.editUserForCat(userForCat);
    }
}

