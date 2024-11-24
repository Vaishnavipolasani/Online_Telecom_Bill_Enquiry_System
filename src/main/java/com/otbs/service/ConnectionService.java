package com.otbs.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otbs.model.Connection;
import com.otbs.repository.ConnectionRepository;

@Service
public class ConnectionService {

	@Autowired
	private ConnectionRepository connectionRepository;
	
	public ConnectionService(ConnectionRepository connectionRepository) {
		this.connectionRepository = connectionRepository;
	}
	
	
	//create connection
	public Connection activateConnection(Connection connection) {
		return connectionRepository.save(connection);
	}
	
	//upgrading the connection
	public Connection upgradePlan(Connection connection, int connectionId) {
		Connection existingConnection = connectionRepository.getById(connectionId);
		existingConnection.setCustomerObj(connection.getCustomerObj());
		existingConnection.setConnectionType(connection.getConnectionType());
		existingConnection.setNetworkType(connection.getNetworkType());
		existingConnection.setProcessingFee(connection.getProcessingFee());
		existingConnection.setOutletObj(connection.getOutletObj());
		existingConnection.setStatus(connection.getStatus());
		
		return connectionRepository.save(existingConnection);
	}
	
	//remove connection
	public boolean terminateConnection(int connectionId) {
		Connection connection = connectionRepository.getById(connectionId);
		connectionRepository.delete(connection);
		return true;
		
	}
	
	public List<Connection> getAllConnections(){
	   return connectionRepository.findAll()
			                       .stream()
			                       .collect(Collectors.toList());
		
	}
	
}
