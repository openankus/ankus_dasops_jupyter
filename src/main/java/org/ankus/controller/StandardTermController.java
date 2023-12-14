package org.ankus.controller;

import org.ankus.model.StandardName;
import org.ankus.model.StandardTermView;
import org.ankus.model.StdcatDto;
import org.ankus.model.StdtrmDto;
import org.ankus.service.StandardTermService;
import org.ankus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpSession;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@Controller
public class StandardTermController {
    @Autowired
    private StandardTermService standardService;
    @Autowired
    private UserService userService;

    @PostMapping(value = "standard-term/list")
    public ResponseEntity standardTermList(@RequestParam String token, @RequestBody Map<String, Object> option, HttpSession httpSession) {
        //if (JwtTokenProvider.validateToken((String) option.get("token")) == null)
        //  return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        List<StandardTermView> slice = standardService.termList(
                (String) option.get("searchColumn"),
                (String) option.get("searchKeyword"),
                (String) option.get("orderColumn"),
                "asc".equals((String) option.get("order")) ? true : false,
                (Integer) option.get("category"));

        return new ResponseEntity<>(slice, header, HttpStatus.OK);
    }//term list

    @GetMapping(value = "standard-term/category-list")
    public ResponseEntity categoryList(@RequestParam String token, HttpSession httpSession) {
        //if (JwtTokenProvider.validateToken((String) option.get("token")) == null)
        //  return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        List<StdcatDto> slice = standardService.categoryList();

        return new ResponseEntity<>(slice, header, HttpStatus.OK);
    }//term list

    @DeleteMapping(value = "standard-term/del-category")
    public ResponseEntity delCategory(@RequestParam String token, @RequestParam Integer id, HttpSession httpSession) {
        //if (JwtTokenProvider.validateToken((String) option.get("token")) == null)
        //  return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        standardService.delCategory(id);

        return new ResponseEntity<>(standardService.categoryList(), header, HttpStatus.OK);
    }//term list

    @PostMapping(value = "standard-term/add-category")
    public ResponseEntity addCategory(@RequestParam String token, @RequestParam String name, HttpSession httpSession) {
        //if (JwtTokenProvider.validateToken((String) option.get("token")) == null)
        //  return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        standardService.addCategory(name);

        return new ResponseEntity<>(standardService.categoryList(), header, HttpStatus.OK);
    }//term list

    @PutMapping(value = "standard-term/edit-category")
    public ResponseEntity editCategory(@RequestParam String token, @RequestParam Integer id, @RequestParam String name, HttpSession httpSession) {
        //if (JwtTokenProvider.validateToken((String) option.get("token")) == null)
        //  return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        standardService.editCategory(id, name);

        return new ResponseEntity<>(standardService.categoryList(), header, HttpStatus.OK);
    }//term list

    @PostMapping(value = "standard-term/delete-term")
    public ResponseEntity delWord(@RequestParam String token, @RequestBody List<Long> idlist, HttpSession httpSession) {
        //if (JwtTokenProvider.validateToken((String) option.get("token")) == null)
        //  return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        standardService.delWord(idlist);

        return new ResponseEntity<>(HttpStatus.OK);
    }//term list

    @PostMapping(value = "standard-term/add-term")
    public ResponseEntity addWord(@RequestParam String token, @RequestBody StandardTermView word, HttpSession httpSession) {
        //if (JwtTokenProvider.validateToken((String) option.get("token")) == null)
        //  return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        StandardName nm = standardService.addWord(word);
        StandardTermView newWrd = new StandardTermView();
        newWrd.setNameId(nm.getId());
        newWrd.setWordId(nm.getWordId());
        newWrd.setName(nm.getName());
        newWrd.setCategory(nm.getCategoryId());

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<>(newWrd, header, HttpStatus.OK);
    }//term list

    @PostMapping(value = "standard-term/save-term")
    public ResponseEntity saveWord(@RequestParam String token, @RequestParam Integer category, @RequestBody List<StandardTermView> words, HttpSession httpSession) {
        //if (JwtTokenProvider.validateToken((String) option.get("token")) == null)
        //  return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        standardService.saveWord(category, words);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "standard-term/update-term")
    public ResponseEntity updateWord(@RequestParam String token, @RequestBody StandardTermView word, HttpSession httpSession) {
        //if (JwtTokenProvider.validateToken((String) option.get("token")) == null)
        //  return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        standardService.updateWord(word);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "standard-term/find-name")
    public ResponseEntity termOfName(@RequestParam String token, @RequestParam String name, @RequestParam Integer category) {
        StandardTermView term = standardService.getTermOfName(name, category);

        if (term == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

            return new ResponseEntity<>(term, header, HttpStatus.OK);
        }
    }

    @GetMapping(value = "standard-term/find-abbr")
    public ResponseEntity termOfEngName(@RequestParam String token, @RequestParam String engName, @RequestParam Integer category) {
        StdtrmDto term = standardService.getTermOfEng(engName, category);

        if (term == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

            return new ResponseEntity<>(term, header, HttpStatus.OK);
        }
    }//termOfEngName

    @PostMapping(value = "standard-term/import")
    public ResponseEntity importTerms(@RequestParam Integer category, @RequestParam String token, MultipartHttpServletRequest request) {
        if (standardService.importTerms(request, category)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}