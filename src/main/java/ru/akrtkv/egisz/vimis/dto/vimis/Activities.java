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
        "activity"
})
@XmlRootElement(name = "activities")
public class Activities {

    @XmlElement(required = true)
    protected List<Activity> activity;

    public List<Activity> getActivity() {
        if (activity == null) {
            activity = new ArrayList<Activity>();
        }
        return this.activity;
    }

    @Override
    public String toString() {
        return "Activities{" +
                "activity=" + activity +
                '}';
    }
}
