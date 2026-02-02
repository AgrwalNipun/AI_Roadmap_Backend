package com.gemini.roadmap2.service;

import com.gemini.roadmap2.models.Roadmap.Roadmap;
import com.gemini.roadmap2.models.Roadmap.RoadmapStep;
import com.gemini.roadmap2.models.Roadmap.RoadmapSubstep;
import com.gemini.roadmap2.models.User;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfService {

    @Autowired
    RoadmapService roadmapService;

    @Autowired
    UserSubstepProgressService userSubstepProgressService;

    // -----------------------------
    // GENERATE PDF (NO FILE CREATED)
    // -----------------------------
    public byte[] generateRoadmapPdf(int id, User user) throws Exception {

        boolean exists = userSubstepProgressService.existsByUserIdAndRoadmapId(user.getId(), id);

        if (!exists) {
            throw new Exception("You cannot access this roadmap");
        }

        Roadmap roadmap = roadmapService.getById(id);

        // Memory stream instead of file
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);

        PdfWriter writer = PdfWriter.getInstance(document, baos);
        document.open();

        // Title
        Font titleFont = new Font(Font.HELVETICA, 20, Font.BOLD);
        Paragraph title = new Paragraph(roadmap.getTitle().toUpperCase(), titleFont);
        title.setSpacingAfter(20);
        document.add(title);

        // MainDescription
        Font descFont = new Font(Font.TIMES_ROMAN, 16, Font.NORMAL);
        Paragraph mainDesc = new Paragraph(roadmap.getTitle(), descFont);
        mainDesc.setSpacingAfter(20);
        document.add(mainDesc);

        // Normal text
        Font normal = new Font(Font.HELVETICA, 12);

        int stepCount = 1;

        for (RoadmapStep step : roadmap.getSteps()) {

            // Step Header
            Paragraph stepTitle = new Paragraph(
                    "Step " + stepCount + ": " + step.getAim(),
                    new Font(Font.HELVETICA, 14, Font.BOLD));
            stepTitle.setSpacingBefore(10);
            stepTitle.setSpacingAfter(5);
            document.add(stepTitle);
            stepCount++;

            // Description
            Paragraph desc = new Paragraph(step.getDescription(), normal);
            desc.setIndentationLeft(20);
            desc.setSpacingAfter(5);
            document.add(desc);

            // Substeps
            for (RoadmapSubstep sub : step.getSubsteps()) {

                float y = writer.getVerticalPosition(true);

                PdfContentByte cb = writer.getDirectContent();
                cb.rectangle(90, y - 19, 10, 10); // checkbox
                cb.stroke();

                Paragraph combined = new Paragraph();
                combined.setIndentationLeft(60);
                combined.setSpacingAfter(8);

                // Bold aim
                Chunk aimChunk = new Chunk(sub.getAim() + " ", new Font(Font.HELVETICA, 12, Font.BOLD));

                // Normal description
                Chunk descChunk = new Chunk(": " + sub.getDescription(), new Font(Font.HELVETICA, 12, Font.NORMAL));

                combined.add(aimChunk);
                combined.add(descChunk);

                // Add to document
                document.add(combined); 
            }

            document.add(new Paragraph("\n"));
        }

        document.close();
        writer.close();

        return baos.toByteArray();
    }

}
