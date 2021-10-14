package ru.akrtkv.egisz.vimis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.akrtkv.egisz.vimis.model.Document;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    Document findByIdAndSendDocumentResult_StatusSyncIsNull(long docId);

    Document findByMisId(String misId);

    Document findBySendDocumentResult_MessageId(String messageId);

    @Query(
            value = """
                    SELECT document.id
                    FROM document
                             INNER JOIN send_document_result result ON document.send_document_result_id = result.id
                    WHERE result.status_sync IS NULL
                    """,
            nativeQuery = true
    )
    List<Long> getNotSentDocsIds();
}
