//
// This file was generated by the Eclipse Implementation of JAXB, v2.3.3
// See https://eclipse-ee4j.github.io/jaxb-ri
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2021.09.24 at 11:00:11 AM MSK
//

package ru.akrtkv.egisz.vimis.dto.vimis;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "thesisRec"
})
@XmlRootElement(name = "thesises")
public class Thesises {

    @XmlElement(name = "thesis-rec", required = true)
    protected List<ThesisRec> thesisRec;

    public List<ThesisRec> getThesisRec() {
        if (thesisRec == null) {
            thesisRec = new ArrayList<ThesisRec>();
        }
        return this.thesisRec;
    }

    @Override
    public String toString() {
        return "Thesises{" +
                "thesisRec=" + thesisRec +
                '}';
    }
}
