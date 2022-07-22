package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.util.*;

public class HistoryListener implements Listener, HistoryReader {

    final Map<Long,Message> history;

    public HistoryListener() {
        history = new TreeMap<>();
    }

    @Override
    public void onUpdated(Message msg) {
      history.put(msg.getId(), (Message) msg.clone());
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.of(history.get(id));
    }
}
