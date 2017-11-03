package com.themeclean.models;

import com.peregrine.nodetypes.models.AbstractComponent;
import com.peregrine.nodetypes.models.IComponent;
import com.peregrine.nodetypes.models.Container;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/*
    //GEN[:DATA
    {
  "definitions": {
    "Block": {
      "type": "object",
      "x-type": "component",
      "properties": {
        "backgroundtype": {
          "type": "string",
          "x-source": "inject",
          "x-form-label": "Background Type",
          "x-form-type": "materialradio",
          "properties": {
            "image": {
              "x-form-name": "Image",
              "x-form-value": "image"
            },
            "color": {
              "x-form-name": "Color",
              "x-form-value": "color"
            },
            "gradient": {
              "x-form-name": "Gradient",
              "x-form-value": "gradient"
            }
          }
        },
        "bgimage": {
          "type": "string",
          "x-source": "inject",
          "x-form-label": "Background Image",
          "x-form-type": "pathbrowser",
          "x-form-visible": "model.backgroundtype == 'image'"
        },
        "parallax": {
          "type": "string",
          "x-source": "inject",
          "x-form-label": "Parallax",
          "x-form-type": "materialswitch",
          "x-form-visible": "model.backgroundtype == 'image'"
        },
        "overlay": {
          "type": "string",
          "x-source": "inject",
          "x-form-label": "Overlay",
          "x-form-type": "materialswitch",
          "x-form-visible": "model.backgroundtype == 'image'"
        },
        "overlaycolor": {
          "type": "string",
          "x-source": "inject",
          "x-form-label": "Overlay Color",
          "x-form-type": "color",
          "x-form-visible": "model.overlay == 'true' and model.backgroundtype == 'image'"
        },
        "overlayopacity": {
          "type": "string",
          "x-source": "inject",
          "x-form-label": "Overlay opacity",
          "x-form-type": "range",
          "x-form-min": 0,
          "x-form-max": 100,
          "x-form-visible": "model.overlay == 'true' and model.backgroundtype == 'image'"
        },
        "bgcolor": {
          "type": "string",
          "x-source": "inject",
          "x-form-label": "Background Color",
          "x-form-type": "color",
          "x-form-visible": "model.backgroundtype == 'color' or model.backgroundtype == 'gradient'"
        },
        "color2": {
          "type": "string",
          "x-source": "inject",
          "x-form-label": "Color 2",
          "x-form-type": "color",
          "x-form-visible": "model.backgroundtype == 'gradient'"
        },
        "fullwidth": {
          "type": "string",
          "x-source": "inject",
          "x-form-label": "Full Width",
          "x-form-type": "materialswitch"
        },
        "fullheight": {
          "type": "string",
          "x-source": "inject",
          "x-form-label": "Full Height",
          "x-form-type": "materialswitch"
        },
        "toppadding": {
          "type": "string",
          "x-source": "inject",
          "x-form-label": "Top Padding",
          "x-form-type": "range",
          "x-form-min": 0,
          "x-form-max": 120,
          "x-form-visible": "model.fullheight != 'true'"
        },
        "bottompadding": {
          "type": "string",
          "x-source": "inject",
          "x-form-label": "Bottom Padding",
          "x-form-type": "range",
          "x-form-min": 0,
          "x-form-max": 120,
          "x-form-visible": "model.fullheight != 'true'"
        }
      }
    }
  },
  "name": "Block",
  "componentPath": "themeclean/components/block",
  "package": "com.themeclean.models",
  "modelName": "Block",
  "classNameParent": "AbstractComponent"
}
//GEN]
*/

//GEN[:DEF
@Model(
        adaptables = Resource.class,
        resourceType = "themeclean/components/block",
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,
        adapters = IComponent.class
)
@Exporter(
        name = "jackson",
        extensions = "json"
)

//GEN]
public class BlockModel extends AbstractComponent {

    public BlockModel(Resource r) { super(r); }

    //GEN[:INJECT
    	/* {"type":"string","x-source":"inject","x-form-label":"Background Type","x-form-type":"materialradio","properties":{"image":{"x-form-name":"Image","x-form-value":"image"},"color":{"x-form-name":"Color","x-form-value":"color"},"gradient":{"x-form-name":"Gradient","x-form-value":"gradient"}}} */
	@Inject
	private String backgroundtype;

	/* {"type":"string","x-source":"inject","x-form-label":"Background Image","x-form-type":"pathbrowser","x-form-visible":"model.backgroundtype == 'image'"} */
	@Inject
	private String bgimage;

	/* {"type":"string","x-source":"inject","x-form-label":"Parallax","x-form-type":"materialswitch","x-form-visible":"model.backgroundtype == 'image'"} */
	@Inject
	private String parallax;

	/* {"type":"string","x-source":"inject","x-form-label":"Overlay","x-form-type":"materialswitch","x-form-visible":"model.backgroundtype == 'image'"} */
	@Inject
	private String overlay;

