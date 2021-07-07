package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Agent;

public interface AgentServices {
    Agent findById(long agentid);
    void deleteUnassigned(long agentid);
    Agent save(Agent agent);
}
