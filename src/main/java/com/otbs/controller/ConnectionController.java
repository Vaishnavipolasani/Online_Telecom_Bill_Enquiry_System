package com.otbs.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otbs.exception.InvalidEntityException;
import com.otbs.model.Connection;
import com.otbs.service.ConnectionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/connection/")
public class ConnectionController {

    @Autowired
    private ConnectionService connectionService;

    public ConnectionController(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @PostMapping("activate")
    public Map<String, Object> activateConnection(@RequestBody @Valid Connection connection) throws InvalidEntityException {
        Connection savedConnection = connectionService.activateConnection(connection);
        savedConnection.setProcessingFee(100.00);

        // Prepare the response map with only required details
        Map<String, Object> response = new HashMap<>();
        response.put("connectionId", savedConnection.getConnectionId());
        response.put("message", "Connection successfully created!");

        return response;
    }


    @PutMapping("upgrade/{connectionId}")
    public Connection upgradePlan(@RequestBody Connection connection, @PathVariable int connectionId) throws InvalidEntityException {
        return connectionService.upgradePlan(connection, connectionId);
    }

    @DeleteMapping("{connectionId}")
    public boolean terminateConnection(@PathVariable int connectionId) throws InvalidEntityException {
        return connectionService.terminateConnection(connectionId);
    }

    @GetMapping("all")
    public List<Connection> getAllConnections() throws InvalidEntityException {
        return connectionService.getAllConnections();
    }

    @GetMapping("{connectionId}")
    public Connection getByConnectionId(@PathVariable int connectionId) throws InvalidEntityException {
        return connectionService.getByConnectionId(connectionId);
    }
    
    @PostMapping("calculate-fee")
    public Connection calculateProcessingFee(@RequestBody Connection connection) {
        connectionService.calculateProcessingFee(connection);
        return connection;
    }
}
