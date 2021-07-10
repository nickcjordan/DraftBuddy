package com.falifa.draftbuddy.ui.prep.scraper.webjson;

import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.FANTASYPROS_RANKINGS_HTML_FILE_PATH;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.ui.prep.scraper.webjson.model.ECRData;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class WebJsonExtractor {

	private static final String ECR_DATA = "var ecrData";

	public ECRData getECRDataFromFile() {
		List<String> lines = toLines(FileSystems.getDefault().getPath(FANTASYPROS_RANKINGS_HTML_FILE_PATH)).filter(x -> x.contains(ECR_DATA)).collect(Collectors.toList());
		String line = lines.get(0);
		String json = line.substring(line.indexOf("{"), line.lastIndexOf("}") + 1);
		return convertToECRData(json);
	}
	
	private ECRData convertToECRData(String json) {
		try {
			return new ObjectMapper().readValue(json, ECRData.class);
		} catch (Exception e) {
			return null;
		}
	}

	private Stream<String> toLines(Path path) {
        try {
            return Files.lines(path);
        } catch (IOException e) {
            return Stream.empty();
        }
    }
	
	

}
