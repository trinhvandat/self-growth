package org.ptit.okrs.core_upload.configuration;

import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class ResourceWebConfiguration implements WebMvcConfigurer {

  /**
   * pathView is sub path of URI (URI = pathView/fileName ) in which fileName is name of file save
   * in server
   */
  @Value("${application.upload.url.view}")
  private String pathSupportViewFile;

  /** pathFileStorageLocation is path Storage File */
  @Value("${application.upload.path.file.storage}")
  private String pathStorageFile;

  /**
   * support view image from server by URL
   *
   * @param registry
   */
  @Override
  public void addResourceHandlers(final ResourceHandlerRegistry registry) {
    registry.addResourceHandler(pathSupportViewFile).addResourceLocations(setPath(pathStorageFile));
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
