package com.zoomInfo;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class test {

    public static final int NUMBER_ENTITIES = 100;
    public static final String APOSTROPHE = "'";

    private Random random = new Random();

    @Test
    public void testResult() throws Exception {
        CalculateApostropheMultiThread calculateApostropheMultiThread = new CalculateApostropheMultiThread();
        List<Document> documents = new ArrayList<>(NUMBER_ENTITIES);
        initialsList(documents);
        Assert.assertEquals(calculateApostropheMultiThread.getAllIdsWithApostrophe(documents),getIdsWithApostrophe(documents));
    }

    private void initialsList(List<Document> documents) {
        IntStream.range(0, 100).mapToObj(i -> new Document(getRandomLong(), generateRandomString(10), generateRandomString(10), getRandomDouble())).forEach(documents::add);
    }

    private double getRandomDouble() {
        return random.nextDouble();
    }

    public Long getRandomLong() {
        return random.nextLong() & Long.MAX_VALUE;
    }

    private String generateRandomString(int length) {
        final String AB = "0'123456789AB'CDEFGHIJKLMNOPQRSTUVWXYZabcd'efghijk'lmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(AB.charAt(random.nextInt(AB.length())));
        }
        return sb.toString();
    }

    private List<Long> getIdsWithApostrophe(List<Document> documents){
        return documents.stream()
                .filter(document -> document.getFirstName().contains(APOSTROPHE))
                .map(document -> document.getId())
                .collect(Collectors.toList());
    }

}
