package com.aivideostudio.kdenlive.builder.repository;

import com.aivideostudio.kdenlive.builder.context.BuildContext;
import com.aivideostudio.kdenlive.model.Filter;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class FilterRepository {

    private final BuildContext context;

    private final Map<String, Filter> filters =
            new LinkedHashMap<>();

    public FilterRepository(BuildContext context) {
        this.context = context;
    }

    public Filter save(Filter filter) {

        filters.put(
                filter.getId(),
                filter);

        if (context.getProject().getTractor() != null) {

            context.getProject()
                    .getTractor()
                    .getFilters()
                    .add(filter);

        }

        return filter;

    }

    public Filter find(String id) {
        return filters.get(id);
    }

    public Collection<Filter> findAll() {
        return filters.values();
    }

}