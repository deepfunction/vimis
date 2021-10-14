package ru.akrtkv.egisz.vimis.ctrl;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.akrtkv.egisz.vimis.dto.vimis.*;
import ru.akrtkv.egisz.vimis.service.AkiNeoService;
import ru.akrtkv.egisz.vimis.service.Log;
import ru.akrtkv.egisz.vimis.utils.ApiPaths;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(ApiPaths.BASE_PATH)
@Validated
public class AkiNeoCtrl {

    private final AkiNeoService service;

    @Autowired
    public AkiNeoCtrl(AkiNeoService service) {
        this.service = service;
    }

    @Log
    @Operation(summary = "Генерация уникального идентификатора случая КАС в ВИМИС АКиНЕО")
    @PostMapping(ApiPaths.AKINEO_GET_KAS_ID)
    public KasResponse getKasId(@RequestBody @NotNull @Valid KasRequest kasRequest) {
        return service.getKasId(kasRequest);
    }

    @Log
    @Operation(summary = "Запрос списка документов клинических рекомендаций")
    @GetMapping(ApiPaths.AKINEO_GET_CLIN_REC_LIST)
    public ClinrecListResponse getClinrecList() {
        return service.getClinrecList();
    }

    @Log
    @Operation(summary = "Запрос структурированной информации по документу клинической рекомендации")
    @GetMapping(ApiPaths.AKINEO_GET_CLIN_REC_INFO)
    public Clinrec getClinrecInfo(@RequestParam @NotNull @NotEmpty String id) {
        return service.getClinrecInfo(id);
    }

    @Log
    @Operation(summary = "Запроса списка документов порядков ОМП")
    @GetMapping(ApiPaths.AKINEO_GET_PROC_PMC_LIST)
    public ProcPMCListResponse getProcPMCList() {
        return service.getProcPMCList();
    }

    @Log
    @Operation(summary = "Запрос структурированной информации по документу порядка ОМП")
    @GetMapping(ApiPaths.AKINEO_GET_PROC_PMC_INFO)
    public Pmc getProcPMCInfo(@RequestParam @NotNull @NotEmpty String id) {
        return service.getProcPMCInfo(id);
    }
}
