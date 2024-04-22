package com.hrsh.mockdata;

import com.hrsh.model.Publisher;

import java.util.UUID;

public class MockPublisherData {
    public static Publisher createPublisher(String name) {
        Publisher publisher = new Publisher();
        publisher.setId(UUID.randomUUID());
        publisher.setName(name);
        return publisher;
    }
}
