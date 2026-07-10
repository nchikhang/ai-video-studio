package com.aivideostudio.kdenlive.builder.repository;

import com.aivideostudio.kdenlive.builder.context.BuildContext;
import com.aivideostudio.kdenlive.model.Transition;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class TransitionRepository {

    private final BuildContext context;

    private final Map<String, Transition> transitions =
            new LinkedHashMap<>();

    public TransitionRepository(BuildContext context) {
        this.context = context;
    }

    public Transition save(Transition transition) {

        transitions.put(
                transition.getId(),
                transition);

        if (context.getProject().getTractor() != null) {

            context.getProject()
                    .getTractor()
                    .getTransitions()
                    .add(transition);

        }

        return transition;

    }

    public Transition find(String id) {
        return transitions.get(id);
    }

    public Collection<Transition> findAll() {
        return transitions.values();
    }

}