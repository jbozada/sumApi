package com.example.sumapi.service;

import com.example.sumapi.model.SumMessage;

public interface SumPublisher {
    void send(SumMessage message);
}
