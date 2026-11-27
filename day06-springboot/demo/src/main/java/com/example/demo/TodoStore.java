package com.example.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;

@Component
public class TodoStore {
    private final List<Todo> todos = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<Todo> findAll() {
        synchronized (todos) {
            return new ArrayList<>(todos);
        }
    }

    public Todo add(String title) {
        Long newId = idGenerator.getAndIncrement();
        Todo todo = new Todo(newId, title, false);
        todos.add(todo);
        return todo;
    }

    public Optional<Todo> toggleDone(Long id) {
        synchronized (todos) {
            for (Todo todo : todos) {
                if (todo.getId().equals(id)) {
                    todo.setDone(!todo.isDone());
                    return Optional.of(todo);
                }
            }
            return Optional.empty();
        }
    }

    public boolean delete(Long id) {
        return todos.removeIf(todo -> todo.getId().equals(id));
    }
}