package pro.sky.telegrambot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import okhttp3.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

// Controller предназначен для отчетов пользователя(усыновителя).
// Получение текстовой информации(рацион, самочувствие), фото.

@RestController
@RequestMapping("/report")
public class ReportController {
    @Operation(
            summary = "Получение фотографии из отчета",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Фотография отчета"
                    )
            },
            tags = "report"
    )

    @Operation(
            summary = "Получение текста отчета",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Текст отчета"
                    )
            },
            tags = "report"
    )

    @Operation(
            summary = "Получение всех отчетов пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список всех отчетов пользователя"
                    )
            },
            tags = "report"
    )

    @Operation(
            summary = "Редактирование статуса отчетов пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Статус отчета изменен"
                    )

            },
            tags = "report"
    )

    @Operation(
            summary = "Отправление сообщений пользователю",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Сообщение пользователю доставлено"
                    )

            },
            tags = "report"
    )

    @Operation(
            summary = "Увеличение испытательного срока пользователю",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Испытательный срок пользователя увеличен"
                    )

            },
            tags = "report"
    )

    @Operation(
            summary = "Информирование пользователя о завершении испытательного срока",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Испытательный срок пользователя завершен"
                    )

            },
            tags = "report"
    )
}


