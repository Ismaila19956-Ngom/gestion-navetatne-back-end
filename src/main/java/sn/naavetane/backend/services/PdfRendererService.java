package sn.naavetane.backend.services;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PdfRendererService {

    private final SpringTemplateEngine templateEngine;

    public byte[] render(String template, Map<String, Object> vars) {

        Context context = new Context();
        vars.forEach(context::setVariable);

        String html = templateEngine.process(template, context);

        // Convertir le HTML5 (Thymeleaf) en XHTML valide pour openhtmltopdf
        Document jsoupDoc = Jsoup.parse(html, "UTF-8");
        jsoupDoc.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        String xhtml = jsoupDoc.html();

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.withHtmlContent(xhtml, null);
        builder.toStream(out);
        try {
            builder.run();
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la génération du PDF", e);
        }

        return out.toByteArray();
    }
}
