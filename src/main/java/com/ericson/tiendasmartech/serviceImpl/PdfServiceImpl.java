package com.ericson.tiendasmartech.serviceImpl;

import com.ericson.tiendasmartech.dto.PedidoDto;
import com.ericson.tiendasmartech.entity.Pedido;
import com.ericson.tiendasmartech.mapper.PedidoMapper;
import com.ericson.tiendasmartech.repository.PedidoRepository;
import com.ericson.tiendasmartech.service.PdfService;
import com.ericson.tiendasmartech.util.PdfUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class PdfServiceImpl implements PdfService {

    private final PdfUtil pdfUtil;
    private final PedidoMapper pedidoMapper;
    private final PedidoRepository pedidoRepository;

    @Override
    public byte[] generarPedidoBytes(String numero) {
        try {
            File file;
            String directoryPath = "src/main/resources/static/pdf";
            String fileName = "P" + numero + ".pdf";
            Path path = Paths.get(directoryPath, fileName);

            if (Files.exists(path)) file = path.toFile();
            else {
                Pedido pedido = pedidoRepository.findByNumero(numero).orElse(new Pedido());
                PedidoDto pedidoDto = pedidoMapper.toDto(pedido);
                file = pdfUtil.generarPdf(pedidoDto);
            }
            if (file != null) return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
