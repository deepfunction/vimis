//
// This file was generated by the Eclipse Implementation of JAXB, v2.3.3
// See https://eclipse-ee4j.github.io/jaxb-ri
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2021.09.24 at 11:00:11 AM MSK
//

package ru.akrtkv.egisz.vimis.dto.vimis;

import javax.xml.bind.annotation.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "name",
        "id",
        "mkb10",
        "ageGroup",
        "profile",
        "revision"
})
@XmlRootElement(name = "summary")
public class Summary {

    @XmlElement(required = true)
    protected String name;

    @XmlElement(required = true)
    protected BigInteger id;

    @XmlElement(name = "MKB10")
    protected List<String> mkb10;

    @XmlElement(name = "age_group")
    protected String ageGroup;

    protected String profile;

    @XmlElement(required = true)
    protected Revision revision;

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger value) {
        this.id = value;
    }

    public List<String> getMKB10() {
        if (mkb10 == null) {
            mkb10 = new ArrayList<String>();
        }
        return this.mkb10;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String value) {
        this.ageGroup = value;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String value) {
        this.profile = value;
    }

    public Revision getRevision() {
        return revision;
    }

    public void setRevision(Revision value) {
        this.revision = value;
    }

    @Override
    public String toString() {
        return "Summary{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", mkb10=" + mkb10 +
                ", ageGroup='" + ageGroup + '\'' +
                ", profile='" + profile + '\'' +
                ", revision=" + revision +
                '}';
    }
}