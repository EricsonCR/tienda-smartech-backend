package com.ericson.tiendasmartech.controller;

import com.ericson.tiendasmartech.service.PdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pdf")
@RequiredArgsConstructor
public class PdfController {

    private final PdfService pdfService;

    @GetMapping("/pedido/{numero}")
    public ResponseEntity<byte[]> pedido(@PathVariable("numero") String numero) {
        byte[] pdfData = pdfService.generarPedidoBytes(numero);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "P" + numero + ".pdf");
        return new ResponseEntity<>(pdfData, headers, HttpStatus.OK);
    }
}
