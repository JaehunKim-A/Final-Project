package com.team1.team1project.controller;

import com.team1.team1project.service.DownloadService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/download")
@RequiredArgsConstructor
public class DownloadController {

	private final DownloadService downloadService;

	@GetMapping("/excel/{type}")
	public void downloadExcel(@PathVariable String type, HttpServletResponse response) throws IOException {
		Workbook workbook = downloadService.createExcelByType(type);

		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment; filename=" + type + ".xlsx");

		workbook.write(response.getOutputStream());
		workbook.close();
	}
}