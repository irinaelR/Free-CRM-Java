package com.crm.application.duplicate;

import com.crm.application.common.ApiClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;

@RestController
@RequestMapping("/api/ObjectCopy")
public class DuplicateController {

    private final ApiClient apiClient;

    public DuplicateController(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @PostMapping("")
    public String copyObj(@RequestParam MultipartFile file) throws IOException, ParseException {
        DuplicateManager manager = new DuplicateManager();
        manager.setFile(file);

        manager.read(apiClient);
        return file.getName();
    }
}
