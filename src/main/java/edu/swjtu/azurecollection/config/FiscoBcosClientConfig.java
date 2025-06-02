package edu.swjtu.azurecollection.config;

import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FiscoBcosClientConfig {

    @Bean
    public Client fiscoClient() {
        // 用 config.toml 初始化 SDK
        BcosSDK sdk = BcosSDK.build("src/main/resources/config.toml");
        return sdk.getClient(1); // ✅ 无需传 groupId
    }
}