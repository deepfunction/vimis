//
// This file was generated by the Eclipse Implementation of JAXB, v2.3.3
// See https://eclipse-ee4j.github.io/jaxb-ri
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2021.09.24 at 11:00:11 AM MSK
//

package ru.akrtkv.egisz.vimis.dto.vimis;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "name",
        "services",
        "medications"
})
@XmlRootElement(name = "activity")
public class Activity {

    @XmlElement(required = true)
    protected String name;

    protected Services services;

    protected Medications medications;

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public Services getServices() {
        return services;
    }

    public void setServices(Services value) {
        this.services = value;
    }

    public Medications getMedications() {
        return medications;
    }

    public void setMedications(Medications value) {
        this.medications = value;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "name='" + name + '\'' +
                ", services=" + services +
                ", medications=" + medications +
                '}';
    }
}
