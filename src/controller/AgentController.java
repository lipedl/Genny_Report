package controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import estruturaDeDados.Cpu;
import estruturaDeDados.DiskData;
import estruturaDeDados.DiskUsage;
import estruturaDeDados.Memory;
import estruturaDeDados.MotherBoard;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import model.Agent;

/**
 *
 * @author user
 */
public class AgentController {

    Gson gson;

    public AgentController() {
        gson = new Gson();
    }

    public ArrayList<DiskUsage> parseDiskUsage(String json) {
        Type diskListType = new TypeToken<ArrayList<DiskUsage>>() {
        }.getType();
        ArrayList<DiskUsage> disks;
        disks = gson.fromJson(json, diskListType);
        return disks;
    }

    public ArrayList<DiskData> parseDiskData(JsonNode node, String plat) {
        JsonNode diskNode, fieldNode, keyNode;
        ArrayList<DiskData> disks;
        String diskName;
        String storage;

        disks = new ArrayList<>();
        if (plat.equals("windows")) {
            diskNode = node.get("disk");
            for (int i = 0; i < diskNode.size(); i++) {
                fieldNode = diskNode.get(i);
                keyNode = fieldNode.get(0);
                diskName = keyNode.get("Model").asText();
                storage = formatReadableByte(keyNode.get("Size").asLong());

                disks.add(new DiskData(storage, diskName));
            }
            if (!disks.isEmpty()) {
                return disks;
            } else {
                return null;
            }
        } else {
            diskNode = node.get("disks");
            for (int i = 0; i < diskNode.size(); i++) {
                disks.add(new DiskData(diskNode.get(i).asText()));
            }
            return disks;
        }
    }

    public Agent parseWMI(String wmi, String plat) throws IOException {
        Agent agent;
        ArrayList<DiskData> disks;
        Cpu cpu;
        MotherBoard mobo;
        ArrayList<Memory> memorys;
        ObjectMapper objMapper;
        JsonNode node;
        String model, gpu, localIp;

        objMapper = new ObjectMapper();
        node = getJsonNode(objMapper, wmi);
        if (plat.equals("windows")) {
            disks = parseDiskData(node, plat);
            memorys = parseMemorys(node);
            cpu = parseCpu(node, plat);
            model = parseModel(node, plat);
            gpu = parseGpu(node, plat);
            mobo = parseMobo(node);
            localIp = parseIp(node, plat);
            agent = new Agent(cpu, gpu, localIp, model, mobo, memorys, disks);
            return agent;
        } else {
            disks = parseDiskData(node, plat);
            cpu = parseCpu(node, plat);
            gpu = parseGpu(node, plat);
            localIp = parseIp(node, plat);
            model = parseModel(node, plat);
            agent = new Agent(cpu, gpu, model, localIp, disks);
            return agent;
        }
    }

    private Cpu parseCpu(JsonNode node, String plat) {
        Cpu cpu;
        JsonNode cpuNode, fieldNode, arrayNode;
        if (plat.equals("windows")) {
            cpuNode = node.get("cpu");
            arrayNode = cpuNode.get(0);
            fieldNode = arrayNode.get(0);
            cpu = new Cpu(fieldNode.get("Name").asText(),
                    fieldNode.get("NumberOfLogicalProcessors").asInt());

            return cpu;
        } else {
            cpuNode = node.get("cpus");
            cpu = new Cpu(cpuNode.get(0).asText(), 0);
            return cpu;
        }
    }

    private ArrayList<Memory> parseMemorys(JsonNode node) {
        ArrayList<Memory> memorys;
        Memory mem;
        int size;
        JsonNode memNode, fieldNode, arrayNode;

        memorys = new ArrayList<>();
        memNode = node.get("mem");
        size = node.get("mem").size();

        for (int i = 0; i < size; i++) {
            arrayNode = memNode.get(i);
            fieldNode = arrayNode.get(0);
            mem = new Memory(fieldNode.get("Capacity").asLong(),
                    fieldNode.get("Speed").asText(),
                    fieldNode.get("Manufacturer").asText());
            memorys.add(mem);
        }
        return memorys;
    }

    private String parseModel(JsonNode node, String plat) {
        JsonNode modelNode, fieldNode, arrayNode;
        if (plat.equals("windows")) {
            modelNode = node.get("comp_sys");
            arrayNode = modelNode.get(0);
            fieldNode = arrayNode.get(0);
            return fieldNode.get("Model").asText();
        } else {
            return node.get("make_model").asText();
        }
    }

    private String parseGpu(JsonNode node, String plat) {
        JsonNode gpuNode, fieldNode, arrayNode;
        String gpu;
        if (plat.equals("windows")) {
            gpuNode = node.get("graphics");
            arrayNode = gpuNode.get(0);
            fieldNode = arrayNode.get(0);
            return fieldNode.get("Name").asText();
        } else {
            gpu = "";
            gpuNode = node.get("gpus");
            for (int i = 0; i < gpuNode.size(); i++) {
                gpu += gpuNode.get(i).asText();
            }
            return gpu;
        }
    }

    private MotherBoard parseMobo(JsonNode node) {
        JsonNode moboNode, fieldNode, arrayNode;

        moboNode = node.get("base_board");
        arrayNode = moboNode.get(0);
        fieldNode = arrayNode.get(0);
        return new MotherBoard(fieldNode.get("Manufacturer").asText(),
                fieldNode.get("Product").asText(),
                fieldNode.get("SerialNumber").asText());
    }

    private String parseIp(JsonNode node, String plat) {
        JsonNode networkNode, fieldNode, keyNode;
        String ip;

        if (plat.equals("windows")) {
            networkNode = node.get("network_config");
            for (int i = 0; i < networkNode.size(); i++) {
                fieldNode = networkNode.get(i);
                fieldNode = fieldNode.get(0);
                if (fieldNode.has("IPAddress")) {
                    try {
                        keyNode = fieldNode.get("IPAddress");
                        ip = keyNode.get(0).asText();
                        return ip;
                    } catch (NullPointerException ex) {
                    }
                }
            }
        } else {
            networkNode = node.get("local_ips");
            ip = "";
            for (int i = 0; i < networkNode.size(); i++) {
                ip += networkNode.get(i).asText() + ",\n";
            }
            return ip;
        }
        return null;
    }

    private JsonNode getJsonNode(ObjectMapper objM, String wmi) throws IOException {
        return objM.readTree(wmi);
    }

    public String formatReadableByte(long bytes) {
        long absB = bytes == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(bytes);
        if (absB < 1024) {
            return bytes + " B";
        }
        long value = absB;
        CharacterIterator ci = new StringCharacterIterator("KMGTPE");
        for (int i = 40; i >= 0 && absB > 0xfffccccccccccccL >> i; i -= 10) {
            value >>= 10;
            ci.next();
        }
        value *= Long.signum(bytes);
        return String.format("%.1f %cB", value / 1024.0, ci.current());
    }
}
