package vedant.olahackathon;

import android.util.Log;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;

public class POJOToJSON {

    private static final String TAG = "PojoToJson";
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static JsonFactory jsonFactory = new JsonFactory();

    public static <T> Object fromJson(String jsonAsString, Class<T> pojoClass) {
        try {
            objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.readValue(jsonAsString, pojoClass);
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e.getMessage() + " :StackTrace : " + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

//    public static <T> Object fromJson(FileReader fr, Class<T> pojoClass)
//            throws IOException {
//        return objectMapper.readValue(fr, pojoClass);
//    }

    public static String toJson(Object pojo, boolean prettyPrint)
            throws IOException {
        StringWriter sw = new StringWriter();
        JsonGenerator jg = jsonFactory.createJsonGenerator(sw);
        if (prettyPrint) {
            jg.useDefaultPrettyPrinter();
        }
        objectMapper.writeValue(jg, pojo);
        return sw.toString();
    }

    public static void toJson(Object pojo, FileWriter fw, boolean prettyPrint)
            throws IOException {
        JsonGenerator jg = jsonFactory.createJsonGenerator(fw);
        if (prettyPrint) {
            jg.useDefaultPrettyPrinter();
        }
        objectMapper.writeValue(jg, pojo);
    }

}
