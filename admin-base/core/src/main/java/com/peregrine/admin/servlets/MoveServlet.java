package com.peregrine.admin.servlets;

/*-
 * #%L
 * admin base - Core
 * %%
 * Copyright (C) 2017 headwire inc.
 * %%
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * #L%
 */

import com.fasterxml.jackson.core.JsonGenerator;
import com.peregrine.admin.replication.ReferenceLister;
import com.peregrine.util.PerUtil;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.peregrine.admin.servlets.ServletHelper.convertSuffixToParams;
import static com.peregrine.util.PerUtil.EQUALS;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static org.apache.sling.api.servlets.ServletResolverConstants.SLING_SERVLET_METHODS;
import static org.apache.sling.api.servlets.ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES;
import static org.apache.sling.api.servlets.ServletResolverConstants.SLING_SERVLET_SELECTORS;
import static org.osgi.framework.Constants.SERVICE_DESCRIPTION;
import static org.osgi.framework.Constants.SERVICE_VENDOR;

@Component(
    service = Servlet.class,
    property = {
        SERVICE_DESCRIPTION + EQUALS + "Peregrine: Move Resource Servlet",
        SERVICE_VENDOR + EQUALS + "headwire.com, Inc",
        SLING_SERVLET_METHODS + EQUALS + "POST",
        SLING_SERVLET_RESOURCE_TYPES + EQUALS + "api/admin/move",
        SLING_SERVLET_RESOURCE_TYPES + EQUALS + "api/admin/rename",
        SLING_SERVLET_SELECTORS + EQUALS + "json"
    }
)
@SuppressWarnings("serial")
/**
 * This servlet provides the ability to move a resource
 *
 *
 */
public class MoveServlet extends AbstractBaseServlet {

    public static final String BEFORE_TYPE = "before";
    public static final String AFTER_TYPE = "after";
    public static final String CHILD_TYPE = "child";

    private final Logger log = LoggerFactory.getLogger(MoveServlet.class);
    private List<String> acceptedTypes = Arrays.asList(BEFORE_TYPE, AFTER_TYPE, CHILD_TYPE);

    @Reference
    private ReferenceLister referenceLister;

