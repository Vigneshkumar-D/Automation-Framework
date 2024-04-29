package org.automationframework.Libraries;
// Practice Class
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileInputValidator {
    private final List<String> inputErrList = new ArrayList<>();

    public void validateFileInput(File file, String labelText) {
        if (file == null || !file.exists()) {
            inputErrList.add("Input Data Error: File does not exist for " + labelText + ".");
            return;
        }
        String fileName = file.getName();
        String fileType = fileName.substring(fileName.lastIndexOf('.') + 1);

        long fileSizeInBytes = file.length();
        long fileSizeInKB = fileSizeInBytes / 1024;
        long fileSizeInMB = fileSizeInKB / 1024;
        List<String> allowedFileTypes = Arrays.asList("xlsx", "txt", "jpeg", "jpg", "png", "gif");
        if (fileSizeInMB > 5) {
            inputErrList.add("Input Data Error: File size exceeds the maximum allowed size (5 MB) for " + labelText + ".");
        }if(!allowedFileTypes.contains(fileType.toLowerCase())){
            inputErrList.add("Input Data Error: The uploaded file must be in Excel format (with the .xlsx extension) for " + labelText + ".");
        }
    }

    public static void main(String[] args) {
        FileInputValidator validator = new FileInputValidator();

        File inputFile = new File("C:/Users/vinay/Downloads/389196.jpg");
        System.out.println(inputFile.getTotalSpace());
        validator.validateFileInput(inputFile, "Sample File");
    }
}
