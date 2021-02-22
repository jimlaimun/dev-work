package org.synalogic_test.readerapi;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.synalogic_test.readerapi.countingUtils.CountingUtilsService;
import org.synalogic_test.readerapi.fileuploaddownload.FileStorageService;
import org.synalogic_test.readerapi.model.Document;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class ReaderapiApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	CountingUtilsService counterUtils;

	@Autowired
    FileStorageService fileStorageService;

	@Test
	@Order(1)
	public void processBaseDocument() throws Exception
	{
		String exampleText = "Hello world & good morning. The date is 18/05/2016";
		MockMultipartFile testFile = new MockMultipartFile("file", "filename.txt", "text/plain", exampleText.getBytes());
		
		this.mockMvc
		.perform(MockMvcRequestBuilders.multipart("/synalogik/reader/processdoc").file(testFile))
		.andExpect(status().is(200))
		.andDo(print())
		.andExpect(content().string(containsString("Word count= 9")));
		
	}

	@Test
	@Order(2)
	public void testValidWords()
	{
		String[] testArray = {"this","is",",","not","a","word","&","not","A!$","sentence"};
		String[] expectStrings = {"this","is","not","a","word","&","not","A","sentence"};
		ArrayList<String> output = counterUtils.isValidWords(testArray);

		assertArrayEquals(expectStrings, output.toArray());
	}

	@Test
	@Order(3)
	public void testDocumentWordLengthModes()
	{
		Integer[] expectedModes = {3,4,7};

		Map<Integer,Integer> wordLengths = new HashMap<>();
		wordLengths.put(1, 1);
        wordLengths.put(2, 1);
        wordLengths.put(3, 2);
        wordLengths.put(4, 2);
        wordLengths.put(5, 1);
        wordLengths.put(7, 2);
        wordLengths.put(10, 0);
		
		Document testDoc = Document.builder()
		.wordCount(9)
		.avgWordCount(4.556)
		.wordLengths(wordLengths)
		.build();

		counterUtils.getModes(wordLengths, testDoc);
		assertEquals(2, testDoc.getModeOfwordLenths());
		assertArrayEquals(expectedModes,testDoc.getLetterCountModes().toArray());
	}

	@Test
	@Order(4)
	public void testCalculatingAverageLength()
	{
		int sampleTotalWordCount = 16;
		int sampleTotalCharacterCount = 123;

		double result = counterUtils.calculateWordLength(sampleTotalCharacterCount, sampleTotalWordCount);
		double expectedResult = 7.688;  //7.6875 rounded up to 3 decimals
		assertEquals(expectedResult, result);
	}

	@Test
	@Order(5)
	public void testFileStorage()
	{
		String exampleText = "Hello world & good morning. The date is 18/05/2016";
		MockMultipartFile testFile = new MockMultipartFile("file", "unitTest.txt", "text/plain", exampleText.getBytes());

		String actualLocation = fileStorageService.storeFile(testFile);
		String expectedLocation = "unitTest.txt";

		assertEquals(expectedLocation, actualLocation);

	}

}
