package org.automationframework.Data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

public class JSONDataReader {
    public List<HashMap<String, String>> getJsonDataListOfObjects(String fileName) throws IOException {
        String filePath = System.getProperty("user.dir")+"/src/test/java/org/automationframework/Data/"+fileName+".json";
        System.out.println(filePath);
        String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {});
    }
    public HashMap<String, HashMap<String, String>>  getJsonDataObjectsOfObjects(String fileName) throws IOException {
        String filePath = System.getProperty("user.dir")+"/src/test/java/org/automationframework/Data/"+fileName+".json";
        System.out.println(filePath);
        String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonContent, new TypeReference<HashMap<String, HashMap<String, String>>>() {});
    }
}
