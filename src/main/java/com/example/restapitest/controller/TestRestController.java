package com.example.restapitest.controller;

import com.example.restapitest.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
public class TestRestController {



    @Autowired
    MainService service;

    @PostMapping("/")
    public ResponseEntity getDismantledFile(@RequestParam MultipartFile sendingFile) {
        if (sendingFile.isEmpty()){
           return new ResponseEntity("Файл не должен быть пустым", HttpStatus.OK);
        } else if (sendingFile.getSize()/(1024*1024) > 1) {
            return new ResponseEntity("Размер файла не должен превышать 1мб", HttpStatus.OK);
        } else {
            try {
                return new ResponseEntity("Id итогового файла =  " + service.getDismantledFileNameId(sendingFile)
                        , HttpStatus.OK);
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity( HttpStatus.BAD_REQUEST);
            }
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getFile(@PathVariable int id) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + service.getFileNameById(id))
                .body(new ByteArrayResource(service.getByteArrayFromFile(id)));
    }
}
