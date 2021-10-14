package ru.akrtkv.egisz.vimis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.akrtkv.egisz.vimis.model.MisMo;

import java.util.List;

@Repository
public interface MisMoRepository extends JpaRepository<MisMo, Long> {

    MisMo getMisMoByOid(String oid);

    List<MisMo> findAllByUrl(String url);

    List<MisMo> findAllByUrlAndDisabledForHour(String url, boolean disabledForHour);

    List<MisMo> findAllByMisUuid(String misUuid);

    List<MisMo> findAll();

    void deleteByOid(String moOid);
}
