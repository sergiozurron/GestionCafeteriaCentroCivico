package integracion.pedido;

import negocio.pedido.TPedido;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.Set;

import integracion.Transaction.Transaction;
import integracion.Transaction.TransactionManager;

public class DAOPedidoImp implements DAOPedido{

    public Integer confirmarPedido(TPedido pedido);

    public TPedido mostrarPedido(Integer id){
        TPedido pedido = null;
        try{
            TransactionManager tm = TransactionManager.getInstancia();
            Transaction t = tm.getTransaction();
        }
    }

    public Set<TPedido> mostrarListaPedidos();

    public Integer modificarPedido (TPedido tpedido);

    public Integer devolverPedido(Integer id);
}