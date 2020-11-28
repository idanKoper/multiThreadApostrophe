package com.zoomInfo;

import org.apache.commons.collections4.ListUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


class CalculateApostropheMultiThread {
    public static final String APOSTROPHE = "'";

    public List<Object> getAllIdsWithApostrophe(List<Document> documents) {

        int numberOfThreads = 10;
        ExecutorService pool = Executors.newFixedThreadPool(numberOfThreads);
        List<FutureTask> taskList = new ArrayList<>();

        List<List<Document>> documentsChunks = ListUtils.partition(documents, numberOfThreads);

        List<Object> resultDocuments = new ArrayList<>();

        IntStream.range(0, numberOfThreads).mapToObj(i -> new FutureTask(new Worker(documentsChunks.get(i)))).forEach(futureTask -> {
            taskList.add(futureTask);
            pool.execute(futureTask);
        });

        taskList.forEach(futureTask -> {
            try {
                resultDocuments.addAll((Collection<?>) futureTask.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });

        pool.shutdown();
        return resultDocuments;
    }

}
