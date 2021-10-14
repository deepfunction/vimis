package ru.akrtkv.egisz.vimis.ctrl;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.akrtkv.egisz.vimis.dto.rir.Document;
import ru.akrtkv.egisz.vimis.dto.rir.ErrorMessage;
import ru.akrtkv.egisz.vimis.service.Log;
import ru.akrtkv.egisz.vimis.service.RirVimisService;
import ru.akrtkv.egisz.vimis.utils.ApiPaths;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(ApiPaths.BASE_PATH)
@Validated
public class VimisCtrl {

    private final RirVimisService service;

    @Autowired
    public VimisCtrl(RirVimisService service) {
        this.service = service;
    }

    @Log
    @Operation(summary = "Прием медицинских сведений")
    @PostMapping(ApiPaths.SEND_DOC)
    public Document sendDocument(
            @RequestBody @Valid @NotNull Document document
    ) {
        return service.sendDocument(document);
    }

    @GetMapping(ApiPaths.ERROR_MESSAGES)
    @Operation(hidden = true)
    public List<ErrorMessage> getErrorMessages(
            @RequestParam("dateStart") @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateStart,
            @RequestParam("dateEnd") @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateEnd
    ) {
        return service.getErrorMessages(dateStart, dateEnd);
    }
}
