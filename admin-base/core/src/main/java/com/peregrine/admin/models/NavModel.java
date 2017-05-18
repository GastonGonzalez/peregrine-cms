package com.peregrine.admin.models;

import com.peregrine.nodetypes.models.AbstractComponent;
import com.peregrine.nodetypes.models.Container;
import com.peregrine.nodetypes.models.IComponent;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;
import javax.inject.Named;

/*
    //GEN[:DATA
    {
  "type": "object",
  "properties": {
    "component": {
      "type": "string",
      "source": "ignore"
    },
    "path": {
      "type": "string",
      "source": "ignore"
    }
  },
  "propertyNames": [
    "component",
    "path"
  ],
  "modelName": "Nav",
  "package": "com.peregrine.admin.models",
  "componentPath": "admin/components/nav",
  "classNameParent": "AbstractComponent"
}
//GEN]
*/

//GEN[:DEF
@Model(
        adaptables = Resource.class,
        resourceType = "admin/components/nav",
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,
        adapters = IComponent.class
)
@Exporter(
        name = "jackson",
        extensions = "json"
)

//GEN]
public class NavModel extends Container {

    public NavModel(Resource r) { super(r); }

    //GEN[:INJECT
    
//GEN]

    //GEN[:GETTERS
    
//GEN]

}