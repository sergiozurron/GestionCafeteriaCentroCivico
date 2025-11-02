package integracion.Transaction;

import java.util.concurrent.concurrentHashMap;

public class TransactionManagerImp extends TransactionManager{
    private ConcurrentHashMap<Thread, Transaction> transactions;

    public TransactionManagerImp(){
        transactions = new ConcurrentHashMap<Thread,Transaction>();
    }

    @Override
    public Transaction newTransaction(){
        Thread currentThread = Thread.currentThread();
        Transaccion existente = transacciones.get(currentThread);

        if (existente == null) {
            Transaccion nueva = FactoriaTransaccion.getInstance().createTransaction();
            transacciones.put(currentThread, nueva);
            return nueva;
        }
        throw new IllegalStateException("Ya existe una transacción activa para este hilo.");
    }

    @Override
    public Transaccion getTransaction() {
        Thread currentThread = Thread.currentThread();
        Transaccion t = transacciones.get(currentThread);

        if (t != null) {
            return t;
        }
        throw new IllegalStateException("No hay ninguna transacción activa para este hilo.");
    }

    @Override
    public void deleteTransaction() {
        Thread currentThread = Thread.currentThread();
        Transaccion t = transacciones.get(currentThread);

        if (t != null) {
            transacciones.remove(currentThread);
        } else {
            throw new IllegalStateException("No existe una transacción para eliminar en este hilo.");
        }
    }
}