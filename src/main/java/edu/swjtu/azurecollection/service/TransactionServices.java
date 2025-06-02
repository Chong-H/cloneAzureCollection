package edu.swjtu.azurecollection.service;

import edu.swjtu.azurecollection.pojo.Transaction;
import edu.swjtu.azurecollection.pojo.dto.TransactionDto;
import edu.swjtu.azurecollection.repository.TransactionRepository;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple5;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class TransactionServices implements ITransactionServices{

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    private TransactionChainService transactionChainService;

    @Override
    public  Transaction add(TransactionDto transaction) {
        Transaction transactionPojo = new Transaction();
        BeanUtils.copyProperties(transaction, transactionPojo);

        System.out.println("Received transaction date: " + transaction.getTransactionDate());
        //
        Transaction saved = transactionRepository.save(transactionPojo);
        transactionChainService.saveTransactionOnChain(saved);
        return saved;
        //return transactionRepository.save(transactionPojo); //origin
    }
    @Override
    public Transaction get(Long collectionId){
        return  transactionRepository.findById(collectionId).orElseThrow(()->{
             throw new IllegalArgumentException ("error");
         });
    }

    @Override
    public Transaction getCheck(Long collectionId){
        // 从数据库查出已有交易记录
        Transaction transaction = get(collectionId);

        // 把 Long 转换成 BigInteger 作为合约方法的参数
        BigInteger txId = BigInteger.valueOf(collectionId);

        // 调用链上方法
        Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, String> result =
                transactionChainService.queryTransaction(txId);

        // 将链上返回结果写入本地 Java 对象
        transaction.setTransactionId(result.getValue1().longValue());
        transaction.setCollectibleId(result.getValue2().longValue());
        transaction.setBuyerId(result.getValue3().longValue());
        transaction.setSellerId(result.getValue4().longValue());
        transaction.setTransactionDate(result.getValue5());

        //  return transaction）

        return transaction;
    }

    @Override
    public Transaction edit(TransactionDto transaction) {
        Transaction transactionPojo = new Transaction();
        BeanUtils.copyProperties(transaction, transactionPojo);

        return transactionRepository.save(transactionPojo);
    }

    @Override
    public Iterable<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
