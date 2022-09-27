package net.shyshkin.war.txttoelasticsearch.partitioner;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class RangePartitioner implements Partitioner {

    private final int expectedMaxSize;

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {

        Map<String, ExecutionContext> result = new HashMap<>();

        int range = expectedMaxSize / gridSize + 1;
        int fromId = 0;
        int toId = range;

        for (int i = 1; i <= gridSize; i++) {
            var executionContext = new ExecutionContext();
            executionContext.put("minValue", fromId);
            executionContext.put("maxValue", toId);

            result.put("partition" + i, executionContext);
            fromId = toId;
            toId += range;
        }

        return result;
    }
}
