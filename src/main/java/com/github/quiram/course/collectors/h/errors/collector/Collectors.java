package com.github.quiram.course.collectors.h.errors.collector;

import java.util.List;
import java.util.stream.Collector;

import static java.lang.String.format;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

public class Collectors {
    public static <ParentType, ResultType extends ParentType> Collector<ParentType, ?, List<ResultType>> toListOf(Class<ResultType> resultType) {
        return collectingAndThen(
                toList(),
                list -> {
                    final List<ParentType> offendingItems = list.stream()
                            .filter(p -> !resultType.isAssignableFrom(p.getClass()))
                            .collect(toList());

                    if (offendingItems.isEmpty()) {
                        return list.stream()
                                .map(resultType::cast)
                                .collect(toList());
                    }

                    throw new RuntimeException(format("The following items cannot be cast to %s: %s", resultType.getSimpleName(), offendingItems));
                });
    }
}
