package com.poa.server.config;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.common.StorageSharedKeyCredential;
import com.microsoft.azure.AzureEnvironment;
import com.microsoft.azure.credentials.ApplicationTokenCredentials;
import com.microsoft.azure.management.Azure;
import com.microsoft.rest.LogLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AzureBeanConfig {

    private String endpoint;


    @Bean
    public StorageSharedKeyCredential storageSharedKeyCredential(@Value("${dpoa.azure-storage.account-name}") String accountName,
                                                                 @Value("${dpoa.azure-storage.account-key}") String accountKey) {

        endpoint = "https://" + accountName + ".blob.core.windows.net";
        return new StorageSharedKeyCredential(accountName, accountKey);
    }

    @Bean
    public BlobServiceClient blobServiceClient(@Autowired StorageSharedKeyCredential storageSharedKeyCredential) {
        return new BlobServiceClientBuilder()
                .endpoint(endpoint)
                .credential(storageSharedKeyCredential)
                .buildClient();
    }

    @Bean
    public ApplicationTokenCredentials applicationTokenCredentials(@Value("${dpoa.azure-ad.client.user-management-client.id}") String clientId,
                                                                   @Value("${dpoa.azure-ad.client.user-management-client.key}") String clientKey,
                                                                   @Value("${dpoa.azure-ad.tenant-id}") String tenantId) {
        return new ApplicationTokenCredentials(clientId, tenantId, clientKey, AzureEnvironment.AZURE);
    }

 /*   @Bean
    public Azure.Authenticated authenticated(@Autowired ApplicationTokenCredentials credentials) {
        return Azure.configure()
                .withLogLevel(LogLevel.BODY)
                .authenticate(credentials);
    }*/




}
