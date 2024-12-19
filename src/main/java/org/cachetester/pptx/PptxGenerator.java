package org.cachetester.pptx;

import org.apache.poi.sl.usermodel.TableCell;
import org.apache.poi.sl.usermodel.TextParagraph;
import org.apache.poi.sl.usermodel.VerticalAlignment;
import org.apache.poi.xslf.usermodel.*;
import org.cachetester.utils.PropertiesConstants;
import org.cachetester.utils.PropertiesUtils;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class PptxGenerator {

    public PptxGenerator() {}

    public void generatePresentation(long totalExecutions, Map<String, Double> metricsContent) throws IOException {
        try (XMLSlideShow ppt = new XMLSlideShow()) {
            XSLFSlide slide = ppt.createSlide();

            XSLFTextBox title = slide.createTextBox();
            title.setAnchor(new Rectangle(250, 20, 400, 200));
            title.setText("Couchbase Test Overview");

            XSLFTextBox executions = slide.createTextBox();
            executions.setAnchor(new Rectangle(170, 60, 400, 200));
            executions.setText("Total executions: " + totalExecutions + ", working threads: " + PropertiesUtils.getProperty(PropertiesConstants.THREADS));

            XSLFTable table = slide.createTable();
            table.setAnchor(new Rectangle(70, 100, 1000, 1000));
            prepareTableHeaders(table);
            fillTableWithMetricsData(metricsContent, table);

            table.setColumnWidth(0, 300);
            table.setColumnWidth(1, 300);

            try (FileOutputStream out = new FileOutputStream(PropertiesUtils.getProperty(PropertiesConstants.PRESENTATION_FILE_PATH))) {
                ppt.write(out);
            }
            System.out.println("Presentation generated: CouchbaseMetrics.pptx");
        }
    }

    private void prepareTableHeaders(XSLFTable table) {
        XSLFTableRow headerRow = table.addRow();
        headerRow.setHeight(40);
        XSLFTableCell firstHeader = headerRow.addCell();
        firstHeader.setText("Metric");
        formatCell(firstHeader);
        XSLFTableCell secondHeader = headerRow.addCell();
        secondHeader.setText("Value");
        formatCell(secondHeader);
    }

    private void fillTableWithMetricsData(Map<String, Double> metricsContent, XSLFTable table) {
        metricsContent.forEach((key, value) -> {
            XSLFTableRow row = table.addRow();
            XSLFTableCell cell1 = row.addCell();
            cell1.setText(key);
            formatCell(cell1);

            XSLFTableCell cell2 = row.addCell();
            cell2.setText(String.format("%.2f", value));
            formatCell(cell2);
        });
    }

    private void formatCell(XSLFTableCell cellRef) {
        cellRef.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cellRef.getTextParagraphs().get(0).setTextAlign(TextParagraph.TextAlign.CENTER);
        cellRef.setBorderColor(TableCell.BorderEdge.bottom, Color.BLACK);
        cellRef.setBorderColor(TableCell.BorderEdge.top, Color.BLACK);
        cellRef.setBorderColor(TableCell.BorderEdge.left, Color.BLACK);
        cellRef.setBorderColor(TableCell.BorderEdge.right, Color.BLACK);
    }
}
