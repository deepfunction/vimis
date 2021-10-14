package ru.akrtkv.egisz.vimis.model;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "signature")
public class Signature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "creation_date", nullable = false, columnDefinition = "timestamp default now()")
    @Generated(GenerationTime.INSERT)
    private LocalDateTime creationDate;

    @Column(name = "data", nullable = false)
    private String data;

    @Column(name = "checksum", nullable = false)
    private Integer checksum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getData() {
        return data;
    }

    public void setData(String value) {
        this.data = value;
    }

    public Integer getChecksum() {
        return checksum;
    }

    public void setChecksum(Integer checksum) {
        this.checksum = checksum;
    }
}
