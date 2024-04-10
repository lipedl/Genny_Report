/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class DAOAgent {

    Connection conn;
    PreparedStatement statement;

    public DAOAgent() {
        try {
            conn = DbConnection.getInstance();
        } catch (SQLException ex) {
            Logger.getLogger(DAOAgent.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro de conexão com banco");
        }
    }

    public ResultSet searchAgents(int id) {
        String sql;
        sql = "select agents_agent.id, clients_client.name, clients_site.name AS site, operating_system, "
                + "plat, hostname, public_ip, total_ram, disks, wmi_detail, "
                + "logged_in_username, description, monitoring_type FROM agents_agent \n"
                + "inner join clients_site ON agents_agent.site_id = clients_site.id \n"
                + "inner join clients_client ON clients_site.client_id = clients_client.id \n"
                + "WHERE clients_client.id = " + id + " order by clients_site.name";
        try {
            conn = DbConnection.getInstance();
            statement = conn.prepareStatement(sql);
            return statement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DAOAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultSet searchCustomFields(int id) {
        String sql;
        sql = "select core_customfield.name as field, agents_agentcustomfield.string_value as fieldValue \n"
                + "from agents_agentcustomfield\n"
                + "inner join core_customfield on core_customfield.id = agents_agentcustomfield.field_id\n"
                + "where agents_agentcustomfield.agent_id =" + id
                + " ORDER BY field DESC";
        try {
            conn = DbConnection.getInstance();
            statement = conn.prepareStatement(sql);
            return statement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DAOAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultSet getCustomFieldsRowCount(int agentId) {
        String sql;
        sql = "select count(*) from agents_agentcustomfield\n"
                + "where agents_agentcustomfield.agent_id =" + agentId;
        try {
            conn = DbConnection.getInstance();
            statement = conn.prepareStatement(sql);
            return statement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DAOAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
