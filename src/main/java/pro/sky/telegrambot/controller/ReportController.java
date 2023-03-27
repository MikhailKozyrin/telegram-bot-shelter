package pro.sky.telegrambot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.model.Report;
import pro.sky.telegrambot.service.ReportService;

    @RestController
    @RequestMapping("/report")
    public class ReportController {
        private final ReportService reportService;

        public ReportController(ReportService reportService) {
            this.reportService = reportService;
        }

        @Operation(
                summary = "Поиск отчета по id",
                responses = {
                        @ApiResponse(
                                responseCode = "200",
                                description = "Найден отчет",
                                content = @Content(
                                        mediaType = MediaType.APPLICATION_JSON_VALUE
                                )
                        ),
                        @ApiResponse(
                                responseCode = "404",
                                description = "Не найден отчет",
                                content = @Content(
                                        mediaType = MediaType.APPLICATION_JSON_VALUE
                                )
                        )
                },
                tags = "Работа с отчетами"
        )
        @GetMapping("{id}")
        public Report getReport(@Parameter(description = "id отчета", example = "1") @PathVariable Long id) {
            return reportService.findReport(id);
        }

        @Operation(
                summary = "Добавление нового отчета",
                responses = {
                        @ApiResponse(
                                responseCode = "200",
                                description = "Добавлен новый отчет с параметрами",
                                content = @Content(
                                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                                        schema = @Schema(implementation = Report.class)
                                )
                        )
                },
                tags = "Работа с отчетами",
                requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                        description = "Параметры нового отчета",
                        content = @Content(
                                mediaType = MediaType.APPLICATION_JSON_VALUE,
                                schema = @Schema(implementation = Report.class)
                        )
                )
        )
        @PostMapping
        public Report createReport(@Parameter(description = "id отчета", example = "1")
                                   @RequestBody Report report) {
            return reportService.createReport(report);
        }

        @Operation(
                summary = "Редактирование отчета",
                responses = {
                        @ApiResponse(
                                responseCode = "200",
                                description = "Обновленный отчет",
                                content = @Content(
                                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                                        schema = @Schema(implementation = Report.class)
                                )
                        )
                },
                tags = "Работа с отчетами",
                requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                        description = "Новые данные отчета",
                        content = @Content(
                                mediaType = MediaType.APPLICATION_JSON_VALUE,
                                schema = @Schema(implementation = Report.class)
                        )
                )
        )
        @PutMapping
        public Report editReport(@RequestBody Report report) {
            return reportService.editReport(report);
        }
    }

