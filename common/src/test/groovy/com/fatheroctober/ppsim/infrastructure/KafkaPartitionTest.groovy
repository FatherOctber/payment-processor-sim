package com.fatheroctober.ppsim.infrastructure

import spock.lang.Specification

import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class KafkaPartitionTest extends Specification {
    def MAX_PARTITIONS = 10

    def "multiple partitions test"() {
        given:
        KafkaPartition partition = new KafkaPartition(MAX_PARTITIONS)
        ExecutorService service = Executors.newFixedThreadPool(MAX_PARTITIONS)

        when:
        def futures = service.invokeAll(createTasks(partition))

        then:
        noExceptionThrown()

        expect:
        def partitions = new HashSet<Integer>()
        for (task in futures) {
            def res = task.get()
            partitions.add(res)
            res > 0 && res < 11
        }
        partitions.size() == MAX_PARTITIONS
    }

    def "single partition test"() {
        given:
        KafkaPartition partition = new KafkaPartition(1)
        ExecutorService service = Executors.newFixedThreadPool(MAX_PARTITIONS)

        when:
        def futures = service.invokeAll(createTasks(partition))

        then:
        noExceptionThrown()

        expect:
        def partitions = new HashSet<Integer>()
        for (task in futures) {
            def res = task.get()
            partitions.add(res)
            res == 1
        }
        partitions.size() == 1
    }

    def createTasks(KafkaPartition partition) {
        List<Callable<Integer>> incrementalTask = new ArrayList<>()
        partition.partitionCount.times {
            incrementalTask.add({ partition.next })
        }
        incrementalTask
    }
}
