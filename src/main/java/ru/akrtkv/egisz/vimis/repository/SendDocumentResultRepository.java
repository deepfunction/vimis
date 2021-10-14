package ru.akrtkv.egisz.vimis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.akrtkv.egisz.vimis.model.SendDocumentResult;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SendDocumentResultRepository extends JpaRepository<SendDocumentResult, Long> {

    @Query(
            value = """
                    SELECT *
                    FROM send_document_result
                             INNER JOIN document doc ON doc.send_document_result_id = send_document_result.id
                    WHERE doc.id = ?
                    """,
            nativeQuery = true
    )
    SendDocumentResult getByDocId(@Param("id") long id);

    SendDocumentResult findByMessageId(String messageId);

    List<SendDocumentResult> findAllByCreationDateBetween(LocalDateTime dateStart, LocalDateTime dateEnd);
}
