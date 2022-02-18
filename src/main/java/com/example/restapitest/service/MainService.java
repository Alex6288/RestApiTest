package com.example.restapitest.service;

import com.example.restapitest.model.TextParagraph;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MainService {

    private Map<Integer, String> fileNamesMap = new HashMap<>();
    private static int fileId = 1;

    /**
     * @param sendingFile файл который необходимо структурировать
     * @return возвращает мапу в которой идет заголок(строка) и тело заголовка(набор строк)
     */
    public int getDismantledFileNameId(MultipartFile sendingFile) throws IOException {
        String resultFileName = getResultFile(getStructureFile(sendingFile), sendingFile.getName()).getName();
        int id = fileId++;
        fileNamesMap.put(id, resultFileName);
        for (int key: fileNamesMap.keySet()){
            if (fileNamesMap.get(key).equals(resultFileName)){
                return key;
            }
        }
        return 0;
    }

    public String getFileNameById(int id) {
        return fileNamesMap.get(id);
    }

    public byte[] getByteArrayFromFile(int id){
        try {
            System.out.println(fileNamesMap.get(id).toString());
            return Files.readAllBytes(Paths.get(fileNamesMap.get(id)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null ;
    }

    private List<TextParagraph> getParagraph(String str){
        List<TextParagraph> resultText = new ArrayList<>();
        int inLevel = 0;
        boolean isFirst = true;
        TextParagraph textParagraph = null;

        for (String line: str.split(System.lineSeparator())) {
            char[] chars = line.toCharArray();
            int i = 0;
            // если строка начинается с символов раздела - то считаем уровень вложености
            while (chars[i] == '#') {
                i++;
            }
            if (i > 0) {
                if (!isFirst) {
                    resultText.add(textParagraph);
                }
                isFirst = false;
                //устанавливаем уровен вложенности
                inLevel = i;
                //создаем новый раздел с заголовком и далее будем добавлять туда строки
                textParagraph = new TextParagraph(line.substring(i, line.length()) ,new ArrayList<>(), inLevel);
            } else {
                if (!isFirst) {
                    textParagraph.addString(line);
                }
            }
        }
        resultText.add(textParagraph);
        return  resultText;
    }

    private List<TextParagraph> getStructureFile(MultipartFile sendingFile) throws IOException {
        byte[] bytes = sendingFile.getBytes();
        return getParagraph(new String(bytes));
    }

    private File getResultFile(List<TextParagraph> textParagraphList, String fileName){
        File file = new File("result" + fileName + ".txt");
        String textParagraph = "";
        String structure = "";
        try (FileWriter fw = new FileWriter(file);){
            for (TextParagraph paragraph: textParagraphList ){
                structure += paragraph.getHeader() + "\n";
                for (int i = 0; i < paragraph.getLevelParagraph(); i++) {
                    textParagraph += "\t";
                }
                textParagraph += paragraph.getHeader() + "\n";

                for (String str: paragraph.getStrings()){
                    for (int i = 0; i < paragraph.getLevelParagraph(); i++) {
                        textParagraph += "\t";
                    }
                    textParagraph += str + "\n";
                }
            }
            Files.write(Path.of(file.getPath()), (structure + "\n" + textParagraph).getBytes());
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
