package edu.swjtu.azurecollection.controller;

import edu.swjtu.azurecollection.pojo.ResponseMessage;
import edu.swjtu.azurecollection.pojo.dto.TransactionDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import edu.swjtu.azurecollection.pojo.Transaction;
import edu.swjtu.azurecollection.service.ITransactionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController //接口方法返回对象 转化为json文本
@RequestMapping("/api/transaction")
public class TransactionController {
    //use REST Style :get(find)@getMapping,post(add)postMapping,put(change)putMapping,delete:deleteMapping

    @Autowired
    ITransactionServices transactionServices;

    //add
    @PostMapping
    public ResponseMessage<Transaction> add(@RequestBody TransactionDto transaction) {
        System.out.println("Received transaction date controller layer: " + transaction.getTransactionDate());
        Transaction newTrans= transactionServices.add(transaction);
        return ResponseMessage.success(newTrans);
    }
    //find by Id
    @GetMapping("/{Id}")
    public ResponseMessage<Transaction> get(@PathVariable Long collectionId) {
        Transaction newTrans=transactionServices.get(collectionId);
        return ResponseMessage.success(newTrans);
    }
    //find by TransId
    @GetMapping("/check/{transactionId}")
    public ResponseMessage<Transaction> check(@PathVariable Long transactionId) {
        Transaction newTrans=transactionServices.getCheck(transactionId);
        return ResponseMessage.success(newTrans);
    }
    //public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {}

    //change
    @PutMapping("/change")
    public ResponseMessage<Transaction> edit(@Validated @RequestBody TransactionDto transaction) {

        System.out.println("Received transaction date controller layer change: " + transaction.getTransactionDate());

        Transaction newTrans=transactionServices.edit(transaction);
        return ResponseMessage.success(newTrans);
    }

    //get all
    @GetMapping
    public ResponseMessage<Iterable<Transaction>> getAllTransaction() {
        Iterable<Transaction> Transactions = transactionServices.getAllTransactions();
        return ResponseMessage.success(Transactions);
    }
    //del



}
