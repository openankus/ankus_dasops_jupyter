package org.ankus.service;

import org.ankus.model.*;
import org.ankus.repository.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StandardTermService {
    @Autowired
    private StandardNameRepository nameRepo;
    @Autowired
    private StandardTermRepository termRepo;
    @Autowired
    private StandardTermCatRepository catRepo;
    @Autowired
    private StandardWordRepository wordRepo;

    @Value("${ankus.home}")
    String ankusHome;

    public List<StandardTermView> termList(String searchCol, String searchWord, String orderCol, boolean asc, int category) {
        PageRequest pr = PageRequest.of(0, 100, asc?
                Sort.by(Sort.Order.asc(orderCol).ignoreCase()) : Sort.by(Sort.Order.desc(orderCol).ignoreCase()));
        //whole
        if (searchWord.isEmpty()) {
            return termRepo.findAllByCategory(category, pr);
//                return termRepo.findAll(
                //                      asc? Sort.by(Sort.Order.asc(orderCol).ignoreCase()) : Sort.by(Sort.Order.desc(orderCol).ignoreCase()))
                //                .stream().map(term->term.toDTO()).collect(Collectors.toList());

//                return termRepo.findAll(
            //                          StandardTermSpecifications.findAllByCat(category),
            //                        asc? Sort.by(Sort.Order.asc(orderCol).ignoreCase()) : Sort.by(Sort.Order.desc(orderCol).ignoreCase()))
            //                  .stream().map(term->term.toDTO()).collect(Collectors.toList());
        }//if : whole

        //search name
        else if (searchCol.equals("name")) {
            return termRepo.findAllByName(searchWord, category, pr);

//            return termRepo.findAll(
//                            StandardTermSpecifications.wordName(searchWord, category),
//                            asc? Sort.by(Sort.Order.asc(orderCol).ignoreCase()) : Sort.by(Sort.Order.desc(orderCol).ignoreCase()))
//                    .stream().map(term->term.toDTO()).collect(Collectors.toList());
        }
        //search english name
        else {
            return termRepo.findAllByEng(searchWord, category, pr);

//            return termRepo.findAll(
//                            StandardTermSpecifications.engName(searchWord, category),
//                            asc? Sort.by(Sort.Order.asc(orderCol).ignoreCase()) : Sort.by(Sort.Order.desc(orderCol).ignoreCase()))
//                    .stream().map(term->term.toDTO()).collect(Collectors.toList());
        }
    }//termList

    public List<StdcatDto> categoryList() {
        return catRepo.findAll(Sort.by(Sort.Order.asc("name").ignoreCase()))
                .stream().map(cat->cat.toDTO()).collect(Collectors.toList());
    }

    public void delCategory(int id) {
        catRepo.deleteById(id);
    }

    public int addCategory(String name) {
        StandardTermCategory cat = new StandardTermCategory();
        cat.setName(name);

        cat = catRepo.save(cat);
        return cat.getId();
    }

    public void editCategory(int id, String name) {
        StandardTermCategory cat = new StandardTermCategory();
        cat.setName(name);
        cat.setId(id);

        catRepo.save(cat);
    }

    public void delWord(List idlist) {
        nameRepo.deleteAllById(idlist);
    }

    public StandardName addWord(StandardTermView word) {
        //find name
        return nameRepo.findOne(StandardTermSpecifications.findName(word.getName(), word.getCategory()))
                //not found
                .orElseGet(() -> {
                    //find word
                    final StandardTerm newWord = wordRepo.findOne(
                            StandardTermSpecifications.findWord(word.getEngName(), word.getCategory())) //find one
                            //not found
                            .orElseGet(() -> {
                                //save word
                                return wordRepo.save(StandardTerm.builder()
                                        .id(0L)
                                        .engName(word.getEngName())
                                        .desc(word.getDesc())
                                        .engDesc(word.getEngDesc())
                                        .categoryId(word.getCategory()).build());
                            });//orElseGet
                    //save name
                    return nameRepo.save(StandardName.builder()
                            .id(0L).name(word.getName()).wordId(newWord.getId()).categoryId(word.getCategory()).build());
                });//orElseGet
    }//add word

    public void saveWord(int category, List<StandardTermView> words) {
        for (StandardTermView w : words) {
            //find abbr
             StandardTerm foundwd = wordRepo.findOne(
                    StandardTermSpecifications.findWord(w.getEngName(), category))
                     //not found
                     .orElseGet(() ->
                            // new word
                             StandardTerm.builder().id(0L)
                                     .categoryId(category)
                                     .engName(w.getEngName()).build());

            foundwd.setDesc(w.getDesc());
            foundwd.setEngDesc(w.getEngDesc());

             //save word
            foundwd = wordRepo.save(foundwd);

            //find name
            StandardName foundnm = nameRepo.findOne(
                    StandardTermSpecifications.findName(w.getName(), category))
                    //not found
                    .orElseGet(() ->
                            //new name
                            StandardName.builder().id(0L)
                                    .categoryId(category)
                                    .name(w.getName()).build());

            foundnm.setWordId(foundwd.getId());
            //save name
            nameRepo.save(foundnm);
        }//for : word list
    } //save word

    public void updateWord(StandardTermView term) {
        //find abbr
        StandardTerm foundwd = wordRepo.findOne(
                        StandardTermSpecifications.findWord(term.getEngName(), term.getCategory()))
                //not found
                .orElseGet(() ->
                        // new word
                        StandardTerm.builder().id(0L)
                                .categoryId(term.getCategory())
                                .engName(term.getEngName()).build());

        foundwd.setDesc(term.getDesc());
        foundwd.setEngDesc(term.getEngDesc());

        //save word
        foundwd = wordRepo.save(foundwd);

        //find name
        StandardName foundnm = nameRepo.findOne(
                        StandardTermSpecifications.findName(term.getName(), term.getCategory()))
                //not found
                .orElseGet(() -> {
                    //find id
                    StandardName nm = nameRepo.findById(term.getNameId()).get();
                    //set name
                    nm.setName(term.getName());
                    return nm;
                });

        foundnm.setWordId(foundwd.getId());
        //save name
        final StandardName ret = nameRepo.save(foundnm);
    } //update word

    public StandardTermView getTermOfName(String name, int category) {
        return termRepo.findOneByName(name, category);
    }

    public StdtrmDto getTermOfEng(String eng, int category) {
        final StandardTerm term = termRepo.findOneByEng(eng, category);

            return (term != null)? term.toDTO():null;
    }

    public boolean importTerms(MultipartHttpServletRequest request, int catid) {
        List<MultipartFile> files = request.getFiles("file");
        if (files == null || files.size() == 0)
            return false;

        MultipartFile mf = files.get(0);
        if (!mf.getOriginalFilename().isEmpty()) {
            File f = new File(ankusHome + File.separator + UUID.randomUUID().toString() + ".csv");

            try {
                mf.transferTo(f);   //throw

                List<StandardTermView> terms = new ArrayList<>();

                //name, abbreviation, english, description
                CSVParser csvp = new CSVParser(new InputStreamReader(new FileInputStream(f), "UTF-8"), CSVFormat.DEFAULT);  //throw
                List<CSVRecord> records = csvp.getRecords();

                for (int i=1; i< records.size(); ++i) {
                    CSVRecord csvr = records.get(i);
                    //check column count
                    if (csvr.size() <4)
                        continue;

                    //name
                    String nm = csvr.get(0);
                    //abbreviation
                    String abbr = csvr.get(1);

                    //check required
                    if (nm.isEmpty() || abbr.isEmpty())
                        continue;

                    //english full name
                    String eng = csvr.get(2);
                    //description
                    String dsc = csvr.get(3);

                    StandardTermView tm = new StandardTermView();
                    tm.setName(nm);
                    tm.setEngName(abbr);
                    tm.setEngDesc(eng);
                    tm.setDesc(dsc);
                    tm.setCategory(catid);
                    terms.add(tm);
                }//for: csv rows

                csvp.close();
                f.delete();

                saveWord(catid, terms);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }//if : check file

        return false;
    }//importTerms
}
