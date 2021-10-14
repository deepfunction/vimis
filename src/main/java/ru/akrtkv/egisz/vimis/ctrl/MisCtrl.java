package ru.akrtkv.egisz.vimis.ctrl;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.akrtkv.egisz.vimis.dto.rir.MisMo;
import ru.akrtkv.egisz.vimis.dto.rir.MisMos;
import ru.akrtkv.egisz.vimis.service.MisMoService;
import ru.akrtkv.egisz.vimis.utils.ApiPaths;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(ApiPaths.BASE_PATH)
@Validated
public class MisCtrl {

    private final MisMoService misMoService;

    @Autowired
    public MisCtrl(MisMoService misMoService) {
        this.misMoService = misMoService;
    }

    @GetMapping(ApiPaths.MIS_ADDRESSES)
    @Operation(hidden = true)
    public List<MisMo> getMisAddresses(@RequestParam("misUuid") @NotNull @NotEmpty String misUuid) {
        return misMoService.getMisAddresses(misUuid);
    }

    @PostMapping(ApiPaths.MIS_ADDRESS_ADD)
    @Operation(hidden = true)
    public void addMisAddress(@RequestBody @Valid @NotNull MisMos misMos) {
        misMoService.addMisAddress(misMos);
    }

    @PutMapping(ApiPaths.MIS_ADDRESS_DELETE)
    @Operation(hidden = true)
    public void deleteMisAddress(@RequestParam("moOid") @NotNull @NotEmpty String moOid) {
        misMoService.deleteMisAddress(moOid);
    }

    @PutMapping(ApiPaths.MIS_ADDRESS_ENABLE)
    @Operation(hidden = true)
    public void enableMisAddress(@RequestParam("url") @NotNull @NotEmpty String url) {
        misMoService.enableMisAddress(url);
    }

    @PutMapping(ApiPaths.MIS_ADDRESS_DISABLE)
    @Operation(hidden = true)
    public void disableMisAddress(
            @RequestParam("url") @NotNull @NotEmpty String url,
            @RequestParam("reason") @NotNull @NotEmpty String reason
    ) {
        misMoService.disableMisAddress(url, reason);
    }
}
