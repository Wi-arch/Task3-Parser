package by.training.parser.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.rtf.RTFEditorKit;

import org.apache.log4j.Logger;

public class TextFileReader {

	private static final Logger LOGGER = Logger.getLogger(TextFileReader.class);
	private static final TextFileReader INSTANCE = new TextFileReader();
	private static final int START_POSITION = 0;
	private static final String RTF_FILE_EXTENSION = "rtf";

	private TextFileReader() {
	}

	public static TextFileReader getInstance() {
		return INSTANCE;
	}

	public String getTextFromFile(String fileName) {
		String result = null;
		try {
			File file = new File(getClass().getClassLoader().getResource(fileName).getFile());
			result = isRtfFileExtension(file.getName()) ? readTextFromRtfFile(file) : readTextFromFile(file);
		} catch (Exception e) {
			LOGGER.fatal("Cannot read source file", e);
			throw new RuntimeException("Cannot read file", e);
		}
		return result;
	}

	private String readTextFromRtfFile(File file) throws IOException, BadLocationException {
		InputStream inputStream = new FileInputStream(file);
		DefaultStyledDocument document = new DefaultStyledDocument();
		new RTFEditorKit().read(inputStream, document, START_POSITION);
		return new String(document.getText(START_POSITION, document.getLength()).getBytes(StandardCharsets.UTF_8));
	}

	private String readTextFromFile(File file) throws IOException {
		return new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
	}

	private boolean isRtfFileExtension(String file) {
		int index = file.lastIndexOf('.');
		String type = index > 0 ? file.substring(++index) : "";
		return type.equalsIgnoreCase(RTF_FILE_EXTENSION);
	}
}
