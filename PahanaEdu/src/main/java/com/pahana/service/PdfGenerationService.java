package com.pahana.service;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.pahana.model.bill.Bill;
import com.pahana.model.bill.BillItem;
import com.pahana.model.customer.Customer;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;

/**
 * LECTURE: Service Layer and Third-Party Library Integration (iTextPDF).
 * This service handles the business logic of creating a PDF receipt.
 * It uses the iText library to construct the document structure.
 */
public class PdfGenerationService {

	public byte[] generateBillPdf(Bill bill, Customer customer, byte[] qrCodeImage) throws java.net.MalformedURLException {
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    PdfWriter writer = new PdfWriter(baos);
	    PdfDocument pdf = new PdfDocument(writer);
	    Document document = new Document(pdf);

	    // --- Document Header ---
	    document.add(new Paragraph("Pahana Edu Bookstore").setTextAlignment(TextAlignment.CENTER).setBold().setFontSize(20));
	    document.add(new Paragraph("Official Receipt").setTextAlignment(TextAlignment.CENTER));
	    document.add(new Paragraph("--------------------------------------------------"));

	    // --- Bill and Customer Info ---
	    document.add(new Paragraph("Bill ID: " + bill.getBillId()));
	    document.add(new Paragraph("Customer: " + customer.getFullName() + " (Acc: " + customer.getAccountNumber() + ")"));
	    document.add(new Paragraph("Date: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(bill.getBillDate())));
	    document.add(new Paragraph("--------------------------------------------------"));

	    // --- Items Table ---
	    Table table = new Table(UnitValue.createPercentArray(new float[]{4, 1, 2, 2}));
	    table.setWidth(UnitValue.createPercentValue(100));
	    table.addHeaderCell(new Cell().add(new Paragraph("Item Name").setBold()));
	    table.addHeaderCell(new Cell().add(new Paragraph("Qty").setBold()));
	    table.addHeaderCell(new Cell().add(new Paragraph("Unit Price").setBold()));
	    table.addHeaderCell(new Cell().add(new Paragraph("Total").setBold()));

	    // --- THIS IS THE IMPROVEMENT ---
	    java.math.BigDecimal calculatedGrandTotal = java.math.BigDecimal.ZERO;

	    if (bill.getBillItems() != null) { // Defensive check
	        for (BillItem item : bill.getBillItems()) {
	            java.math.BigDecimal lineTotal = item.getPricePerUnit().multiply(new java.math.BigDecimal(item.getQuantity()));
	            calculatedGrandTotal = calculatedGrandTotal.add(lineTotal); // Add to our running total

	            table.addCell(new Cell().add(new Paragraph(item.getItemName())));
	            table.addCell(new Cell().add(new Paragraph(String.valueOf(item.getQuantity()))));
	            table.addCell(new Cell().add(new Paragraph(item.getPricePerUnit().toString())));
	            table.addCell(new Cell().add(new Paragraph(lineTotal.toString())));
	        }
	    }
	    document.add(table);
	    
	    // --- Grand Total ---
	    // Now we use our freshly calculated total, ensuring accuracy
	    document.add(new Paragraph("Grand Total: " + calculatedGrandTotal.toString()).setTextAlignment(TextAlignment.RIGHT).setBold().setFontSize(14));
	    document.add(new Paragraph("\n"));

	    // --- QR Code ---
	    if (qrCodeImage != null) {
	        Image img = new Image(ImageDataFactory.create(qrCodeImage));
			img.setWidth(100f);
			img.setHeight(100f);
			document.add(img.setTextAlignment(TextAlignment.CENTER));
			document.add(new Paragraph("Scan for details").setTextAlignment(TextAlignment.CENTER).setFontSize(8));
	    }

	    document.close();
	    return baos.toByteArray();
	}
}