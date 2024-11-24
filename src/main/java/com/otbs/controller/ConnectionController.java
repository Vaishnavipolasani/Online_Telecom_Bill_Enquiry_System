package com.otbs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otbs.model.Connection;
import com.otbs.service.ConnectionService;

@RestController
@RequestMapping("/connection/")
public class ConnectionController {
	
	@Autowired
	private ConnectionService connectionService;
	
	public ConnectionController(ConnectionService connectionService) {
		this.connectionService = connectionService;
	}
	
	@PostMapping("activate")
	public Connection activateConnection(@RequestBody Connection connection) {
		return connectionService.activateConnection(connection);
	}
	
	
	@PutMapping("upgrade/{connectionId}")
	public Connection upgradePlan(@RequestBody Connection connection,@PathVariable int connectionId) {
		return connectionService.upgradePlan(connection, connectionId);
	}

	@DeleteMapping("{connectionId}")
	public boolean terminateConnection(@PathVariable int connectionId) {
		return connectionService.terminateConnection(connectionId);
	}

	@GetMapping("all")
	public List<Connection> getAllConnections(){
		return connectionService.getAllConnections();
	}



}
