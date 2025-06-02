package edu.swjtu.azurecollection.contract;

import org.fisco.bcos.sdk.abi.FunctionReturnDecoder;
import org.fisco.bcos.sdk.abi.TypeReference;
import org.fisco.bcos.sdk.abi.datatypes.Event;
import org.fisco.bcos.sdk.abi.datatypes.Function;
import org.fisco.bcos.sdk.abi.datatypes.Type;
import org.fisco.bcos.sdk.abi.datatypes.Utf8String;
import org.fisco.bcos.sdk.abi.datatypes.generated.Uint256;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple5;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.contract.Contract;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.eventsub.EventCallback;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unchecked")
public class TransactionRecord extends Contract {
    public static final String[] BINARY_ARRAY = {"608060405234801561001057600080fd5b506106d3806100206000396000f300608060405260043610610057576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806308ce237b1461005c5780631e6c598e146100ed5780632db25e05146101af575b600080fd5b34801561006857600080fd5b506100eb60048036038101908080359060200190929190803590602001909291908035906020019092919080359060200190929190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610271565b005b3480156100f957600080fd5b50610118600480360381019080803590602001909291905050506103da565b6040518086815260200185815260200184815260200183815260200180602001828103825283818151815260200191508051906020019080838360005b83811015610170578082015181840152602081019050610155565b50505050905090810190601f16801561019d5780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390f35b3480156101bb57600080fd5b506101da600480360381019080803590602001909291905050506104a8565b6040518086815260200185815260200184815260200183815260200180602001828103825283818151815260200191508051906020019080838360005b83811015610232578082015181840152602081019050610217565b50505050905090810190601f16801561025f5780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390f35b60008060008781526020019081526020016000206000015414151561029557600080fd5b60a060405190810160405280868152602001858152602001848152602001838152602001828152506000808781526020019081526020016000206000820151816000015560208201518160010155604082015181600201556060820151816003015560808201518160040190805190602001906103139291906105d2565b509050507f1f659189015c89ff795d93127e9212146d943ee70f806a1fb2c81a5020f8123185858585856040518086815260200185815260200184815260200183815260200180602001828103825283818151815260200191508051906020019080838360005b8381101561039557808201518184015260208101905061037a565b50505050905090810190601f1680156103c25780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390a15050505050565b6000602052806000526040600020600091509050806000015490806001015490806002015490806003015490806004018054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561049e5780601f106104735761010080835404028352916020019161049e565b820191906000526020600020905b81548152906001019060200180831161048157829003601f168201915b5050505050905085565b60008060008060606104b8610652565b60008088815260200190815260200160002060a0604051908101604052908160008201548152602001600182015481526020016002820154815260200160038201548152602001600482018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156105975780601f1061056c57610100808354040283529160200191610597565b820191906000526020600020905b81548152906001019060200180831161057a57829003601f168201915b505050505081525050905080600001518160200151826040015183606001518460800151809050955095509550955095505091939590929450565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061061357805160ff1916838001178555610641565b82800160010185558215610641579182015b82811115610640578251825591602001919060010190610625565b5b50905061064e9190610682565b5090565b60a06040519081016040528060008152602001600081526020016000815260200160008152602001606081525090565b6106a491905b808211156106a0576000816000905550600101610688565b5090565b905600a165627a7a723058200b610a0418a624cb38571d7bb935fb9e841982014426a1bee3e52973497e1f190029"};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"608060405234801561001057600080fd5b506106d3806100206000396000f300608060405260043610610057576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680630fbfa00b1461005c5780632d28bf4b146100ed578063c42695c2146101af575b600080fd5b34801561006857600080fd5b506100eb60048036038101908080359060200190929190803590602001909291908035906020019092919080359060200190929190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610271565b005b3480156100f957600080fd5b50610118600480360381019080803590602001909291905050506103da565b6040518086815260200185815260200184815260200183815260200180602001828103825283818151815260200191508051906020019080838360005b83811015610170578082015181840152602081019050610155565b50505050905090810190601f16801561019d5780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390f35b3480156101bb57600080fd5b506101da600480360381019080803590602001909291905050506104a8565b6040518086815260200185815260200184815260200183815260200180602001828103825283818151815260200191508051906020019080838360005b83811015610232578082015181840152602081019050610217565b50505050905090810190601f16801561025f5780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390f35b60008060008781526020019081526020016000206000015414151561029557600080fd5b60a060405190810160405280868152602001858152602001848152602001838152602001828152506000808781526020019081526020016000206000820151816000015560208201518160010155604082015181600201556060820151816003015560808201518160040190805190602001906103139291906105d2565b509050507f6949b73fccbcb582dfa65d80f1555bcffec337c5dda3a3979776f905387f1dcc85858585856040518086815260200185815260200184815260200183815260200180602001828103825283818151815260200191508051906020019080838360005b8381101561039557808201518184015260208101905061037a565b50505050905090810190601f1680156103c25780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390a15050505050565b6000602052806000526040600020600091509050806000015490806001015490806002015490806003015490806004018054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561049e5780601f106104735761010080835404028352916020019161049e565b820191906000526020600020905b81548152906001019060200180831161048157829003601f168201915b5050505050905085565b60008060008060606104b8610652565b60008088815260200190815260200160002060a0604051908101604052908160008201548152602001600182015481526020016002820154815260200160038201548152602001600482018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156105975780601f1061056c57610100808354040283529160200191610597565b820191906000526020600020905b81548152906001019060200180831161057a57829003601f168201915b505050505081525050905080600001518160200151826040015183606001518460800151809050955095509550955095505091939590929450565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061061357805160ff1916838001178555610641565b82800160010185558215610641579182015b82811115610640578251825591602001919060010190610625565b5b50905061064e9190610682565b5090565b60a06040519081016040528060008152602001600081526020016000815260200160008152602001606081525090565b6106a491905b808211156106a0576000816000905550600101610688565b5090565b905600a165627a7a723058203a46aac08561e9bf3040cd96fd83344b13d0ab50ff271118895d61906d93ebc30029"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"constant\":false,\"inputs\":[{\"name\":\"transactionId\",\"type\":\"uint256\"},{\"name\":\"collectibleId\",\"type\":\"uint256\"},{\"name\":\"buyerId\",\"type\":\"uint256\"},{\"name\":\"sellerId\",\"type\":\"uint256\"},{\"name\":\"transactionDate\",\"type\":\"string\"}],\"name\":\"recordTrade\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"name\":\"trades\",\"outputs\":[{\"name\":\"transactionId\",\"type\":\"uint256\"},{\"name\":\"collectibleId\",\"type\":\"uint256\"},{\"name\":\"buyerId\",\"type\":\"uint256\"},{\"name\":\"sellerId\",\"type\":\"uint256\"},{\"name\":\"transactionDate\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"transactionId\",\"type\":\"uint256\"}],\"name\":\"getTrade\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"},{\"name\":\"\",\"type\":\"uint256\"},{\"name\":\"\",\"type\":\"uint256\"},{\"name\":\"\",\"type\":\"uint256\"},{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"transactionId\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"collectibleId\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"buyerId\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"sellerId\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"transactionDate\",\"type\":\"string\"}],\"name\":\"TradeRecorded\",\"type\":\"event\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_RECORDTRADE = "recordTrade";

    public static final String FUNC_TRADES = "trades";

    public static final String FUNC_GETTRADE = "getTrade";

    public static final Event TRADERECORDED_EVENT = new Event("TradeRecorded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
    ;

    protected TransactionRecord(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public TransactionReceipt recordTrade(BigInteger transactionId, BigInteger collectibleId, BigInteger buyerId, BigInteger sellerId, String transactionDate) {
        final Function function = new Function(
                FUNC_RECORDTRADE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(transactionId), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(collectibleId), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(buyerId), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(sellerId), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(transactionDate)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] recordTrade(BigInteger transactionId, BigInteger collectibleId, BigInteger buyerId, BigInteger sellerId, String transactionDate, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_RECORDTRADE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(transactionId), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(collectibleId), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(buyerId), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(sellerId), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(transactionDate)), 
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForRecordTrade(BigInteger transactionId, BigInteger collectibleId, BigInteger buyerId, BigInteger sellerId, String transactionDate) {
        final Function function = new Function(
                FUNC_RECORDTRADE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(transactionId), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(collectibleId), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(buyerId), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(sellerId), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(transactionDate)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, String> getRecordTradeInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_RECORDTRADE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, String>(

                (BigInteger) results.get(0).getValue(), 
                (BigInteger) results.get(1).getValue(), 
                (BigInteger) results.get(2).getValue(), 
                (BigInteger) results.get(3).getValue(), 
                (String) results.get(4).getValue()
                );
    }

    public Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, String> trades(BigInteger param0) throws ContractException {
        final Function function = new Function(FUNC_TRADES, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
        List<Type> results = executeCallWithMultipleValueReturn(function);
        return new Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, String>(
                (BigInteger) results.get(0).getValue(), 
                (BigInteger) results.get(1).getValue(), 
                (BigInteger) results.get(2).getValue(), 
                (BigInteger) results.get(3).getValue(), 
                (String) results.get(4).getValue());
    }

    public Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, String> getTrade(BigInteger transactionId) throws ContractException {
        final Function function = new Function(FUNC_GETTRADE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(transactionId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
        List<Type> results = executeCallWithMultipleValueReturn(function);
        return new Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, String>(
                (BigInteger) results.get(0).getValue(), 
                (BigInteger) results.get(1).getValue(), 
                (BigInteger) results.get(2).getValue(), 
                (BigInteger) results.get(3).getValue(), 
                (String) results.get(4).getValue());
    }

    public List<TradeRecordedEventResponse> getTradeRecordedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRADERECORDED_EVENT, transactionReceipt);
        ArrayList<TradeRecordedEventResponse> responses = new ArrayList<TradeRecordedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TradeRecordedEventResponse typedResponse = new TradeRecordedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.transactionId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.collectibleId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.buyerId = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.sellerId = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.transactionDate = (String) eventValues.getNonIndexedValues().get(4).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void subscribeTradeRecordedEvent(String fromBlock, String toBlock, List<String> otherTopics, EventCallback callback) {
        String topic0 = eventEncoder.encode(TRADERECORDED_EVENT);
        subscribeEvent(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void subscribeTradeRecordedEvent(EventCallback callback) {
        String topic0 = eventEncoder.encode(TRADERECORDED_EVENT);
        subscribeEvent(ABI,BINARY,topic0,callback);
    }

    public static TransactionRecord load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new TransactionRecord(contractAddress, client, credential);
    }

    public static TransactionRecord deploy(Client client, CryptoKeyPair credential) throws ContractException {
        return deploy(TransactionRecord.class, client, credential, getBinary(client.getCryptoSuite()), "");
    }

    public static class TradeRecordedEventResponse {
        public TransactionReceipt.Logs log;

        public BigInteger transactionId;

        public BigInteger collectibleId;

        public BigInteger buyerId;

        public BigInteger sellerId;

        public String transactionDate;
    }
}
