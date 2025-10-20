package com.webgram.dgpsn.controllers;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.webgram.dgpsn.repositories.LabelRepository;


@RestController
@AllArgsConstructor
@RequestMapping("/export")
public class ExportController {

    private final LabelRepository phaseRepository;

  //  private final VelocityEngine velocityEngine;

   /* @GetMapping(value = "/pdf")
    public ModelAndView exportToPdf(HttpServletResponse response) {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty("resource.loader", "class");
        velocityEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        velocityEngine.init();
        var dataToExcel = phaseRepository.findAll();
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("dataList", dataToExcel);
        StringWriter writer1 = new StringWriter();
        Template template = velocityEngine.getTemplate("template.vm");
        template.merge(velocityContext, writer1);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"export.pdf\"");

        // Génération du PDF à partir du HTML rendu
        try {
            OutputStream outputStream = response.getOutputStream();
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.open();
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(writer.toString().getBytes()));
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    @GetMapping(value = "/excel", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> exportToExcel() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("phase");

        // Créer un style pour l'en-tête
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        // Créer un style pour les cellules de données
        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setWrapText(true);

        var dataToExcel = phaseRepository.findAll();
        int i = 1;
        Row row = sheet.createRow(0);
        // Ajuster la largeur des colonnes pour s'adapter au contenu
        sheet.autoSizeColumn(0);
        Cell cellCode = row.createCell(0);
        cellCode.setCellStyle(headerStyle); // Appliquer le style de l'en-tête
        cellCode.setCellValue("Code");
        Cell cellLibelle = row.createCell(1);
        cellLibelle.setCellStyle(headerStyle); // Appliquer le style de l'en-tête
        cellLibelle.setCellValue("Libellé");
        sheet.setColumnWidth(1, 7000);
        for (PhaseEntity phase: dataToExcel) {
            row = sheet.createRow(i);
            sheet.autoSizeColumn(0);
            cellCode = row.createCell(0);
            cellCode.setCellStyle(dataStyle);
            cellCode.setCellValue(phase.getCode());
            cellLibelle = row.createCell(1);
            cellLibelle.setCellStyle(dataStyle);
            cellLibelle.setCellValue(phase.getLibelle());
            i++;
        }

        // Convertir le classeur en tableau d'octets
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        byte[] excelBytes = outputStream.toByteArray();

        // Définir les en-têtes de la réponse HTTP
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "export.xlsx");

        return ResponseEntity.ok().headers(headers).body(excelBytes);
    }

    @GetMapping(value = "/pdf1", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> exportToPDF() throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        // Créer le contenu du PDF
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.beginText();
        contentStream.newLineAtOffset(50, 700);
        var dataToExcel = phaseRepository.findAll();
      //  contentStream.setNonStrokingColor(255, 0, 0);
        contentStream.showText("Code");
        contentStream.newLineAtOffset(100, 0);
        contentStream.showText("Libellé");
        contentStream.newLineAtOffset(100, 0);
        contentStream.newLineAtOffset(-2 * 100, -20);
        for (PhaseEntity phase: dataToExcel) {
            contentStream.showText(phase.getCode());
            contentStream.newLineAtOffset(100, 0);
            contentStream.showText(phase.getLibelle());
            contentStream.newLineAtOffset(100, 0);
            contentStream.newLineAtOffset(-2 * 100, -20);
        }

        contentStream.endText();
        contentStream.close();

        // Convertir le document en tableau d'octets
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.save(outputStream);
        byte[] pdfBytes = outputStream.toByteArray();

        // Définir les en-têtes de la réponse HTTP
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "export.pdf");

        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    } */
}
