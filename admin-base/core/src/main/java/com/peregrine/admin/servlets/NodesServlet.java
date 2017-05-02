package com.peregrine.admin.servlets;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.models.factory.ModelFactory;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component(
        service = Servlet.class,
        property = {
                Constants.SERVICE_DESCRIPTION + "=nodes servlet",
                Constants.SERVICE_VENDOR + "=headwire.com, Inc",
                ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES +"=api/admin/nodes"
        }
)
@SuppressWarnings("serial")
public class NodesServlet extends SlingSafeMethodsServlet {

    private final Logger log = LoggerFactory.getLogger(NodesServlet.class);

    @Reference
    ModelFactory modelFactory;

    @Override
    protected void doGet(SlingHttpServletRequest request,
                         SlingHttpServletResponse response) throws ServletException,
            IOException {

        String suffix = request.getRequestPathInfo().getSuffix();
        Map<String, String> params = convertSuffixToParams(suffix);
        if(params.containsKey("path")) {
            String path = params.get("path");
            String[] segments = path.split("/");
            log.debug("lookup path {}, {}", path, segments.length);
            JsonFactory jf = new JsonFactory();
            JsonGenerator jg = jf.createGenerator(response.getWriter());
            convertResource(jg, request.getResourceResolver(), segments, 1, path);
            jg.close();
        }
    }

    private void convertResource(JsonGenerator jg, ResourceResolver rs, String[] segments, int pos, String fullPath) throws IOException {
        String path = "";
        for(int i = 1; i <= pos; i++) {
            path += "/" + segments[i];
        }
        log.debug("looking up {}", path);
        Resource res = rs.getResource(path);
        jg.writeStartObject();
        jg.writeStringField("name",res.getName());
        jg.writeStringField("path",res.getPath());
        jg.writeStringField("resourceType",res.getValueMap().get("jcr:primaryType", String.class));
        jg.writeArrayFieldStart("children");
        Iterable<Resource> children = res.getChildren();
        for(Resource child : children) {
            if(fullPath.startsWith(child.getPath())) {
                convertResource(jg, rs, segments, pos+1, fullPath);
            } else {
                jg.writeStartObject();
                jg.writeStringField("name",child.getName());
                jg.writeStringField("path",child.getPath());
                jg.writeStringField("resourceType",child.getValueMap().get("jcr:primaryType", String.class));
                jg.writeEndObject();
            }
        }
        jg.writeEndArray();
        jg.writeEndObject();
    }

    private Map<String,String> convertSuffixToParams(String suffix) {
        HashMap<String, String> ret = new HashMap<String, String>();

        String[] params = suffix.split("//");
        for(int i = 0; i < params.length; i+=2) {
            ret.put(params[i].substring(1), params[i+1]);
        }

        return ret;
    }

}

