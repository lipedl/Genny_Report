/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import controller.AgentController;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistencia.DAOAgent;
import view.TelaPrincipal_Consulta;

/**
 *
 * @author user
 */
public class AgentFactory {

    private Agent creatAgent(ResultSet rs) throws SQLException {
        Agent agt;
        AgentController agtController;
        String client, site, wmi, os, plat, hostname, publicIp, disks, monitoringType, user, description;
        int totalRam, agentId;
        try {

            agtController = new AgentController();
            client = rs.getString("name");
            site = rs.getString("site");
            user = rs.getString("logged_in_username");
            monitoringType = rs.getString("monitoring_type");
            description = rs.getString("description");
            os = rs.getString("operating_system");
            plat = rs.getString("plat");
            hostname = rs.getString("hostname");
            publicIp = rs.getString("public_ip");
            totalRam = rs.getInt("total_ram");
            disks = rs.getString("disks");
            wmi = rs.getString("wmi_detail");
            agentId = rs.getInt("id");
            agt = agtController.parseWMI(wmi, plat);
            agt.setDiskUsageList(agtController.parseDiskUsage(disks));
            agt.setParam(agentId, client, site, os, plat, hostname, publicIp, totalRam, user, monitoringType, description);
            agt.setCustomFields(buildCustomFieldSet(agentId));
            return agt;
        } catch (IOException ex) {
            Logger.getLogger(TelaPrincipal_Consulta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Agent> listAgents(int id) throws SQLException {
        Agent agt;
        ArrayList<Agent> agents;
        ResultSet rs;

        rs = dbSearchAgent(id);
        agents = new ArrayList<>();
        while (rs.next()) {
            agt = creatAgent(rs);
            agents.add(agt);
        }
        return agents;
    }

    public String[][] buildCustomFieldSet(int AgentId) {
        String[][] customFields;
        ResultSet rs;
        int l, c, rowCount;
        l = 0;
        c = 0;
        try {
            rowCount = getCustomFieldsRowCount(AgentId);
            if (rowCount > 0) {
                rs = dbSearchCustomFields(AgentId);
                customFields = new String[rowCount][2];

                while (rs.next()) {
                    customFields[l][c] = rs.getString("field");
                    c++;
                    customFields[l][c] = rs.getString("fieldValue");
                    l++;
                    c--;

                }
                return customFields;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AgentFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultSet dbSearchAgent(int clientId) {
        DAOAgent dAcess;
        dAcess = new DAOAgent();
        return dAcess.searchAgents(clientId);
    }

    public ResultSet dbSearchCustomFields(int agentId) {
        DAOAgent dAcess;
        dAcess = new DAOAgent();
        return dAcess.searchCustomFields(agentId);
    }

    public int getCustomFieldsRowCount(int agentId) throws SQLException {
        DAOAgent dAcess;
        ResultSet rs;
        dAcess = new DAOAgent();

        rs = dAcess.getCustomFieldsRowCount(agentId);
        rs.next();
        return rs.getInt("count");
    }
}
