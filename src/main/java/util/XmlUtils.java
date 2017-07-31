package util;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.FileInputStream;

public class XmlUtils {
    public static boolean xsdValidate(String xmlPath, String xsdPath) {
        try {
            FileInputStream xmlFis = new FileInputStream(xmlPath);
            FileInputStream xsdFis = new FileInputStream(xsdPath);
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(xsdFis));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlFis));
            return true;
        } catch(Exception e) {
            return false;
        }
    }
}
