package integracion.pedido;

import negocio.pedido.TPedido;
import java.util.set;

public interface DAOPedido{
    public Integer confirmarPedido(TPedido pedido);

    public TPedido mostrarPedido(Integer id);

    public Set<TPedido> mostrarListaPedidos();

    public Integer modificarPedido (TPedido tpedido);

    public Integer devolverPedido(Integer id);
}