	/* {"type":"string","x-source":"inject","x-form-label":"Overlay Color","x-form-type":"color","x-form-visible":"model.overlay == 'true' and model.backgroundtype == 'image'"} */
	@Inject
	private String overlaycolor;

	/* {"type":"string","x-source":"inject","x-form-label":"Overlay opacity","x-form-type":"range","x-form-min":0,"x-form-max":100,"x-form-visible":"model.overlay == 'true' and model.backgroundtype == 'image'"} */
	@Inject
	private String overlayopacity;

	/* {"type":"string","x-source":"inject","x-form-label":"Background Color","x-form-type":"color","x-form-visible":"model.backgroundtype == 'color' or model.backgroundtype == 'gradient'"} */
	@Inject
	private String bgcolor;

	/* {"type":"string","x-source":"inject","x-form-label":"Color 2","x-form-type":"color","x-form-visible":"model.backgroundtype == 'gradient'"} */
	@Inject
	private String color2;

	/* {"type":"string","x-source":"inject","x-form-label":"Full Width","x-form-type":"materialswitch"} */
	@Inject
	private String fullwidth;

	/* {"type":"string","x-source":"inject","x-form-label":"Full Height","x-form-type":"materialswitch"} */
	@Inject
	private String fullheight;

	/* {"type":"string","x-source":"inject","x-form-label":"Top Padding","x-form-type":"range","x-form-min":0,"x-form-max":120,"x-form-visible":"model.fullheight != 'true'"} */
	@Inject
	private String toppadding;

	/* {"type":"string","x-source":"inject","x-form-label":"Bottom Padding","x-form-type":"range","x-form-min":0,"x-form-max":120,"x-form-visible":"model.fullheight != 'true'"} */
	@Inject
	private String bottompadding;


//GEN]

    //GEN[:GETTERS
    	/* {"type":"string","x-source":"inject","x-form-label":"Background Type","x-form-type":"materialradio","properties":{"image":{"x-form-name":"Image","x-form-value":"image"},"color":{"x-form-name":"Color","x-form-value":"color"},"gradient":{"x-form-name":"Gradient","x-form-value":"gradient"}}} */
	public String getBackgroundtype() {
		return backgroundtype;
	}

	/* {"type":"string","x-source":"inject","x-form-label":"Background Image","x-form-type":"pathbrowser","x-form-visible":"model.backgroundtype == 'image'"} */
	public String getBgimage() {
		return bgimage;
	}

	/* {"type":"string","x-source":"inject","x-form-label":"Parallax","x-form-type":"materialswitch","x-form-visible":"model.backgroundtype == 'image'"} */
	public String getParallax() {
		return parallax;
	}

	/* {"type":"string","x-source":"inject","x-form-label":"Overlay","x-form-type":"materialswitch","x-form-visible":"model.backgroundtype == 'image'"} */
	public String getOverlay() {
		return overlay;
	}

	/* {"type":"string","x-source":"inject","x-form-label":"Overlay Color","x-form-type":"color","x-form-visible":"model.overlay == 'true' and model.backgroundtype == 'image'"} */
	public String getOverlaycolor() {
		return overlaycolor;
	}

	/* {"type":"string","x-source":"inject","x-form-label":"Overlay opacity","x-form-type":"range","x-form-min":0,"x-form-max":100,"x-form-visible":"model.overlay == 'true' and model.backgroundtype == 'image'"} */
	public String getOverlayopacity() {
		return overlayopacity;
	}

	/* {"type":"string","x-source":"inject","x-form-label":"Background Color","x-form-type":"color","x-form-visible":"model.backgroundtype == 'color' or model.backgroundtype == 'gradient'"} */
	public String getBgcolor() {
		return bgcolor;
	}

	/* {"type":"string","x-source":"inject","x-form-label":"Color 2","x-form-type":"color","x-form-visible":"model.backgroundtype == 'gradient'"} */
	public String getColor2() {
		return color2;
	}

	/* {"type":"string","x-source":"inject","x-form-label":"Full Width","x-form-type":"materialswitch"} */
	public String getFullwidth() {
		return fullwidth;
	}

	/* {"type":"string","x-source":"inject","x-form-label":"Full Height","x-form-type":"materialswitch"} */
	public String getFullheight() {
		return fullheight;
	}

	/* {"type":"string","x-source":"inject","x-form-label":"Top Padding","x-form-type":"range","x-form-min":0,"x-form-max":120,"x-form-visible":"model.fullheight != 'true'"} */
	public String getToppadding() {
		return toppadding;
	}

	/* {"type":"string","x-source":"inject","x-form-label":"Bottom Padding","x-form-type":"range","x-form-min":0,"x-form-max":120,"x-form-visible":"model.fullheight != 'true'"} */
	public String getBottompadding() {
		return bottompadding;
	}


//GEN]

    //GEN[:CUSTOMGETTERS
    //GEN]

}
