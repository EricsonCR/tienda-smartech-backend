package com.ericson.tiendasmartech.serviceImpl;

import com.ericson.tiendasmartech.dto.*;
import com.ericson.tiendasmartech.entity.*;
import com.ericson.tiendasmartech.mapper.PedidoMapper;
import com.ericson.tiendasmartech.model.ServiceResponse;
import com.ericson.tiendasmartech.repository.PedidoRepository;
import com.ericson.tiendasmartech.service.EmailService;
import com.ericson.tiendasmartech.service.PdfService;
import com.ericson.tiendasmartech.service.PedidoService;
import com.ericson.tiendasmartech.util.PedidoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PdfService pdfService;
    private final EmailService emailService;
    private final PedidoMapper pedidoMapper;
    private final PedidoUtil pedidoUtil;

    @Override
    @Transactional
    public ServiceResponse registrar(PedidoDto pedidoDto) {
        try {
            Pedido pedido = pedidoMapper.toEntity(pedidoDto);
            for (PedidoDetalle pedidoDetalle : pedido.getPedidoDetalles()) pedidoDetalle.setPedido(pedido);
            pedido.setNumero(pedidoUtil.generarNumeroPedido());
            pedidoRepository.save(pedido);

            File file = pdfService.generarPdf(pedidoMapper.toDto(pedido));
            EmailDto emailDto = pedidoUtil.generarEmailPedido(pedido.getUsuario().getEmail(), pedido.getNumero());
//            if (!emailService.sendEmailFile(emailDto, file))
//                return new ServiceResponse("Pedido ok, Email fallido, ", HttpStatus.OK, pedidoMapper.toDto(pedido));
            return new ServiceResponse("Pedido registrado", HttpStatus.OK, pedidoMapper.toDto(pedido));
        } catch (Exception e) {
            return new ServiceResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }


}
