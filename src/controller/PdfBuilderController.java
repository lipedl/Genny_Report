/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import estruturaDeDados.CustomDate;
import estruturaDeDados.DiskData;
import estruturaDeDados.DiskUsage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Agent;

/**
 *
 * @author user
 */
public class PdfBuilderController {

    ArrayList<Agent> agents;
    String clientName;
    String companyName;
    String headerTitle;

    public PdfBuilderController(ArrayList<Agent> agents) throws FileNotFoundException, IOException {
        this.agents = agents;
        clientName = agents.get(0).getClient();
        ObjectMapper mapper = new ObjectMapper();
        Reader input = new FileReader("PDFHeaderConfig.json");
        JsonNode json = mapper.readTree(input);
        companyName = json.get("companyName").asText();
        headerTitle = json.get("title").asText();
    }

    public void creatPdf() {
        Document document = new Document();
        String fileName, path;
        path = System.getProperty("user.home");
        fileName = path + "/Downloads/Inventario_Equipamentos_" + clientName + ".pdf";
        try {
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();
            Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, BaseColor.BLACK);

            createHeader(document, font);
            createStations(document, font);
            buildAgentsChapter(document);

            document.addCreationDate();
            document.addTitle("Inventário de Equipamentos");
            document.close();

        } catch (URISyntaxException | IOException | DocumentException ex) {
            Logger.getLogger(PdfBuilderController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void createHeader(Document document, Font font) throws URISyntaxException, BadElementException, IOException, URISyntaxException, URISyntaxException, DocumentException {
        LineSeparator ls;
        CustomDate date;
        Chunk chk;
        Font font2;

        date = new CustomDate();
        font.setSize(20);
        font.setFamily(FontFactory.TIMES_BOLD);
        Paragraph ph = new Paragraph(companyName + " - " + headerTitle, font);
        chk = new Chunk(new VerticalPositionMark());
        ph.add(chk);
        font2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, BaseColor.BLACK);
        chk = new Chunk(date.getFormatedDate(), font2);
        ph.add(chk);
        document.add(ph);
        font.setFamily(FontFactory.TIMES_ROMAN);
        ls = new LineSeparator();
        ls.setOffset(-5);
        document.add(ls);
        font.setSize(16);
        ph = new Paragraph("Cliente: " + clientName, font);
        document.add(ph);
    }

    private void createStations(Document document, Font font) throws DocumentException, BadElementException, IOException {
        int works, servers;
        Paragraph ph;
        LineSeparator ls;

        works = 0;
        servers = 0;
        for (Agent agent : agents) {
            if (agent.getMonitoringType().equals("server")) {
                servers += 1;
            } else {
                works += 1;
            }
        }

        font.setSize(12);
        ph = new Paragraph();
        ph.add(new Chunk("Servidores: " + servers, font));
        document.add(ph);
        ph = new Paragraph("Estações de Trabalho: " + works, font);
        document.add(ph);
        ls = new LineSeparator();
        ls.setOffset(-5);
        document.add(ls);
    }

    private void buildAgentsChapter(Document doc) throws DocumentException {
        Paragraph ph;
        LineSeparator ls;
        Font fontLabel, fontText;
        ls = new LineSeparator();
        fontLabel = FontFactory.getFont(FontFactory.TIMES_BOLD, 14, BaseColor.BLACK);
        fontText = FontFactory.getFont(FontFactory.TIMES, 14, BaseColor.BLACK);

        doc.add(new Paragraph("Relação de Equipamentos", fontLabel));
        ls.setOffset(-5);
        doc.add(ls);
        for (Agent agent : agents) {
            ph = new Paragraph("Nome do Equipamento: ", fontLabel);
            ph.add(new Chunk(agent.getHostname(), fontText));
            doc.add(ph);
            buildCustomFields(doc, agent.getCustomFields());
            if (agent.isWindows()) {
                buildWindowsAgents(doc, agent);
            } else {
                buildLinuxAgents(doc, agent);
            }
            buildDiskUsage(doc, agent);
            doc.add(ls);
        }
    }

    private void buildWindowsAgents(Document doc, Agent agent) throws DocumentException {
        PdfPTable table;
        String disksText;
        int size = 10;

        table = new PdfPTable(2);
        table.setWidthPercentage(101);

        disksText = "";
        for (DiskData disk : agent.getDiskDataList()) {
            disksText += disk.getDiskName() + " - " + disk.getStorage() + "\n   ";
        }
        fillCell(table, "Modelo", agent.getProductModel(), size);
        fillCell(table, "OS", agent.getOs(), size);
        fillCell(table, "Número de série", agent.getMobo().getSerialNumber(), size);
        fillCell(table, "Cpu", agent.getCpu().getCpuName(), size);
        fillCell(table, "Descrição", agent.getDescription(), size);
        fillCell(table, "Placa mãe", agent.getMobo().getManufacture()
                + " " + agent.getMobo().getBoardModel(), size);
        fillCell(table, "Site", agent.getSite(), size);
        fillCell(table, "RAM", String.valueOf(agent.getTotalRam()) + "GB", size);
        fillCell(table, "Ip local", agent.getLocalIp(), size);
        fillCell(table, "Processador Gráfico", agent.getGpu(), size);
        fillCell(table, "Ip público", agent.getPublicIp(), size);
        fillCell(table, "Discos", disksText, 9);
        doc.add(table);
    }

    private void buildLinuxAgents(Document doc, Agent agent) throws DocumentException {
        PdfPTable table;
        String disksText;
        int size = 10;

        table = new PdfPTable(2);
        table.setWidthPercentage(101);

        disksText = "";
        for (DiskData disk : agent.getDiskDataList()) {
            disksText += disk.getFullLabel() + "\n  ";
        }

        fillCell(table, "Modelo", agent.getProductModel(), size);
        fillCell(table, "OS", agent.getOs(), size);
        fillCell(table, "Descrição", agent.getDescription(), size);
        fillCell(table, "Cpu", agent.getCpu().getCpuName(), size);
        fillCell(table, "Site", agent.getSite(), size);
        fillCell(table, "RAM", String.valueOf(agent.getTotalRam()) + "GB", size);
        fillCell(table, "Ip local", agent.getLocalIp(), size);
        fillCell(table, "Processador Gráfico", agent.getGpu(), size);
        fillCell(table, "Ip público", agent.getPublicIp(), size);
        fillCell(table, "Discos", disksText, 9);
        doc.add(table);

    }

    private void buildDiskUsage(Document doc, Agent agent) throws DocumentException {
        Font fontLabel, fontText;
        Chunk glue;
        Paragraph ph;

        glue = new Chunk(new VerticalPositionMark());
        fontLabel = FontFactory.getFont(FontFactory.TIMES_BOLD, 10, BaseColor.BLACK);
        fontText = FontFactory.getFont(FontFactory.TIMES, 10, BaseColor.BLACK);

        if (!agent.getDiskUsageList().isEmpty()) {
            ph = new Paragraph("Uso de discos", fontLabel);
            ph.setAlignment(Paragraph.ALIGN_CENTER);
            doc.add(ph);
            for (DiskUsage disk : agent.getDiskUsageList()) {
                ph = new Paragraph("Unidade: ", fontLabel);
                ph.add(new Chunk(disk.getDevice(), fontText));
                ph.add(glue);
                ph.add(new Chunk("Total: ", fontLabel));
                ph.add(new Chunk(disk.getTotal(), fontText));
                ph.add(glue);
                ph.add(new Chunk("Ocupado: ", fontLabel));
                ph.add(new Chunk(disk.getUsed(), fontText));
                ph.add(glue);
                ph.add(new Chunk("Livre: ", fontLabel));
                ph.add(new Chunk(disk.getFree(), fontText));
                doc.add(ph);
            }
        }

    }

    private void fillCell(PdfPTable table, String label, String data, int size) {
        Font fontLabel, fontText;
        Paragraph ph;
        PdfPCell cell;
        int border = Rectangle.NO_BORDER;

        fontLabel = FontFactory.getFont(FontFactory.TIMES_BOLD, 10, BaseColor.BLACK);
        fontText = FontFactory.getFont(FontFactory.TIMES, size, BaseColor.BLACK);
        ph = new Paragraph(label + ": ", fontLabel);
        ph.add(new Chunk(data, fontText));
        cell = new PdfPCell();

        cell.addElement(ph);
        cell.setBorder(border);
//        cell.setFixedHeight(30);
//        cell.setBorderWidth(2);
        table.addCell(cell);
    }

    private void buildCustomFields(Document doc, String[][] customFields) throws DocumentException {
        Paragraph ph;
        Font fontLabel, fontText;
        int length;

        fontLabel = FontFactory.getFont(FontFactory.TIMES_BOLD, 9, BaseColor.BLACK);
        fontText = FontFactory.getFont(FontFactory.TIMES, 9, BaseColor.BLACK);
        try {
            if (!customFields[0][1].equals("")) {
                length = customFields.length;
                ph = new Paragraph();
                for (int l = 0; l < length; l++) {
                    ph.add(new Chunk(customFields[l][0] + ": ", fontLabel));
                    ph.add(new Chunk(customFields[l][1] + " ", fontText));
                }
                doc.add(ph);
            }
        } catch (NullPointerException ex) {
        }
    }
}
