package com.grupoms.app.negocio.PromocionJPA;
import java.util.List;

public interface PromocionSA {
    
    public Integer altaPromocion(TPromocion promocion);
    public Integer bajaPromocion(Integer id);
    public Integer modificarPromocion(TPromocion promocion);
    public TPromocion mostrarPromocion(Integer id);
    public List<TPromocion> listarPromociones();
    // public List<TPromocion> VerPromocionesPorSocio( no sé qué poner aquí ); // tampoco sé cómo llamar la función :'(
}
