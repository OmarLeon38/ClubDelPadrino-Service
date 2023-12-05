package com.upao.clubdelpadrino.service.service;

import com.upao.clubdelpadrino.service.entity.DetallePedido;
import com.upao.clubdelpadrino.service.entity.Pedido;
import com.upao.clubdelpadrino.service.entity.dto.GenerarPedidoDTO;
import com.upao.clubdelpadrino.service.entity.dto.PedidoDetalleDTO;
import com.upao.clubdelpadrino.service.repository.DetallePedidoRepository;
import com.upao.clubdelpadrino.service.repository.PedidoRepository;
import com.upao.clubdelpadrino.service.repository.ProductoRepository;
import com.upao.clubdelpadrino.service.utils.GenericResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.upao.clubdelpadrino.service.utils.Global.*;

@Service
@Transactional
public class PedidoService {
    private final PedidoRepository repository;
    private final DetallePedidoRepository detallePedidoRepository;
    private final DetallePedidoService dpService;
    private final ProductoRepository pRepository;

    public PedidoService(PedidoRepository repository, DetallePedidoRepository detallePedidoRepository, DetallePedidoService dpService, ProductoRepository pRepository) {
        this.repository = repository;
        this.detallePedidoRepository = detallePedidoRepository;
        this.dpService = dpService;
        this.pRepository = pRepository;
    }

    public GenericResponse<List<PedidoDetalleDTO>> obtenerMisCompras(int idClient) {
        final List<PedidoDetalleDTO> dtos = new ArrayList<>();
        final Iterable<Pedido> pedidos = repository.obtenerMisCompras(idClient);
        pedidos.forEach(pedido -> {
            dtos.add(new PedidoDetalleDTO(pedido, detallePedidoRepository.findByPedido(pedido.getId())));
        });
        return new GenericResponse<>(OPERACION_CORRECTA, RPTA_OK, "Petición correcta", dtos);
    }

    public GenericResponse guardarPedido(GenerarPedidoDTO dto) {
        Date date = new Date();
        dto.getPedido().setFechaCompra(new java.sql.Date(date.getTime()));
        dto.getPedido().setAnularPedido(false);
        dto.getPedido().setMonto(dto.getPedido().getMonto());
        dto.getPedido().setCliente(dto.getCliente());
        this.repository.save(dto.getPedido());
        for (DetallePedido dp : dto.getDetallePedido()) {
            dp.setPedido(dto.getPedido());
            this.pRepository.actalizarStock(dp.getCantidad(), dp.getProducto().getId());
        }
        this.dpService.guardarDetalles(dto.getDetallePedido());
        return new GenericResponse(TIPO_DATA, RPTA_OK, OPERACION_CORRECTA, dto);
    }

    public GenericResponse anularPedido(int id) {
        Pedido p = this.repository.findById(id).orElse(new Pedido());
        if (p.getId() != 0) {
            p.setAnularPedido(true);
            this.repository.save(p);
            return new GenericResponse(TIPO_DATA, RPTA_OK, OPERACION_CORRECTA, p);
        } else {
            return new GenericResponse(TIPO_DATA, RPTA_ERROR, OPERACION_INCORRECTA, "Pedido inválido");
        }
    }

    @NotNull
    public ResponseEntity<Resource> exportInvoice(int idClient, int idOrden) {
        Optional<Pedido> optPedido = this.repository.findByIdAndClienteId(idClient, idOrden);
        Double rpta = this.detallePedidoRepository.totalByIdCustomer(idClient, idOrden);
        if (optPedido.isPresent()) {
            try {
                final Pedido pedido = optPedido.get();
                final File file = ResourceUtils.getFile("classpath:exportInvoice.jasper");
                final File imgLogo = ResourceUtils.getFile("classpath:imagenes/logoCDP.jpeg");
                final JasperReport report = (JasperReport) JRLoader.loadObject(file);

                final HashMap<String, Object> parameters = new HashMap<>();
                parameters.put("nombreCliente", pedido.getCliente().getNombreCompletoCiente());
                parameters.put("imgLogo", new FileInputStream(imgLogo));
                parameters.put("total", rpta);
                parameters.put("dsInvoice", new JRBeanCollectionDataSource((Collection<?>) this.detallePedidoRepository.findByPedido(idOrden)));

                JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
                byte[] reporte = JasperExportManager.exportReportToPdf(jasperPrint);
                String sdf = (new SimpleDateFormat("dd/MM/yyyy")).format(new Date());
                StringBuilder stringBuilder = new StringBuilder().append("Boleta de venta:");
                ContentDisposition contentDisposition = ContentDisposition.builder("attachement")
                        .filename(stringBuilder.append(pedido.getId())
                                .append("fecha:")
                                .append(sdf)
                                .append(".pdf")
                                .toString())
                        .build();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentDisposition(contentDisposition);
                return ResponseEntity.ok().contentLength((long) reporte.length)
                        .contentType(MediaType.APPLICATION_PDF)
                        .headers(headers).body(new ByteArrayResource(reporte));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            return ResponseEntity.noContent().build();
        }
        return null;
    }
}