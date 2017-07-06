package com.peregrine.it.admin;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.peregrine.it.util.AbstractTest;
import com.peregrine.it.util.TestConstants;
import org.apache.sling.testing.clients.ClientException;
import org.apache.sling.testing.clients.SlingClient;
import org.apache.sling.testing.junit.rules.SlingInstanceRule;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;

import static com.peregrine.it.util.TestConstants.TEMPLATE_PATH;
import static com.peregrine.it.util.TestHarness.checkResourceByJson;
import static com.peregrine.it.util.TestHarness.createPage;
import static com.peregrine.it.util.TestHarness.createFolderStructure;
import static com.peregrine.util.PerConstants.JCR_CONTENT;
import static com.peregrine.util.PerConstants.JCR_PRIMARY_TYPE;
import static com.peregrine.util.PerConstants.JCR_TITLE;
import static com.peregrine.util.PerConstants.PAGE_CONTENT_TYPE;
import static com.peregrine.util.PerConstants.PAGE_PRIMARY_TYPE;
import static com.peregrine.util.PerConstants.SLING_RESOURCE_TYPE;
import static com.peregrine.util.PerUtil.TEMPLATE;

/**
 * Created by schaefa on 6/30/17.
 */
public class CreatePageIT
    extends AbstractTest
{
    public static final String ROOT_PATH = "/content/tests/create-page";

    private static final Logger logger = LoggerFactory.getLogger(CreatePageIT.class.getName());

    @ClassRule
    public static SlingInstanceRule slingInstanceRule = new SlingInstanceRule();

    @BeforeClass
    public static void setUpAll() {
        SlingClient client = slingInstanceRule.getAdminClient();
        try {
            client.doDelete(ROOT_PATH, null, null, 200);
        } catch(ClientException e) {
            logger.warn("Could not delete root path: '{}' -> ignore", ROOT_PATH, e);
        }
    }


    @Test
    public void testCreatePage() throws Exception {
        String rootFolderPath = ROOT_PATH + "/page-cp";
        String pageName = "newPage";
        SlingClient client = slingInstanceRule.getAdminClient();
        // Create the folder structure
        createFolderStructure(client, rootFolderPath);
        createPage(client, rootFolderPath, pageName, TEMPLATE_PATH, 200);
        JsonFactory jf = new JsonFactory();
        StringWriter writer = new StringWriter();
        JsonGenerator json = jf.createGenerator(writer);
        json.writeStartObject();
        json.writeStringField(JCR_PRIMARY_TYPE, PAGE_PRIMARY_TYPE);
        json.writeObjectFieldStart(JCR_CONTENT);
        json.writeStringField(JCR_PRIMARY_TYPE, PAGE_CONTENT_TYPE);
        json.writeStringField(SLING_RESOURCE_TYPE, "example/components/page");
        json.writeStringField(JCR_TITLE, pageName);
        json.writeStringField(TEMPLATE, TEMPLATE_PATH);
        json.writeEndObject();
        json.writeEndObject();
        json.close();

        checkResourceByJson(client, rootFolderPath + "/" + pageName, 2, writer.toString(), true);
    }

    @Override
    public Logger getLogger() {
        return logger;
    }
}
