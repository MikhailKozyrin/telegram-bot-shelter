package pro.sky.telegrambot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.model.UserCat;
import pro.sky.telegrambot.service.UserCatService;

@RestController
@RequestMapping("/userCat")
public class UserCatController {
    private final UserCatService userCatService;


    public UserCatController(UserCatService userCatService) {
        this.userCatService = userCatService;
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
    public UserCat getUserCat(@Parameter(description = "chat_id пользователя", example = "1236554")
                                    @PathVariable Long chatId) {
        return userCatService.findUser(chatId);
    }

    @Operation(
            summary = "Добавление нового пользователя приюта для котов",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Добавлен новый пользователь с параметрами",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserCat.class)
                            )
                    )
            },
            tags = "Работа с пользователями приюта для котов",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Параметры нового пользователя",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserCat.class)
                    )
            )
    )
    @PostMapping
    public UserCat createUserForCat(@Parameter(description = "chat_id пользователя", example = "1259874")
                                       @RequestBody UserCat userForCat) {
        return userCatService.createUser(userForCat);
    }

    @Operation(
            summary = "Редактирование пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Обновленный пользователь",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserCat.class)
                            )
                    )
            },
            tags = "Работа с пользователями приюта для котов",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Новые данные пользователя",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserCat.class)
                    )
            )
    )
    @PutMapping
    public UserCat editVolunteer(@RequestBody UserCat userForCat) {
        return userCatService.editUser(userForCat);
    }
}
