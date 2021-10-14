package ru.akrtkv.egisz.vimis.model;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "document")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "mis_id", unique = true, nullable = false)
    private String misId;

    @Column(name = "creation_date", nullable = false, columnDefinition = "timestamp default now()")
    @Generated(GenerationTime.INSERT)
    private LocalDateTime creationDate;

    @Column(name = "mo_oid", nullable = false)
    private String moOid;

    @Column(name = "vimis_type", nullable = false)
    private Integer vimisType;

    @Column(name = "doc_type", nullable = false)
    private Integer docType;

    @Column(name = "doc_type_version", nullable = false)
    private Integer docTypeVersion;

    @Column(name = "encoded_document", nullable = false)
    @Type(type = "text")
    private String encodedDocument;

    @OneToOne(targetEntity = Signature.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "signature_id")
    private Signature signature;

    @OneToOne(targetEntity = SendDocumentResult.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private SendDocumentResult sendDocumentResult;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMisId() {
        return misId;
    }

    public void setMisId(String misId) {
        this.misId = misId;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getMoOid() {
        return moOid;
    }

    public void setMoOid(String moOid) {
        this.moOid = moOid;
    }

    public Integer getVimisType() {
        return vimisType;
    }

    public void setVimisType(Integer vimisType) {
        this.vimisType = vimisType;
    }

    public Integer getDocType() {
        return docType;
    }

    public void setDocType(Integer docType) {
        this.docType = docType;
    }

    public Integer getDocTypeVersion() {
        return docTypeVersion;
    }

    public void setDocTypeVersion(Integer docTypeVersion) {
        this.docTypeVersion = docTypeVersion;
    }

    public String getEncodedDocument() {
        return encodedDocument;
    }

    public void setEncodedDocument(String encodedDocument) {
        this.encodedDocument = encodedDocument;
    }

    public Signature getSignature() {
        return signature;
    }

    public void setSignature(Signature signature) {
        this.signature = signature;
    }

    public SendDocumentResult getSendDocumentResult() {
        return sendDocumentResult;
    }

    public void setSendDocumentResult(SendDocumentResult sendDocumentResult) {
        this.sendDocumentResult = sendDocumentResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return misId.equals(document.misId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(misId);
    }
}
