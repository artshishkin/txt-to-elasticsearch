package net.shyshkin.war.txttoelasticsearch.listener;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@Component
public class ZipOperationsExecutionListener implements JobExecutionListener {

    private final static String UNZIPPED_FILES_LOCATION = "data/output";

    @Override
    public void beforeJob(JobExecution jobExecution) {
        String zipFileName = jobExecution.getJobParameters().getString("zipFile");
        Objects.requireNonNull(zipFileName);
        ZipFile zipFile = new ZipFile(zipFileName);

        try {
            zipFile.extractAll(UNZIPPED_FILES_LOCATION);
        } catch (ZipException e) {
            throw new RuntimeException(e);
        }
        try {
            Path path = Files.list(Path.of(UNZIPPED_FILES_LOCATION))
                    .findFirst()
                    .get();
            jobExecution.getExecutionContext().putString("inputFile", path.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        String inputFile = jobExecution.getExecutionContext().getString("inputFile");
        try {
            Files.deleteIfExists(Path.of(inputFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
