package spec.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.swagger.handler.ValidatorController;
import io.swagger.oas.inflector.models.RequestContext;
import io.swagger.oas.inflector.models.ResponseContext;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ValidatorTest {

    private static final String IMAGE = "image";
    private static final String PNG = "png";
    private static final String VALID_YAML = "/valid_oas3.yaml";
    private static final String INVALID_YAML ="/invalid_oas3.yaml";
    private static final String VALID_IMAGE = "valid.png";
    private static final String INVALID_IMAGE = "invalid.png";
    private static final String APPLICATION = "application";
    private static final String JSON = "json";
    private static final String INFO_MISSING = "attribute info is missing";


    @Test
    public void testValidateValidJsonSchema() throws Exception {


    }

    @Test
    public void testValidateInvalidJsonSchema() throws Exception {


    }



    @Test
    public void testValidateValidSpecByUrl() throws Exception {


    }

    @Test
    public void testValidateInvalidSpecByUrl() throws Exception {


    }

    @Test
    public void testValidateInvalidSpecByContent() throws Exception {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        final JsonNode rootNode = mapper.readTree(Files.readAllBytes(java.nio.file.Paths.get(getClass().getResource(INVALID_YAML).toURI())));
        ValidatorController validator = new ValidatorController();
        ResponseContext response = validator.validateByContent(new RequestContext(), rootNode);

        Assert.assertEquals(IMAGE, response.getContentType().getType());
        Assert.assertEquals(PNG, response.getContentType().getSubtype());
        InputStream entity = (InputStream)response.getEntity();
        InputStream valid = this.getClass().getClassLoader().getResourceAsStream(INVALID_IMAGE);

        Assert.assertTrue( validateEquals(entity,valid) == true);


    }


    @Test
    public void testValidateValidSpecByContent() throws Exception {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        final JsonNode rootNode = mapper.readTree(Files.readAllBytes(java.nio.file.Paths.get(getClass().getResource(VALID_YAML).toURI())));
        ValidatorController validator = new ValidatorController();
        ResponseContext response = validator.validateByContent(new RequestContext(), rootNode);

        Assert.assertEquals(IMAGE, response.getContentType().getType());
        Assert.assertEquals(PNG, response.getContentType().getSubtype());
        InputStream entity = (InputStream)response.getEntity();
        InputStream valid = this.getClass().getClassLoader().getResourceAsStream(VALID_IMAGE);


        Assert.assertTrue( validateEquals(entity,valid) == true);
    }



    @Test
    public void testDebugValidSpecByUrl() throws Exception {


    }

    @Test
    public void testDebugInvalidSpecByUrl() throws Exception {


    }

    @Test
    public void testDebugInvalidSpecByContent() throws Exception {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        final JsonNode rootNode = mapper.readTree(Files.readAllBytes(java.nio.file.Paths.get(getClass().getResource(INVALID_YAML).toURI())));
        ValidatorController validator = new ValidatorController();
        ResponseContext response = validator.reviewByContent(new RequestContext(), rootNode);

        Assert.assertEquals(APPLICATION, response.getContentType().getType());
        Assert.assertEquals(JSON, response.getContentType().getSubtype());
        List messages = (ArrayList) response.getEntity();
        Assert.assertTrue(messages.get(0).equals(INFO_MISSING));

    }

    @Test
    public void testDebugValidSpecByContent() throws Exception {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        final JsonNode rootNode = mapper.readTree(Files.readAllBytes(java.nio.file.Paths.get(getClass().getResource(VALID_YAML).toURI())));
        ValidatorController validator = new ValidatorController();
        ResponseContext response = validator.reviewByContent(new RequestContext(), rootNode);

        Assert.assertEquals(APPLICATION, response.getContentType().getType());
        Assert.assertEquals(JSON, response.getContentType().getSubtype());
        List messages = (ArrayList) response.getEntity();
        Assert.assertTrue(messages == null);

    }



    private boolean validateEquals(InputStream image1, InputStream image2) throws IOException {

        int i =  image1.read();
        while (-1 != i)
        {
            int y = image2.read();
            if (i != y)
            {
                return false;
            }
            i = image1.read();
        }

        int y = image2.read();
        return(y == -1);
    }


}
