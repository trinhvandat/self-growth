package org.ptit.okrs.core_upload.service.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.core_exception.InternalServerError;
import org.ptit.okrs.core_upload.exception.FileNameInvalidException;
import org.ptit.okrs.core_upload.service.CoreUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
public class CoreUploadServiceImpl implements CoreUploadService {

  @Value("${application.upload.url.view}")
  private String subUriViewFile;
  private final Path fileStorageLocation;

  public CoreUploadServiceImpl(String pathStorageFolder) {
    this.fileStorageLocation = Paths.get(pathStorageFolder).toAbsolutePath().normalize();
    createFolderStorage(fileStorageLocation);
  }

  /**
   * upload file from server
   * @param file - file select from device of client
   * @return path of file uploaded
   */
  @Override
  public String uploadFile(MultipartFile file) {
    log.info("(uploadFile)fileName: {}", file.getOriginalFilename());
    var fileName = file.getOriginalFilename();

    try {
      if (!fileName.contains(".")) {
        log.error("(uploadFile)fileName: {} invalid", fileName);
        throw new FileNameInvalidException(fileName);
      }

      fileName = new Date().getTime() + "." + getFileExtension(file.getOriginalFilename());
      Path targetLocation = fileStorageLocation.resolve(fileName);
      Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

      return subUriViewFile.replace("/**", "/") + fileName;
    } catch (Exception ex) {
      log.error("(uploadFile)exception: {}", ex.getMessage());
      throw new InternalServerError(
          "Could not upload file " + file.getOriginalFilename() + ". Please try again!");
    }
  }

  /**
   * get File Extension of the file uploaded to the server
   * @param fileName - the name of the file uploaded to the server
   * @return the file name extensions of the file uploaded to the server
   */
  private String getFileExtension(String fileName) {
    log.info("(getFileExtension)fileName: {}", fileName);
    String[] fileNameParts = fileName.split("\\.");
    return fileNameParts[fileNameParts.length - 1];
  }

  /**
   * create folder follow path is nominated : is absolute path
   *
   * @param fileStorageLocation - the path to save file is uploaded up server
   */
  private void createFolderStorage(Path fileStorageLocation) {
    log.info("(createFolderStorage)fileStorageLocation: {}", fileStorageLocation);
    try {
      Files.createDirectories(fileStorageLocation);
    } catch (Exception ex) {
      log.error("(createFolderStorage)exception: {}", ex.getMessage());
      throw new InternalServerError("Unable to create folder containing uploaded files.");
    }
  }

  private String setPath(String subPath) {
    log.info("(setPath)subPath: {}", subPath);
    var path = Paths.get(subPath).toAbsolutePath().normalize().toString();
    if (path.contains(":")) {
      path = "file:\\\\\\" + path + "\\";
    } else {
      path = "file:~\\" + path + "\\";
    }
    log.info("(setPath)path: {}", path);
    return path;
  }
}
