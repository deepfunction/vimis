package ru.akrtkv.egisz.vimis.dto.rir;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.akrtkv.egisz.vimis.validator.MoOid;
import ru.akrtkv.egisz.vimis.validator.SignatureData;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class Document {

    private Long rirId;

    @NotNull
    @NotEmpty
    private String misId;

    @NotNull
    @NotEmpty
    @MoOid
    private String moOid;

    @Schema(description = """
            3 – Акушерство, гинекология и неонатология;
            4 – Сердечно - сосудистые заболевания;
            1 – Онкология
            """)
    @NotNull
    private Integer vimisType;

    @Schema(description = """
            1 - Направление на оказание медицинских услуг СЭМД: Направление на госпитализацию, восстановительное лечение, обследование, консультацию https://portal.egisz.rosminzdrav.ru/materials/2933
            2 - Протокол инструментального исследования СЭМД: Протокол инструментального исследования https://portal.egisz.rosminzdrav.ru/materials/3291
            3 - Протокол лабораторного исследования, СЭМД: Протокол лабораторного исследования https://portal.egisz.rosminzdrav.ru/materials/2939
            4 - Протокол прижизненного патолого-анатомического исследования биопсийного (операционного) материала СЭМД: Протокол прижизненного патологоанатомического исследования https://portal.egisz.rosminzdrav.ru/materials/2941       
            5 - Осмотр (консультация) пациента СЭМД: Протокол консультации https://portal.egisz.rosminzdrav.ru/materials/2937
            6 - Решение (протокол) врачебной комиссии (консилиума врачей)
            7 - Диспансерное наблюдение
            8 - Лечение в условиях стационара (дневного стационара) СЭМД: Эпикриз в стационаре выписной https://portal.egisz.rosminzdrav.ru/materials/2943
            9 - Протокол цитологического исследования
            10 - Протокол оперативного вмешательства
            11 - Протокол на случай выявления у больного запущенной формы злокачественного новообразования
            12 - Оказание медицинской помощи в амбулаторных условиях СЭМД: Эпикриз по законченному случаю амбулаторный https://portal.egisz.rosminzdrav.ru/materials/2945    
            13 - Медицинское свидетельство о смерти (CDA) https://portal.egisz.rosminzdrav.ru/materials/2931 https://portal.egisz.rosminzdrav.ru/materials/3753 https://portal.egisz.rosminzdrav.ru/materials/3815
            14 - Медицинское свидетельство о перинатальной смерти (CDA) https://portal.egisz.rosminzdrav.ru/materials/3605
            15 - Талон на оказание ВМП
            16 - Ретроспективные данные по пациентам с онкологической патологией
            17 - Выписной эпикриз родильного дома СЭМД: Выписной эпикриз из родильного дома https://portal.egisz.rosminzdrav.ru/materials/2925
            18 - Карта вызова скорой медицинской помощи
            19 - Медицинское свидетельство о перинатальной смерти (CDA) https://portal.egisz.rosminzdrav.ru/materials/3605
            20 - Протокол консультации https://portal.egisz.rosminzdrav.ru/materials/3845
            21 - Протокол консультации в рамках диспансерного наблюдения https://portal.egisz.rosminzdrav.ru/materials/3845
            22 - Извещение о критическом акушерском состоянии
            """)
    @NotNull
    private Integer docType;

    @Schema(description = """
            Для целей передачи информации в рамках протокола информационного взаимодействия ВИМИС ССЗ необходимо указывать значение 2,
            для ВИМИС Онкология 1 или 3 в зависимости от docType,
            для ВИМИС АКиНЕО 2 или 3 в зависимости от docType
            """)
    @NotNull
    private Integer docTypeVersion;

    @Schema(description = "СЭМД, кодированный в base64")
    @NotNull
    @NotEmpty
    private String encodedDocument;

    @Schema(description = "Блок электронной подписи")
    @SignatureData
    @Valid
    private Signature signature;

    public Long getRirId() {
        return rirId;
    }

    public void setRirId(Long rirId) {
        this.rirId = rirId;
    }

    public String getMisId() {
        return misId;
    }

    public void setMisId(String misId) {
        this.misId = misId;
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

    @Override
    public String toString() {
        return "Document{" +
                "rirId=" + rirId +
                ", misId='" + misId + '\'' +
                ", moOid='" + moOid + '\'' +
                ", vimisType=" + vimisType +
                ", docType=" + docType +
                ", docTypeVersion=" + docTypeVersion +
                ", encodedDocument='" + encodedDocument + '\'' +
                ", signature=" + signature +
                '}';
    }
}
