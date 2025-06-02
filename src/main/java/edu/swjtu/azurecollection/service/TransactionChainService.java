package edu.swjtu.azurecollection.service;

import edu.swjtu.azurecollection.contract.TransactionRecord;
import edu.swjtu.azurecollection.pojo.Transaction;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple5;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;


import java.math.BigInteger;

@Service
public class TransactionChainService {

    private final Client client;
    private TransactionRecord transactionRecord;
    private CryptoKeyPair cryptoKeyPair; // ✅ 添加这行

    @Value("${contract.transactionRecordAddress}")
    private String contractAddress; // 从配置文件中读取合约地址

    public TransactionChainService(Client client) {
        this.client = client;
    }

    @PostConstruct
    public void initContract() {
        try {
            // 从客户端生成账户（使用默认私钥或节点私钥）
            this.cryptoKeyPair = client.getCryptoSuite().getCryptoKeyPair();

            // ✅ 正确调用 load()，传入 3 个参数
            transactionRecord = TransactionRecord.load(contractAddress, client, cryptoKeyPair);

            System.out.println("✅ 合约加载成功：" + contractAddress);
        } catch (Exception e) {
            System.err.println("❌ 合约加载失败: " + e.getMessage());
        }
    }

    public void syncToBlockchain(Transaction tx) {
        try {
            transactionRecord.recordTrade(
                    BigInteger.valueOf(tx.getTransactionId()),
                    BigInteger.valueOf(tx.getCollectibleId()),
                    BigInteger.valueOf(tx.getBuyerId()),
                    BigInteger.valueOf(tx.getSellerId()),
                    tx.getTransactionDate()
            );
            System.out.println("✅ 交易上链成功 ID: " + tx.getTransactionId());
        } catch (Exception e) {
            System.err.println("❌ 上链失败：" + e.getMessage());
        }
    }
    public void saveTransactionOnChain(Transaction tx) {
        try {
            TransactionReceipt receipt = transactionRecord.recordTrade(
                    BigInteger.valueOf(tx.getTransactionId()),
                    BigInteger.valueOf(tx.getCollectibleId()),
                    BigInteger.valueOf(tx.getBuyerId()),
                    BigInteger.valueOf(tx.getSellerId()),
                    tx.getTransactionDate() // String 类型无需转换
            );
            System.out.println("✅ 区块链交易已记录，交易哈希: " + receipt.getTransactionHash());
        } catch (Exception e) {
            System.err.println("❌ 写入区块链失败: " + e.getMessage());
        }
    }
    public Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, String> queryTransaction(BigInteger txId) {
        try {
            return transactionRecord.getTrade(txId);
        } catch (ContractException e) {
            System.err.println("❌ 查询失败：" + e.getMessage());
            throw new RuntimeException("查询失败", e);
        }
    }
}
