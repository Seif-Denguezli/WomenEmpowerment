package tn.esprit.spring.service.courses;

import java.io.IOException;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import tn.esprit.spring.entities.FileInfo;
import tn.esprit.spring.repository.FileDBRepository;

@Service
public class FileStorageService {
	@Autowired
	  private FileDBRepository fileDBRepository;
	  public FileInfo store(MultipartFile file) throws IOException {
	    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
	    FileInfo FileInfo = new FileInfo(fileName, file.getContentType(), file.getBytes());
	    return fileDBRepository.save(FileInfo);
	  }
	  public FileInfo getFile(String id) {
	    return fileDBRepository.findById(id).get();
	  }
	  
	  public Stream<FileInfo> getAllFiles() {
	    return fileDBRepository.findAll().stream();
	  }
}
