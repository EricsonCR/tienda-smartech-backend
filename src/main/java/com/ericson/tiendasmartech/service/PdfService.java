package com.ericson.tiendasmartech.service;

import com.ericson.tiendasmartech.dto.PedidoDto;

import java.io.File;

public interface PdfService {
    File generarPdf(PedidoDto pedido);
}
