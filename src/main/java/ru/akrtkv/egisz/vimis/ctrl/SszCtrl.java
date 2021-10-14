package ru.akrtkv.egisz.vimis.ctrl;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.akrtkv.egisz.vimis.dto.vimis.Clinrec;
import ru.akrtkv.egisz.vimis.dto.vimis.ClinrecListResponse;
import ru.akrtkv.egisz.vimis.dto.vimis.Pmc;
import ru.akrtkv.egisz.vimis.dto.vimis.ProcPMCListResponse;
import ru.akrtkv.egisz.vimis.service.Log;
import ru.akrtkv.egisz.vimis.service.SszService;
import ru.akrtkv.egisz.vimis.utils.ApiPaths;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(ApiPaths.BASE_PATH)
@Validated
public class SszCtrl {

    private final SszService service;

    @Autowired
    public SszCtrl(SszService service) {
        this.service = service;
    }

    @Log
    @Operation(summary = "Запрос списка документов клинических рекомендаций")
    @GetMapping(ApiPaths.SSZ_GET_CLIN_REC_LIST)
    public ClinrecListResponse getClinrecList() {
        return service.getClinrecList();
    }

    @Log
    @Operation(summary = "Запрос структурированной информации по документу клинической рекомендации")
    @GetMapping(ApiPaths.SSZ_GET_CLIN_REC_INFO)
    public Clinrec getClinrecInfo(@RequestParam @NotNull @NotEmpty String id) {
        return service.getClinrecInfo(id);
    }

    @Log
    @Operation(summary = "Запроса списка документов порядков ОМП")
    @GetMapping(ApiPaths.SSZ_GET_PROC_PMC_LIST)
    public ProcPMCListResponse getProcPMCList() {
        return service.getProcPMCList();
    }

    @Log
    @Operation(summary = "Запрос структурированной информации по документу порядка ОМП")
    @GetMapping(ApiPaths.SSZ_GET_PROC_PMC_INFO)
    public Pmc getProcPMCInfo(@RequestParam @NotNull @NotEmpty String id) {
        return service.getProcPMCInfo(id);
    }
}
