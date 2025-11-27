package com.grupoms.app.negocio.PromocionJPA;
import java.util.List;

public interface PromocionSA {
    
    public Integer altaPromocion(TPromocion promocion);
    public Integer bajaPromocion(Integer id);
    public Integer modificarPromocion(TPromocion promocion);
    public TPromocion mostrarPromocion(Integer id);
    public List<TPromocion> listarPromociones();
    public List<TPromocion> VerPromocionesPorSocio(Integer idSocio);
}
