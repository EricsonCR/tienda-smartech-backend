package com.ericson.tiendasmartech.util;

import com.ericson.tiendasmartech.entity.CarritoDetalle;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

@Component
public class CarritoUtil {

    public int indexItem(long id, List<CarritoDetalle> detalles) {
        return IntStream
                .range(0, detalles.size())
                .filter(i -> detalles.get(i).getProducto().getId() == id)
                .findFirst()
                .orElse(-1); // Retorna -1 si no encuentra el elemento
    }

    public boolean existsItem(long id, List<CarritoDetalle> detalles) {
        for (CarritoDetalle carritoDetalle : detalles) {
            if (carritoDetalle.getProducto().getId() == id) return true;
        }
        return false;
    }
}
