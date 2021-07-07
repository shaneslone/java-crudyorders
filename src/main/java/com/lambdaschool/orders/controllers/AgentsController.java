package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Agent;
import com.lambdaschool.orders.services.AgentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agents")
public class AgentsController {
    @Autowired
    private AgentServices agentServices;
    // http://localhost:2019/agents/agent/{agentid}
    @GetMapping(value = "/agent/{agentid}", produces = "application/json")
    public ResponseEntity<?> findByAgentId(@PathVariable long agentid) {
        Agent agent = agentServices.findById(agentid);
        return new ResponseEntity<>(agent, HttpStatus.OK);
    }

    // http://localhost:2019/agents/agent/unassigned/{agentid}
    @DeleteMapping(value = "agent/unassigned/{agentid}")
    public ResponseEntity<?> deleteUnassignedAgentById(@PathVariable long agentid){
        agentServices.deleteUnassigned(agentid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
