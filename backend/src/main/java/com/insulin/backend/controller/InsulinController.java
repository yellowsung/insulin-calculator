package com.insulin.backend.controller;

import com.insulin.backend.dto.GlucoseRequest;
import com.insulin.backend.dto.InsulinResponse;
import com.insulin.backend.service.InsulinService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class InsulinController {

    private final InsulinService insulinService;

    public InsulinController(InsulinService insulinService) {
        this.insulinService = insulinService;
    }

    @PostMapping("/calculate")
    public InsulinResponse calculate(@RequestBody GlucoseRequest request) {

        InsulinService.Result result =
                insulinService.calculate(
                        request.getGlucose(),
                        request.getCurrentInsulin()
                );

        return new InsulinResponse(
                result.getInsulin(),
                result.getStatus().name(),
                result.getMessage()
        );
    }
}