package com.zoomInfo;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class Worker implements Callable {

    public static final String APOSTROPHE = "'";

    final private List<Document> documents;

    public Worker(List<Document> documents) {
        this.documents = documents;
    }

    @Override
    public Object call() throws Exception {
        return documents.stream()
                .filter(document -> document.getFirstName().contains(APOSTROPHE))
                .map(document -> document.getId())
                .collect(Collectors.toList());
    }
}

