package com.poa.server.service;

import cn.hutool.core.lang.UUID;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;

import com.poa.server.entity.PoaFile;
import com.poa.server.exception.PoaException;
import com.poa.server.repository.FileRepository;
import com.poa.server.util.IdUtil;
import com.poa.server.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
public class FileService {
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private BlobServiceClient blobServiceClient;


    @Value("${dpoa.azure-storage.containerName}")
    private String containerName;


    @Value("${dpoa.redis-key.file-id-incr}")
    private String FILE_ID_INCR;

    /**
     * upload file to azure storage
     *
     * @param temporaryFile file
     * @return file info
     */
    public void uploadFileToStorage(File temporaryFile) {
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName.toLowerCase());
        BlobClient blobClient = containerClient.getBlobClient(temporaryFile.getName());
        // upload file to storage
        blobClient.uploadFromFile(temporaryFile.toPath().toString());
    }

    /**
     * download file to out put stream
     * @param fileId file id
     * @return out put stream
     */
    public File download(String fileId) {
        Optional<PoaFile> optionalFileCenter = fileRepository.findById(fileId);
        if (!optionalFileCenter.isPresent()) {
            throw new PoaException( "File does not exists!");
        }
        PoaFile file = optionalFileCenter.get();

        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        BlobClient blobClient = containerClient.getBlobClient(file.getAzureFileName());
        String tempFile = System.currentTimeMillis() + file.getName();
        blobClient.downloadToFile(tempFile);

        return new File(tempFile);
    }

    /**//**
     * add content to file
     *
     * @param file    pdf file
     * @param content content
     *//*
    public File addContent(File file, String content) throws Exception {
        File resultFile = File.createTempFile(System.currentTimeMillis() + "", file.getName());
        Document document = new Document();
        FileOutputStream outputStream = new FileOutputStream(resultFile);
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        document.open();
        PdfContentByte cb = writer.getDirectContent();

        // Load existing PDF
        PdfReader reader = new PdfReader(new FileInputStream(file));
        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
            PdfImportedPage page = writer.getImportedPage(reader, i);
            // Copy first page of existing PDF into output PDF
            cb.addTemplate(page, 0, 0);
            document.newPage();
        }

        // Add new data / text here
        document.newPage();
        Paragraph elements = new Paragraph(content);
        elements.setAlignment(Element.ALIGN_CENTER);
        document.add(elements);

        document.close();

        return resultFile;
    }

    *//**
     * find file information by file id
     *
     * @param fileId file id
     * @return file information
     *//*
    public FileCenter findByFileId(String fileId) {
        Optional<FileCenter> optionalFileCenter = fileRepository.findById(fileId);
        if (!optionalFileCenter.isPresent()) {
            throw new DpoaException(ExceptionObj.of(HttpStatus.BAD_REQUEST, "file does not exists"));
        }
        return optionalFileCenter.get();
    }*/
}
