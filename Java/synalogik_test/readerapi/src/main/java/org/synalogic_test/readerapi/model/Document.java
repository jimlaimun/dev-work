package org.synalogic_test.readerapi.model;

import java.util.ArrayList;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Document {
    private long id;
    private int wordCount;
    private double avgWordCount;
    private Map<Integer,Integer> wordLengths;
    private ArrayList<Integer> letterCountModes;
    private int modeOfwordLenths;


}
