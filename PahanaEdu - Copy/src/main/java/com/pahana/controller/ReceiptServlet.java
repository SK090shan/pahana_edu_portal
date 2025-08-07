package com.pahana.controller;

import com.pahana.dao.BillDAO;
import com.pahana.dao.CustomerDAO;
import com.pahana.factory.DAOFactory;
import com.pahana.model.bill.Bill;
import com.pahana.model.customer.Customer;
import com.pahana.service.PdfGenerationService;
import com.pahana.service.QRService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/receipt")
public class ReceiptServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BillDAO billDAO;
    private CustomerDAO customerDAO;
    private QRService qrService;
    private PdfGenerationService pdfService;

    public void init() {
        billDAO = DAOFactory.getBillDAO();
       
        customerDAO = DAOFactory.getCustomerDAO();
      
        qrService = new QRService();
        pdfService = new PdfGenerationService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int billId = Integer.parseInt(request.getParameter("billId"));

            // 1. Fetch all necessary data from the database
            Bill bill = billDAO.getBillById(billId);
            if (bill == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Bill not found.");
                return;
            }
            Customer customer = customerDAO.getCustomerById(bill.getCustomerId());
            if (customer == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Customer associated with this bill not found.");
                return;
            }

            // 2. Generate the QR Code
            String qrText = "Receipt ID: " + bill.getBillId() + "\nCustomer: " + customer.getFullName() + "\nTotal: " + bill.getTotalAmount();
            byte[] qrCodeImage = qrService.generateQRCodeImage(qrText, 150, 150);

            // 3. Generate the PDF
            byte[] pdfBytes = pdfService.generateBillPdf(bill, customer, qrCodeImage);

            // 4. Set HTTP headers to tell the browser to download the file
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"receipt-" + billId + ".pdf\"");
            response.setContentLength(pdfBytes.length);

            // 5. Write the PDF byte array to the response output stream
            OutputStream os = response.getOutputStream();
            os.write(pdfBytes);
            os.flush();

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generating receipt.");
        }
    }
}