    @Override
    Response handleRequest(Request request) throws IOException {
        String fromPath = request.getParameter("path");
        Resource from = PerUtil.getResource(request.getResourceResolver(), fromPath);
        String toPath = request.getParameter("to");
        if(request.getResource().getName().equals("move")) {
            String type = request.getParameter("type");
            Resource to = PerUtil.getResource(request.getResourceResolver(), toPath);
            if(from == null) {
                return new ErrorResponse().setHttpErrorCode(SC_BAD_REQUEST).setErrorMessage("Given Path does not yield a resource").setRequestPath(fromPath);
            } else if(!acceptedTypes.contains(type)) {
                return new ErrorResponse().setHttpErrorCode(SC_BAD_REQUEST).setErrorMessage("Type is not recognized: " + type).setRequestPath(fromPath);
            } else if(to == null) {
                return new ErrorResponse().setHttpErrorCode(SC_BAD_REQUEST).setErrorMessage("Target Path: " + toPath + " is not found").setRequestPath(fromPath);
            } else {
                // Look for Referenced By list before we updating
                List<com.peregrine.admin.replication.Reference> references = referenceLister.getReferencedByList(from);

                boolean addAsChild = CHILD_TYPE.equals(type);
                boolean addBefore = BEFORE_TYPE.equals(type);
                Resource target = addAsChild ? to : to.getParent();
                Resource newResource = request.getResourceResolver().move(from.getPath(), target.getPath());
                // Reorder if needed
                if(!addAsChild) {
                    Node toNode = target.adaptTo(Node.class);
                    if(addBefore) {
                        try {
                            toNode.orderBefore(newResource.getName(), to.getName());
                        } catch(RepositoryException e) {
                            return new ErrorResponse().setHttpErrorCode(SC_BAD_REQUEST).setErrorMessage("New Resource: " + newResource.getPath() + " could not be reordered")
                                .setRequestPath(fromPath).setException(e);
                        }
                    } else {
                        try {
                            NodeIterator i = toNode.getNodes();
                            Node nextNode = null;
                            while(i.hasNext()) {
                                Node child = i.nextNode();
                                if(child.getName().equals(to.getName())) {
                                    if(i.hasNext()) {
                                        nextNode = i.nextNode();
                                    }
                                    break;
                                }
                            }
                            if(nextNode != null) {
                                toNode.orderBefore(newResource.getName(), nextNode.getName());
                            }
                        } catch(RepositoryException e) {
                            return new ErrorResponse().setHttpErrorCode(SC_BAD_REQUEST).setErrorMessage("New Resource: " + newResource.getPath() + " could not be reordered (after)").setRequestPath(fromPath).setException(e);
                        }
                    }
                }
                // Update the references
                for(com.peregrine.admin.replication.Reference reference : references) {
                    Resource propertyResource = reference.getPropertyResource();
                    ModifiableValueMap properties = PerUtil.getModifiableProperties(propertyResource);
                    if(properties.containsKey(reference.getPropertyName())) {
                        properties.put(reference.getPropertyName(), newResource.getPath());
                    }
                }
                request.getResourceResolver().commit();
                JsonResponse answer = new JsonResponse();
                JsonGenerator json = answer.getJson();
                json.writeStringField("sourceName", from.getName());
                json.writeStringField("sourcePath", from.getPath());
                json.writeStringField("tagetName", newResource.getName());
                json.writeStringField("targetPath", newResource.getPath());
                return answer;
            }
        } else if(request.getResource().getName().equals("rename")) {
            if(from == null) {
                return new ErrorResponse().setHttpErrorCode(SC_BAD_REQUEST).setErrorMessage("Given Path does not yield a resource").setRequestPath(fromPath);
            } else if(toPath == null || toPath.isEmpty()) {
                return new ErrorResponse().setHttpErrorCode(SC_BAD_REQUEST).setErrorMessage("Given New Name (to) is not provided").setRequestPath(fromPath);
            } else if(toPath.indexOf('/') >= 0) {
                return new ErrorResponse().setHttpErrorCode(SC_BAD_REQUEST).setErrorMessage("Given New Name: " + toPath + " cannot have a slash").setRequestPath(fromPath);
            } else {
                String newPath = from.getParent().getPath() + "/" + toPath;
                log.info("Rename from: '{}' to: '{}'", from.getPath(), newPath);
                Node fromNode = from.adaptTo(Node.class);
                try {
                    // Before the rename obtain the next node sibling and then after the move order the renamed node before its next sibling
                    Node parent = fromNode.getParent();
                    Node next = null;
                    if(parent != null) {
                        NodeIterator i = parent.getNodes();
                        while(i.hasNext()) {
                            Node child = i.nextNode();
                            if(child.getName().equals(fromNode.getName())) {
                                if(i.hasNext()) {
                                    next = i.nextNode();
                                    break;
                                }
                            }
                        }
                    }
                    fromNode.getSession().move(from.getPath(), newPath);
                    if(next != null) {
                        parent.orderBefore(toPath, next.getName());
                    }
                    fromNode.getSession().save();
                } catch(RepositoryException e) {
                    return new ErrorResponse().setHttpErrorCode(SC_BAD_REQUEST).setErrorMessage("Rename Failed: " + e.getMessage()).setRequestPath(fromPath).setException(e);
                }
                JsonResponse answer = new JsonResponse();
                JsonGenerator json = answer.getJson();
                json.writeStringField("sourceName", from.getName());
                json.writeStringField("sourcePath", from.getPath());
                json.writeStringField("tagetName", toPath);
                json.writeStringField("targetPath", newPath);
                return answer;
            }
        } else {
            return new ErrorResponse().setHttpErrorCode(SC_BAD_REQUEST).setErrorMessage("Unknown request: " + request.getResource().getName());
        }
    }
}