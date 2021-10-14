package ru.akrtkv.egisz.vimis.model;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "mis_mo")
public class MisMo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "oid", nullable = false, unique = true)
    private String oid;

    @Column(name = "short_name", nullable = false, unique = true)
    private String shortName;

    @Column(name = "disabled_for_hour", nullable = false, columnDefinition = "boolean default false")
    @Generated(GenerationTime.INSERT)
    private boolean disabledForHour;

    @Column(name = "disabled_for_long", nullable = false, columnDefinition = "boolean default false")
    @Generated(GenerationTime.INSERT)
    private boolean disabledForLong;

    @Column(name = "disabled_from")
    private LocalDateTime disabledFrom;

    @Column(name = "disabling_reason")
    @Type(type = "text")
    private String disablingReason;

    @Column(name = "deleted", nullable = false, columnDefinition = "boolean default false")
    @Generated(GenerationTime.INSERT)
    private boolean deleted;

    @Column(name = "url")
    private String url;

    @Column(name = "mis_uuid")
    private String misUuid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public boolean isDisabledForHour() {
        return disabledForHour;
    }

    public void setDisabledForHour(boolean disabledForHour) {
        this.disabledForHour = disabledForHour;
    }

    public boolean isDisabledForLong() {
        return disabledForLong;
    }

    public void setDisabledForLong(boolean disabledForLong) {
        this.disabledForLong = disabledForLong;
    }

    public LocalDateTime getDisabledFrom() {
        return disabledFrom;
    }

    public void setDisabledFrom(LocalDateTime disabledFrom) {
        this.disabledFrom = disabledFrom;
    }

    public String getDisablingReason() {
        return disablingReason;
    }

    public void setDisablingReason(String disablingReason) {
        this.disablingReason = disablingReason;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMisUuid() {
        return misUuid;
    }

    public void setMisUuid(String misUuid) {
        this.misUuid = misUuid;
    }

    @Override
    public String toString() {
        return "MisMo{" +
                "id=" + id +
                ", oid='" + oid + '\'' +
                ", shortName='" + shortName + '\'' +
                ", disabledForHour=" + disabledForHour +
                ", disabledForLong=" + disabledForLong +
                ", disabledFrom=" + disabledFrom +
                ", disablingReason='" + disablingReason + '\'' +
                ", deleted=" + deleted +
                ", url='" + url + '\'' +
                ", misUuid='" + misUuid + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MisMo misMo = (MisMo) o;
        return oid.equals(misMo.oid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(oid);
    }
}
