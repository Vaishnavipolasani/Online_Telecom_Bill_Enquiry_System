package com.otbs.service;

import com.otbs.model.Connection;
import com.otbs.model.ConnectionLog;
import com.otbs.repository.ConnectionLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConnectionLogService {

    @Autowired
    private ConnectionLogRepository connectionLogRepository;

    // Log the connection changes
    public void logConnectionChange(Connection connection, String changeType) {
        ConnectionLog connectionLog = new ConnectionLog();
        connectionLog.setConnectionObj(connection);
        connectionLog.setConnectionType(connection.getConnectionType());
        connectionLog.setNetworkType(changeType);
        connectionLog.setChangedate(LocalDate.now());

        connectionLogRepository.save(connectionLog);
    }

    // Fetch all logs
    public List<ConnectionLog> getAllLogs() {
        return connectionLogRepository.findAll();
    }

    // Fetch logs for a specific connection
    public List<ConnectionLog> getLogsByConnectionId(int connectionId) {
        return connectionLogRepository.findAll()
                .stream()
                .filter(log -> log.getConnectionObj().getConnectionId() == connectionId)
                .toList();
    }
}
