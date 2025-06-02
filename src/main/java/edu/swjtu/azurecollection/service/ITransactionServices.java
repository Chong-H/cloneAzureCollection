package edu.swjtu.azurecollection.service;
import edu.swjtu.azurecollection.pojo.Transaction;
import edu.swjtu.azurecollection.pojo.dto.TransactionDto;

public interface ITransactionServices {
    //add transaction history
    Transaction add(TransactionDto transaction);
    //find by id
    Transaction get(Long collectionId);

    Transaction getCheck(Long collectionId);

    Transaction edit(TransactionDto transaction);

    Iterable<Transaction> getAllTransactions();
}
