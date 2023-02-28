package pro.sky.telegrambot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Controller для пользователя.
// Предназначен для добавления, редактирования и удаления пользователя (усыновителя).

@RestController
@RequestMapping("/client")
public class ClientController {
    @Operation(
            summary = "Добавление нового усыновителя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Новый усыновитель добавлен"
                    )
            },
            tags = "client"
    )

    @Operation(
            summary = "Редактирование данных пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Данные пользователя изменены"
                    )
            },
            tags = "client"
    )

    @Operation(
            summary = "Удаление пользователя из базы данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь удален"
                    )
            },
            tags = "client"
    )

    @Operation(
            summary = "Поиск пользователя по ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденный пользователь"
                    )

            },
            tags = "client"
    )

    @Operation(
            summary = "Получение списка всех пользователей",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список всех пользователей"
                    )

            },
            tags = "client"
    )


}



