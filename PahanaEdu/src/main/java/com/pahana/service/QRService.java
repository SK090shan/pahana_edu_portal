package com.pahana.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * LECTURE: Advanced Features - QR Code Generation with ZXing.
 * This service encapsulates the logic for creating a QR code image.
 * It takes a string as input and returns the QR code as a byte array,
 * which can then be easily embedded into a PDF or displayed on a web page.
 */
public class QRService {

    public byte[] generateQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        
        // Use a ByteArrayOutputStream to write the image data to memory
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        
        // Return the image data as a byte array
        return pngOutputStream.toByteArray();
    }
}