package ru.akrtkv.egisz.vimis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.akrtkv.egisz.vimis.dto.rir.MisMo;
import ru.akrtkv.egisz.vimis.dto.rir.MisMos;
import ru.akrtkv.egisz.vimis.exception.MisMoServiceException;
import ru.akrtkv.egisz.vimis.repository.MisMoRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class MisMoService {

    private final MisMoRepository misMoRepository;

    @Autowired
    public MisMoService(MisMoRepository misMoRepository) {
        this.misMoRepository = misMoRepository;
    }

    @Log
    public ru.akrtkv.egisz.vimis.model.MisMo getMisMoByOrgOid(String organisationOid) {
        return misMoRepository.getMisMoByOid(organisationOid);
    }

    public boolean checkMoState(ru.akrtkv.egisz.vimis.model.MisMo misMo) {
        return !misMo.isDisabledForHour() && !misMo.isDisabledForLong()
                || (misMo.isDisabledForHour() && misMo.getDisabledFrom().plusHours(1).isBefore(LocalDateTime.now()));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void disableMis(ru.akrtkv.egisz.vimis.model.MisMo misMo, String message) {
        try {
            List<ru.akrtkv.egisz.vimis.model.MisMo> misMoList = misMoRepository.findAllByUrl(misMo.getUrl());
            for (var mis : misMoList) {
                if (!mis.isDisabledForHour()) {
                    mis.setDisabledForHour(true);
                    mis.setDisabledFrom(LocalDateTime.now());
                } else if (misMo.isDisabledForHour() && misMo.getDisabledFrom().plusHours(1).isBefore(LocalDateTime.now())) {
                    mis.setDisabledForHour(false);
                    mis.setDisabledForLong(true);
                    mis.setDisabledFrom(LocalDateTime.now());
                }
                mis.setDisablingReason(message);
            }
            misMoRepository.saveAll(misMoList);
        } catch (DataAccessException e) {
            throw new MisMoServiceException(e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void setMisAvailable(ru.akrtkv.egisz.vimis.model.MisMo misMo) {
        try {
            List<ru.akrtkv.egisz.vimis.model.MisMo> misMoList = misMoRepository.findAllByUrlAndDisabledForHour(misMo.getUrl(), true);
            if (!misMoList.isEmpty()) {
                for (var mis : misMoList) {
                    mis.setDisabledForHour(false);
                    mis.setDisabledFrom(null);
                    mis.setDisablingReason(null);
                }
                misMoRepository.saveAll(misMoList);
            }
        } catch (DataAccessException e) {
            throw new MisMoServiceException(e);
        }
    }

    public List<MisMo> getMisAddresses(String misUuid) {
        try {
            List<ru.akrtkv.egisz.vimis.model.MisMo> misMoList = misMoRepository.findAllByMisUuid(misUuid);
            List<MisMo> misMoDtoList = new ArrayList<>();
            for (ru.akrtkv.egisz.vimis.model.MisMo misMo : misMoList) {
                var misMoDto = new MisMo();
                misMoDto.setId(misMo.getId());
                misMoDto.setMisUuid(misMo.getMisUuid());
                misMoDto.setMoOid(misMo.getOid());
                misMoDto.setMoName(misMo.getShortName());
                if (misMo.isDisabledForHour()) {
                    misMoDto.setStatus("Отключено на час с " + misMo.getDisabledFrom().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
                    misMoDto.setDisabled(true);
                    misMoDto.setDisablingReason(misMo.getDisablingReason());
                } else if (misMo.isDisabledForLong()) {
                    misMoDto.setStatus("Отключено с " + misMo.getDisabledFrom().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
                    misMoDto.setDisabled(true);
                    misMoDto.setDisablingReason(misMo.getDisablingReason());
                } else {
                    misMoDto.setStatus("Включено");
                }
                misMoDto.setMisUrl(misMo.getUrl());
                misMoDtoList.add(misMoDto);
            }
            return misMoDtoList;
        } catch (DataAccessException e) {
            throw new MisMoServiceException(e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addMisAddress(MisMos misMos) {
        try {
            List<ru.akrtkv.egisz.vimis.model.MisMo> misMoList = new ArrayList<>();
            List<ru.akrtkv.egisz.vimis.model.MisMo> misMoExistingList = misMoRepository.findAll();
            for (var mo : misMos.getMoList()) {
                var misMo = new ru.akrtkv.egisz.vimis.model.MisMo();
                misMo.setOid(mo.getOid());
                misMo.setShortName(mo.getNameShort());
                misMo.setMisUuid(misMos.getMisUuid());
                misMo.setUrl(misMos.getUrl());
                if (!misMoExistingList.contains(misMo)) {
                    misMoList.add(misMo);
                }
            }
            misMoRepository.saveAll(misMoList);
            evictMoCache();
        } catch (DataAccessException e) {
            throw new MisMoServiceException(e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteMisAddress(String moOid) {
        try {
            misMoRepository.deleteByOid(moOid);
            evictMoCache();
        } catch (DataAccessException e) {
            throw new MisMoServiceException(e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void enableMisAddress(String url) {
        try {
            var misMoList = misMoRepository.findAllByUrl(url);
            for (var mis : misMoList) {
                mis.setDisabledForHour(false);
                mis.setDisabledForLong(false);
                mis.setDisabledFrom(null);
                mis.setDisablingReason(null);
            }
            misMoRepository.saveAll(misMoList);
            evictMoCache();
        } catch (DataAccessException e) {
            throw new MisMoServiceException(e);
        }
    }

    public void disableMisAddress(String url, String reason) {
        try {
            var misMoList = misMoRepository.findAllByUrl(url);
            for (var mis : misMoList) {
                mis.setDisabledForHour(false);
                mis.setDisabledForLong(true);
                mis.setDisabledFrom(LocalDateTime.now());
                mis.setDisablingReason(reason);
            }
            misMoRepository.saveAll(misMoList);
            evictMoCache();
        } catch (DataAccessException e) {
            throw new MisMoServiceException(e);
        }
    }

    @Cacheable("mo-oid")
    public List<String> getMoOids() {
        List<ru.akrtkv.egisz.vimis.model.MisMo> misMoList = misMoRepository.findAll();
        List<String> moOidList = new ArrayList<>();
        for (var misMo : misMoList) {
            moOidList.add(misMo.getOid());
        }
        return moOidList;
    }

    @CacheEvict(value = "mo-oid", allEntries = true)
    public void evictMoCache() {
    }
}
